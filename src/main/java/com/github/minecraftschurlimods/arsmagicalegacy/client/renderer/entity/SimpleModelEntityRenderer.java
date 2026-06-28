package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

public class SimpleModelEntityRenderer<T extends Entity, M extends EntityModel<ModelEntityRenderer.State>> extends ModelEntityRenderer<T, ModelEntityRenderer.State, M> {
    private final Identifier texture;

    public SimpleModelEntityRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation, Function<ModelPart, M> modelFactory, Identifier texture) {
        super(context, modelFactory.apply(context.bakeLayer(modelLayerLocation)));
        this.texture = texture;
    }

    @Override
    protected Identifier getTexture(State state) {
        return texture;
    }

    @Override
    public State createRenderState() {
        return new State();
    }
}
