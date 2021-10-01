package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IKnowledgeHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurli.arsmagicalegacy.network.SyncKnowledgePacket;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public final class KnowledgeHelper implements IKnowledgeHelper {
    private static final Lazy<KnowledgeHelper> INSTANCE = Lazy.concurrentOf(KnowledgeHelper::new);

    public static KnowledgeHelper instance() {
        return INSTANCE.get();
    }

    private static final Capability<KnowledgeHolder> KNOWLEDGE = CapabilityManager.get(new CapabilityToken<>() {});

    public KnowledgeHolder getKnowledgeHolder(Player player) {
        return player.getCapability(KNOWLEDGE).orElseThrow(() -> new RuntimeException("Could not retrieve skill capability for player %s".formatted(player.getUUID())));
    }

    @Override
    public boolean knows(Player player, ResourceLocation skill) {
        return getKnowledgeHolder(player).knows(skill);
    }

    @Override
    public boolean knows(Player player, ISkill skill) {
        return getKnowledgeHolder(player).knows(skill);
    }

    @Override
    public boolean canLearn(Player player, ResourceLocation skill) {
        return getKnowledgeHolder(player).canLearn(skill);
    }

    @Override
    public boolean canLearn(Player player, ISkill skill) {
        return getKnowledgeHolder(player).canLearn(skill);
    }

    @Override
    public void learn(Player player, ResourceLocation skill) {
        getKnowledgeHolder(player).learn(skill);
        onChanged(player);
    }

    @Override
    public void learn(Player player, ISkill skill) {
        getKnowledgeHolder(player).learn(skill);
        onChanged(player);
    }

    @Override
    public void forget(Player player, ResourceLocation skill) {
        getKnowledgeHolder(player).forget(skill);
        onChanged(player);
    }

    @Override
    public void forget(Player player, ISkill skill) {
        getKnowledgeHolder(player).forget(skill);
        onChanged(player);
    }

    @Override
    public void forgetAll(Player player) {
        getKnowledgeHolder(player).forgetAll();
        onChanged(player);
    }

    @Override
    public int getSkillPoint(Player player, ResourceLocation point) {
        return getKnowledgeHolder(player).getSkillPoint(point);
    }

    @Override
    public int getSkillPoint(Player player, ISkillPoint point) {
        return getKnowledgeHolder(player).getSkillPoint(point);
    }

    @Override
    public int getCurrentLevel(Player player) {
        return getKnowledgeHolder(player).level();
    }

    @Override
    public void addSkillPoint(Player player, ResourceLocation skillPoint, int amount) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint, amount);
        onChanged(player);
    }

    @Override
    public void addSkillPoint(Player player, ISkillPoint skillPoint, int amount) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint, amount);
        onChanged(player);
    }

    @Override
    public void addSkillPoint(Player player, ResourceLocation skillPoint) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint);
        onChanged(player);
    }

    @Override
    public void addSkillPoint(Player player, ISkillPoint skillPoint) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint);
        onChanged(player);
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceLocation skillPoint, int amount) {
        var success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint, amount);
        onChanged(player);
        return success;
    }

    @Override
    public boolean consumeSkillPoint(Player player, ISkillPoint skillPoint, int amount) {
        var success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint, amount);
        onChanged(player);
        return success;
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceLocation skillPoint) {
        var success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint);
        onChanged(player);
        return success;
    }

    @Override
    public boolean consumeSkillPoint(Player player, ISkillPoint skillPoint) {
        var success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint);
        onChanged(player);
        return success;
    }

    private void onChanged(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new SyncKnowledgePacket(getKnowledgeHolder(player)), player);
    }

    @Override
    public float getXp(Player player) {
        return getKnowledgeHolder(player).xp();
    }

    @Override
    public Collection<ResourceLocation> getKnownSkills(Player player) {
        return getKnowledgeHolder(player).skills();
    }

    public static Capability<KnowledgeHolder> getCapability() {
        return KNOWLEDGE;
    }

    public static class KnowledgeHolderProvider implements ICapabilitySerializable<Tag> {
        private LazyOptional<KnowledgeHolder> lazy = LazyOptional.of(KnowledgeHelper.KnowledgeHolder::empty);

        @Nullable
        @Override
        public Tag serializeNBT() {
            return lazy.lazyMap(knowledgeHolder -> KnowledgeHolder.CODEC.encodeStart(NbtOps.INSTANCE, knowledgeHolder).result())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .orElse(null);
        }

        @Override
        public void deserializeNBT(final Tag nbt) {
            lazy = KnowledgeHolder.CODEC
                    .decode(NbtOps.INSTANCE, nbt)
                    .get()
                    .mapLeft(Pair::getFirst)
                    .mapLeft(knowledgeHolder -> (NonNullSupplier<KnowledgeHolder>)(() -> knowledgeHolder))
                    .map(LazyOptional::of, pairPartialResult -> LazyOptional.empty());
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull final Capability<T> cap, @Nullable final Direction side) {
            return KnowledgeHelper.getCapability().orEmpty(cap, lazy);
        }
    }

    public static final class KnowledgeHolder {
        public static final Codec<KnowledgeHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ResourceLocation.CODEC.listOf().xmap((Function<List<ResourceLocation>, Set<ResourceLocation>>) HashSet::new, (Function<Set<ResourceLocation>, List<ResourceLocation>>) ArrayList::new).fieldOf("skills").forGetter(KnowledgeHolder::skills),
                Codec.compoundList(ResourceLocation.CODEC, Codec.INT).xmap(pairs -> pairs.stream().collect(Pair.toMap()), map -> map.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue())).toList()).fieldOf("skill_points").forGetter(KnowledgeHolder::skillPoints),
                Codec.INT.fieldOf("level").forGetter(KnowledgeHolder::level),
                Codec.FLOAT.fieldOf("xp").forGetter(KnowledgeHolder::xp)
        ).apply(inst, KnowledgeHolder::new));

        private final Set<ResourceLocation> skills;
        private final Map<ResourceLocation, Integer> skillPoints;
        private int level;
        private float xp;

        public KnowledgeHolder(Set<ResourceLocation> skills, Map<ResourceLocation, Integer> skillPoints, int level, float xp) {
            this.skills = skills;
            this.skillPoints = skillPoints;
            this.level = level;
            this.xp = xp;
        }

        public static KnowledgeHolder empty() {
            return new KnowledgeHolder(new HashSet<>(), new HashMap<>(), -1, 0);
        }

        public synchronized boolean canLearn(ResourceLocation skill) {
            return skills.containsAll(ArsMagicaAPI.get().getSkillManager().get(skill).getParents());
        }

        public synchronized void learn(ResourceLocation skill) {
            skills.add(skill);
        }

        public synchronized void forget(ResourceLocation skill) {
            skills.remove(skill);
        }

        public synchronized void addSkillPoint(ResourceLocation skillPoint, int amount) {
            skillPoints.putIfAbsent(skillPoint, 0);
            int amt = skillPoints.get(skillPoint);
            skillPoints.put(skillPoint, amt + amount);
        }

        public synchronized boolean consumeSkillPoint(ResourceLocation skillPoint, int amount) {
            if (!skillPoints.containsKey(skillPoint)) return false;
            int amt = skillPoints.get(skillPoint);
            if (amt < amount) return false;
            skillPoints.put(skillPoint, amt - amount);
            return true;
        }

        public void forgetAll() {
            skills.clear();
        }

        public synchronized Set<ResourceLocation> skills() {
            return Collections.unmodifiableSet(skills);
        }

        public synchronized Map<ResourceLocation, Integer> skillPoints() {
            return Collections.unmodifiableMap(skillPoints);
        }

        public int level() {
            return level;
        }

        public float xp() {
            return xp;
        }

        public void onSync(KnowledgeHolder knowledgeHolder) {
            this.skills.clear();
            this.skills.addAll(knowledgeHolder.skills());
            this.skillPoints.clear();
            this.skillPoints.putAll(knowledgeHolder.skillPoints());
            this.level = knowledgeHolder.level();
            this.xp = knowledgeHolder.xp();
        }

        public boolean knows(ResourceLocation skill) {
            return skills().contains(skill);
        }

        public boolean knows(ISkill skill) {
            return knows(skill.getId());
        }

        public boolean canLearn(ISkill skill) {
            return canLearn(skill.getId());
        }

        public void learn(ISkill skill) {
            learn(skill.getId());
        }

        public void forget(ISkill skill) {
            forget(skill.getId());
        }

        public int getSkillPoint(ResourceLocation skillPoint) {
            return skillPoints().getOrDefault(skillPoint, 0);
        }

        public int getSkillPoint(ISkillPoint skillPoint) {
            return getSkillPoint(skillPoint.getId());
        }

        public void addSkillPoint(ISkillPoint skillPoint, int amount) {
            addSkillPoint(skillPoint.getId(), amount);
        }

        public boolean consumeSkillPoint(ISkillPoint skillPoint, int amount) {
            return consumeSkillPoint(skillPoint.getId(), amount);
        }

        public void addSkillPoint(ISkillPoint skillPoint) {
            addSkillPoint(skillPoint, 1);
        }

        public boolean consumeSkillPoint(ISkillPoint skillPoint) {
            return consumeSkillPoint(skillPoint, 1);
        }

        public void addSkillPoint(ResourceLocation skillPoint) {
            addSkillPoint(skillPoint, 1);
        }

        public boolean consumeSkillPoint(ResourceLocation skillPoint) {
            return consumeSkillPoint(skillPoint, 1);
        }
    }
}
