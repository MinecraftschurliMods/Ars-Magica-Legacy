package at.minecraftschurli.mods.arsmagicalegacy.block;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LiquidEtheriumCauldronBlock extends AbstractCauldronBlock {
    private static final MapCodec<LiquidEtheriumCauldronBlock> CODEC = simpleCodec(LiquidEtheriumCauldronBlock::new);
    public static final CauldronInteraction.Dispatcher CAULDRON_INTERACTIONS = new CauldronInteraction.Dispatcher();
    public static final Identifier CAULDRON_INTERACTIONS_ID = ArsMagicaApi.id("liquid_etherium");

    public LiquidEtheriumCauldronBlock(Properties properties) {
        super(properties, CAULDRON_INTERACTIONS);
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
        return 3;
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return 0.9375;
    }

    public static InteractionResult fillBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack emptyStack) {
        return CauldronInteractions.fillBucket(state, level, pos, player, hand, emptyStack, AMItems.LIQUID_ETHERIUM_BUCKET.toStack(), _ -> true, SoundEvents.BUCKET_FILL);
    }

    public static InteractionResult emptyBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack filledStack) {
        return CauldronInteractions.emptyBucket(level, pos, player, hand, filledStack, AMBlocks.LIQUID_ETHERIUM_CAULDRON.get().defaultBlockState(), SoundEvents.BUCKET_EMPTY);
    }
}
