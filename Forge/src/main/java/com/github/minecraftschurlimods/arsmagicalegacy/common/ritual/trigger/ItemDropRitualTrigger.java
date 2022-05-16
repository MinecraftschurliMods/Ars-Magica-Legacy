package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Context;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualTrigger;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.NBTIngredient;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public record ItemDropRitualTrigger(List<Ingredient> ingredients) implements RitualTrigger {
    public static final Codec<ItemDropRitualTrigger> CODEC = RecordCodecBuilder.create(inst -> inst.group(CodecHelper.INGREDIENT.listOf().fieldOf("ingredients").forGetter(ItemDropRitualTrigger::ingredients)).apply(inst, ItemDropRitualTrigger::new));

    public static ItemDropRitualTrigger ingredients(Ingredient... ingredients) {
        return new ItemDropRitualTrigger(Arrays.asList(ingredients));
    }

    public static ItemDropRitualTrigger ingredient(Ingredient ingredient) {
        return new ItemDropRitualTrigger(List.of(ingredient));
    }

    public static ItemDropRitualTrigger items(Item... items) {
        return new ItemDropRitualTrigger(Arrays.stream(items).map(Ingredient::of).toList());
    }

    public static ItemDropRitualTrigger item(Item item) {
        return ItemDropRitualTrigger.ingredient(Ingredient.of(item));
    }

    @SafeVarargs
    public static ItemDropRitualTrigger tags(TagKey<Item>... itemsTags) {
        return new ItemDropRitualTrigger(Arrays.stream(itemsTags).map(Ingredient::of).toList());
    }

    public static ItemDropRitualTrigger tag(TagKey<Item> itemsTag) {
        return ItemDropRitualTrigger.ingredient(Ingredient.of(itemsTag));
    }

    public static ItemDropRitualTrigger stacks(ItemStack... itemsStacks) {
        return new ItemDropRitualTrigger(Arrays.stream(itemsStacks).map(Ingredient::of).toList());
    }

    public static ItemDropRitualTrigger stack(ItemStack itemsStack) {
        return ItemDropRitualTrigger.ingredient(Ingredient.of(itemsStack));
    }

    public static ItemDropRitualTrigger stacksExact(ItemStack... itemsStacks) {
        return new ItemDropRitualTrigger(Arrays.stream(itemsStacks).<Ingredient>map(NBTIngredient::of).toList());
    }

    public static ItemDropRitualTrigger stackExact(ItemStack itemsStack) {
        return ItemDropRitualTrigger.ingredient(NBTIngredient.of(itemsStack));
    }

    @Override
    public void register(final Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((TickEvent.PlayerTickEvent t) -> {
            if ((t.side != LogicalSide.SERVER && !t.player.getGameProfile().getName().equals("test-mock-player")) || t.phase != TickEvent.Phase.START) return;
            if (!(t.player.level instanceof ServerLevel serverLevel)) return;
            for (final ItemEntity item : serverLevel.getEntitiesOfClass(ItemEntity.class, AABB.ofSize(t.player.position(), 5, 5, 5), itemEntity -> ingredients.stream().anyMatch(ingredient -> ingredient.test(itemEntity.getItem())))) {
                if (ritual.perform(t.player, serverLevel, item.getOnPos(), Context.EMPTY)) return;
            }
        });
    }

    @Override
    public boolean trigger(final Player player, final ServerLevel level, final BlockPos pos, Context ctx) {
        Set<ItemEntity> consumable = new HashSet<>();
        var ingredients = new ArrayList<>(this.ingredients);
        level.getEntities().get(EntityTypeTest.forClass(ItemEntity.class), AABB.ofSize(Vec3.atCenterOf(pos), 3, 3, 3), itemEntity -> {
            for (Iterator<Ingredient> iterator = ingredients.iterator(); iterator.hasNext(); ) {
                final Ingredient ingredient = iterator.next();
                if (ingredient.test(itemEntity.getItem())) {
                    consumable.add(itemEntity);
                    iterator.remove();
                }
            }
        });
        if (!ingredients.isEmpty()) {
            return false;
        }
        for (ItemEntity itemEntity : consumable) {
            itemEntity.kill();
        }
        return true;
    }

    @Override
    public Codec<? extends RitualTrigger> codec() {
        return CODEC;
    }
}
