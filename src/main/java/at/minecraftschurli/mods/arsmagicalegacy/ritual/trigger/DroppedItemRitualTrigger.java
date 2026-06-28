package at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger;

import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMExtraCodecs;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public record DroppedItemRitualTrigger(List<Ingredient> ingredients, double radius, int checkInterval) implements RitualTrigger<ItemEntity> {
    public static final MapCodec<DroppedItemRitualTrigger> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(DroppedItemRitualTrigger::ingredients),
        AMExtraCodecs.POSITIVE_DOUBLE_CODEC.optionalFieldOf("radius", 1.).forGetter(DroppedItemRitualTrigger::radius),
        ExtraCodecs.POSITIVE_INT.optionalFieldOf("check_interval", 20).forGetter(DroppedItemRitualTrigger::checkInterval)
    ).apply(inst, DroppedItemRitualTrigger::new));

    public DroppedItemRitualTrigger(Ingredient... ingredients) {
        this(Arrays.asList(ingredients), 1, 20);
    }

    @Override
    public MapCodec<? extends RitualTrigger<ItemEntity>> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec, ItemEntity context) {
        if (ingredients.isEmpty() || level.getGameTime() % checkInterval != 0) return false;
        List<ItemStack> stacks = level.getEntities(EntityTypeTest.forClass(ItemEntity.class), AABB.ofSize(vec, radius, radius, radius), EntitySelector.NO_SPECTATORS)
            .stream()
            .map(ItemEntity::getItem)
            .map(ItemStack::copy)
            .toList();
        if (stacks.isEmpty()) return false;
        ingredientsLoop:
        for (Ingredient ingredient : ingredients) {
            for (ItemStack stack : stacks) {
                if (!stack.isEmpty() && ingredient.test(stack)) {
                    stack.shrink(1);
                    continue ingredientsLoop;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void consume(@Nullable Player player, Level level, Vec3 vec, ItemEntity context) {
        List<ItemEntity> entities = level.getEntities(EntityTypeTest.forClass(ItemEntity.class), AABB.ofSize(vec, radius, radius, radius), EntitySelector.NO_SPECTATORS);
        for (Ingredient ingredient : ingredients) {
            for (ItemEntity entity : entities) {
                ItemStack stack = entity.getItem();
                if (!stack.isEmpty() && ingredient.test(stack)) {
                    stack.shrink(1);
                    break;
                }
            }
        }
    }
}
