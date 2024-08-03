package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.TriPredicate;

import java.util.function.Function;

public interface RitualRequirement extends TriPredicate<Player, ServerLevel, BlockPos> {
    ResourceKey<Registry<MapCodec<? extends RitualRequirement>>> REGISTRY_KEY = ResourceKey.createRegistryKey(ArsMagicaAPI.resource("ritual_requirement_type"));
    Codec<RitualRequirement> CODEC = Codec.lazyInitialized(() -> ArsMagicaAPI.get().getRitualRequirementTypeRegistry().byNameCodec()).dispatch(RitualRequirement::codec, Function.identity());

    @Override
    boolean test(Player player, ServerLevel serverLevel, BlockPos pos);

    MapCodec<? extends RitualRequirement> codec();
}
