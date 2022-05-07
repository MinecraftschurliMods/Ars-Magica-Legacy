package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.TriPredicate;

public interface RitualRequirement extends TriPredicate<Player, ServerLevel, BlockPos> {
    Codec<RitualRequirement> CODEC = ResourceLocation.CODEC.dispatch("type", RitualManager::getRitualRequirementType, RitualManager::getRitualRequirementCodec);

    @Override
    boolean test(Player player, ServerLevel serverLevel, BlockPos pos);

    Codec<? extends RitualRequirement> codec();
}
