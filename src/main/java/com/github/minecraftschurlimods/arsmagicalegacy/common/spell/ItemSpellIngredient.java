package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@SuppressWarnings("deprecation")
public record ItemSpellIngredient(Ingredient item, int count) implements SpellIngredient {
    public static final MapCodec<ItemSpellIngredient> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Ingredient.CODEC.fieldOf("item").forGetter(ItemSpellIngredient::item),
        Codec.INT.fieldOf("count").forGetter(ItemSpellIngredient::count)
    ).apply(inst, ItemSpellIngredient::new));

    @Override
    public MapCodec<? extends SpellIngredient> codec() {
        return AMSpells.ITEM_SPELL_INGREDIENT.get();
    }

    @Override
    public List<Component> tooltip(@Nullable Level level) {
        List<ItemStack> itemStacks = asItemStacks();
        Component countComponent = Component.translatable(AMTranslations.SPELL_INGREDIENT_COUNT_KEY, count);
        if (itemStacks.isEmpty()) return List.of(countComponent);
        if (itemStacks.size() == 1 || level == null) return List.of(itemStacks.getFirst().getItemName(), countComponent);
        return List.of(Objects.requireNonNull(AMUtil.getByTick(new ArrayList<>(itemStacks.stream().map(ItemStack::getItemName).toList()), (int) (level.getGameTime() / 20))), countComponent);
    }

    @Override
    public boolean canCombine(SpellIngredient other) {
        if (!(other instanceof ItemSpellIngredient that)) return false;
        List<ItemStack> thisItems = this.item.items()
            .map(Holder::value)
            .map(Item::getDefaultInstance)
            .toList();
        List<ItemStack> thatItems = that.item.items()
            .map(Holder::value)
            .map(Item::getDefaultInstance)
            .toList();
        return thisItems.size() == thatItems.size() && IntStream.range(0, thisItems.size()).allMatch(i -> ItemStack.isSameItemSameComponents(thisItems.get(i), thatItems.get(i)));
    }

    @Override
    @Nullable
    public SpellIngredient combine(SpellIngredient other) {
        return canCombine(other) ? new ItemSpellIngredient(item, count + ((ItemSpellIngredient) other).count) : null;
    }

    @Override
    public boolean consume(Level level, BlockPos pos) {
        for (ItemEntity entity : level.getEntities(EntityTypeTest.forClass(ItemEntity.class), new AABB(pos).inflate(1, 1, 1).move(0, -2, 0), e -> true)) {
            if (consume(entity.getItem())) {
                level.playSound(null, pos.getX(), pos.getY() - 2, pos.getZ(), AMSounds.SPELLCRAFTING_ADD_INGREDIENT.get(), SoundSource.BLOCKS, 1, 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ItemStack> asItemStacks() {
        List<ItemStack> list = item.display()
            .resolveForStacks(ContextMap.EMPTY)
            .stream()
            .map(e -> e.copyWithCount(count))
            .toList();
        return !list.isEmpty() ? list : item.items()
            .map(Holder::value)
            .map(e -> new ItemStack(e, count))
            .toList();
    }

    private boolean consume(ItemStack stack) {
        ResourceHandler<ItemResource> handler = stack.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(stack));
        if (handler != null) {
            try (Transaction transaction = Transaction.openRoot()) {
                int count = this.count;
                for (int i = 0; i < handler.size(); i++) {
                    ItemResource resource = handler.getResource(i);
                    if (!item.test(resource.toStack())) continue;
                    count -= handler.extract(resource, count, transaction);
                    if (count <= 0) {
                        transaction.commit();
                        return true;
                    }
                }
            }
        }
        if (item.test(stack) && stack.getCount() >= count) {
            stack.shrink(count);
            return true;
        }
        return false;
    }
}
