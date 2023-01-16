package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.TriPredicate;

public interface IRitualRequirement extends TriPredicate<Player, ServerLevel, BlockPos> {
    Codec<IRitualRequirement> CODEC = ResourceLocation.CODEC.dispatch("type", ArsMagicaAPI.get().getRitualManager()::getRitualRequirementType, ArsMagicaAPI.get().getRitualManager()::getRitualRequirementCodec);

    @Override
    boolean test(Player player, ServerLevel serverLevel, BlockPos pos);

    Codec<? extends IRitualRequirement> codec();
}
