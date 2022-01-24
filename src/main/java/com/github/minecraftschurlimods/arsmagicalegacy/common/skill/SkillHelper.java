package com.github.minecraftschurlimods.arsmagicalegacy.common.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriteriaTriggers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.github.minecraftschurlimods.simplenetlib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class SkillHelper implements ISkillHelper {
    private static final Lazy<SkillHelper> INSTANCE = Lazy.concurrentOf(SkillHelper::new);
    private static final Capability<KnowledgeHolder> KNOWLEDGE = CapabilityManager.get(new CapabilityToken<>() {
    });

    /**
     * @return The only instance of this class.
     */
    public static SkillHelper instance() {
        return INSTANCE.get();
    }

    /**
     * Registers the required network packets.
     */
    public static void init() {
        ArsMagicaLegacy.NETWORK_HANDLER.register(SyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
    }

    /**
     * @return The knowledge capability.
     */
    public static Capability<KnowledgeHolder> getCapability() {
        return KNOWLEDGE;
    }

    /**
     * Handles synchronization with the client.
     *
     * @param holder  The capability to sync.
     * @param context The networking context.
     */
    private static void handleSync(KnowledgeHolder holder, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(KNOWLEDGE).ifPresent(cap -> cap.onSync(holder)));
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
        AMCriteriaTriggers.PLAYER_LEARNED_SKILL.trigger((ServerPlayer) player, skill);
        syncToPlayer(player);
    }

    @Override
    public void learn(Player player, ISkill skill) {
        getKnowledgeHolder(player).learn(skill);
        AMCriteriaTriggers.PLAYER_LEARNED_SKILL.trigger((ServerPlayer) player, skill.getId());
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
    public void learnAll(Player player) {
        getKnowledgeHolder(player).learnAll();
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
        boolean success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint, amount);
        syncToPlayer(player);
        return success;
    }

    @Override
    public boolean consumeSkillPoint(Player player, ISkillPoint skillPoint, int amount) {
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
    public boolean consumeSkillPoint(Player player, ISkillPoint skillPoint) {
        boolean success = getKnowledgeHolder(player).consumeSkillPoint(skillPoint);
        syncToPlayer(player);
        return success;
    }

    @Override
    public ItemStack getOrbForSkillPoint(ResourceLocation skillPoint) {
        return getStackForSkillPoint(AMItems.INFINITY_ORB.get(), skillPoint);
    }

    @Override
    public ItemStack getOrbForSkillPoint(ISkillPoint skillPoint) {
        return getStackForSkillPoint(AMItems.INFINITY_ORB.get(), skillPoint);
    }

    @Override
    public <T extends Item & ISkillPointItem> ItemStack getStackForSkillPoint(T item, ResourceLocation skillPoint) {
        ItemStack stack = new ItemStack(item);
        Optional.ofNullable(ArsMagicaAPI.get().getSkillPointRegistry().getValue(skillPoint)).ifPresent(skill -> item.setSkillPoint(stack, skill));
        return stack;
    }

    @Override
    public <T extends Item & ISkillPointItem> ItemStack getStackForSkillPoint(T item, ISkillPoint skillPoint) {
        return getStackForSkillPoint(item, skillPoint.getId());
    }

    @Nullable
    @Override
    public ISkillPoint getSkillPointForStack(ItemStack stack) {
        if (stack.getItem() instanceof ISkillPointItem item) {
            return item.getSkillPoint(stack);
        }
        return null;
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
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new SyncPacket(getKnowledgeHolder(player)), player);
    }

    private KnowledgeHolder getKnowledgeHolder(Player player) {
        return player.getCapability(KNOWLEDGE).orElseThrow(() -> new RuntimeException("Could not retrieve skill capability for player %s{%s}".formatted(player.getDisplayName().getString(), player.getUUID())));
    }

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

    public record KnowledgeHolder(Set<ResourceLocation> skills, Map<ResourceLocation, Integer> skillPoints) {
        //@formatter:off
        public static final Codec<KnowledgeHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                CodecHelper.setOf(ResourceLocation.CODEC).fieldOf("skills").forGetter(KnowledgeHolder::skills),
                CodecHelper.mapOf(ResourceLocation.CODEC, Codec.INT).fieldOf("skill_points").forGetter(KnowledgeHolder::skillPoints)
        ).apply(inst, KnowledgeHolder::new));
        //@formatter:on

        public static KnowledgeHolder empty() {
            return new KnowledgeHolder(new HashSet<>(), new HashMap<>());
        }

        public synchronized boolean canLearn(ResourceLocation skill) {
            var skillManager = ArsMagicaAPI.get().getSkillManager();
            ISkill iSkill = skillManager.get(skill);
            boolean canLearn = true;
            for (ResourceLocation rl : iSkill.getCost().keySet()) {
                if (skillPoints.getOrDefault(rl, 0) < iSkill.getCost().get(rl)) {
                    canLearn = false;
                }
            }
            return canLearn && skills.containsAll(skillManager.get(skill).getParents());
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

        public void learnAll() {
            for (ISkill skill : ArsMagicaAPI.get().getSkillManager().getSkills()) {
                skills.add(skill.getId());
            }
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
