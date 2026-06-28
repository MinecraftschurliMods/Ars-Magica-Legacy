package com.github.minecraftschurlimods.arsmagicalegacy.common.apiimpl;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.AffinityChangeEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.LevelChangeEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.MagicAttachment;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttachments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriterionTriggers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMagic;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;
import java.util.Map;
import java.util.Set;

final class MagicHelperImpl implements MagicHelper {
    @Override
    public int getLevel(Player player) {
        return player.getData(AMAttachments.MAGIC).level();
    }

    @Override
    public double getXp(Player player) {
        return player.getData(AMAttachments.MAGIC).xp();
    }

    @Override
    public double getXpForNextLevel(int level) {
        return level <= 0 ? 0 : AMServerConfig.LEVEL_MULTIPLIER.get() * Math.pow(AMServerConfig.LEVEL_BASE.get(), level - 1);
    }

    @Override
    public void addLevel(Player player, int level) {
        setLevel(player, getLevel(player) + level);
    }

    @Override
    public void setLevel(Player player, int level) {
        level = Math.max(0, level);
        MagicAttachment data = player.getData(AMAttachments.MAGIC);
        int oldLevel = data.level();
        NeoForge.EVENT_BUS.post(new LevelChangeEvent(player, oldLevel, level));
        if (player instanceof ServerPlayer serverPlayer) {
            AMCriterionTriggers.LEVEL_CHANGE.get().trigger(serverPlayer, level);
        }
        player.setData(AMAttachments.MAGIC, data.setLevel(level));
        List<? extends Holder<SkillPoint>> skillPoints = AMRegistries.skillPoints(player.registryAccess()).listElements().toList();
        for (int i = oldLevel + 1; i <= level; i++) {
            for (Holder<SkillPoint> holder : skillPoints) {
                SkillPoint skillPoint = holder.value();
                int minEarnLevel = skillPoint.minEarnLevel();
                int levelsForPoint = skillPoint.levelsForPoint();
                if (minEarnLevel >= 0 && levelsForPoint >= 0 && i >= minEarnLevel && (i - minEarnLevel) % levelsForPoint == 0) {
                    addSkillPoint(player, holder);
                }
            }
        }
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        double oldMaxMana = manaHelper.getMaxMana(player);
        double newMaxMana = manaHelper.getManaBase() + manaHelper.getManaMultiplier() * (level - 1);
        manaHelper.setMaxMana(player, newMaxMana);
        manaHelper.increaseMana(player, newMaxMana - oldMaxMana);
        manaHelper.setManaRegeneration(player, newMaxMana * manaHelper.getManaRegenerationMultiplier());
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        double oldMaxBurnout = burnoutHelper.getMaxBurnout(player);
        double newMaxBurnout = burnoutHelper.getBurnoutBase() + burnoutHelper.getBurnoutMultiplier() * (level - 1);
        burnoutHelper.setMaxBurnout(player, newMaxBurnout);
        burnoutHelper.decreaseBurnout(player, newMaxBurnout - oldMaxBurnout);
        burnoutHelper.setBurnoutRegeneration(player, burnoutHelper.getBurnoutBase() * burnoutHelper.getBurnoutRegenerationMultiplier());
        player.level().playSound(null, player, AMSounds.LEVEL_UP.get(), SoundSource.PLAYERS, 1, 1);
    }

    @Override
    public void addXp(Player player, double xp) {
        setXp(player, getXp(player) + xp);
    }

