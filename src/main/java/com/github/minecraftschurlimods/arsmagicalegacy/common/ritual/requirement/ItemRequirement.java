package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualRequirement;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record ItemRequirement(List<Ingredient> ingredients, int radius) implements RitualRequirement {
    public static final Codec<ItemRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecHelper.INGREDIENT.listOf().fieldOf("ingredients").forGetter(ItemRequirement::ingredients),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("radius").forGetter(ItemRequirement::radius)
    ).apply(inst, ItemRequirement::new));

    @Override
    public boolean test(Player player, ServerLevel serverLevel, BlockPos pos) {
        List<ItemEntity> items = serverLevel.getEntitiesOfClass(ItemEntity.class, AABB.ofSize(Vec3.atCenterOf(pos), radius * 2, radius * 2, radius * 2));
        List<Ingredient> ingredients = new ArrayList<>(this.ingredients);
        for (Iterator<ItemEntity> iterator = items.iterator(); iterator.hasNext(); ) {
            ItemEntity item = iterator.next();
            for (Iterator<Ingredient> iter = ingredients.iterator(); iter.hasNext(); ) {
                Ingredient ingredient = iter.next();
                if (ingredient.test(item.getItem())) {
                    iter.remove();
                    iterator.remove();
                    break;
                }
            }
        }
        return false;
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }
}
