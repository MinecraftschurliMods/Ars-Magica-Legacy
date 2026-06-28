package com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ControlledParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleController;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleControllerInstance;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMExtraCodecs;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record ArcToEntityController(boolean stopOtherControllers, boolean killOnFinish, double speed) implements ParticleController {
    public static final Identifier ID = ArsMagicaApi.id("arc_to_entity");
    public static final MapCodec<ArcToEntityController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(AMExtraCodecs.POSITIVE_DOUBLE_CODEC.optionalFieldOf("speed", 0.05).forGetter(ArcToEntityController::speed))
        .apply(inst, ArcToEntityController::new));
    private static final String DELTA_KEY = "delta";

    public ArcToEntityController(double speed) {
        this(false, false, speed);
    }

    public ArcToEntityController() {
        this(0.05);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick(ParticleControllerInstance instance) {
        Entity target = instance.getTargetOrFinish();
        if (target == null || !instance.hasContext(DELTA_KEY)) return;
        double delta = (double) instance.getContext(DELTA_KEY) + speed;
        if (delta >= 1) {
            instance.finish();
            return;
        }
        instance.setContext(DELTA_KEY, delta);
        ControlledParticle particle = instance.particle;
        Vec3 start = particle.getPos();
        Vec3 offset = new Vec3(particle.random().nextDouble() * 0.5 - 0.25, 0, particle.random().nextDouble() * 0.5 - 0.25);
        Vec3 control1 = new Vec3(start.x + (target.getX() - start.x) / 3, start.y + (target.getEyeY() - start.y) / 3, start.z + (target.getZ() - start.z) / 3).add(offset);
        Vec3 control2 = new Vec3(start.x + (target.getX() - start.x) / 3 * 2, start.y + (target.getEyeY() - start.y) / 3 * 2, start.z + (target.getZ() - start.z) / 3 * 2).add(offset);
        Vec3 bezier = AMUtil.bezier(start, control1, control2, target.getEyePosition(), delta);
        particle.setPos(bezier.x, bezier.y, bezier.z);
    }

    @Override
    public Identifier id() {
        return ID;
    }
}
