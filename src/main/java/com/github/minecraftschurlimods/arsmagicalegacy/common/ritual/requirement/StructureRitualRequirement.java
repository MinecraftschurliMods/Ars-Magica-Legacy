package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

public record StructureRitualRequirement(Identifier structure, BlockPos offset) implements RitualRequirement {
    public static final MapCodec<StructureRitualRequirement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Identifier.CODEC.fieldOf("structure").forGetter(StructureRitualRequirement::structure),
        BlockPos.CODEC.optionalFieldOf("offset", BlockPos.ZERO).forGetter(StructureRitualRequirement::offset)
    ).apply(inst, StructureRitualRequirement::new));

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec) {
        IMultiblock multiblock = PatchouliAPI.get().getMultiblock(structure);
        return multiblock != null && multiblock.validate(level, BlockPos.containing(vec).offset(offset)) != null;
    }
}
