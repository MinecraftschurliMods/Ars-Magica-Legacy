package at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ControlledParticle;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleController;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleControllerInstance;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMExtraCodecs;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.EntityHitResult;

public record ApproachEntityController(boolean stopOtherControllers, boolean killOnFinish, double speed, double distance) implements ParticleController {
    public static final Identifier ID = ArsMagicaApi.id("approach_entity");
    public static final MapCodec<ApproachEntityController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(AMExtraCodecs.POSITIVE_DOUBLE_CODEC.fieldOf("speed").forGetter(ApproachEntityController::speed))
        .and(AMExtraCodecs.POSITIVE_DOUBLE_CODEC.fieldOf("distance").forGetter(ApproachEntityController::distance))
        .apply(inst, ApproachEntityController::new));

    public ApproachEntityController(double speed, double distance) {
        this(false, false, speed, distance);
    }

    @Override
    public void tick(ParticleControllerInstance instance) {
        if (!(instance.hitResult instanceof EntityHitResult result) || result.getEntity() instanceof LivingEntity living && living.isDeadOrDying() || instance.particle.horizontalDistanceTo(result.getEntity().position()) <= distance) {
            instance.finish();
        }
    }

    @Override
    public void tickFirst(ParticleControllerInstance instance) {
        Entity target = instance.getTargetOrFinish();
        if (target == null) return;
        ControlledParticle particle = instance.particle;
        double x = target.getX() - particle.x();
        double z = target.getZ() - particle.z();
        double angle = Math.atan2(z, x);
        double y = particle.y() - switch (target) {
            case LivingEntity living -> living.getEyeY();
            case ItemEntity item -> item.getY();
            default -> target.getY() + target.getBbHeight() / 2;
        };
        particle.setParticleSpeed(speed * Math.cos(angle), speed * Math.sin(-Math.atan2(y, Math.sqrt(x * x + z * z))), speed * Math.sin(angle));
    }

    @Override
    public Identifier id() {
        return ID;
    }
}