    @Override
    public void setXp(Player player, double xp) {
        xp = Math.max(0, xp);
        int level = getLevel(player);
        double xpForNextLevel = getXpForNextLevel(level);
        while (xp >= xpForNextLevel) {
            xp -= xpForNextLevel;
            level++;
            xpForNextLevel = getXpForNextLevel(level);
        }
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).setXp(xp));
        if (level > getLevel(player)) {
            setLevel(player, level);
        }
    }

    @Override
    public boolean knowsMagic(Player player) {
        return getLevel(player) > 0 || AMServerConfig.MAGIC_ADVANCEMENT.get().isEmpty() || player.isCreative() || player.isSpectator();
    }

    @Override
    public void initiateMagic(Player player) {
        setLevel(player, Math.max(1, getLevel(player)));
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        manaHelper.setMana(player, manaHelper.getMaxMana(player));
        AMRegistries.skillPoints(player.registryAccess())
            .get(AMMagic.BLUE_POINT)
            .ifPresent(skillPoint -> addSkillPoint(player, skillPoint, AMServerConfig.EXTRA_SKILL_POINTS.get()));
    }

    @Override
    public boolean knows(Player player, Holder<Skill> skill) {
        return player.getData(AMAttachments.MAGIC).skills().contains(skill);
    }

    @Override
    public boolean canLearn(Player player, Holder<Skill> skill) {
        if (knows(player, skill)) return false;
        MagicAttachment data = player.getData(AMAttachments.MAGIC);
        boolean hasSkillPoints = skill
            .value()
            .cost()
            .filter(e -> getSkillPoint(player, e) > 0)
            .isPresent();
        boolean hasParents = data.skills().containsAll(skill.value().parents());
        return hasSkillPoints && hasParents;
    }

    @Override
    public List<? extends Holder<Skill>> getKnown(Player player) {
        return AMRegistries.skills(player.registryAccess())
            .listElements()
            .filter(holder -> knows(player, holder))
            .toList();
    }

    @Override
    public List<? extends Holder<Skill>> getUnknown(Player player) {
        return AMRegistries.skills(player.registryAccess())
            .listElements()
            .filter(holder -> !knows(player, holder))
            .toList();
    }

    @Override
    public void learn(Player player, Holder<Skill> skill) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkills(set -> set.add(skill)));
        if (player instanceof ServerPlayer serverPlayer) {
            AMCriterionTriggers.SKILL_CHANGE.get().trigger(serverPlayer);
        }
    }

    @Override
    public void forget(Player player, Holder<Skill> skill) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkills(set -> set.remove(skill)));
        if (player instanceof ServerPlayer serverPlayer) {
            AMCriterionTriggers.SKILL_CHANGE.get().trigger(serverPlayer);
        }
    }

    @Override
    public void learnAll(Player player) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkills(set -> set.addAll(AMRegistries.skills(false).listElements().toList())));
        if (player instanceof ServerPlayer serverPlayer) {
            AMCriterionTriggers.SKILL_CHANGE.get().trigger(serverPlayer);
        }
    }

    @Override
    public void forgetAll(Player player) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkills(Set::clear));
        if (player instanceof ServerPlayer serverPlayer) {
            AMCriterionTriggers.SKILL_CHANGE.get().trigger(serverPlayer);
        }
    }

    @Override
    public int getSkillPoint(Player player, Holder<SkillPoint> skillPoint) {
        return player.getData(AMAttachments.MAGIC).skillPoints().getOrDefault(skillPoint, 0);
    }

    @Override
    public void addSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkillPoints(map -> map.compute(skillPoint, (k, v) -> v == null ? Math.max(0, amount) : Math.max(0, v + amount))));
    }

    @Override
    public void addSkillPoint(Player player, Holder<SkillPoint> skillPoint) {
        addSkillPoint(player, skillPoint, 1);
    }

    @Override
    public void setSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkillPoints(map -> map.put(skillPoint, Math.max(0, amount))));
    }

    @Override
    public double getAffinityDepth(Player player, Holder<Affinity> affinity) {
        return affinity.is(Affinity.NONE) ? 0 : player.getData(AMAttachments.MAGIC).affinityShifts().getOrDefault(affinity, 0.);
    }

    @Override
    public void setAffinityDepth(Player player, Holder<Affinity> affinity, double depth) {
        setAffinityDepth(player, affinity, depth, false, false);
    }

    @Override
    public void setAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities) {
        setAffinityDepth(player, affinities, false, false);
    }

    @Override
    public void setAffinityDepth(Player player, Holder<Affinity> affinity, double depth, boolean bypassLocks, boolean commandSource) {
        setAffinityDepth(player, Map.of(affinity, depth), bypassLocks, commandSource);
    }

    @Override
    public void setAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities, boolean bypassLocks, boolean commandSource) {
        modifyAffinities(player, affinities, (data, affinity, depth) -> affinity.is(Affinity.NONE) ? data : data.updateAffinityShifts(map -> map.put(affinity, Math.clamp(depth, 0, 1))), bypassLocks, commandSource);
    }

    @Override
    public void addAffinityDepth(Player player, Holder<Affinity> affinity, double depth) {
        addAffinityDepth(player, affinity, depth, false, false);
    }

    @Override
    public void addAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities) {
        addAffinityDepth(player, affinities, false, false);
    }

    @Override
    public void addAffinityDepth(Player player, Holder<Affinity> affinity, double depth, boolean bypassLocks, boolean commandSource) {
        addAffinityDepth(player, Map.of(affinity, depth), bypassLocks, commandSource);
    }

    @Override
    public void addAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities, boolean bypassLocks, boolean commandSource) {
        modifyAffinities(player, affinities, this::addAffinityDepth, bypassLocks, commandSource);
    }

    @Override
    public void applyAffinityShift(Player player, Holder<Affinity> affinity, double shift) {
        applyAffinityShift(player, affinity, shift, false, false);
    }

    @Override
    public void applyAffinityShift(Player player, Map<Holder<Affinity>, Double> affinityShifts) {
        applyAffinityShift(player, affinityShifts, false, false);
    }

    @Override
    public void applyAffinityShift(Player player, Holder<Affinity> affinity, double shift, boolean bypassLocks, boolean commandSource) {
        applyAffinityShift(player, Map.of(affinity, shift), bypassLocks, commandSource);
    }

    @Override
    public void applyAffinityShift(Player player, Map<Holder<Affinity>, Double> affinityShifts, boolean bypassLocks, boolean commandSource) {
        modifyAffinities(player, affinityShifts, (data, affinity, shift) -> {
            Affinity value = affinity.value();
            double direct = shift * AMServerConfig.DIRECT_OPPOSITE_MULTIPLIER.get();
            double major = shift * AMServerConfig.MAJOR_OPPOSITE_MULTIPLIER.get();
            double minor = shift * AMServerConfig.MINOR_OPPOSITE_MULTIPLIER.get();
            double adjacent = shift * AMServerConfig.ADJACENT_MULTIPLIER.get();
            data = addAffinityDepth(data, affinity, shift);
            data = addAffinityDepth(data, value.directOpposite(), -direct);
            for (Holder<Affinity> holder : value.majorOpposites()) {
                data = addAffinityDepth(data, holder, -major);
            }
            for (Holder<Affinity> holder : value.minorOpposites()) {
                data = addAffinityDepth(data, holder, -minor);
            }
            for (Holder<Affinity> holder : value.adjacents()) {
                data = addAffinityDepth(data, holder, adjacent);
            }
            return data;
        }, bypassLocks, commandSource);
    }

    @Override
    public void lockAffinities(Player player) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).setAffinityLocked(true));
    }

    @Override
    public void unlockAffinities(Player player) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).setAffinityLocked(false));
    }

    @Override
    public void updateAffinityLock(Player player) {
        MagicAttachment data = player.getData(AMAttachments.MAGIC);
        player.setData(AMAttachments.MAGIC, data.setAffinityLocked(data.affinityShifts().values().stream().anyMatch(e -> e >= 1)));
    }

    private void modifyAffinities(Player player, Map<Holder<Affinity>, Double> affinities, TriFunction<MagicAttachment, Holder<Affinity>, Double, MagicAttachment> operator, boolean bypassLocks, boolean commandSource) {
        AffinityChangeEvent.Pre event = NeoForge.EVENT_BUS.post(new AffinityChangeEvent.Pre(player, affinities, bypassLocks, commandSource));
        if (event.isCanceled()) return;
        MagicAttachment data = player.getData(AMAttachments.MAGIC);
        if (data.affinityLocked() && !event.isBypassLocks()) return;
        MagicAttachment originalData = data;
        for (Map.Entry<Holder<Affinity>, Double> entry : event.getAffinityShifts().entrySet()) {
            Holder<Affinity> affinity = entry.getKey();
            if (affinity.is(Affinity.NONE)) continue;
            double value = entry.getValue();
            data = operator.apply(data, affinity, value);
            double originalValue = originalData.affinityShifts().getOrDefault(affinity, 0.);
            if (originalValue != value && player instanceof ServerPlayer serverPlayer) {
                AMCriterionTriggers.AFFINITY_CHANGE.get().trigger(serverPlayer, affinity, originalValue, value);
            }
        }
        player.setData(AMAttachments.MAGIC, data);
        updateAffinityLock(player);
        ArsMagicaApi.abilityHelper().onMagicChange(player, originalData, data);
        NeoForge.EVENT_BUS.post(new AffinityChangeEvent.Post(player, event.getAffinityShifts(), event.isBypassLocks(), commandSource));
    }

    private MagicAttachment addAffinityDepth(MagicAttachment data, Holder<Affinity> affinity, double depth) {
        return affinity.is(Affinity.NONE) ? data : data.updateAffinityShifts(map -> map.put(affinity, Math.clamp(depth + data.affinityShifts().getOrDefault(affinity, 0.), 0, 1)));
    }
}
