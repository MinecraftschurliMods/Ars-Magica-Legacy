package at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ControlledParticle;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleController;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleControllerInstance;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleSpawner;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.AMParticle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;

public record LeaveTrailController(boolean stopOtherControllers, boolean killOnFinish, ParticleSpawner spawner) implements ParticleController {
    public static final Identifier ID = ArsMagicaApi.id("leave_trail");
    public static final MapCodec<LeaveTrailController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(ParticleSpawner.CODEC.fieldOf("spawner").forGetter(LeaveTrailController::spawner))
        .apply(inst, LeaveTrailController::new));

    public LeaveTrailController(ParticleSpawner spawner) {
        this(false, false, spawner);
    }

    @Override
    public void tick(ParticleControllerInstance instance) {
        ControlledParticle particle = instance.particle;
        AMParticle.spawn(particle.level(), particle.x(), particle.y(), particle.z(), spawner, particle.getColor(), instance.caster, instance.directEntity, instance.hitResult);
    }

    @Override
    public Identifier id() {
        return ID;
    }
}
