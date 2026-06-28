package at.minecraftschurli.mods.arsmagicalegacy.blockentity;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli.MultiblockMatcher;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEtheriumTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.clock.ClockManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;
import org.jspecify.annotations.Nullable;

public class CelestialPrismBlockEntity extends EtheriumGeneratorBlockEntity {
    private static final MultiblockMatcher CHALK = new MultiblockMatcher(AMMultiblocks.CELESTIAL_PRISM_CHALK);
    private static final MultiblockMatcher PILLARS_1 = new MultiblockMatcher(AMMultiblocks.CELESTIAL_PRISM_PILLARS_1);
    private static final MultiblockMatcher PILLARS_2 = new MultiblockMatcher(AMMultiblocks.CELESTIAL_PRISM_PILLARS_2);
    private static final MultiblockMatcher PILLARS_3 = new MultiblockMatcher(AMMultiblocks.CELESTIAL_PRISM_PILLARS_3);
    private static final MultiblockMatcher PILLARS_4 = new MultiblockMatcher(AMMultiblocks.CELESTIAL_PRISM_PILLARS_4);
    private static final String TIME_KEY = "time";
    private int time = 0;

    public CelestialPrismBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.CELESTIAL_PRISM.get(), pos, state, AMEtheriumTypes.LIGHT);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        ClockManager clockManager = level.clockManager();
        Timeline dayTimeline = level.registryAccess().getOrThrow(Timelines.OVERWORLD_DAY).value();
        // TODO use time markers
        if (etherium >= getMaxAmount() || dayTimeline.getCurrentTicks(clockManager) % 24000 >= 12000 || !level.canSeeSky(pos.above())) return;
        time--;
        if (time <= 0) {
            time = 6 - getTier(level, pos);
            etherium++;
        }
        setChanged();
    }

    @Override
    public int getMaxAmount() {
        return AMServerConfig.CELESTIAL_PRISM_MAX_ETHERIUM.get();
    }

    @Override
    public int getTier(Level level, BlockPos pos) {
        if (PILLARS_1.test(level, pos)) return 2;
        if (PILLARS_2.test(level, pos)) return 3;
        if (PILLARS_3.test(level, pos)) return 4;
        if (PILLARS_4.test(level, pos)) return 5;
        return CHALK.test(level, pos) ? 1 : 0;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        time = input.getIntOr(TIME_KEY, 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt(TIME_KEY, time);
    }

    @Override
    @Nullable
    public AABB getOutline(Level level, BlockPos pos, BlockState state) {
        return state.getValue(CelestialPrismBlock.PART) == CelestialPrismBlock.Part.LOWER ? new AABB(Vec3.ZERO, new Vec3(1, 2, 1)) : null;
    }

    @Override
    public int getOutlineColor(Level level, BlockPos pos, BlockState state) {
        return AMRegistries.etheriumTypes(level.registryAccess()).getOrThrow(AMEtheriumTypes.LIGHT).value().color();
    }
}
