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
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.ItemHandlerExtractionQuery;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import java.util.List;
import java.util.Optional;

public final class SpellHelper implements ISpellHelper {
    private static final Lazy<SpellHelper> INSTANCE = Lazy.concurrentOf(SpellHelper::new);
    private static final String SPELL_KEY = ArsMagicaAPI.MOD_ID + ":spell";
    private static final String SPELL_ICON_KEY = ArsMagicaAPI.MOD_ID + ":spell_icon";
    private static final String SPELL_NAME_KEY = ArsMagicaAPI.MOD_ID + ":spell_name";

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
    @Nullable
    public Entity getPointedEntity(Entity entity, Level level, double range, boolean clipFluids) {
        Entity result = null;
        Vec3 vec = new Vec3(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
        Vec3 lookVec = entity.getLookAngle();
        double d = 0;
        for (Entity e : level.getEntities(entity, entity.getBoundingBox().inflate(lookVec.x * range, lookVec.y * range, lookVec.z * range).inflate(1, 1, 1))) {
            HitResult hit = level.clip(new ClipContext(new Vec3(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ()), new Vec3(e.getX(), e.getY() + e.getEyeHeight(), e.getZ()), ClipContext.Block.COLLIDER, clipFluids ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, entity));
            if (e.canBeCollidedWith() && hit.getType() == HitResult.Type.MISS) {
                float size = Math.max(0.8F, e.getBbWidth());
                AABB aabb = e.getBoundingBox().inflate(size, size, size);
                Optional<Vec3> optional = aabb.clip(vec, lookVec);
                if (aabb.contains(vec)) {
                    result = e;
                    d = 0;
                } else if (optional.isPresent()) {
                    double distance = vec.distanceTo(optional.get());
                    if ((distance < d) || (d == 0)) {
                        result = e;
                        d = distance;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public HitResult trace(Entity entity, Level level, double range, boolean entities, boolean targetNonSolid) {
        HitResult entityHit = null;
        if (entities) {
            Entity pointed = getPointedEntity(entity, level, range, targetNonSolid);
            if (pointed != null) {
                entityHit = new EntityHitResult(pointed);
            }
        }
        HitResult blockHit = level.clip(new ClipContext(entity.getEyePosition(), entity.getEyePosition().add(entity.getLookAngle().scale(range)), ClipContext.Block.OUTLINE, targetNonSolid ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE, entity));
        return entityHit == null || blockHit.getLocation().distanceTo(entity.position()) < entityHit.getLocation().distanceTo(entity.position()) ? blockHit : entityHit;
    }

    @Override
    public float getModifiedStat(float baseValue, ISpellPartStat stat, List<ISpellModifier> modifiers, ISpell spell, LivingEntity caster, @Nullable HitResult target) {
        float modified = baseValue;
        for (ISpellModifier iSpellModifier : modifiers) {
            if (iSpellModifier.getStatsModified().contains(stat)) {
                ISpellPartStatModifier modifier = iSpellModifier.getStatModifier(stat);
                modified = modifier.modify(baseValue, modified, spell, caster, target);
            }
        }
        return modified;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, @Nullable HitResult target, int castingTicks, int index, boolean awardXp) {
        List<Pair<? extends ISpellPart, List<ISpellModifier>>> pwm = spell.partsWithModifiers();
        Pair<? extends ISpellPart, List<ISpellModifier>> part = pwm.get(index);
        switch (part.getFirst().getType()) {
            case COMPONENT -> {
                SpellCastResult result = SpellCastResult.EFFECT_FAILED;
                ISpellComponent component = (ISpellComponent) part.getFirst();
                if (MinecraftForge.EVENT_BUS.post(new SpellEvent.Cast.Component(caster, spell, component, part.getSecond(), target))) return SpellCastResult.CANCELLED;
                if (target instanceof EntityHitResult entityHitResult) {
                    result = component.invoke(spell, caster, level, part.getSecond(), entityHitResult, index + 1, castingTicks);
                }
                if (target instanceof BlockHitResult blockHitResult) {
                    result = component.invoke(spell, caster, level, part.getSecond(), blockHitResult, index + 1, castingTicks);
                }
                return result.isFail() || index + 1 == pwm.size() ? result : invoke(spell, caster, level, target, castingTicks, index + 1, awardXp);
            }
            case SHAPE -> {
                ISpellShape shape = (ISpellShape) part.getFirst();
                return shape.invoke(spell, caster, level, part.getSecond(), target, castingTicks, index + 1, awardXp);
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
        spell.currentShapeGroupIndex((byte) ((spell.currentShapeGroupIndex() + 1) % spell.shapeGroups().stream().filter(e -> !e.isEmpty()).count()));
        helper.setSpell(stack, spell);
    }
}
