package com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.Arrays;

public class AddConditionsModifier extends LootModifier {
    public static final MapCodec<AddConditionsModifier> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(e -> e.conditions),
            LOOT_CONDITIONS_CODEC.fieldOf("extra_conditions").forGetter(e -> e.extraConditions)
    ).apply(inst, AddConditionsModifier::new));
    private final LootItemCondition[] extraConditions;

    public AddConditionsModifier(LootItemCondition[] conditions, LootItemCondition[] extraConditions) {
        super(conditions);
        this.extraConditions = extraConditions;
    }

    public AddConditionsModifier(LootItemCondition[] conditions, LootItemCondition extraCondition) {
        this(conditions, new LootItemCondition[]{extraCondition});
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return Arrays.stream(extraConditions).allMatch(e -> e.test(context)) ? generatedLoot : new ObjectArrayList<>();
    }
}
