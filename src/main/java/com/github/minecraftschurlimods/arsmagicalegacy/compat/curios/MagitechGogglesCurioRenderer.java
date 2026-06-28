package com.github.minecraftschurlimods.arsmagicalegacy.compat.curios;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class MagitechGogglesCurioRenderer implements ICurioRenderer {
    private final ArmorModelSet<PlayerModel> armorModelSet;
    private final ArmorModelSet<PlayerModel> slimArmorModelSet;

    public MagitechGogglesCurioRenderer() {
        armorModelSet = armorModelSet(false);
        slimArmorModelSet = armorModelSet(true);
    }

    @Override
    public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S renderState, RenderLayerParent<S, M> renderLayerParent, EntityRendererProvider.Context context, float yRotation, float xRotation) {
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && renderState instanceof AvatarRenderState state && renderLayerParent.getModel() instanceof PlayerModel playerModel) {
            context.getEquipmentRenderer().renderLayers(renderState.isBaby ? EquipmentClientInfo.LayerType.HUMANOID_BABY : EquipmentClientInfo.LayerType.HUMANOID, equippable.assetId().orElseThrow(), (playerModel.slim ? slimArmorModelSet : armorModelSet).get(EquipmentSlot.HEAD), state, stack, poseStack, submitNodeCollector, renderState.lightCoords, renderState.outlineColor);
        }
    }

    private static ArmorModelSet<PlayerModel> armorModelSet(boolean slim) {
        return ArmorModelSet.bake(slim ? ModelLayers.PLAYER_SLIM_ARMOR : ModelLayers.PLAYER_ARMOR, AMClientUtil.mc().getEntityModels(), part -> new PlayerModel(part, slim));
    }
}
