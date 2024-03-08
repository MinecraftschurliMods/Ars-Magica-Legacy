package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.TriPredicate;

import java.util.function.Function;

public interface RitualRequirement extends TriPredicate<Player, ServerLevel, BlockPos> {
    ResourceKey<Registry<Codec<? extends RitualRequirement>>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ritual_requirement_type"));
    Codec<RitualRequirement> CODEC = ExtraCodecs.lazyInitializedCodec(() -> ArsMagicaAPI.get().getRitualRequirementTypeRegistry().byNameCodec()).dispatch(RitualRequirement::codec, Function.identity());

    @Override
    boolean test(Player player, ServerLevel serverLevel, BlockPos pos);

    Codec<? extends RitualRequirement> codec();
}
