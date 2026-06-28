package com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;

/// Accessor interface for a particle controlled by a [ParticleControllerInstance].
@ApiStatus.NonExtendable
public interface ControlledParticle {
    /// @return Whether the particle is removed.
    boolean isRemoved();

    /// Sets the particle to be removed.
    ///
    /// @param removed The removal state to set.
    void setRemoved(boolean removed);

    /// @return The x position of the particle.
    double x();

    /// @return The y position of the particle.
    double y();

    /// @return The z position of the particle.
    double z();

    /// @return The position of the particle.
    Vec3 getPos();

    /// Sets the position of the particle.
    ///
    /// @param x The x position to set.
    /// @param y The y position to set.
    /// @param z The z position to set.
    void setPos(double x, double y, double z);

    /// Sets the speed of the particle.
    ///
    /// @param x The x speed to set.
    /// @param y The y speed to set.
    /// @param z The z speed to set.
    void setParticleSpeed(double x, double y, double z);

    /// @return The color of the particle.
    int getColor();

    /// @param color The color to set.
    void setColor(int color);

    /// @return The alpha value of the particle.
    float getAlpha();

    /// @param alpha The alpha value to set.
    void setAlpha(float alpha);

    /// @return The [ClientLevel] the particle is in.
    ClientLevel level();

    /// @return The particle's [RandomSource].
    RandomSource random();

    /// @return The particle's lifetime.
    int getLifetime();

    /// @param lifetime The lifetime to set.
    void setLifetime(int lifetime);

    /// @param vec The [Vec3] to measure the horizontal distance to.
    /// @return The horizontal distance between the particle and the given [Vec3].
    double horizontalDistanceTo(Vec3 vec);

    /// @param scale The scale factor to apply.
    /// @return The particle.
    Particle scale(float scale);

    /// Moves the particle by the specified amounts.
    ///
    /// @param x The amount to move by in x direction.
    /// @param y The amount to move by in y direction.
    /// @param z The amount to move by in z direction.
    void move(double x, double y, double z);
}
