package at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleController;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleControllerInstance;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;

public record ChangeSizeController(boolean stopOtherControllers, boolean killOnFinish, float from, float to, int duration) implements ParticleController {
    public static final Identifier ID = ArsMagicaApi.id("change_size");
    public static final MapCodec<ChangeSizeController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("from", 0.5f).forGetter(ChangeSizeController::from))
        .and(ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("to", 0.05f).forGetter(ChangeSizeController::to))
        .and(ExtraCodecs.POSITIVE_INT.optionalFieldOf("duration", 5).forGetter(ChangeSizeController::duration))
        .apply(inst, ChangeSizeController::new));

    public ChangeSizeController(float from, float to, int duration) {
        this(false, false, from, to, duration);
    }

    public ChangeSizeController() {
        this(0.5f, 0.05f, 5);
    }

    @Override
    public void tick(ParticleControllerInstance instance) {
        instance.particle.scale(Mth.lerp(Math.clamp(instance.getTickCount() / duration, 0, 1), from, to));
    }

    @Override
    public Identifier id() {
        return ID;
    }
}
