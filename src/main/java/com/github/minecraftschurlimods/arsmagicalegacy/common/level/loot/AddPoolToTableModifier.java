package com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddPoolToTableModifier extends LootModifier {
    public static final MapCodec<AddPoolToTableModifier> CODEC = RecordCodecBuilder.mapCodec(inst -> codecStart(inst).and(inst.group(
            ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("source").forGetter(AddPoolToTableModifier::source),
            ResourceLocation.CODEC.listOf().fieldOf("tables").forGetter(AddPoolToTableModifier::tables)
    )).apply(inst, AddPoolToTableModifier::new));
    private final ResourceKey<LootTable> source;
    private final List<ResourceLocation> tables;

    private ResourceKey<LootTable> source() {
        return source;
    }

    private List<ResourceLocation> tables() {
        return tables;
    }

    public AddPoolToTableModifier(LootItemCondition[] conditionsIn, ResourceKey<LootTable> source, List<ResourceLocation> tables) {
        super(conditionsIn);
        this.source = source;
        this.tables = tables;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    @NotNull
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (tables.contains(context.getQueriedLootTableId())) {
            context.getResolver().get(Registries.LOOT_TABLE, source).orElseThrow().value().getRandomItemsRaw(context, generatedLoot::add);
        }
        return generatedLoot;
    }
}
