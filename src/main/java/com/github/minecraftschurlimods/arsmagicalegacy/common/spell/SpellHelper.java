package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStatModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellParticleSpawner;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.ItemHandlerExtractionQuery;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpawnComponentParticlesPacket;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class SpellHelper implements ISpellHelper {
    private static final Lazy<SpellHelper> INSTANCE = Lazy.concurrentOf(SpellHelper::new);
    private static final String SPELL_KEY = ArsMagicaAPI.MOD_ID + ":spell";
    private static final String SPELL_ICON_KEY = ArsMagicaAPI.MOD_ID + ":spell_icon";
    private static final String SPELL_NAME_KEY = ArsMagicaAPI.MOD_ID + ":spell_name";
    private final Map<ISpellComponent, ISpellParticleSpawner> particleSpawners = new HashMap<>();

    private SpellHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static SpellHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public ISpell getSpell(ItemStack stack) {
        if (stack.isEmpty()) return ISpell.EMPTY;
        return ISpell.CODEC.decode(NbtOps.INSTANCE, stack.getOrCreateTagElement(SPELL_KEY)).map(Pair::getFirst).get().mapRight(DataResult.PartialResult::message).ifRight(ArsMagicaLegacy.LOGGER::warn).left().orElse(ISpell.EMPTY);
    }

    @Override
    public void setSpell(ItemStack stack, ISpell spell) {
        stack.getOrCreateTag().put(SPELL_KEY, ISpell.CODEC.encodeStart(NbtOps.INSTANCE, spell).get().mapRight(DataResult.PartialResult::message).ifRight(ArsMagicaLegacy.LOGGER::warn).left().orElse(new CompoundTag()));
    }

    @Override
    public boolean isValidSpell(ISpell spell) {
        //check spell stack
        if (spell.spellStack().isEmpty()) return false;
        if (spell.spellStack().parts().get(0).getType() != ISpellPart.SpellPartType.COMPONENT) return false;
        //find last non-empty shape group
        List<ShapeGroup> groups = spell.shapeGroups();
        if (groups.stream().allMatch(ShapeGroup::isEmpty)) return false;
        int last = -1;
        for (int i = 0; i < groups.size(); i++) {
            ShapeGroup group = groups.get(i);
            if (!group.isEmpty()) {
                last = i;
            }
        }
        //check for empty shape groups between other non-empty shape groups
        if (last == -1) return false;
        groups = groups.stream().filter(e -> !e.isEmpty()).toList();
        if (last != groups.size() - 1) return false;
        //check shape groups themselves
        for (ShapeGroup group : groups) {
            if (group.parts().get(0).getType() != ISpellPart.SpellPartType.SHAPE) return false;
        }
        return true;
    }

    @Override
    public ItemStack getSpellItemStackFromEntity(LivingEntity entity) {
        ItemStack stack = getSpellItemStackInHand(entity, InteractionHand.MAIN_HAND);
        var helper = ArsMagicaAPI.get().getSpellHelper();
        if (helper.getSpell(stack) != ISpell.EMPTY) return stack;
        stack = getSpellItemStackInHand(entity, InteractionHand.OFF_HAND);
        return helper.getSpell(stack) != ISpell.EMPTY ? stack : ItemStack.EMPTY;
    }

    @Override
    public ItemStack getSpellItemStackInHand(LivingEntity entity, InteractionHand hand) {
        ItemStack stack = entity.getItemInHand(hand);
        if (stack.getItem() instanceof SpellBookItem) {
            stack = SpellBookItem.getSelectedSpell(stack);
        }
        return stack;
    }

    @Override
    public Optional<Component> getSpellName(ItemStack stack) {
        return Optional.of(stack.getOrCreateTag().getString(SPELL_NAME_KEY)).filter(s -> !s.isEmpty()).map(s -> {
            try {
                return Component.Serializer.fromJson(s);
            } catch (Exception e) {
                return null;
            }
        });
    }

    @Override
    public void setSpellName(ItemStack stack, String name) {
        setSpellName(stack, Component.nullToEmpty(name));
    }

    @Override
    public void setSpellName(ItemStack stack, Component name) {
        stack.getOrCreateTag().putString(SPELL_NAME_KEY, Component.Serializer.toJson(name));
    }

    @Override
    public Optional<ResourceLocation> getSpellIcon(ItemStack stack) {
        return Optional.of(stack.getOrCreateTag().getString(SPELL_ICON_KEY)).filter(s -> !s.isEmpty()).map(ResourceLocation::tryParse);
    }

    @Override
    public void setSpellIcon(ItemStack stack, ResourceLocation icon) {
        stack.getOrCreateTag().putString(SPELL_ICON_KEY, icon.toString());
    }

    @Override
    public boolean hasReagents(LivingEntity caster, Collection<ItemFilter> reagents) {
        return !(caster instanceof Player player) || reagents.stream().allMatch(new ItemHandlerExtractionQuery(new PlayerMainInvWrapper(player.getInventory()))::canExtract);
    }

    @Override
    public void consumeReagents(LivingEntity caster, Collection<ItemFilter> reagents) {
        if (!(caster instanceof Player player)) return;
        ItemHandlerExtractionQuery query = new ItemHandlerExtractionQuery(new PlayerMainInvWrapper(player.getInventory()));
        if (reagents.stream().allMatch(f -> query.extract(f).tryCommit())) {
            query.commit();
        }
    }

    @Override
    public void registerParticleSpawner(ISpellComponent component, ISpellParticleSpawner particleSpawner) {
        particleSpawners.put(component, particleSpawner);
    }

    @Override
    public void spawnParticles(ISpellComponent component, ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        ISpellParticleSpawner spawner = particleSpawners.get(component);
        if (spawner != null) {
            spawner.spawnParticles(spell, caster, hit, random, color);
        }
    }

    //Optimized and adapted from GameRenderer#pick
    @Override
    @Nullable
    public Entity getPointedEntity(Entity entity, double range) {
        Vec3 from = entity.getEyePosition(1);
        Vec3 view = entity.getViewVector(1);
        Vec3 to = from.add(view.x * range, view.y * range, view.z * range);
        AABB aabb = entity.getBoundingBox().expandTowards(view.scale(range)).inflate(1, 1, 1);
        EntityHitResult hit = ProjectileUtil.getEntityHitResult(entity, from, to, aabb, e -> !e.isSpectator() && e.isPickable(), range * range);
        return hit != null && from.distanceTo(hit.getLocation()) < range ? hit.getEntity() : null;
    }

    @Override
    public HitResult trace(Entity entity, Level level, double range, boolean entities, boolean targetNonSolid) {
        if (entities) {
            Entity pointed = getPointedEntity(entity, range);
            if (pointed != null) return new EntityHitResult(pointed);
        }
        return level.clip(new ClipContext(entity.getEyePosition(), entity.getEyePosition().add(entity.getLookAngle().scale(range)), ClipContext.Block.OUTLINE, targetNonSolid ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE, entity));
    }

    @Override
    public float getModifiedStat(float baseValue, ISpellPartStat stat, List<ISpellModifier> modifiers, ISpell spell, LivingEntity caster, @Nullable HitResult target, int componentIndex) {
        componentIndex--;
        float modified = baseValue;
        for (ISpellModifier iSpellModifier : modifiers) {
            if (iSpellModifier.getStatsModified().contains(stat)) {
                ISpellPartStatModifier modifier = iSpellModifier.getStatModifier(stat);
                modified = modifier.modify(baseValue, modified, spell, caster, target, componentIndex);
            }
        }
        SpellEvent.ModifyStats event = new SpellEvent.ModifyStats(caster, spell, stat, baseValue, modified);
        MinecraftForge.EVENT_BUS.post(event);
        return event.modified;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, @Nullable HitResult target, int castingTicks, int index, boolean awardXp) {
        List<Pair<? extends ISpellPart, List<ISpellModifier>>> pwm = spell.partsWithModifiers();
        Pair<? extends ISpellPart, List<ISpellModifier>> pair = pwm.get(index);
        ISpellPart part = pair.getFirst();
        List<ISpellModifier> modifiers = pair.getSecond();
        switch (part.getType()) {
            case COMPONENT -> {
                if (level.isClientSide()) return SpellCastResult.SUCCESS;
                ISpellComponent component = (ISpellComponent) part;
                SpellCastResult result = SpellCastResult.EFFECT_FAILED;
                if (MinecraftForge.EVENT_BUS.post(new SpellEvent.Cast.Component(caster, spell, component, modifiers, target)))
                    return SpellCastResult.CANCELLED;
                if (target instanceof EntityHitResult entityHitResult) {
                    result = component.invoke(spell, caster, level, modifiers, entityHitResult, index + 1, castingTicks);
                    if (result.isSuccess()) {
                        ArsMagicaLegacy.NETWORK_HANDLER.sendToAllAround(new SpawnComponentParticlesPacket(component, caster, Either.right(entityHitResult), getColor(modifiers, spell, caster, index + 1, -1)), level, BlockPos.containing(target.getLocation()), 64);
                    }
                }
                if (target instanceof BlockHitResult blockHitResult) {
                    result = component.invoke(spell, caster, level, modifiers, blockHitResult, index + 1, castingTicks);
                    if (result.isSuccess()) {
                        ArsMagicaLegacy.NETWORK_HANDLER.sendToAllAround(new SpawnComponentParticlesPacket(component, caster, Either.left(blockHitResult), getColor(modifiers, spell, caster, index + 1, -1)), level, BlockPos.containing(target.getLocation()), 64);
                    }
                }
                return result.isFail() || index + 1 == pwm.size() ? result : invoke(spell, caster, level, target, castingTicks, index + 1, awardXp);
            }
            case SHAPE -> {
                ISpellShape shape = (ISpellShape) part;
                return shape.invoke(spell, caster, level, modifiers, target, castingTicks, index + 1, awardXp);
            }
            default -> {
                return SpellCastResult.EFFECT_FAILED;
            }
        }
    }

    @Override
    public void nextShapeGroup(ItemStack stack) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        ISpell spell = helper.getSpell(stack);
        byte index = (byte) (spell.currentShapeGroupIndex() + 1);
        long count = spell.shapeGroups().stream().filter(e -> !e.isEmpty()).count();
        if (index >= count) {
            index -= count;
        }
        spell.currentShapeGroupIndex(index);
        helper.setSpell(stack, spell);
    }

    @Override
    public void prevShapeGroup(ItemStack stack) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        ISpell spell = helper.getSpell(stack);
        byte index = (byte) (spell.currentShapeGroupIndex() - 1);
        long count = spell.shapeGroups().stream().filter(e -> !e.isEmpty()).count();
        if (index < 0) {
            index += count;
        }
        spell.currentShapeGroupIndex(index);
        helper.setSpell(stack, spell);
    }

    @Override
    public int getColor(List<ISpellModifier> modifiers, ISpell spell, LivingEntity caster, int index, int defaultColor) {
        return (int) getModifiedStat(defaultColor, SpellPartStats.COLOR, modifiers, spell, caster, null, index);
    }
}
