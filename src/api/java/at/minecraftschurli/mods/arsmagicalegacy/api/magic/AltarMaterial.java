package at.minecraftschurli.mods.arsmagicalegacy.api.magic;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;

import java.util.function.Function;

/// Represents an altar's material.
///
/// @param block The [Block] of the material.
/// @param stair The [StairBlock] of the material.
/// @param power The power of the material.
public record AltarMaterial(Block block, StairBlock stair, int power) {
    public static final Codec<AltarMaterial> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(AltarMaterial::block),
        BuiltInRegistries.BLOCK.byNameCodec().comapFlatMap(b -> b instanceof StairBlock s ? DataResult.success(s) : DataResult.error(() -> "Non-stair block " + BuiltInRegistries.BLOCK.getKey(b) + " used as altar material stairs"), Function.identity()).fieldOf("stair").forGetter(AltarMaterial::stair),
        Codec.INT.fieldOf("power").forGetter(AltarMaterial::power)
    ).apply(inst, AltarMaterial::new));
    public static final Codec<Holder<AltarMaterial>> CODEC = RegistryFileCodec.create(AMRegistries.Keys.ALTAR_MATERIAL, DIRECT_CODEC);
}
