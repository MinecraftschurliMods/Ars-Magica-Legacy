package com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddPoolToTableModifier extends LootModifier {
    public static final Codec<AddPoolToTableModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).and(inst.group(
            ResourceLocation.CODEC.fieldOf("source").forGetter(AddPoolToTableModifier::source),
            ResourceLocation.CODEC.listOf().fieldOf("tables").forGetter(AddPoolToTableModifier::tables)
    )).apply(inst, AddPoolToTableModifier::new));
    private final ResourceLocation source;
    private final List<ResourceLocation> tables;

    private ResourceLocation source() {
        return source;
    }

    private List<ResourceLocation> tables() {
        return tables;
    }

    public AddPoolToTableModifier(LootItemCondition[] conditionsIn, ResourceLocation source, List<ResourceLocation> tables) {
        super(conditionsIn);
        this.source = source;
        this.tables = tables;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    @NotNull
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (tables.contains(context.getQueriedLootTableId())) {
            context.getResolver().getLootTable(source).getRandomItemsRaw(context, generatedLoot::add);
        }
        return generatedLoot;
    }
}
