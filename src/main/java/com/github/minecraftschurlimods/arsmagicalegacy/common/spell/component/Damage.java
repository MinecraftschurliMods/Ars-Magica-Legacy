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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Damage extends AbstractComponent {
    private final Function<LivingEntity, DamageSource> damageSourceFunction;
    private final Function<LivingEntity, Float> damage;
    private final Predicate<Entity> failIf;

    public Damage(Function<LivingEntity, DamageSource> damageSourceFunction, Function<LivingEntity, Float> damage, Predicate<Entity> failIf) {
        super(SpellPartStats.HEALING, SpellPartStats.DAMAGE);
        this.damageSourceFunction = damageSourceFunction;
        this.damage = damage;
        this.failIf = failIf;
    }

    public Damage(Function<LivingEntity, DamageSource> damageSourceFunction, float damage, Predicate<Entity> failIf) {
        this(damageSourceFunction, e -> damage, failIf);
    }

    public Damage(Function<LivingEntity, DamageSource> damageSourceFunction, Function<LivingEntity, Float> damage) {
        this(damageSourceFunction, damage, e -> false);
    }

    public Damage(Function<LivingEntity, DamageSource> damageSourceFunction, float damage) {
        this(damageSourceFunction, e -> damage, e -> false);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (!(target.getEntity() instanceof LivingEntity living)) return SpellCastResult.EFFECT_FAILED;
        if (living instanceof Player && living != caster && !((ServerLevel) level).getServer().isPvpAllowed())
            return SpellCastResult.EFFECT_FAILED;
        if (!failIf.test(living)) {
            float damage = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(this.damage.apply(living), SpellPartStats.DAMAGE, modifiers, spell, caster, target);
            if (damage < 0) {
                living.heal(-damage);
                return SpellCastResult.SUCCESS;
            }
            return living.hurt(damageSourceFunction.apply(caster), damage) ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
