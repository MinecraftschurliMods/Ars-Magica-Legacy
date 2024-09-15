package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttachments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Summon extends AbstractComponent {
    private static final float MANA_PER_HP = 20; //TODO config

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (caster.isShiftKeyDown() && target.getEntity() instanceof Mob mob) {
            ItemStack stack = ArsMagicaAPI.get().getSpellHelper().getSpellItemStackFromEntity(caster);
            if (stack.isEmpty()) return SpellCastResult.EFFECT_FAILED;
            stack.set(AMDataComponents.SELECTED_ENTITY, mob.getType());
            return SpellCastResult.SUCCESS;
        }
        return summon(caster, level, target);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return summon(caster, level, target);
    }
    
    private static SpellCastResult summon(LivingEntity caster, Level level, HitResult target) {
        var api = ArsMagicaAPI.get();
        var spellHelper = api.getSpellHelper();
        var manaHelper = api.getManaHelper();
        Minions minions = caster.hasData(AMAttachments.SUMMON_MINIONS) ? caster.getData(AMAttachments.SUMMON_MINIONS) : new Minions(List.of());
        if (minions.getCount() >= spellHelper.getMaxSummons(caster)) return SpellCastResult.NO_SUMMONS;
        ItemStack stack = spellHelper.getSpellItemStackFromEntity(caster);
        if (stack.isEmpty() || !stack.has(AMDataComponents.SELECTED_ENTITY)) return SpellCastResult.EFFECT_FAILED;
        if (!(level instanceof ServerLevel serverLevel)) return SpellCastResult.EFFECT_FAILED;
        EntityType<?> entityType = stack.get(AMDataComponents.SELECTED_ENTITY);
        if (!(Objects.requireNonNull(entityType).create(level) instanceof Mob mob)) return SpellCastResult.EFFECT_FAILED;
        mob.setPos(target.getLocation());
        EventHooks.finalizeMobSpawn(mob, serverLevel, level.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
        float mana = mob.getMaxHealth() * MANA_PER_HP;
        if (manaHelper.getMana(caster) < mana) return SpellCastResult.NOT_ENOUGH_MANA;
        mob.setData(AMAttachments.SUMMON_OWNER, new Owner(caster.getUUID()));
        caster.setData(AMAttachments.SUMMON_MINIONS, minions.add(mob.getUUID()));
        level.addFreshEntity(mob);
        manaHelper.decreaseMana(caster, mana);
        return SpellCastResult.SUCCESS;
    }

    public record Minions(List<UUID> uuids) {
        public static final MapCodec<Minions> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                UUIDUtil.CODEC.listOf().fieldOf("uuids").forGetter(Minions::uuids)
        ).apply(inst, Minions::new));
        
        public Minions add(UUID uuid) {
            List<UUID> list = new ArrayList<>(uuids);
            list.add(uuid);
            return new Minions(list);
        }
        
        public Minions remove(UUID uuid) {
            List<UUID> list = new ArrayList<>(uuids);
            list.remove(uuid);
            return new Minions(list);
        }
        
        public int getCount() {
            return uuids.size();
        }
    }

    public record Owner(Optional<UUID> uuid) {
        public static final MapCodec<Owner> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                UUIDUtil.CODEC.optionalFieldOf("uuid").forGetter(Owner::uuid)
        ).apply(inst, Owner::new));
        
        public Owner(UUID uuid) {
            this(Optional.of(uuid));
        }
    }
}
