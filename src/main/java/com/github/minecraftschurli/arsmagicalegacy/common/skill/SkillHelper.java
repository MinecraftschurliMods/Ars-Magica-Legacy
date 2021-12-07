package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.github.minecraftschurli.simplenetlib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SkillHelper implements ISkillHelper {
    private static final Lazy<SkillHelper> INSTANCE = Lazy.concurrentOf(SkillHelper::new);
    private static final Capability<KnowledgeHolder> KNOWLEDGE = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static final class SyncPacket extends CodecPacket<KnowledgeHolder> {

        public SyncPacket(KnowledgeHolder data) {
            super(data);
        }

        public SyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            SkillHelper.handleSync(this.data, context);
        }

        @Override
        protected Codec<KnowledgeHolder> getCodec() {
            return KnowledgeHolder.CODEC;
        }

    }

    /**
     * @return The only instance of this class.
     */
    public static SkillHelper instance() {
        return INSTANCE.get();
    }

    /**
     * Registers the network packets to the network handler.
     */
    public static void init() {
        ArsMagicaLegacy.NETWORK_HANDLER.register(SyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
    }

    /**
     * Returns the knowledge capability for the given player.
     *
     * @param player The player to get the player capability for.
     * @return The knowledge capability for the given player.
     */
    public KnowledgeHolder getKnowledgeHolder(Player player) {
        return player.getCapability(KNOWLEDGE).orElseThrow(() -> new RuntimeException("Could not retrieve skill capability for player %s{%s}".formatted(player.getDisplayName().getString(), player.getUUID())));
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

    /**
     * @return The default capability.
     */
    public static Capability<KnowledgeHolder> getCapability() {
        return KNOWLEDGE;
    }

    /**
     * Called on player death, syncs the capabilites.
     *
     * @param original The old player from the event.
     * @param player   The new player from the event.
     */
    public void syncOnDeath(Player original, Player player) {
        original.getCapability(KNOWLEDGE).ifPresent(knowledgeHolder -> player.getCapability(KNOWLEDGE).ifPresent(holder -> holder.onSync(knowledgeHolder)));
    }

    /**
     * Called on player join, syncs the capablities.
     *
     * @param player The player from the event.
     */
    public void syncToPlayer(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new SyncPacket(getKnowledgeHolder(player)), player);
    }

    private static void handleSync(KnowledgeHolder knowledgeHolder, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(KNOWLEDGE)
                .ifPresent(holder -> holder.onSync(knowledgeHolder)));
    }

    public record KnowledgeHolder(Set<ResourceLocation> skills, Map<ResourceLocation, Integer> skillPoints) {
        //@formatter:off
        public static final Codec<KnowledgeHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                CodecHelper.setOf(ResourceLocation.CODEC).fieldOf("skills").forGetter(KnowledgeHolder::skills),
                CodecHelper.mapOf(ResourceLocation.CODEC, Codec.INT).fieldOf("skill_points").forGetter(KnowledgeHolder::skillPoints)
        ).apply(inst, KnowledgeHolder::new));
        //@formatter:on

        /**
         * @return A new empty KnowledgeHolder.
         */
        public static KnowledgeHolder empty() {
            return new KnowledgeHolder(new HashSet<>(), new HashMap<>());
        }

        /**
         * @param skill The skill to check this for.
         * @return Whether the skill can be learned or not.
         */
        public synchronized boolean canLearn(ResourceLocation skill) {
            return skills.containsAll(ArsMagicaAPI.get().getSkillManager().get(skill).getParents());
        }

        /**
         * Adds a skill to the known skills list.
         *
         * @param skill The skill to add.
         */
        public synchronized void learn(ResourceLocation skill) {
            skills.add(skill);
        }

        /**
         * Removes a skill from the known skills list.
         *
         * @param skill The skill to remove.
         */
        public synchronized void forget(ResourceLocation skill) {
            skills.remove(skill);
        }

        /**
         * Adds a skill point to the known skill points list.
         *
         * @param skillPoint The skill point to add.
         * @param amount     The amount of skill points to add.
         */
        public synchronized void addSkillPoint(ResourceLocation skillPoint, int amount) {
            skillPoints.putIfAbsent(skillPoint, 0);
            int amt = skillPoints.get(skillPoint);
            skillPoints.put(skillPoint, amt + amount);
        }

        /**
         * Adds a skill point to the known skill points list. Returns whether this worked or not.
         *
         * @param skillPoint The skill point to add.
         * @param amount     The amount of skill points to add.
         */
        public synchronized boolean consumeSkillPoint(ResourceLocation skillPoint, int amount) {
            if (!skillPoints.containsKey(skillPoint)) return false;
            int amt = skillPoints.get(skillPoint);
            if (amt < amount) return false;
            skillPoints.put(skillPoint, amt - amount);
            return true;
        }

        /**
         * Clears the known skill points list.
         */
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

        /**
         * Handles synchronization.
         *
         * @param knowledgeHolder The received KnowledgeHolder.
         */
        public void onSync(KnowledgeHolder knowledgeHolder) {
            this.skills.clear();
            this.skills.addAll(knowledgeHolder.skills());
            this.skillPoints.clear();
            this.skillPoints.putAll(knowledgeHolder.skillPoints());
        }

        /**
         * @param skill The skill to check.
         * @return Whether the capability contains the given skill or not.
         */
        public boolean knows(ResourceLocation skill) {
            return skills().contains(skill);
        }

        /**
         * @param skill The skill to check.
         * @return Whether the capability contains the given skill or not.
         */
        public boolean knows(ISkill skill) {
            return knows(skill.getId());
        }

        /**
         * @param skill The skill to check this for.
         * @return Whether the skill can be learned or not.
         */
        public boolean canLearn(ISkill skill) {
            return canLearn(skill.getId());
        }

        /**
         * Adds a skill to the known skills list.
         *
         * @param skill The skill to add.
         */
        public void learn(ISkill skill) {
            learn(skill.getId());
        }

        /**
         * Removes a skill from the known skills list.
         *
         * @param skill The skill to remove.
         */
        public void forget(ISkill skill) {
            forget(skill.getId());
        }

        /**
         * @param skillPoint The skill point to get.
         * @return The amount of skill points this capability holds, or 0 if none are found.
         */
        public int getSkillPoint(ResourceLocation skillPoint) {
            return skillPoints().getOrDefault(skillPoint, 0);
        }

        /**
         * @param skillPoint The skill point to get.
         * @return The amount of skill points this capability holds, or 0 if none are found.
         */
        public int getSkillPoint(ISkillPoint skillPoint) {
            return getSkillPoint(skillPoint.getId());
        }

        /**
         * Adds a skill point to the known skill points list.
         *
         * @param skillPoint The skill point to add.
         * @param amount     The amount of skill points to add.
         */
        public void addSkillPoint(ISkillPoint skillPoint, int amount) {
            addSkillPoint(skillPoint.getId(), amount);
        }

        /**
         * Adds a skill point to the known skill points list. Returns whether this worked or not.
         *
         * @param skillPoint The skill point to add.
         * @param amount     The amount of skill points to add.
         */
        public boolean consumeSkillPoint(ISkillPoint skillPoint, int amount) {
            return consumeSkillPoint(skillPoint.getId(), amount);
        }

        /**
         * Adds 1 skill point to the known skill points list.
         *
         * @param skillPoint The skill point to add.
         */
        public void addSkillPoint(ISkillPoint skillPoint) {
            addSkillPoint(skillPoint, 1);
        }

        /**
         * Adds 1 skill point to the known skill points list. Returns whether this worked or not.
         *
         * @param skillPoint The skill point to add.
         */
        public boolean consumeSkillPoint(ISkillPoint skillPoint) {
            return consumeSkillPoint(skillPoint, 1);
        }

        /**
         * Adds 1 skill point to the known skill points list.
         *
         * @param skillPoint The skill point to add.
         */
        public void addSkillPoint(ResourceLocation skillPoint) {
            addSkillPoint(skillPoint, 1);
        }

        /**
         * Adds 1 skill point to the known skill points list. Returns whether this worked or not.
         *
         * @param skillPoint The skill point to add.
         */
        public boolean consumeSkillPoint(ResourceLocation skillPoint) {
            return consumeSkillPoint(skillPoint, 1);
        }
    }
}
