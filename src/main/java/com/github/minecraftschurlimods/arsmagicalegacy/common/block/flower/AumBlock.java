package com.github.minecraftschurlimods.arsmagicalegacy.common.block.flower;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AumBlock extends FlowerBlock {
    //Uses regeneration and manually returns the actual effect below to circumvent problems with suppliers and laziness
    public AumBlock() {
        super(MobEffects.REGENERATION, 7, BlockBehaviour.Properties.copy(Blocks.POPPY));
    }

    @Override
    public MobEffect getSuspiciousStewEffect() {
        return AMMobEffects.MANA_REGEN.get();
    }
}
