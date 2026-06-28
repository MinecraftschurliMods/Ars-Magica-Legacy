package com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleController;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleControllerInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public record MoveInViewDirectionController(boolean stopOtherControllers, boolean killOnFinish, double minSpeed, double maxSpeed) implements ParticleController {
    public static final Identifier ID = ArsMagicaApi.id("move_in_view_direction");
    public static final MapCodec<MoveInViewDirectionController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(Codec.DOUBLE.fieldOf("min_speed").forGetter(MoveInViewDirectionController::minSpeed))
        .and(Codec.DOUBLE.fieldOf("max_speed").forGetter(MoveInViewDirectionController::maxSpeed))
        .apply(inst, MoveInViewDirectionController::new));

    public MoveInViewDirectionController(double minSpeed, double maxSpeed) {
        this(false, false, minSpeed, maxSpeed);
    }

    @Override
    public void tick(ParticleControllerInstance instance) {
    }

    @Override
    public void tickFirst(ParticleControllerInstance instance) {
        Entity target = instance.getTargetOrFinish();
        if (target == null) return;
        double yaw = target.getYHeadRot() + 90;
        double speed = Mth.lerp(instance.particle.random().nextDouble(), minSpeed, maxSpeed);
        instance.particle.setParticleSpeed(Math.cos(Math.toRadians(yaw)) * speed, -Math.sin(Math.toRadians(target.getXRot())) * speed, Math.sin(Math.toRadians(yaw)) * speed);
    }

    @Override
    public Identifier id() {
        return ID;
    }
}
