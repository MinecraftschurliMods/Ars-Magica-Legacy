package at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ControlledParticle;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleController;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleControllerInstance;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

public record FadeOutController(boolean stopOtherControllers, boolean killOnFinish, float speed) implements ParticleController {
    public static final Identifier ID = ArsMagicaApi.id("fade_out");
    public static final MapCodec<FadeOutController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("speed", 0.05f).forGetter(FadeOutController::speed))
        .apply(inst, FadeOutController::new));

    public FadeOutController(float speed) {
        this(false, false, speed);
    }

    public FadeOutController() {
        this(0.05f);
    }

    @Override
    public void tick(ParticleControllerInstance instance) {
        ControlledParticle particle = instance.particle;
        particle.setAlpha(particle.getAlpha() - speed);
        if (particle.getAlpha() <= 0) {
            instance.finish();
        }
    }

    @Override
    public Identifier id() {
        return ID;
    }
}
