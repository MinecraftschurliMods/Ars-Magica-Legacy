package at.minecraftschurli.mods.arsmagicalegacy.blockentity;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli.MultiblockMatcher;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEtheriumTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class BlackAuremBlockEntity extends EtheriumGeneratorBlockEntity {
    private static final MultiblockMatcher CHALK = new MultiblockMatcher(AMMultiblocks.BLACK_AUREM_CHALK);
    private static final MultiblockMatcher PILLARS_1 = new MultiblockMatcher(AMMultiblocks.BLACK_AUREM_PILLARS_1);
    private static final MultiblockMatcher PILLARS_2 = new MultiblockMatcher(AMMultiblocks.BLACK_AUREM_PILLARS_2);
    private static final MultiblockMatcher PILLARS_3 = new MultiblockMatcher(AMMultiblocks.BLACK_AUREM_PILLARS_3);
    private static final MultiblockMatcher PILLARS_4 = new MultiblockMatcher(AMMultiblocks.BLACK_AUREM_PILLARS_4);
    private static final String TIME_KEY = "time";
    private int time = 0;

    public BlackAuremBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.BLACK_AUREM.get(), pos, state, AMEtheriumTypes.DARK);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (etherium >= getMaxAmount()) return;
        time--;
        if (time <= 0) {
            time = 6 - getTier(level, pos);
            Vec3 vec3 = Vec3.atBottomCenterOf(pos);
            List<Mob> mobs = level.getEntities(EntityTypeTest.forClass(Mob.class), new AABB(vec3.add(-2, 0, -2), vec3.add(2, 4, 2)), e -> !e.is(AMTags.EntityTypes.BLACK_AUREM_IMMUNE));
            mobs.sort(Comparator.comparingDouble(e -> e.distanceToSqr(vec3)));
            for (Mob mob : mobs) {
                if (mob.isAlive() && !mob.isInvertedHealAndHarm() && mob.hurtServer(serverLevel, level.damageSources().magic(), 1)) {
                    etherium++;
                    break;
                }
            }
        }
        setChanged();
    }

    @Override
    public int getMaxAmount() {
        return AMServerConfig.BLACK_AUREM_MAX_ETHERIUM.get();
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
    public AABB getOutline(Level level, BlockPos pos, BlockState state) {
        return AABB.unitCubeFromLowerCorner(Vec3.ZERO);
    }

    @Override
    public int getOutlineColor(Level level, BlockPos pos, BlockState state) {
        return AMRegistries.etheriumTypes(level.registryAccess()).getOrThrow(AMEtheriumTypes.DARK).value().color();
    }
}
