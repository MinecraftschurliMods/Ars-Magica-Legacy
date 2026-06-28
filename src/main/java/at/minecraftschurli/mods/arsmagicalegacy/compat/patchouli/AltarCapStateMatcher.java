package at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

import java.util.Objects;

public final class AltarCapStateMatcher implements IStateMatcher {
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    public AltarCapStateMatcher() {
        predicate = (level, _, state) -> AMRegistries.altarCapMaterials(level instanceof Level l ? l.registryAccess() : AMRegistries.registryAccess(false))
            .stream()
            .anyMatch(material -> state.is(material.block()));
    }

    @Override
    public BlockState getDisplayedState(long ticks) {
        AltarCapMaterial material = AMUtil.getByTick(AMRegistries.altarCapMaterials(true)
            .stream()
            .toArray(AltarCapMaterial[]::new), (int) ticks / 20);
        return Objects.requireNonNull(material).block().defaultBlockState();
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
