package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.github.minecraftschurli.codeclib.CodecPacket;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.*;
import java.util.function.Function;

public final class SkillHelper implements ISkillHelper {
    private static final Lazy<SkillHelper> INSTANCE = Lazy.concurrentOf(SkillHelper::new);

    private static final CodecPacket.CodecPacketFactory<KnowledgeHolder> SYNC_PACKET = CodecPacket.create(
            KnowledgeHolder.CODEC,
            SkillHelper::handleSync);

    public static SkillHelper instance() {
        return INSTANCE.get();
    }

    private static final Capability<KnowledgeHolder> KNOWLEDGE = CapabilityManager.get(new CapabilityToken<>() {});

    public KnowledgeHolder getKnowledgeHolder(Player player) {
        return player.getCapability(KNOWLEDGE)
                     .orElseThrow(() -> new RuntimeException("Could not retrieve skill capability for player %s{%s}".formatted(
                             player.getDisplayName()
                                   .getString(),
                             player.getUUID())));
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
        syncToPlayer(player);
    }

    @Override
    public void learn(Player player, ISkill skill) {
        getKnowledgeHolder(player).learn(skill);
        syncToPlayer(player);
    }

    @Override
    public void forget(Player player, ResourceLocation skill) {
        getKnowledgeHolder(player).forget(skill);
        syncToPlayer(player);
    }

    @Override
    public void forget(Player player, ISkill skill) {
        getKnowledgeHolder(player).forget(skill);
        syncToPlayer(player);
    }

    @Override
    public void forgetAll(Player player) {
        getKnowledgeHolder(player).forgetAll();
        syncToPlayer(player);
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
    public void addSkillPoint(Player player, ResourceLocation skillPoint, int amount) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint, amount);
        syncToPlayer(player);
    }

    @Override
    public void addSkillPoint(Player player, ISkillPoint skillPoint, int amount) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint, amount);
        syncToPlayer(player);
    }

    @Override
    public void addSkillPoint(Player player, ResourceLocation skillPoint) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint);
        syncToPlayer(player);
    }

    @Override
    public void addSkillPoint(Player player, ISkillPoint skillPoint) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint);
        syncToPlayer(player);
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceLocation skillPoint, int amount) {
        var success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint, amount);
        syncToPlayer(player);
        return success;
    }

    @Override
    public boolean consumeSkillPoint(Player player, ISkillPoint skillPoint, int amount) {
        var success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint, amount);
        syncToPlayer(player);
        return success;
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceLocation skillPoint) {
        var success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint);
        syncToPlayer(player);
        return success;
    }

    @Override
    public boolean consumeSkillPoint(Player player, ISkillPoint skillPoint) {
        var success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint);
        syncToPlayer(player);
        return success;
    }

    @Override
    public Collection<ResourceLocation> getKnownSkills(Player player) {
        return getKnowledgeHolder(player).skills();
    }

    public static Capability<KnowledgeHolder> getCapability() {
        return KNOWLEDGE;
    }

    public void syncToPlayer(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(SYNC_PACKET.create(getKnowledgeHolder(player)), player);
    }

    private static void handleSync(KnowledgeHolder knowledgeHolder, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(KNOWLEDGE)
                                                                .ifPresent(holder -> holder.onSync(knowledgeHolder)));
    }

    public record KnowledgeHolder(Set<ResourceLocation> skills, Map<ResourceLocation, Integer> skillPoints) {
        //@formatter:off
        public static final Codec<KnowledgeHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                        CodecHelper.setOf(ResourceLocation.CODEC)
                                .fieldOf("skills")
                                .forGetter(KnowledgeHolder::skills),
                        CodecHelper.mapOf(ResourceLocation.CODEC, Codec.INT)
                                .fieldOf("skill_points")
                                .forGetter(KnowledgeHolder::skillPoints)
                ).apply(inst, KnowledgeHolder::new));
        //@formatter:on

        public static KnowledgeHolder empty() {
            return new KnowledgeHolder(new HashSet<>(), new HashMap<>());
        }

        public synchronized boolean canLearn(ResourceLocation skill) {
            return skills.containsAll(ArsMagicaAPI.get()
                                                  .getSkillManager()
                                                  .get(skill)
                                                  .getParents());
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
            if (!skillPoints.containsKey(skillPoint)) {
                return false;
            }
            int amt = skillPoints.get(skillPoint);
            if (amt < amount) {
                return false;
            }
            skillPoints.put(skillPoint, amt - amount);
            return true;
        }

        public void forgetAll() {
            skills.clear();
        }

        @Override
        public synchronized Set<ResourceLocation> skills() {
            return Collections.unmodifiableSet(skills);
        }

        @Override
        public synchronized Map<ResourceLocation, Integer> skillPoints() {
            return Collections.unmodifiableMap(skillPoints);
        }

        public void onSync(KnowledgeHolder knowledgeHolder) {
            this.skills.clear();
            this.skills.addAll(knowledgeHolder.skills());
            this.skillPoints.clear();
            this.skillPoints.putAll(knowledgeHolder.skillPoints());
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
