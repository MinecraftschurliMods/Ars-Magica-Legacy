package com.github.minecraftschurlimods.arsmagicalegacy.common.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriteriaTriggers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.github.minecraftschurlimods.simplenetlib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public final class SkillHelper implements ISkillHelper {
    private static final Lazy<SkillHelper> INSTANCE = Lazy.concurrentOf(SkillHelper::new);
    private static final Capability<KnowledgeHolder> KNOWLEDGE = CapabilityManager.get(new CapabilityToken<>() {});

    private SkillHelper() {}

    /**
     * @return The only instance of this class.
     */
    public static SkillHelper instance() {
        return INSTANCE.get();
    }

    /**
     * @return The knowledge capability.
     */
    public static Capability<KnowledgeHolder> getCapability() {
        return KNOWLEDGE;
    }

    private static void handleSync(KnowledgeHolder holder, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(KNOWLEDGE).ifPresent(cap -> cap.onSync(holder)));
    }

    @Override
    public boolean knows(Player player, ResourceLocation skill) {
        return getKnowledgeHolder(player).knows(skill);
    }

    @Override
    public boolean knows(Player player, Skill skill, RegistryAccess registryAccess) {
        return getKnowledgeHolder(player).knows(skill, registryAccess);
    }

    @Override
    public boolean canLearn(Player player, ResourceLocation skill, RegistryAccess registryAccess) {
        return getKnowledgeHolder(player).canLearn(registryAccess.registryOrThrow(Skill.REGISTRY_KEY).getOptional(skill).orElseThrow());
    }

    @Override
    public boolean canLearn(Player player, Skill skill) {
        return getKnowledgeHolder(player).canLearn(skill);
    }

    @Override
    public void learn(Player player, ResourceLocation skill) {
        getKnowledgeHolder(player).learn(skill);
        if (player instanceof ServerPlayer serverPlayer) {
            AMCriteriaTriggers.PLAYER_LEARNED_SKILL.trigger(serverPlayer, skill);
        }
        syncToPlayer(player);
    }

    @Override
    public void learn(Player player, Skill skill, RegistryAccess registryAccess) {
        getKnowledgeHolder(player).learn(skill, registryAccess);
        if (player instanceof ServerPlayer serverPlayer) {
            AMCriteriaTriggers.PLAYER_LEARNED_SKILL.trigger(serverPlayer, skill.getId(registryAccess));
        }
        syncToPlayer(player);
    }

    @Override
    public void forget(Player player, ResourceLocation skill) {
        getKnowledgeHolder(player).forget(skill);
        syncToPlayer(player);
    }

    @Override
    public void forget(Player player, Skill skill, RegistryAccess registryAccess) {
        getKnowledgeHolder(player).forget(skill, registryAccess);
        syncToPlayer(player);
    }

    @Override
    public void learnAll(Player player, RegistryAccess registryAccess) {
        getKnowledgeHolder(player).learnAll(registryAccess);
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
    public int getSkillPoint(Player player, SkillPoint point) {
        return getKnowledgeHolder(player).getSkillPoint(point);
    }

    @Override
    public void addSkillPoint(Player player, ResourceLocation skillPoint, int amount) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint, amount);
        syncToPlayer(player);
    }

    @Override
    public void addSkillPoint(Player player, SkillPoint skillPoint, int amount) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint, amount);
        syncToPlayer(player);
    }

    @Override
    public void addSkillPoint(Player player, ResourceLocation skillPoint) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint);
        syncToPlayer(player);
    }

    @Override
    public void addSkillPoint(Player player, SkillPoint skillPoint) {
        getKnowledgeHolder(player).addSkillPoint(skillPoint);
        syncToPlayer(player);
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceLocation skillPoint, int amount) {
        boolean success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint, amount);
        syncToPlayer(player);
        return success;
    }

    @Override
    public boolean consumeSkillPoint(Player player, SkillPoint skillPoint, int amount) {
        boolean success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint, amount);
        syncToPlayer(player);
        return success;
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceLocation skillPoint) {
        boolean success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint);
        syncToPlayer(player);
        return success;
    }

    @Override
    public boolean consumeSkillPoint(Player player, SkillPoint skillPoint) {
        boolean success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint);
        syncToPlayer(player);
        return success;
    }

    @Override
    public ItemStack getOrbForSkillPoint(ResourceLocation skillPoint) {
        return getStackForSkillPoint(AMItems.INFINITY_ORB.get(), skillPoint);
    }

    @Override
    public ItemStack getOrbForSkillPoint(SkillPoint skillPoint) {
        return getStackForSkillPoint(AMItems.INFINITY_ORB.get(), skillPoint);
    }

    @Override
    public <T extends Item & ISkillPointItem> ItemStack getStackForSkillPoint(T item, ResourceLocation skillPoint) {
        ItemStack stack = new ItemStack(item);
        Optional.ofNullable(ArsMagicaAPI.get().getSkillPointRegistry().getValue(skillPoint)).ifPresent(skill -> item.setSkillPoint(stack, skill));
        return stack;
    }

    @Override
    public <T extends Item & ISkillPointItem> ItemStack getStackForSkillPoint(T item, SkillPoint skillPoint) {
        return getStackForSkillPoint(item, skillPoint.getId());
    }

    @Override
    public SkillPoint getSkillPointForStack(ItemStack stack) {
        if (stack.getItem() instanceof ISkillPointItem item) return item.getSkillPoint(stack);
        return AMSkillPoints.NONE.get();
    }

    @Override
    public Collection<ResourceLocation> getKnownSkills(Player player) {
        return getKnowledgeHolder(player).skills();
    }

    /**
     * Called on player death, syncs the capability.
     *
     * @param original The now-dead player.
     * @param player   The respawning player.
     */
    public void syncOnDeath(Player original, Player player) {
        original.getCapability(KNOWLEDGE).ifPresent(knowledgeHolder -> player.getCapability(KNOWLEDGE).ifPresent(holder -> holder.onSync(knowledgeHolder)));
        syncToPlayer(player);
    }

    /**
     * Syncs the capability to the client.
     *
     * @param player The player to sync to.
     */
    public void syncToPlayer(Player player) {
        if (player.getGameProfile().getName().equals("test-mock-player")) {
            return;
        }
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new SkillSyncPacket(getKnowledgeHolder(player)), player);
    }

    private KnowledgeHolder getKnowledgeHolder(Player player) {
        return player.getCapability(KNOWLEDGE).orElseThrow(() -> new RuntimeException("Could not retrieve skill capability for player %s{%s}".formatted(player.getDisplayName().getString(), player.getUUID())));
    }

    public static final class SkillSyncPacket extends CodecPacket<KnowledgeHolder> {
        public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "knowledge_sync");

        public SkillSyncPacket(KnowledgeHolder data) {
            super(ID, data);
        }

        public SkillSyncPacket(FriendlyByteBuf buf) {
            super(ID, buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            SkillHelper.handleSync(data, context);
        }

        @Override
        protected Codec<KnowledgeHolder> codec() {
            return KnowledgeHolder.CODEC;
        }
    }

    public record KnowledgeHolder(Set<ResourceLocation> skills, Map<ResourceLocation, Integer> skillPoints) {
        //@formatter:off
        public static final Codec<KnowledgeHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                CodecHelper.setOf(ResourceLocation.CODEC)
                           .xmap(HashSet::new, Function.identity())
                           .fieldOf("skills")
                           .forGetter(KnowledgeHolder::skills),
                Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT)
                     .<Map<ResourceLocation,Integer>>xmap(HashMap::new, Function.identity())
                     .fieldOf("skill_points")
                     .forGetter(KnowledgeHolder::skillPoints)
        ).apply(inst, KnowledgeHolder::new));
        //@formatter:on

        public static KnowledgeHolder empty() {
            return new KnowledgeHolder(new HashSet<>(), new HashMap<>());
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
         * Adds the given skill to the list of known skills.
         *
         * @param skill The id of the skill to add.
         */
        public synchronized void learn(ResourceLocation skill) {
            skills.add(skill);
        }

        /**
         * Adds the given skill to the list of known skills.
         *
         * @param skill The skill to add.
         */
        public void learn(Skill skill, RegistryAccess registryAccess) {
            learn(skill.getId(registryAccess));
        }

        /**
         * Adds all skills in the registry to the known skills list.
         */
        public synchronized void learnAll(RegistryAccess registryAccess) {
            for (Skill skill : registryAccess.registryOrThrow(Skill.REGISTRY_KEY)) {
                skills.add(skill.getId(registryAccess));
            }
        }

        /**
         * Removes the given skill from the list of known skills.
         *
         * @param skill The id of the skill to remove.
         */
        public synchronized void forget(ResourceLocation skill) {
            skills.remove(skill);
        }

        /**
         * Removes the given skill from the list of known skills.
         *
         * @param skill The skill to remove.
         */
        public void forget(Skill skill, RegistryAccess registryAccess) {
            forget(skill.getId(registryAccess));
        }

        /**
         * Clears the known skills list.
         */
        public synchronized void forgetAll() {
            skills.clear();
        }

        /**
         * @param skill The skill to check.
         * @return Whether the given skill can be learned or not.
         */
        public synchronized boolean canLearn(Skill skill) {
            boolean canLearn = true;
            for (ResourceLocation rl : skill.cost().keySet()) {
                if (skillPoints.getOrDefault(rl, 0) < skill.cost().get(rl)) {
                    canLearn = false;
                }
            }
            return canLearn && skills.containsAll(skill.parents());
        }

        /**
         * @param skill The id of the skill to check.
         * @return Whether the given skill is in the known skills list or not.
         */
        public synchronized boolean knows(ResourceLocation skill) {
            return skills().contains(skill);
        }

        /**
         * @param skill The skill to check.
         * @return Whether the given skill is in the known skills list or not.
         */
        public boolean knows(Skill skill, RegistryAccess registryAccess) {
            return knows(skill.getId(registryAccess));
        }

        /**
         * Adds the given amount of the given skill point to the list of skill points.
         *
         * @param skillPoint The id of the skill point to add.
         * @param amount     The amount to add.
         */
        public synchronized void addSkillPoint(ResourceLocation skillPoint, int amount) {
            skillPoints.putIfAbsent(skillPoint, 0);
            int amt = skillPoints.get(skillPoint);
            skillPoints.put(skillPoint, amt + amount);
        }

        /**
         * Adds the given amount of the given skill point to the list of skill points.
         *
         * @param skillPoint The skill point to add.
         * @param amount     The amount to add.
         */
        public void addSkillPoint(SkillPoint skillPoint, int amount) {
            addSkillPoint(skillPoint.getId(), amount);
        }

        /**
         * Adds one of the given skill point to the list of skill points.
         *
         * @param skillPoint The id of the skill point to add.
         */
        public void addSkillPoint(ResourceLocation skillPoint) {
            addSkillPoint(skillPoint, 1);
        }

        /**
         * Adds one of the given skill point to the list of skill points.
         *
         * @param skillPoint The skill point to add.
         */
        public void addSkillPoint(SkillPoint skillPoint) {
            addSkillPoint(skillPoint, 1);
        }

        /**
         * Removes the given amount of the given skill point from the list of skill points.
         *
         * @param skillPoint The id of the skill point to remove.
         * @param amount     The amount to remove.
         * @return True if the operation succeeded, false otherwise.
         */
        public synchronized boolean consumeSkillPoint(ResourceLocation skillPoint, int amount) {
            if (!skillPoints.containsKey(skillPoint)) return false;
            int amt = skillPoints.get(skillPoint);
            if (amt < amount) return false;
            skillPoints.put(skillPoint, amt - amount);
            return true;
        }

        /**
         * Removes the given amount of the given skill point from the list of skill points.
         *
         * @param skillPoint The skill point to remove.
         * @param amount     The amount to remove.
         * @return True if the operation succeeded, false otherwise.
         */
        public boolean consumeSkillPoint(SkillPoint skillPoint, int amount) {
            return consumeSkillPoint(skillPoint.getId(), amount);
        }

        /**
         * Removes the given amount of the given skill point from the list of skill points.
         *
         * @param skillPoint The id of the skill point to remove.
         * @return True if the operation succeeded, false otherwise.
         */
        public boolean consumeSkillPoint(ResourceLocation skillPoint) {
            return consumeSkillPoint(skillPoint, 1);
        }

        /**
         * Removes the given amount of the given skill point from the list of skill points.
         *
         * @param skillPoint The skill point to remove.
         * @return True if the operation succeeded, false otherwise.
         */
        public boolean consumeSkillPoint(SkillPoint skillPoint) {
            return consumeSkillPoint(skillPoint, 1);
        }

        /**
         * @param skillPoint The id of the skill point type to check.
         * @return The amount of skill points of the given type that are present in this holder.
         */
        public int getSkillPoint(ResourceLocation skillPoint) {
            return skillPoints().getOrDefault(skillPoint, 0);
        }

        /**
         * @param skillPoint The skill point type to check.
         * @return The amount of skill points of the given type that are present in this holder.
         */
        public int getSkillPoint(SkillPoint skillPoint) {
            return getSkillPoint(skillPoint.getId());
        }

        /**
         * Syncs the values with the given data object.
         *
         * @param data The data object to sync with.
         */
        public void onSync(KnowledgeHolder data) {
            skills.clear();
            skills.addAll(data.skills());
            skillPoints.clear();
            skillPoints.putAll(data.skillPoints());
        }
    }
}
