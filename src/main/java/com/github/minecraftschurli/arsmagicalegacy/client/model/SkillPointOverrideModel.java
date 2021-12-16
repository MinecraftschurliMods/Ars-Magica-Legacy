package com.github.minecraftschurli.arsmagicalegacy.client.model;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

public class SkillPointOverrideModel extends BakedModelWrapper<BakedModel> {
    private final ItemOverrides overrides = new SkillPointItemOverrides();

    public SkillPointOverrideModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    private static class SkillPointItemOverrides extends ItemOverrides {
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            ISkillPoint skillPoint = ArsMagicaAPI.get().getSkillHelper().getSkillPointForStack(stack);
            ResourceLocation rl = new ResourceLocation(skillPoint.getId().getNamespace(), "item/" + stack.getItem().getRegistryName().getPath() + "_" + skillPoint.getId().getPath());
            return Minecraft.getInstance().getModelManager().getModel(rl);
        }
    }
}
