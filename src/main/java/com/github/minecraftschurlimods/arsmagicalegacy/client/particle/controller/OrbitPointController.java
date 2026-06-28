package com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ControlledParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleController;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleControllerInstance;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMExtraCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public record OrbitPointController(boolean stopOtherControllers, boolean killOnFinish, double minSpeed, double maxSpeed, double minDistance, double maxDistance, boolean followTarget) implements ParticleController {
    public static final Identifier ID = ArsMagicaApi.id("orbit_point");
    public static final MapCodec<OrbitPointController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(AMExtraCodecs.doubleRangeCodec(0, 180).fieldOf("min_speed").forGetter(OrbitPointController::minSpeed))
        .and(AMExtraCodecs.doubleRangeCodec(0, 180).fieldOf("max_speed").forGetter(OrbitPointController::maxSpeed))
        .and(AMExtraCodecs.NON_NEGATIVE_DOUBLE_CODEC.optionalFieldOf("min_distance", 1.).forGetter(OrbitPointController::minDistance))
        .and(AMExtraCodecs.POSITIVE_DOUBLE_CODEC.optionalFieldOf("max_distance", 1.5).forGetter(OrbitPointController::maxDistance))
        .and(Codec.BOOL.optionalFieldOf("follow_target", false).forGetter(OrbitPointController::followTarget))
        .apply(inst, OrbitPointController::new));
    private static final String ANGLE_KEY = "angle";
    private static final String CLOCKWISE_KEY = "clockwise";
    private static final String DISTANCE_KEY = "distance";
    private static final String SPEED_KEY = "speed";

    public OrbitPointController(double minSpeed, double maxSpeed, double minDistance, double maxDistance, boolean followTarget) {
        this(false, false, minSpeed, maxSpeed, minDistance, maxDistance, followTarget);
    }

    public OrbitPointController(double speed, double minDistance, double maxDistance, boolean followTarget) {
        this(speed, speed, minDistance, maxDistance, followTarget);
    }

    public OrbitPointController(double minSpeed, double maxSpeed, boolean followTarget) {
        this(minSpeed, maxSpeed, 1, 1.5, followTarget);
    }

    public OrbitPointController(double speed, boolean followTarget) {
        this(speed, speed, 1, 1.5, followTarget);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick(ParticleControllerInstance instance) {
        Vec3 target = followTarget ? instance.getTargetOrFinish().position() : instance.getLocationOrFinish();
        if (target == null || !instance.hasContext(ANGLE_KEY) || !instance.hasContext(DISTANCE_KEY) || !instance.hasContext(CLOCKWISE_KEY)) return;
        double angle = instance.getContext(ANGLE_KEY);
        double distance = instance.getContext(DISTANCE_KEY);
        angle += instance.getContext(CLOCKWISE_KEY) ? minSpeed : -minSpeed + 360;
        angle %= 360;
        instance.setContext(ANGLE_KEY, angle);
        ControlledParticle particle = instance.particle;
        particle.setPos(target.x() + Math.cos(angle) * distance, particle.y(), target.z() + Math.sin(angle) * distance);
    }

    @Override
    public void tickFirst(ParticleControllerInstance instance) {
        RandomSource random = instance.particle.random();
        instance.setContext(ANGLE_KEY, random.nextDouble() * 360);
        instance.setContext(CLOCKWISE_KEY, random.nextBoolean());
        instance.setContext(DISTANCE_KEY, Mth.lerp(random.nextDouble(), minDistance, maxDistance));
        instance.setContext(SPEED_KEY, Mth.lerp(random.nextDouble(), minSpeed, maxSpeed));
        tick(instance);
    }

    @Override
    public Identifier id() {
        return ID;
    }
}
