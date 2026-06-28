package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.block.InscriptionTableBlock;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class InscriptionTableUpgradeItem extends Item {
    private final int tier;

    public InscriptionTableUpgradeItem(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!AMServerConfig.INSCRIPTION_TABLE_IN_WORLD_UPGRADING.get()) return super.useOn(context);
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() == AMBlocks.INSCRIPTION_TABLE.get() && state.getValue(InscriptionTableBlock.TIER) == tier - 1) {
            if (state.getValue(InscriptionTableBlock.HALF) == InscriptionTableBlock.Half.LEFT) {
                pos = pos.relative(state.getValue(InscriptionTableBlock.FACING).getClockWise());
                state = level.getBlockState(pos);
            }
            level.setBlockAndUpdate(pos, state.setValue(InscriptionTableBlock.TIER, tier));
            Player player = context.getPlayer();
            if (player != null && !player.isCreative()) {
                context.getItemInHand().shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
