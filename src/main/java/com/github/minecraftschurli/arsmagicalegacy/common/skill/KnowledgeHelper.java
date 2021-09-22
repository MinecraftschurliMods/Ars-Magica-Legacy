package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IKnowledgeHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IKnowledgeHolder;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
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

    @CapabilityInject(IKnowledgeHolder.class)
    private static Capability<IKnowledgeHolder> KNOWLEDGE;

    @Override
    public IKnowledgeHolder getKnowledgeHolder(Player player) {
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
    }

    @Override
    public void learn(Player player, ISkill skill) {
        getKnowledgeHolder(player).learn(skill);
    }

    @Override
    public void forget(Player player, ResourceLocation skill) {
        getKnowledgeHolder(player).forget(skill);
    }

    @Override
    public void forget(Player player, ISkill skill) {
        getKnowledgeHolder(player).forget(skill);
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
    }

    @Override
    public void addSkillPoint(Player player, ISkillPoint skillPoint, int amount) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint, amount);
    }

    @Override
    public void addSkillPoint(Player player, ResourceLocation skillPoint) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint);
    }

    @Override
    public void addSkillPoint(Player player, ISkillPoint skillPoint) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint);
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceLocation skillPoint, int amount) {
        return getKnowledgeHolder(player).consumeSkillPoint(skillPoint, amount);
    }

    @Override
    public boolean consumeSkillPoint(Player player, ISkillPoint skillPoint, int amount) {
        return getKnowledgeHolder(player).consumeSkillPoint(skillPoint, amount);
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceLocation skillPoint) {
        return getKnowledgeHolder(player).consumeSkillPoint(skillPoint);
    }

    @Override
    public boolean consumeSkillPoint(Player player, ISkillPoint skillPoint) {
        return getKnowledgeHolder(player).consumeSkillPoint(skillPoint);
    }

    @Override
    public float getXp(Player player) {
        return getKnowledgeHolder(player).xp();
    }

    public static Capability<IKnowledgeHolder> getCapability() {
        return KNOWLEDGE;
    }

    public static class KnowledgeHolderProvider implements ICapabilitySerializable<Tag> {
        private LazyOptional<IKnowledgeHolder> lazy = LazyOptional.of(KnowledgeHelper.KnowledgeHolder::empty);

        @Nullable
        @Override
        public Tag serializeNBT() {
            return lazy.lazyMap(knowledgeHolder -> KnowledgeHelper.KnowledgeHolder.CODEC.encodeStart(NbtOps.INSTANCE, ((KnowledgeHelper.KnowledgeHolder)knowledgeHolder)).result())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .orElse(null);
        }

        @Override
        public void deserializeNBT(final Tag nbt) {
            lazy = KnowledgeHelper.KnowledgeHolder.CODEC
                    .decode(NbtOps.INSTANCE, nbt)
                    .get()
                    .mapLeft(Pair::getFirst)
                    .mapLeft(knowledgeHolder -> (NonNullSupplier<IKnowledgeHolder>)(() -> knowledgeHolder))
                    .map(LazyOptional::of, pairPartialResult -> LazyOptional.empty());
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull final Capability<T> cap, @Nullable final Direction side) {
            return KnowledgeHelper.getCapability().orEmpty(cap, lazy);
        }
    }

    public record KnowledgeHolder(Set<ResourceLocation> skills, Map<ResourceLocation, Integer> skillPoints, int level, float xp) implements IKnowledgeHolder {
        public static final Codec<KnowledgeHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ResourceLocation.CODEC.listOf().xmap((Function<List<ResourceLocation>, Set<ResourceLocation>>) HashSet::new, (Function<Set<ResourceLocation>, List<ResourceLocation>>) ArrayList::new).fieldOf("skills").forGetter(KnowledgeHolder::skills),
                Codec.compoundList(ResourceLocation.CODEC, Codec.INT).xmap(pairs -> pairs.stream().collect(Pair.toMap()), map -> map.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue())).toList()).fieldOf("skill_points").forGetter(KnowledgeHolder::skillPoints),
                Codec.INT.fieldOf("level").forGetter(KnowledgeHolder::level),
                Codec.FLOAT.fieldOf("xp").forGetter(KnowledgeHolder::xp)
        ).apply(inst, KnowledgeHolder::new));

        public static KnowledgeHolder empty() {
            return new KnowledgeHolder(new HashSet<>(), new HashMap<>(), -1, 0);
        }

        @Override
        public synchronized boolean knows(ResourceLocation skill) {
            return skills.contains(skill);
        }

        @Override
        public synchronized boolean canLearn(ResourceLocation skill) {
            return skills.containsAll(ArsMagicaAPI.get().getSkillManager().get(skill).getParents());
        }

        @Override
        public synchronized void learn(ResourceLocation skill) {
            skills.add(skill);
        }

        @Override
        public synchronized void forget(ResourceLocation skill) {
            skills.remove(skill);
        }

        @Override
        public synchronized int getSkillPoint(ResourceLocation skillPoint) {
            return skillPoints().get(skillPoint);
        }

        @Override
        public synchronized void addSkillPoint(ResourceLocation skillPoint, int amount) {
            skillPoints.putIfAbsent(skillPoint, 0);
            int amt = skillPoints.get(skillPoint);
            skillPoints.put(skillPoint, amt + amount);
        }

        @Override
        public synchronized boolean consumeSkillPoint(ResourceLocation skillPoint, int amount) {
            if (!skillPoints.containsKey(skillPoint)) return false;
            int amt = skillPoints.get(skillPoint);
            if (amt < amount) return false;
            skillPoints.put(skillPoint, amt - amount);
            return true;
        }

        @Override
        public synchronized Set<ResourceLocation> skills() {
            return Collections.unmodifiableSet(skills);
        }

        @Override
        public synchronized Map<ResourceLocation, Integer> skillPoints() {
            return Collections.unmodifiableMap(skillPoints);
        }
    }
}
