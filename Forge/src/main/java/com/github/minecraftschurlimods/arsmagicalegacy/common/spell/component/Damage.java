package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
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
    private final Predicate<LivingEntity> failIf;

    public Damage(Function<LivingEntity, DamageSource> damageSourceFunction, Function<LivingEntity, Float> damage, Predicate<LivingEntity> failIf) {
        super(SpellPartStats.DAMAGE, SpellPartStats.HEALING);
        this.damageSourceFunction = damageSourceFunction;
        this.damage = damage;
        this.failIf = failIf;
    }

    public Damage(Function<LivingEntity, DamageSource> damageSourceFunction, float damage, Predicate<LivingEntity> failIf) {
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
        if (failIf.test(living)) return SpellCastResult.EFFECT_FAILED;
        float damage = this.damage.apply(living);
        if (living instanceof Player && living != caster && !((ServerLevel) level).getServer().isPvpAllowed() && damage > 0)
            return SpellCastResult.EFFECT_FAILED;
        if (damage < 0) {
            damage = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(damage, SpellPartStats.HEALING, modifiers, spell, caster, target);
            living.heal(-damage);
            return SpellCastResult.SUCCESS;
        }
        damage = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(damage, SpellPartStats.DAMAGE, modifiers, spell, caster, target);
        return living.hurt(damageSourceFunction.apply(caster), damage) ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
