package at.minecraftschurli.mods.arsmagicalegacy.block;

public class RedstoneInlayBlock extends InlayBlock {
    public RedstoneInlayBlock(Properties properties) {
        super(properties);
    }

/* TODO inlays
    @Override
    public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        float speed = super.getRailMaxSpeed(state, level, pos, cart) * AMServerConfig.REDSTONE_INLAY_SPEED_MULTIPLIER.get().floatValue();
        Vec3 deltaMovement = cart.getDeltaMovement();
        double x = deltaMovement.x();
        double z = deltaMovement.z();
        cart.setDeltaMovement(Math.signum(x) * Math.max(Math.abs(x) * speed, speed), deltaMovement.y(), Math.signum(z) * Math.max(Math.abs(z) * speed, speed));
        return speed;
    }
*/
}
