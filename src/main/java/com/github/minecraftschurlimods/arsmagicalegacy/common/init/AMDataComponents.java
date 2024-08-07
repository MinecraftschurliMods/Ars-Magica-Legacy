package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.DATA_COMPONENT_TYPES;

@NonExtendable
public interface AMDataComponents {
    Supplier<DataComponentType<ISpell>> SPELL = DATA_COMPONENT_TYPES.registerComponentType("spell", builder -> builder.persistent(ISpell.CODEC).networkSynchronized(ISpell.STREAM_CODEC));
    Supplier<DataComponentType<ISpell>> SPELL_RECIPE = DATA_COMPONENT_TYPES.registerComponentType("spell_recipe", builder -> builder.persistent(ISpell.CODEC).networkSynchronized(ISpell.STREAM_CODEC));
    Supplier<DataComponentType<Component>> SPELL_NAME = DATA_COMPONENT_TYPES.registerComponentType("spell_name", builder -> builder.persistent(ComponentSerialization.CODEC).networkSynchronized(ComponentSerialization.STREAM_CODEC));
    Supplier<DataComponentType<ResourceLocation>> SPELL_ICON = DATA_COMPONENT_TYPES.registerComponentType("spell_icon", builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
    Supplier<DataComponentType<Holder<Affinity>>> AFFINITY = DATA_COMPONENT_TYPES.registerComponentType("affinity", builder -> builder.persistent(ArsMagicaAPI.get().getAffinityRegistry().holderByNameCodec()).networkSynchronized(ByteBufCodecs.holderRegistry(Affinity.REGISTRY_KEY)));
    Supplier<DataComponentType<Holder<SkillPoint>>> SKILL_POINT = DATA_COMPONENT_TYPES.registerComponentType("skill_point", builder -> builder.persistent(ArsMagicaAPI.get().getSkillPointRegistry().holderByNameCodec()).networkSynchronized(ByteBufCodecs.holderRegistry(SkillPoint.REGISTRY_KEY)));
    Supplier<DataComponentType<Integer>> SELECTED_SLOT = DATA_COMPONENT_TYPES.registerComponentType("selected_slot", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    Supplier<DataComponentType<ItemContainerContents>> SPELLS = DATA_COMPONENT_TYPES.registerComponentType("spells", builder -> builder.persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC));
    Supplier<DataComponentType<Integer>> TIER = DATA_COMPONENT_TYPES.registerComponentType("tier", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    Supplier<DataComponentType<EtheriumType>> ETHERIUM_TYPE = DATA_COMPONENT_TYPES.registerComponentType("etherium_type", builder -> builder.persistent(EtheriumType.CODEC).networkSynchronized(EtheriumType.STREAM_CODEC));
    Supplier<DataComponentType<BlockPos>> SAVED_POS = DATA_COMPONENT_TYPES.registerComponentType("saved_pos", builder -> builder.persistent(BlockPos.CODEC).networkSynchronized(BlockPos.STREAM_CODEC));
    Supplier<DataComponentType<Float>> MANA_REPAIR_COST = DATA_COMPONENT_TYPES.registerComponentType("mana_repair_cost", builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));
    Supplier<DataComponentType<Block>> SELECTED_BLOCK = DATA_COMPONENT_TYPES.registerComponentType("selected_block", builder -> builder.persistent(BuiltInRegistries.BLOCK.byNameCodec().orElse(Blocks.AIR)).networkSynchronized(ByteBufCodecs.registry(Registries.BLOCK)));
    Supplier<DataComponentType<GlobalPos>> RECALL_POSITION = DATA_COMPONENT_TYPES.registerComponentType("recall_position", builder -> builder.persistent(GlobalPos.CODEC).networkSynchronized(GlobalPos.STREAM_CODEC));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
