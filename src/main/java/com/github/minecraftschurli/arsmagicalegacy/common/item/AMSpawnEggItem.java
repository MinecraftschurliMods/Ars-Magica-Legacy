package com.github.minecraftschurli.arsmagicalegacy.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings("ConstantConditions")
public class AMSpawnEggItem extends SpawnEggItem {
    private final Lazy<EntityType<? extends Mob>> defaultType;

    public AMSpawnEggItem(Supplier<EntityType<? extends Mob>> pDefaultType, int pBackgroundColor, int pHighlightColor, Properties pProperties) {
        super(null, pBackgroundColor, pHighlightColor, pProperties);
        this.defaultType = Lazy.of(pDefaultType);
    }

    @NotNull
    @Override
    public EntityType<?> getType(@Nullable final CompoundTag pNbt) {
        EntityType<?> type = super.getType(pNbt);
        return type != null ? type : defaultType.get();
    }

    public void init() {
        SpawnEggItem.BY_ID.put(defaultType.get(), this);
    }
}
