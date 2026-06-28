package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BossRenderer<T extends AbstractBoss, R extends LivingEntityRenderState> extends GeoEntityRenderer<T, R> {
    private final Map<String, Function<T, Boolean>> boneVisibilities;

    public BossRenderer(EntityRendererProvider.Context context, EntityType<? extends T> type, Map<String, Function<T, Boolean>> boneVisibilities) {
        super(context, type);
        this.boneVisibilities = boneVisibilities;
    }

    @Override
    public void addRenderData(T animatable, @Nullable Void relatedObject, R renderState, float partialTick) {
        renderState.addGeckolibData(AMClientUtil.ACTION_DATA_TICKET, animatable.getAction());
        List<String> hiddenBones = new ArrayList<>();
        for (Map.Entry<String, Function<T, Boolean>> entry : boneVisibilities.entrySet()) {
            if (entry.getValue().apply(animatable)) {
                hiddenBones.add(entry.getKey());
            }
        }
        renderState.addGeckolibData(AMClientUtil.HIDDEN_BONES_DATA_TICKET, hiddenBones);
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<R> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);
        List<String> bones = renderPassInfo.getGeckolibData(AMClientUtil.HIDDEN_BONES_DATA_TICKET);
        if (bones == null) return;
        for (String bone : bones) {
            snapshots.ifPresent(bone, b -> b.skipRender(true));
        }
    }

    public static <T extends AbstractBoss> void register(EntityRenderersEvent.RegisterRenderers event, DeferredHolder<EntityType<?>, EntityType<T>> holder) {
        register(event, holder, Map.of());
    }

    public static <T extends AbstractBoss> void register(EntityRenderersEvent.RegisterRenderers event, DeferredHolder<EntityType<?>, EntityType<T>> holder, Map<String, Function<T, Boolean>> boneVisibilities) {
        event.registerEntityRenderer(holder.get(), context -> new BossRenderer<>(context, holder.get(), boneVisibilities));
    }
}
