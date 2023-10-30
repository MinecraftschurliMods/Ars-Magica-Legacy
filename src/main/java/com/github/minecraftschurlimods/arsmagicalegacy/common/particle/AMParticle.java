package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;

import java.util.ArrayList;
import java.util.List;

public class AMParticle extends TextureSheetParticle {
    private final List<ParticleController> controllers = new ArrayList<>();

    public AMParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
    }

    @Override
    public void tick() {
        super.tick();
        for (ParticleController controller : controllers) {
            controller.baseTick();
            if (controller.stopOtherControllers) break;
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public AMParticle addController(ParticleController controller) {
        controllers.add(controller);
        return this;
    }
}
