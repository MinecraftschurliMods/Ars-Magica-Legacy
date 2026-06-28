package com.github.minecraftschurlimods.arsmagicalegacy.common.block;

public class GoldInlayBlock extends InlayBlock {
    public GoldInlayBlock(Properties properties) {
        super(properties);
    }

/* TODO inlays
    @Override
    public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        if (cart.getDeltaMovement().horizontalDistance() > 0.01) {
            Vec3i normal = cart.getMotionDirection().getUnitVec3i();
            int range = AMServerConfig.GOLD_INLAY_RANGE.get();
            for (int i = 0; i < range; i++) {
                pos = pos.offset(normal);
                if (level.getBlockState(pos).is(this)) {
                    cart.setPos(cart.position().add(Vec3.atLowerCornerOf(normal.multiply(i + 1))));
                    break;
                }
            }
        }
        return super.getRailMaxSpeed(state, level, pos, cart);
    }
*/
}
