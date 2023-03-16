package com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.SimpleEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class BlackAuremBlockEntity extends BlockEntity {
    private final SimpleEtheriumProvider provider = new SimpleEtheriumProvider(EtheriumType.DARK, Config.SERVER.MAX_ETHERIUM_STORAGE.get()).setCallback(BlackAuremBlockEntity::onConsume);
    private final LazyOptional<IEtheriumProvider> etheriumHandler = LazyOptional.of(() -> provider);
    private int timer;

    public BlackAuremBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.BLACK_AUREM.get(), pWorldPosition, pBlockState);
    }

    private static void onConsume(Level level, BlockPos consumerPos, int amount) {
        // TODO spawn particles
    }

    void tick(Level level, BlockPos pos, BlockState state) {
        if (timer <= 0) {
            int tier = state.getBlock() instanceof BlackAuremBlock block ? block.getTier(level, pos) : 0;
            List<LivingEntity> entities = level.getEntities(EntityTypeTest.forClass(LivingEntity.class), AABB.ofSize(Vec3.atCenterOf(pos), 5, 5, 5), livingEntity -> !(livingEntity instanceof Player));
            entities.sort(Comparator.comparingDouble(value -> value.distanceToSqr(pos.getX(), pos.getY(), pos.getZ())));
            int c = Math.max(1, tier / 2);
            for (LivingEntity entity : entities) {
                if (entity.isAlive() && entity.attackable() && !entity.isInvertedHealAndHarm() && entity.hurt(DamageSource.MAGIC, 1)) {
                    provider.add(c);
                    break;
                }
            }
            timer = 6 / (tier + 1);
        } else {
            timer--;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("etheriumValue", provider.getAmount());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        provider.set(tag.getInt("etheriumValue"));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return EtheriumHelper.instance().getEtheriumProviderCapability().orEmpty(cap, etheriumHandler);
    }
}
