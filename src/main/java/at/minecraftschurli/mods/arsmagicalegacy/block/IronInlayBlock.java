package at.minecraftschurli.mods.arsmagicalegacy.block;

public class IronInlayBlock extends InlayBlock {
    public IronInlayBlock(Properties properties) {
        super(properties);
    }

/* TODO inlays
    @Override
    public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        Vec3 deltaMovement = cart.getDeltaMovement();
        cart.setDeltaMovement(-deltaMovement.x(), deltaMovement.y(), -deltaMovement.z());
        return super.getRailMaxSpeed(state, level, pos, cart);
    }
*/
}
