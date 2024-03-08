package com.github.minecraftschurlimods.arsmagicalegacy.common.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriteriaTriggers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.github.minecraftschurlimods.codeclib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ATTACHMENT_TYPES;

public final class SkillHelper implements ISkillHelper {
    private static final Lazy<SkillHelper> INSTANCE = Lazy.concurrentOf(SkillHelper::new);
    private static final Supplier<AttachmentType<KnowledgeHolder>> KNOWLEDGE = ATTACHMENT_TYPES.register("knowledge", () -> AttachmentType.builder(KnowledgeHolder::empty).serialize(KnowledgeHolder.CODEC).copyOnDeath().copyHandler(KnowledgeHolder::copy).build());

    private SkillHelper() {}

    /**
     * @return The only instance of this class.
     */
    public static SkillHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public boolean knows(Player player, ResourceLocation skill) {
        return player.getData(KNOWLEDGE).knows(skill);
    }

    @Override
    public boolean knows(Player player, Skill skill, RegistryAccess registryAccess) {
        return knows(player, skill.getId(registryAccess));
    }

    @Override
    public boolean canLearn(Player player, ResourceLocation skill, RegistryAccess registryAccess) {
        return canLearn(player, registryAccess.registryOrThrow(Skill.REGISTRY_KEY).getOptional(skill).orElseThrow());
    }

    @Override
    public boolean canLearn(Player player, Skill skill) {
        return player.getData(KNOWLEDGE).canLearn(skill);
    }

    @Override
    public void learn(Player player, ResourceLocation skill) {
        KnowledgeHolder holder = player.getData(KNOWLEDGE);
        holder.learn(skill);
        if (player instanceof ServerPlayer serverPlayer) {
            AMCriteriaTriggers.PLAYER_LEARNED_SKILL.get().trigger(serverPlayer, skill);
        }
        syncToPlayer(player);
    }

    @Override
    public void learn(Player player, Skill skill, RegistryAccess registryAccess) {
        KnowledgeHolder holder = player.getData(KNOWLEDGE);
        holder.learn(skill.getId(registryAccess));
        if (player instanceof ServerPlayer serverPlayer) {
            AMCriteriaTriggers.PLAYER_LEARNED_SKILL.get().trigger(serverPlayer, skill.getId(registryAccess));
        }
        syncToPlayer(player);
    }

    @Override
    public void forget(Player player, ResourceLocation skill) {
        KnowledgeHolder holder = player.getData(KNOWLEDGE);
        holder.forget(skill);
        syncToPlayer(player);
    }

    @Override
    public void forget(Player player, Skill skill, RegistryAccess registryAccess) {
        forget(player, skill.getId(registryAccess));
    }

    @Override
    public void learnAll(Player player, RegistryAccess registryAccess) {
        KnowledgeHolder holder = player.getData(KNOWLEDGE);
        holder.learnAll(registryAccess);
        syncToPlayer(player);
    }

    @Override
    public void forgetAll(Player player) {
        KnowledgeHolder holder = player.getData(KNOWLEDGE);
        holder.forgetAll();
        syncToPlayer(player);
    }

    @Override
    public int getSkillPoint(Player player, ResourceLocation point) {
        return player.getData(KNOWLEDGE).getSkillPoint(point);
    }

    @Override
    public int getSkillPoint(Player player, SkillPoint point) {
        return getSkillPoint(player, point.getId());
    }

    @Override
    public void addSkillPoint(Player player, ResourceLocation skillPoint, int amount) {
        KnowledgeHolder holder = player.getData(KNOWLEDGE);
        holder.addSkillPoint(skillPoint, amount);
        syncToPlayer(player);
    }

    @Override
    public void addSkillPoint(Player player, ResourceKey<SkillPoint> skillPoint, int amount) {
        addSkillPoint(player, skillPoint.location(), amount);
    }

