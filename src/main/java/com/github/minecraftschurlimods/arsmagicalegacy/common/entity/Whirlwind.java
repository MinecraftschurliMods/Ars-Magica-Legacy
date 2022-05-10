package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageSources;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class Whirlwind extends Entity {
    private final Map<Player, Integer> cooldowns = new HashMap<>();

    public Whirlwind(EntityType<? extends Whirlwind> type, Level level) {
        super(type, level);
    }

    /**
     * Creates a new instance of this class in the given level. This is necessary, as otherwise the entity registration yells at us with some weird overloading error.
     *
     * @param level the level to create the new instance in
     * @return a new instance of this class in the given level
     */
    public static Whirlwind create(Level level) {
        return new Whirlwind(AMEntities.WHIRLWIND.get(), level);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide() && tickCount > 140) {
            kill();
        }
        cooldowns.replaceAll((k, v) -> Math.max(v - 1, 0));
        setPos(position().add(getDeltaMovement()));
    }

    @Override
    public void playerTouch(Player pPlayer) {
        super.playerTouch(pPlayer);
        if (level.isClientSide() || pPlayer.isCreative()) return;
        Integer cd = cooldowns.get(pPlayer);
        if (cd == null || cd <= 0) {
            if (!level.isClientSide && level.getRandom().nextInt(100) < 10) {
                int slot = pPlayer.getInventory().items.size() + level.getRandom().nextInt(4);
                ItemStack stack = pPlayer.getInventory().getItem(slot).copy();
                if (!pPlayer.getInventory().add(stack)) {
                    ItemEntity item = new ItemEntity(level, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), stack);
                    item.setDeltaMovement(level.getRandom().nextDouble() * 0.2 - 0.1, level.getRandom().nextDouble() * 0.2 - 0.1, level.getRandom().nextDouble() * 0.2 - 0.1);
                    level.addFreshEntity(item);
                }
                pPlayer.getInventory().setItem(slot, ItemStack.EMPTY);
            }
            pPlayer.hurt(AMDamageSources.WIND, 2);
            pPlayer.setDeltaMovement(getDeltaMovement().x() + level.getRandom().nextFloat() * 0.2f, getDeltaMovement().y() + 0.8, getDeltaMovement().z() + level.getRandom().nextFloat() * 0.2f);
            pPlayer.fallDistance = 0f;
            cooldowns.put(pPlayer, 20);
        }
    }
}
