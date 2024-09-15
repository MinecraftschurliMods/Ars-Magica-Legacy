package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Damage extends AbstractComponent.OnEntity {
    private final DamageSourceFunction damageSourceFunction;
    private final Function<LivingEntity, Double> damage;

    public Damage(DamageSourceFunction damageSourceFunction, Function<LivingEntity, Double> damage) {
        super(SpellPartStats.DAMAGE, SpellPartStats.HEALING);
        this.damageSourceFunction = damageSourceFunction;
        this.damage = damage;
    }

    public Damage(DamageSourceFunction damageSourceFunction, Supplier<Double> damage) {
        this(damageSourceFunction, e -> damage.get());
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (!(target.getEntity() instanceof LivingEntity living)) return SpellCastResult.EFFECT_FAILED;
        DamageSource damageSource = damageSourceFunction.getDamageSource(caster, directEntity);
        if (target.getEntity().isInvulnerableTo(damageSource)) return SpellCastResult.EFFECT_FAILED;
        float damage = this.damage.apply(living).floatValue();
        if (living instanceof Player && living != caster && !((ServerLevel) level).getServer().isPvpAllowed() && damage > 0)
            return SpellCastResult.EFFECT_FAILED;
        if (damage < 0) {
            damage = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(-damage, SpellPartStats.HEALING, modifiers, spell, caster, target, index);
            living.heal(damage);
            return SpellCastResult.SUCCESS;
        }
        damage = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(damage, SpellPartStats.DAMAGE, modifiers, spell, caster, target, index);
        return living.hurt(damageSource, damage) ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @FunctionalInterface
    public interface DamageSourceFunction {
        DamageSource getDamageSource(LivingEntity entity, @Nullable Entity directEntity);
    }
}
