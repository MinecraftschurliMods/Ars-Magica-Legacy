package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.Nullable;

/// The context of a spell cast, passed into many spell casting methods in [SpellHelper] and related places.
///
/// @param spell        The [Spell] that is cast.
/// @param level        The [Level] the [Spell] is cast in.
/// @param caster       The [LivingEntity] casting the [Spell]. May be null.
/// @param directEntity The entity applying the [Spell], e.g. a projectile. May or may not be identical to the caster. May be null.
/// @param hitResult    The [HitResult] of the spell cast. May be null.
/// @param consume      Whether to consume mana and burnout or not.
/// @param awardXp      Whether to award xp or not.
public record SpellCastContext(Spell spell, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult, boolean consume, boolean awardXp) {
    /// The context of a spell cast, passed into many spell casting methods in [SpellHelper] and related places.
    ///
    /// This constructor is called when beginning a spell cast, as a [direct entity][SpellCastContext#directEntity] and a [hit result][SpellCastContext#hitResult] will never be present at that stage.
    ///
    /// @param spell   The [Spell] that is cast.
    /// @param level   The [Level] the [Spell] is cast in.
    /// @param caster  The [LivingEntity] casting the [Spell]. May be null.
    /// @param consume Whether to consume mana and burnout or not.
    /// @param awardXp Whether to award xp or not.
    public SpellCastContext(Spell spell, Level level, @Nullable LivingEntity caster, boolean consume, boolean awardXp) {
        this(spell, level, caster, null, null, consume, awardXp);
    }

    /// @param spell The [Spell] to set.
    /// @return A new context object with the [Spell] set.
    public SpellCastContext setSpell(Spell spell) {
        return spell == this.spell ? this : new SpellCastContext(spell, level, caster, directEntity, hitResult, consume, awardXp);
    }

    /// @param directEntity The direct [Entity] to set.
    /// @return A new context object with the direct [Entity] set.
    public SpellCastContext setDirectEntity(Entity directEntity) {
        return new SpellCastContext(spell, level, caster, directEntity, hitResult, consume, awardXp);
    }

    /// @param hitResult The [HitResult] to set.
    /// @return A new context object with the [HitResult] set.
    public SpellCastContext setHitResult(HitResult hitResult) {
        return new SpellCastContext(spell, level, caster, directEntity, hitResult, consume, awardXp);
    }

    /// @param directEntity The direct [Entity] to set.
    /// @param hitResult    The [HitResult] to set.
    /// @return A new context object with the direct [Entity] and [HitResult] set.
    public SpellCastContext setDirectEntityAndHitResult(Entity directEntity, HitResult hitResult) {
        return new SpellCastContext(spell, level, caster, directEntity, hitResult, consume, awardXp);
    }

    /// @return Whether the context's [HitResult] is null or a miss, effectively meaning it should not be used.
    public boolean isHitResultNullOrMiss() {
        return hitResult == null || hitResult.getType() == HitResult.Type.MISS;
    }
}
