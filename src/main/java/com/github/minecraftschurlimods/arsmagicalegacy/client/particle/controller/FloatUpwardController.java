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

public record FloatUpwardController(boolean stopOtherControllers, boolean killOnFinish, double jitter, double minSpeed, double maxSpeed) implements ParticleController {
    public static final Identifier ID = ArsMagicaApi.id("float_upward");
    public static final MapCodec<FloatUpwardController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(AMExtraCodecs.NON_NEGATIVE_DOUBLE_CODEC.optionalFieldOf("jitter", 0.).forGetter(FloatUpwardController::jitter))
        .and(Codec.DOUBLE.fieldOf("min_speed").forGetter(FloatUpwardController::minSpeed))
        .and(Codec.DOUBLE.fieldOf("max_speed").forGetter(FloatUpwardController::maxSpeed))
        .apply(inst, FloatUpwardController::new));
    private static final String SPEED_KEY = "speed";

    public FloatUpwardController(double jitter, double minSpeed, double maxSpeed) {
        this(false, false, jitter, minSpeed, maxSpeed);
    }

    public FloatUpwardController(double jitter, double speed) {
        this(false, false, jitter, speed, speed);
    }

    public FloatUpwardController(double speed) {
        this(0, speed);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick(ParticleControllerInstance instance) {
        ControlledParticle particle = instance.particle;
        if (particle.y() > particle.level().getMaxY()) {
            instance.finish();
        } else if (instance.hasContext(SPEED_KEY)) {
            particle.move(particle.random().nextDouble() * jitter - jitter / 2, instance.getContext(SPEED_KEY), particle.random().nextDouble() * jitter - jitter / 2);
        }
    }

    @Override
    public void tickFirst(ParticleControllerInstance instance) {
        instance.setContext(SPEED_KEY, Mth.lerp(instance.particle.random().nextDouble(), minSpeed, maxSpeed));
        tick(instance);
    }

    @Override
    public Identifier id() {
        return ID;
    }
}
