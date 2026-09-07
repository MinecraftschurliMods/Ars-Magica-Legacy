package at.minecraftschurli.mods.arsmagicalegacy.block;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMFluids;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class LiquidEtheriumBlock extends LiquidBlock {
    public LiquidEtheriumBlock(Properties properties) {
        super(AMFluids.LIQUID_ETHERIUM.get(), properties.mapColor(MapColor.CLAY).replaceable().noCollision().strength(100).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY).lightLevel(_ -> 5));
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(AMMobEffects.MANA_REGENERATION, 1));
        }
        super.entityInside(state, level, pos, entity, effectApplier, isPrecise);
    }
}
