package com.github.minecraftschurli.arsmagicalegacy.client.model;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

public class AffinityOverrideModel extends BakedModelWrapper<BakedModel> {
    private final ItemOverrides overrides = new AffinityItemOverrides();

    public AffinityOverrideModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    private static class AffinityItemOverrides extends ItemOverrides {
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            IAffinity affinity = ArsMagicaAPI.get().getAffinityHelper().getAffinityForStack(stack);
            if (affinity.getId().equals(IAffinity.NONE)) return model;
            ResourceLocation rl = new ResourceLocation(affinity.getId().getNamespace(), "item/" + stack.getItem().getRegistryName().getPath() + "_" + affinity.getId().getPath());
            return Minecraft.getInstance().getModelManager().getModel(rl);
        }
    }
}
