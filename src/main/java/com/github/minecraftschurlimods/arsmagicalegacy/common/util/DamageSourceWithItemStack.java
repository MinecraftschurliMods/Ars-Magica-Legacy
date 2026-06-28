package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("unused")
public class DamageSourceWithItemStack extends DamageSource {
    private final ItemStack stack;

    public DamageSourceWithItemStack(Holder<DamageType> type, @Nullable Entity directEntity, @Nullable Entity causingEntity, @Nullable Vec3 damageSourcePosition, ItemStack stack) {
        super(type, directEntity, causingEntity, damageSourcePosition);
        this.stack = stack;
    }

    public DamageSourceWithItemStack(Holder<DamageType> type, @Nullable Entity directEntity, @Nullable Entity causingEntity, ItemStack stack) {
        super(type, directEntity, causingEntity);
        this.stack = stack;
    }

    public DamageSourceWithItemStack(Holder<DamageType> type, Vec3 damageSourcePosition, ItemStack stack) {
        super(type, damageSourcePosition);
        this.stack = stack;
    }

    public DamageSourceWithItemStack(Holder<DamageType> type, @Nullable Entity entity, ItemStack stack) {
        super(type, entity);
        this.stack = stack;
    }

    public DamageSourceWithItemStack(Holder<DamageType> type, ItemStack stack) {
        super(type);
        this.stack = stack;
    }

    @Override
    public ItemStack getWeaponItem() {
        return stack;
    }
}
