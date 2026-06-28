package at.minecraftschurli.mods.arsmagicalegacy.client.renderer.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EtheriumBlockEntityRenderer<T extends BlockEntity> extends AbstractEtheriumBlockEntityRenderer<T, AbstractEtheriumBlockEntityRenderer.RenderState> {
    @SuppressWarnings("unused")
    public EtheriumBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }
}
