package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.AffinityChangingEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStatModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellParticleSpawner;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.ItemHandlerExtractionQuery;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpawnComponentParticlesPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.server.AMPermissions;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.wrapper.PlayerMainInvWrapper;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class SpellHelper implements ISpellHelper {
    private static final Lazy<SpellHelper> INSTANCE = Lazy.concurrentOf(SpellHelper::new);
    private static final ResourceLocation AFFINITY_GAINS = new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity_gains");
    private final Map<ISpellComponent, ISpellParticleSpawner> particleSpawners = new HashMap<>();

    private SpellHelper() {}

    /**
     * @return The only instance of this class.
     */
    public static SpellHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public SpellCastResult cast(ISpell spell, LivingEntity caster, Level level, int castingTicks, boolean consume, boolean awardXp) {
        if (caster instanceof ServerPlayer player && !PermissionAPI.getPermission(player, AMPermissions.CAN_CAST_SPELL))
            return SpellCastResult.NO_PERMISSION;
        if (NeoForge.EVENT_BUS.post(new SpellEvent.Cast.Pre(caster, spell)).isCanceled()) return SpellCastResult.CANCELLED;
        if (caster.hasEffect(AMMobEffects.SILENCE)) return SpellCastResult.SILENCED;
        float mana = spell.mana(caster);
        float burnout = spell.burnout(caster);
        Collection<ItemFilter> reagents = spell.reagents(caster);
        var api = ArsMagicaAPI.get();
        var manaHelper = api.getManaHelper();
        var burnoutHelper = api.getBurnoutHelper();
        var spellHelper = api.getSpellHelper();
        if (consume && !(caster instanceof Player p && p.isCreative())) {
            if (manaHelper.getMana(caster) < mana) return SpellCastResult.NOT_ENOUGH_MANA;
            if (burnoutHelper.getMaxBurnout(caster) - burnoutHelper.getBurnout(caster) < burnout)
                return SpellCastResult.BURNED_OUT;
            if (!spellHelper.hasReagents(caster, reagents)) return SpellCastResult.MISSING_REAGENTS;
        }
        SpellCastResult result = spellHelper.invoke(spell, caster, level, null, castingTicks, 0, awardXp);
        if (level.isClientSide()) {
            NeoForge.EVENT_BUS.post(new SpellEvent.Cast.Post(caster, spell));
            return result;
        }
        if (caster instanceof Player p && p.isCreative()) return result;
        if (consume && result.isConsume()) {
            manaHelper.decreaseMana(caster, mana, true);
            burnoutHelper.increaseBurnout(caster, burnout);
            spellHelper.consumeReagents(caster, reagents);
        }
        NeoForge.EVENT_BUS.post(new SpellEvent.Cast.Post(caster, spell));
        if (awardXp && result.isSuccess() && caster instanceof Player player) {
            boolean affinityGains = api.getSkillHelper().knows(player, AFFINITY_GAINS) && level.registryAccess().registryOrThrow(Skill.REGISTRY_KEY).containsKey(AFFINITY_GAINS);
            boolean continuous = spell.isContinuous();
            Map<Affinity, Double> affinityShifts = spell.affinityShifts();
            for (Map.Entry<Affinity, Double> entry : affinityShifts.entrySet()) {
                Affinity affinity = entry.getKey();
                Double shift = entry.getValue();
                if (continuous) {
                    shift /= 4;
                }
                if (affinityGains) {
                    shift *= 1.1;
                }
                AffinityChangingEvent.Pre event = new AffinityChangingEvent.Pre(player, affinity, shift.floatValue(), false);
                if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
                    var helper = ArsMagicaAPI.get().getAffinityHelper();
                    helper.applyAffinityShift(player, event.affinity, event.shift);
                    helper.updateLock(player);
                    NeoForge.EVENT_BUS.post(new AffinityChangingEvent.Post(player, event.affinity, (float) helper.getAffinityDepth(player, event.affinity), false));
                }
            }
            float xp = 0.05f * affinityShifts.size();
            if (continuous) xp /= 4;
            if (affinityGains) xp *= 0.9f;
            api.getMagicHelper().awardXp(player, xp);
        }
        return result;
    }

    @Override
    public ISpell getSpell(ItemStack stack) {
        if (stack.isEmpty()) return ISpell.EMPTY;
        return stack.getOrDefault(AMDataComponents.SPELL, ISpell.EMPTY);
    }

    @Override
    public void setSpell(ItemStack stack, ISpell spell) {
        stack.set(AMDataComponents.SPELL, spell);
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
        NeoForge.EVENT_BUS.post(event);
        return event.modified;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, @Nullable HitResult target, int castingTicks, int index, boolean awardXp) {
        List<Pair<? extends ISpellPart, List<ISpellModifier>>> pwm = spell.partsWithModifiers();
        Pair<? extends ISpellPart, List<ISpellModifier>> pair = pwm.get(index);
        ISpellPart part = pair.getFirst();
        List<ISpellModifier> modifiers = pair.getSecond();
        switch (part.getType()) {
            case COMPONENT -> {
                if (level.isClientSide()) return SpellCastResult.SUCCESS;
                ISpellComponent component = (ISpellComponent) part;
                SpellCastResult result = SpellCastResult.EFFECT_FAILED;
                if (NeoForge.EVENT_BUS.post(new SpellEvent.Cast.Component(caster, spell, component, modifiers, target)).isCanceled())
                    return SpellCastResult.CANCELLED;
                switch (target) {
                    case EntityHitResult entityHitResult -> {
                        result = component.invoke(spell, caster, directEntity, level, modifiers, entityHitResult, index + 1, castingTicks);
                        if (result.isSuccess()) {
                            Vec3 location = target.getLocation();
                            PacketDistributor.sendToPlayersNear((ServerLevel) level, null, location.x, location.y, location.z, 64, new SpawnComponentParticlesPacket(component, caster, Either.right(entityHitResult), getColor(modifiers, spell, caster, index + 1, -1)));
                        }
                    }
                    case BlockHitResult blockHitResult -> {
                        result = component.invoke(spell, caster, directEntity, level, modifiers, blockHitResult, index + 1, castingTicks);
                        if (result.isSuccess()) {
                            Vec3 location = target.getLocation();
                            PacketDistributor.sendToPlayersNear((ServerLevel) level, null, location.x, location.y, location.z, 64, new SpawnComponentParticlesPacket(component, caster, Either.left(blockHitResult), getColor(modifiers, spell, caster, index + 1, -1)));
                        }
                    }
                    case null, default -> {}
                }
                return result.isFail() || index + 1 == pwm.size() ? result : invoke(spell, caster, directEntity, level, target, castingTicks, index + 1, awardXp);
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
        long index = (spell.currentShapeGroupIndex() + 1);
        long count = spell.shapeGroups().stream().filter(e -> !e.isEmpty()).count();
        if (index >= count) {
            index -= count;
        }
        spell.currentShapeGroupIndex((byte) index);
        helper.setSpell(stack, spell);
    }

    @Override
    public void prevShapeGroup(ItemStack stack) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        ISpell spell = helper.getSpell(stack);
        long index = (spell.currentShapeGroupIndex() - 1);
        long count = spell.shapeGroups().stream().filter(e -> !e.isEmpty()).count();
        if (index < 0) {
            index += count;
        }
        spell.currentShapeGroupIndex((byte) index);
        helper.setSpell(stack, spell);
    }

    @Override
    public int getColor(List<ISpellModifier> modifiers, ISpell spell, LivingEntity caster, int index, int defaultColor) {
        return (int) getModifiedStat(defaultColor, SpellPartStats.COLOR, modifiers, spell, caster, null, index);
    }

    @Override
    public ItemStack makeSpellFromPrefab(PrefabSpell prefabSpell) {
        ItemStack stack = new ItemStack(AMItems.SPELL.value());
        stack.set(AMDataComponents.SPELL, prefabSpell.spell());
        stack.set(AMDataComponents.SPELL_NAME, prefabSpell.name());
        stack.set(DataComponents.ITEM_NAME, prefabSpell.name());
        stack.set(AMDataComponents.SPELL_ICON, prefabSpell.icon());
        return stack;
    }
}
