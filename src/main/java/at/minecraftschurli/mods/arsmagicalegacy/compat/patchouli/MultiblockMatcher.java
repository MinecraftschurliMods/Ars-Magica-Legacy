package at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.function.BiPredicate;

public record MultiblockMatcher(Identifier identifier) implements BiPredicate<Level, BlockPos> {
    @Override
    public boolean test(Level level, BlockPos pos) {
        IMultiblock multiblock = PatchouliAPI.get().getMultiblock(identifier);
        return multiblock != null && multiblock.validate(level, pos) != null;
    }
}
