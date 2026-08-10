package at.minecraftschurli.mods.arsmagicalegacy.util;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.mods.arsmagicalegacy.compat.curios.AMCuriosHelper;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.item.SpellRecipeItem;
import at.minecraftschurli.mods.arsmagicalegacy.packet.OpenBookInLecternPacket;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

public final class AMUtil {
    public static final Identifier MISSINGNO = Identifier.withDefaultNamespace("missingno");
    private static final RandomSource RANDOM = RandomSource.create();

    private AMUtil() {
    }

    public static <T> int getCommandSelf(CommandContext<CommandSourceStack> context, Function<ServerPlayer, T> function, ToIntFunction<T> toIntFunction, BiFunction<Component, T, Component> messageFactory) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        T value = function.apply(player);
        context.getSource().sendSuccess(() -> messageFactory.apply(Objects.requireNonNull(player.getDisplayName()), value), true);
        return toIntFunction.applyAsInt(value);
    }

    public static <T> int getCommand(CommandContext<CommandSourceStack> context, Function<ServerPlayer, T> function, ToIntFunction<T> toIntFunction, BiFunction<Component, T, Component> messageFactory) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "target");
        T value = function.apply(player);
        context.getSource().sendSuccess(() -> messageFactory.apply(Objects.requireNonNull(player.getDisplayName()), value), true);
        return toIntFunction.applyAsInt(value);
    }

    public static int runCommandSelf(CommandContext<CommandSourceStack> context, Consumer<ServerPlayer> consumer, Function<Component, Component> messageFactory) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        consumer.accept(player);
        context.getSource().sendSuccess(() -> messageFactory.apply(Objects.requireNonNull(player.getDisplayName())), true);
        return 1;
    }

    public static int runCommand(CommandContext<CommandSourceStack> context, Consumer<ServerPlayer> consumer, Function<Component, Component> singleMessageFactory, IntFunction<Component> multipleMessageFactory) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "target");
        players.forEach(consumer);
        if (players.size() == 1) {
            context.getSource().sendSuccess(() -> singleMessageFactory.apply(Objects.requireNonNull(players.iterator().next().getDisplayName())), true);
        } else {
            context.getSource().sendSuccess(() -> multipleMessageFactory.apply(players.size()), true);
        }
        return players.size();
    }

    @SuppressWarnings("DataFlowIssue")
    @Nullable
    public static Holder<SpellPart> spellPart(Holder<Skill> skill) {
        return AMRegistries.SPELL_PARTS.get(skill.getKey().identifier()).orElse(null);
    }

    @SuppressWarnings("DataFlowIssue")
    @Nullable
    public static Holder<Skill> skill(Holder<SpellPart> part, boolean client) {
        return AMRegistries.skills(client).get(part.getKey().identifier()).orElse(null);
    }

    public static Vec3 bezier(Vec3 start, Vec3 control1, Vec3 control2, Vec3 end, double delta) {
        delta = Math.clamp(delta, 0, 1);
        double invertedDelta = 1 - delta;
        return Vec3.ZERO
            .add(start.scale(invertedDelta * invertedDelta * invertedDelta))
            .add(control1.scale(3 * invertedDelta * invertedDelta * delta))
            .add(control2.scale(3 * invertedDelta * delta * delta))
            .add(end.scale(delta * delta * delta));
    }

    public static boolean cancelDestroyBlock(Level level, BlockPos pos, BlockState state, Player player, ItemStack stack) {
        if (NeoForge.EVENT_BUS.post(new BreakBlockEvent(level, pos, state, player)).isCanceled()) return true;
        state = state.getBlock().playerWillDestroy(level, pos, state, player);
        level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
        if (state.onDestroyedByPlayer(level, pos, player, stack, true, level.getFluidState(pos))) {
            player.awardStat(Stats.BLOCK_MINED.get(state.getBlock()));
            return false;
        }
        return true;
    }

    @Nullable
    public static Component cancelTeleport(Entity entity, @Nullable LivingEntity caster) {
        if (caster != null && caster.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) return AMTranslations.NO_TELEPORT;
        if (entity instanceof LivingEntity living && living.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) return AMTranslations.NO_TELEPORT_OTHER;
        return null;
    }

    public static List<ItemStack> destroyBlockAndGetDrops(ServerLevel level, BlockPos pos, BlockState state, Player player, ItemStack stack) {
        return cancelDestroyBlock(level, pos, state, player, stack) ? List.of() : Block.getDrops(state, level, pos, level.getBlockEntity(pos), player, stack);
    }

    public static void doCompendiumConversion(ItemFrame itemFrame) {
        if (!itemFrame.getItem().is(AMTags.Items.ARCANE_COMPENDIUM_BOOKS)) {
            if (itemFrame.hasData(AMAttachments.COMPENDIUM_TIMER)) {
                itemFrame.removeData(AMAttachments.COMPENDIUM_TIMER);
            }
            return;
        }
        Direction direction = itemFrame.getDirection();
        if (direction.getAxis() == Direction.Axis.Y) return;
        int range = AMServerConfig.ARCANE_COMPENDIUM_CONVERSION_HORIZONTAL_RANGE.getAsInt();
        Level level = itemFrame.level();
        BlockPos pos = itemFrame.getPos().offset(direction.getUnitVec3i().multiply(Math.ceilDiv(range, 2))).below();
        List<BlockPos> positions = new ArrayList<>();
        for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-range / 2, 0, -range / 2), pos.offset(range / 2, 1 - AMServerConfig.ARCANE_COMPENDIUM_CONVERSION_VERTICAL_RANGE.getAsInt(), range / 2))) {
            BlockPos above = blockPos.above();
            if (level.getBlockState(blockPos).is(AMBlocks.LIQUID_ETHERIUM) && !level.getBlockState(above).isSolidRender()) {
                positions.add(new BlockPos(blockPos));
            }
        }
        if (!positions.isEmpty()) {
            int timer = itemFrame.getData(AMAttachments.COMPENDIUM_TIMER);
            if (timer >= AMServerConfig.ARCANE_COMPENDIUM_CONVERSION_DURATION.getAsInt()) {
                itemFrame.setItem(ArsMagicaApi.book().create());
                if (level.isClientSide()) {
                    AMClientUtil.spawnArcaneCompendiumConversionFinishParticles(itemFrame.position());
                }
                itemFrame.removeData(AMAttachments.COMPENDIUM_TIMER);
            } else {
                if (level.isClientSide()) {
                    AMClientUtil.spawnArcaneCompendiumConversionParticles(positions, itemFrame.position());
                }
                itemFrame.setData(AMAttachments.COMPENDIUM_TIMER, timer + 1);
            }
        } else if (itemFrame.hasData(AMAttachments.COMPENDIUM_TIMER)) {
            itemFrame.removeData(AMAttachments.COMPENDIUM_TIMER);
        }
    }

    public static boolean doRuleTest(RuleTest test, BlockState state) {
        RANDOM.setSeed(42);
        return test.test(state, RANDOM);
    }

    public static <A, B> BiConsumer<A, B> dropResult(BiFunction<A, B, ?> function) {
        return function::apply;
    }

    public static <T> @Nullable T getByTick(T[] array, int tick) {
        return array.length == 0 ? null : array[tick % array.length];
    }

    public static <T> @Nullable T getByTick(List<T> list, int tick) {
        return list.isEmpty() ? null : list.get(tick % list.size());
    }

    public static ItemStack getEnchanted(ItemStack stack, List<SpellModifier> modifiers, SpellCastContext context, Map<ResourceKey<Enchantment>, SpellStat> enchantments) {
        stack.set(AMDataComponents.SPELL, context.spell());
        Registry<Enchantment> registry = context.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        for (Map.Entry<ResourceKey<Enchantment>, SpellStat> entry : enchantments.entrySet()) {
            stack.enchant(registry.getOrThrow(entry.getKey()), (int) helper.getModifiedStat(0, entry.getValue(), modifiers, context));
        }
        return stack;
    }

    public static ItemStack getEnchantedSpell(List<SpellModifier> modifiers, SpellCastContext context, Map<ResourceKey<Enchantment>, SpellStat> enchantments) {
        return getEnchanted(AMItems.SPELL.toStack(), modifiers, context, enchantments);
    }

    public static List<BlockPos> getHangingColumn(GrowthContext context, Block head, Block body) {
        ServerLevel level = context.level();
        BlockPos originalPos = context.pos();
        List<BlockPos> list = new ArrayList<>();
        list.add(originalPos);
        BlockPos pos = originalPos.above();
        while (is(level.getBlockState(pos), head, body)) {
            list.addFirst(pos);
            pos = pos.above();
        }
        pos = originalPos.below();
        while (is(level.getBlockState(pos), head, body)) {
            list.add(pos);
            pos = pos.below();
        }
        return list;
    }

    public static HitResult getHitResult(Vec3 from, Vec3 to, Entity entity, boolean targetNonSolid) {
        HitResult hitResult = entity.level().clip(new ClipContext(from, to, targetNonSolid ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, targetNonSolid ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, entity));
        if (hitResult.getType() != HitResult.Type.MISS) {
            to = hitResult.getLocation();
        }
        HitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity.level(), entity, from, to, new AABB(from, to), _ -> true, 0);
        if (entityHitResult != null) {
            hitResult = entityHitResult;
        }
        return hitResult;
    }

    public static HitResult getHitResult(Entity entity, double length, boolean targetNonSolid, float partialTick) {
        Vec3 eyePos = entity.getEyePosition(partialTick);
        return getHitResult(eyePos, eyePos.add(entity.getHeadLookAngle().scale(length)), entity, targetNonSolid);
    }

    public static HitResult getHitResult(Entity entity, List<SpellModifier> modifiers, SpellCastContext context, double baseRange, float partialTick) {
        return getHitResult(entity,
            ArsMagicaApi.spellHelper().getModifiedStat(baseRange, AMSpells.RANGE_STAT, modifiers, context),
            ArsMagicaApi.spellHelper().getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, context) > 0,
            partialTick);
    }

    public static HitResult getHitResult(LivingEntity entity, Spell spell, double baseRange, float partialTick) {
        return getHitResult(entity, spell.currentShapeGroup().primaryModifiers(), new SpellCastContext(spell, entity.level(), entity, false, false), baseRange, partialTick);
    }

    public static List<Plant> getPlants(BlockState state, RegistryAccess registryAccess) {
        return AMRegistries.plants(registryAccess)
            .listElements()
            .map(Holder::value)
            .filter(plant -> doRuleTest(plant.allStates(), state))
            .toList();
    }

    public static <T extends Comparable<T>> String getPropertyValueName(Property<T> property, BlockState state) {
        return property.getName(property.value(state.getValue(property)).value());
    }

    public static boolean handleLecternUse(Level level, BlockPos pos, BlockState state, LecternBlockEntity lectern, Player player, InteractionHand hand) {
        ItemStack book = lectern.getBook();
        if (book.isEmpty()) {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.is(ItemTags.LECTERN_BOOKS) || !stack.is(AMItems.SPELL_RECIPE)) return false;
            int pageCount = SpellRecipeItem.getPageCount(stack);
            if (pageCount == 0) return false;
            lectern.setBook(stack.consumeAndReturn(1, player));
            LecternBlock.resetBookState(player, level, pos, state, true);
            level.playSound(null, pos, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1f, 1f);
            lectern.pageCount = pageCount;
            return true;
        } else if (book.is(AMItems.SPELL_RECIPE)) {
            if (player.isSecondaryUseActive()) {
                takeLecternBook(player, level, pos);
            } else if (!level.isClientSide() && player instanceof ServerPlayer sp) {
                PacketDistributor.sendToPlayer(sp, new OpenBookInLecternPacket(pos, book));
            }
            return true;
        }
        return false;
    }

    public static boolean is(BlockState state, Block... blocks) {
        return Arrays.stream(blocks).anyMatch(state::is);
    }

    public static boolean isInEquipmentSlot(LivingEntity entity, EquipmentSlot slot, Item item) {
        return entity.getItemBySlot(slot).is(item);
    }

    public static boolean isInCurioSlot(LivingEntity entity, Item item) {
        return ModList.get().isLoaded("curios") && AMCuriosHelper.hasItemEquipped(entity, item);
    }

    public static boolean isInEquipmentOrCurioSlot(LivingEntity entity, EquipmentSlot slot, Item item) {
        return isInEquipmentSlot(entity, slot, item) || isInCurioSlot(entity, item);
    }

    public static VoxelShape joinShapes(VoxelShape first, VoxelShape... others) {
        VoxelShape result = first;
        for (VoxelShape shape : others) {
            result = Shapes.joinUnoptimized(result, shape, BooleanOp.OR);
        }
        return result.optimize();
    }

    public static Collector<MutableComponent, MutableComponent, MutableComponent> joiningComponents(Component delimiter) {
        return Collector.of(Component::empty, dropResult((c1, c2) -> !c1.getString().isEmpty() ? c1.append(delimiter).append(c2) : c1.append(c2)), (c1, c2) -> !c1.getString().isEmpty() ? c1.append(delimiter).append(c2) : c1.append(c2));
    }

    @SafeVarargs
    public static <T> NonNullList<T> nonNullList(T defaultValue, T... entries) {
        NonNullList<T> list = NonNullList.withSize(entries.length, defaultValue);
        for (int i = 0; i < entries.length; i++) {
            list.set(i, entries[i]);
        }
        return list;
    }

    public static <T> ItemStack set(ItemStack stack, DataComponentType<T> type, T value) {
        stack.set(type, value);
        return stack;
    }

    public static void setMinionTargets(ServerLevel level, LivingEntity owner, LivingEntity target) {
        owner.getData(AMAttachments.SUMMON_MINIONS)
            .uuids()
            .stream()
            .map(level::getEntity)
            .filter(Mob.class::isInstance)
            .map(Mob.class::cast)
            .filter(mob -> !mob.getUUID().equals(target.getUUID()))
            .filter(mob -> mob.canAttack(target))
            .forEach(mob -> mob.setTarget(target));
    }

    public static void takeLecternBook(Player player, Level level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof LecternBlockEntity lectern)) return;
        ItemStack stack = lectern.getBook();
        lectern.setBook(ItemStack.EMPTY);
        LecternBlock.resetBookState(player, level, pos, level.getBlockState(pos), false);
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    public static <T> ItemStackTemplate template(Holder<Item> item, DataComponentType<T> componentType, T value) {
        return new ItemStackTemplate(item, DataComponentPatch.builder().set(componentType, value).build());
    }

    public static float wrapToRadians(float degrees) {
        return (float) Math.toRadians(Mth.wrapDegrees(degrees));
    }
}
