package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public record IngredientRitualRequirement(Ingredient ingredient, double radius) implements RitualRequirement {
    public static final MapCodec<IngredientRitualRequirement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Ingredient.CODEC.fieldOf("ingredient").forGetter(IngredientRitualRequirement::ingredient),
        Codec.DOUBLE.fieldOf("radius").forGetter(IngredientRitualRequirement::radius)
    ).apply(inst, IngredientRitualRequirement::new));

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec) {
        return level.getEntitiesOfClass(ItemEntity.class, new AABB(vec.add(-radius, -radius, -radius), vec.add(radius, radius, radius)))
            .stream()
            .map(ItemEntity::getItem)
            .anyMatch(ingredient);
    }

    @Override
    public void consume(@Nullable Player player, Level level, Vec3 vec) {
        level.getEntitiesOfClass(ItemEntity.class, new AABB(vec.add(-radius, -radius, -radius), vec.add(radius, radius, radius)))
            .stream()
            .filter(e -> ingredient.test(e.getItem()))
            .findAny()
            .ifPresent(e -> {
                e.getItem().shrink(1);
                if (e.getItem().isEmpty()) {
                    e.remove(Entity.RemovalReason.KILLED);
                }
            });
    }
}