    @Override
    public void addSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount) {
        addSkillPoint(player, skillPoint.unwrapKey().orElseThrow(), amount);
    }

    @Override
    public void addSkillPoint(Player player, SkillPoint skillPoint, int amount) {
        addSkillPoint(player, skillPoint.getId(), amount);
    }

    @Override
    public void addSkillPoint(Player player, ResourceLocation skillPoint) {
        addSkillPoint(player, skillPoint, 1);
    }

    @Override
    public void addSkillPoint(Player player, ResourceKey<SkillPoint> skillPoint) {
        addSkillPoint(player, skillPoint, 1);
    }

    @Override
    public void addSkillPoint(Player player, Holder<SkillPoint> skillPoint) {
        addSkillPoint(player, skillPoint, 1);
    }

    @Override
    public void addSkillPoint(Player player, SkillPoint skillPoint) {
        addSkillPoint(player, skillPoint.getId(), 1);
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceLocation skillPoint, int amount) {
        KnowledgeHolder holder = player.getData(KNOWLEDGE);
        boolean val = holder.consumeSkillPoint(skillPoint, amount);
        syncToPlayer(player);
        return val;
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceKey<SkillPoint> skillPoint, int amount) {
        return consumeSkillPoint(player, skillPoint.location(), amount);
    }

    @Override
    public boolean consumeSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount) {
        return consumeSkillPoint(player, skillPoint.unwrapKey().orElseThrow(), amount);
    }

    @Override
    public boolean consumeSkillPoint(Player player, SkillPoint skillPoint, int amount) {
        return consumeSkillPoint(player, skillPoint.getId(), amount);
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceKey<SkillPoint> skillPoint) {
        return consumeSkillPoint(player, skillPoint, 1);
    }

    @Override
    public boolean consumeSkillPoint(Player player, Holder<SkillPoint> skillPoint) {
        return consumeSkillPoint(player, skillPoint, 1);
    }

    @Override
    public boolean consumeSkillPoint(Player player, ResourceLocation skillPoint) {
        return consumeSkillPoint(player, skillPoint, 1);
    }

    @Override
    public boolean consumeSkillPoint(Player player, SkillPoint skillPoint) {
        return consumeSkillPoint(player, skillPoint.getId(), 1);
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
        Optional.ofNullable(ArsMagicaAPI.get().getSkillPointRegistry().get(skillPoint)).ifPresent(skill -> item.setSkillPoint(stack, skill));
        return stack;
    }

    @Override
    public <T extends Item & ISkillPointItem> ItemStack getStackForSkillPoint(T item, SkillPoint skillPoint) {
        return getStackForSkillPoint(item, skillPoint.getId());
    }

    @Override
    public SkillPoint getSkillPointForStack(ItemStack stack) {
        if (stack.getItem() instanceof ISkillPointItem item) return item.getSkillPoint(stack);
        return AMSkillPoints.NONE.value();
    }

    @Override
    public Collection<ResourceLocation> getKnownSkills(Player player) {
        return player.getData(KNOWLEDGE).skills();
    }

    /**
     * Syncs the capability to the client.
     *
     * @param player The player to sync to.
     */
    public void syncToPlayer(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        PacketDistributor.PLAYER.with(serverPlayer).send(new SkillSyncPacket(player.getData(KNOWLEDGE)));
    }

    public static void registerSyncPacket(IPayloadRegistrar registrar) {
        registrar.play(SkillSyncPacket.ID, SkillSyncPacket::new, builder -> builder.client(SkillSyncPacket::handle));
    }

    private static class SkillSyncPacket extends CodecPacket<KnowledgeHolder> {
        public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "knowledge_sync");

        public SkillSyncPacket(KnowledgeHolder knowledge) {
            super(knowledge);
        }

        public SkillSyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        protected Codec<KnowledgeHolder> codec() {
            return KnowledgeHolder.CODEC;
        }

        @Override
        public ResourceLocation id() {
            return ID;
        }

        private void handle(PlayPayloadContext context) {
            context.workHandler().submitAsync(() -> context.player().orElseThrow().setData(KNOWLEDGE, this.data));
        }
    }

    public record KnowledgeHolder(Set<ResourceLocation> skills, Map<ResourceLocation, Integer> skillPoints) {
        //@formatter:off
        private static final MapCodec<Set<ResourceLocation>> SKILLS_CODEC = CodecHelper
                .setOf(ResourceLocation.CODEC)
                .<Set<ResourceLocation>>xmap(HashSet::new, Function.identity())
                .fieldOf("skills");
        private static final MapCodec<Map<ResourceLocation, Integer>> SKILL_POINTS_CODEC = Codec
                .unboundedMap(ResourceLocation.CODEC, Codec.INT)
                .<Map<ResourceLocation, Integer>>xmap(HashMap::new, Function.identity())
                .fieldOf("skill_points");
        public static final Codec<KnowledgeHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                SKILLS_CODEC.forGetter(KnowledgeHolder::skills),
                SKILL_POINTS_CODEC.forGetter(KnowledgeHolder::skillPoints)
        ).apply(inst, KnowledgeHolder::new));
        //@formatter:on

        public static KnowledgeHolder empty() {
            return new KnowledgeHolder(new HashSet<>(), new HashMap<>());
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
            return skills.contains(skill);
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
         * @param skillPoint The id of the skill point type to check.
         * @return The amount of skill points of the given type that are present in this holder.
         */
        public int getSkillPoint(ResourceLocation skillPoint) {
            return skillPoints.getOrDefault(skillPoint, 0);
        }

        public static KnowledgeHolder copy(IAttachmentHolder owner, KnowledgeHolder knowledgeHolder) {
            return new KnowledgeHolder(new HashSet<>(knowledgeHolder.skills()), new HashMap<>(knowledgeHolder.skillPoints()));
        }
    }
}
