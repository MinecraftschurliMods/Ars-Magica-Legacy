package at.minecraftschurli.mods.arsmagicalegacy.api.client.particle;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/// An instance of a [ParticleController], holding runtime context.
public final class ParticleControllerInstance {
    public final ControlledParticle particle;
    public final ParticleController controller;
    @Nullable
    public final LivingEntity caster;
    @Nullable
    public final Entity directEntity;
    @Nullable
    public final HitResult hitResult;
    private final Map<String, Object> context = new HashMap<>();
    private int tickCount = 0;
    private boolean first = true;
    private boolean finished = false;

    /// @param particle     The [ControlledParticle] the controller belongs to.
    /// @param controller   The [ParticleController] to query for values.
    /// @param caster       The [LivingEntity] casting the [Spell]. May be null if this is not called from a spell cast.
    /// @param directEntity The entity applying the [Spell], e.g. a projectile. May or may not be identical to the caster. May be null if this is not called from a spell cast.
    /// @param hitResult    The [HitResult] of the spell cast. May be null if this is not called from a spell cast.
    public ParticleControllerInstance(ControlledParticle particle, ParticleController controller, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        this.particle = particle;
        this.controller = controller;
        this.caster = caster;
        this.directEntity = directEntity;
        this.hitResult = hitResult;
    }

    /// Ticks the instance.
    public void tick() {
        tickCount++;
        if (particle.isRemoved() || finished) return;
        if (first) {
            controller.tickFirst(this);
            first = false;
        } else {
            controller.tick(this);
        }
    }

    /// Marks the instance as finished and (if [ParticleController#killOnFinish()] is true) removes the particle.
    public void finish() {
        finished = true;
        if (controller.killOnFinish() && !particle.isRemoved()) {
            particle.setRemoved(true);
        }
    }

    /// @return Whether the instance is marked as finished.
    public boolean isFinished() {
        return finished;
    }

    /// @return The amount of ticks this instance has been active so far.
    public int getTickCount() {
        return tickCount;
    }

    /// @param key The key of the context value to check.
    /// @return Whether the instance contains a context value by the given key.
    public boolean hasContext(String key) {
        return context.containsKey(key);
    }

    /// @param key The key of the context value to get.
    /// @return The associated context value.
    /// @param <T> The type of the context value.
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T getContext(String key) {
        return (T) context.get(key);
    }

    /// Puts an extra instance context value.
    ///
    /// @param key   The key of the context value.
    /// @param value The context value.
    /// @param <T> The type of the context value.
    public <T> void setContext(String key, T value) {
        context.put(key, value);
    }

    /// Tries to get the [Entity] from the [HitResult]. If unsuccessful, finishes the controller and returns null.
    ///
    /// @return The target [Entity] or null.
    @Nullable
    public Entity getTargetOrFinish() {
        if (!(hitResult instanceof EntityHitResult result)) {
            finish();
            return null;
        }
        return result.getEntity();
    }

    /// Tries to get the [Entity] from the [HitResult]. If unsuccessful, finishes the controller and returns null.
    ///
    /// @return The target [Entity] or null.
    @Nullable
    public Vec3 getLocationOrFinish() {
        if (hitResult == null) return null;
        if (hitResult.getType() != HitResult.Type.MISS) return hitResult.getLocation();
        finish();
        return null;
    }
}
