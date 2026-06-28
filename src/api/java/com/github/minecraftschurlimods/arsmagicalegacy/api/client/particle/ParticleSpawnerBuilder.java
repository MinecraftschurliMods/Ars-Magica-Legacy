package com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/// Builder class for [ParticleSpawner], for use in [ParticleSpawnerProvider]. Get an instance via [ParticleSpawnerProvider#builder(Identifier, ParticleOptions, int, int)].
public class ParticleSpawnerBuilder {
    public final Identifier id;
    private final List<ICondition> conditions = new ArrayList<>();
    private final ParticleOptions particle;
    private final int count;
    private final int minLifetime;
    private final int maxLifetime;
    private final List<ParticleController> controllers = new ArrayList<>();
    private Vec3 minOffset = Vec3.ZERO;
    private Vec3 maxOffset = Vec3.ZERO;
    private Vec3 minSpeed = Vec3.ZERO;
    private Vec3 maxSpeed = Vec3.ZERO;
    private float gravity = 0;
    private float scale = 1;
    private int color = -1;
    private float alpha = 1;

    /// @param id          The id of the [ParticleSpawner] being built.
    /// @param particle    The [ParticleOptions] to use.
    /// @param count       The amount of particles to spawn.
    /// @param minLifetime The min lifetime of the particles.
    /// @param maxLifetime The max lifetime of the particles.
    public ParticleSpawnerBuilder(Identifier id, ParticleOptions particle, int count, int minLifetime, int maxLifetime) {
        this.id = id;
        this.particle = particle;
        this.count = count;
        this.minLifetime = minLifetime;
        this.maxLifetime = maxLifetime;
    }

    /// @param id       The id of the [ParticleSpawner] being built.
    /// @param particle The [ParticleOptions] to use.
    /// @param count    The amount of particles to spawn.
    /// @param lifetime The lifetime of the particles.
    public ParticleSpawnerBuilder(Identifier id, ParticleOptions particle, int count, int lifetime) {
        this(id, particle, count, lifetime, lifetime);
    }

    /// Sets the offset of the particles. Particles will be randomly offset between (minX, minY, minZ) and (maxX, maxY, maxZ).
    ///
    /// @param minX The min x offset.
    /// @param maxX The max x offset.
    /// @param minY The min y offset.
    /// @param maxY The max y offset.
    /// @param minZ The min z offset.
    /// @param maxZ The max z offset.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder offset(double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
        minOffset = new Vec3(minX, minY, minZ);
        maxOffset = new Vec3(maxX, maxY, maxZ);
        return this;
    }

    /// Sets the offset of the particles.
    ///
    /// @param x The x offset.
    /// @param y The y offset.
    /// @param z The z offset.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder offset(double x, double y, double z) {
        minOffset = maxOffset = new Vec3(x, y, z);
        return this;
    }

    /// Sets the speed of the particles. Particles will be randomly given a speed between (minX, minY, minZ) and (maxX, maxY, maxZ).
    ///
    /// @param minX The min x speed.
    /// @param maxX The max x speed.
    /// @param minY The min y speed.
    /// @param maxY The max y speed.
    /// @param minZ The min z speed.
    /// @param maxZ The max z speed.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder speed(double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
        minSpeed = new Vec3(minX, minY, minZ);
        maxSpeed = new Vec3(maxX, maxY, maxZ);
        return this;
    }

    /// Sets the speed of the particles. Particles will be randomly given a speed between (minX, y, minZ) and (maxX, y, maxZ).
    ///
    /// @param minX The min x speed.
    /// @param maxX The max x speed.
    /// @param y    The y speed.
    /// @param minZ The min z speed.
    /// @param maxZ The max z speed.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder speed(double minX, double maxX, double y, double minZ, double maxZ) {
        minSpeed = new Vec3(minX, y, minZ);
        maxSpeed = new Vec3(maxX, y, maxZ);
        return this;
    }

    /// Sets the speed of the particles.
    ///
    /// @param x The x speed.
    /// @param y The y speed.
    /// @param z The z speed.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder speed(double x, double y, double z) {
        minSpeed = maxSpeed = new Vec3(x, y, z);
        return this;
    }

    /// @param gravity The gravity of the particles.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder gravity(float gravity) {
        this.gravity = gravity;
        return this;
    }

    /// @param scale The scale of the particles.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder scale(float scale) {
        this.scale = scale;
        return this;
    }

    /// @param color The color of the particles.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder color(int color) {
        this.color = color;
        return this;
    }

    /// @param alpha The alpha of the particles.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    /// @param controller The [ParticleController] to add.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder controller(ParticleController controller) {
        this.controllers.add(controller);
        return this;
    }

    /// @param condition The [ICondition] to add.
    /// @return This builder, for chaining.
    public ParticleSpawnerBuilder addCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    /// @return All [ICondition] in the builder.
    public List<ICondition> getConditions() {
        return Collections.unmodifiableList(conditions);
    }

    /// @return The built [ParticleSpawner].
    public ParticleSpawner build() {
        return new ParticleSpawner(particle, count, minLifetime, maxLifetime, minOffset, maxOffset, minSpeed, maxSpeed, gravity, scale, color, alpha, controllers);
    }
}
