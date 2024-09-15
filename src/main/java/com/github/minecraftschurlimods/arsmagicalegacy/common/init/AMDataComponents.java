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
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.DATA_COMPONENT_TYPES;

@NonExtendable
public interface AMDataComponents {
    Supplier<DataComponentType<Holder<Affinity>>>      AFFINITY         = register("affinity", ArsMagicaAPI.get().getAffinityRegistry().holderByNameCodec(), ByteBufCodecs.holderRegistry(Affinity.REGISTRY_KEY));
    Supplier<DataComponentType<EtheriumType>>          ETHERIUM_TYPE    = register("etherium_type", EtheriumType.CODEC, EtheriumType.STREAM_CODEC);
    Supplier<DataComponentType<Float>>                 MANA_REPAIR_COST = register("mana_repair_cost", Codec.FLOAT, ByteBufCodecs.FLOAT);
    Supplier<DataComponentType<GlobalPos>>             RECALL_POSITION  = register("recall_position", GlobalPos.CODEC, GlobalPos.STREAM_CODEC);
    Supplier<DataComponentType<BlockPos>>              SAVED_POS        = register("saved_pos", BlockPos.CODEC, BlockPos.STREAM_CODEC);
    Supplier<DataComponentType<Block>>                 SELECTED_BLOCK   = register("selected_block", BuiltInRegistries.BLOCK.byNameCodec().orElse(Blocks.AIR), ByteBufCodecs.registry(Registries.BLOCK));
    Supplier<DataComponentType<EntityType<?>>>         SELECTED_ENTITY  = register("selected_entity", BuiltInRegistries.ENTITY_TYPE.byNameCodec(), ByteBufCodecs.registry(Registries.ENTITY_TYPE));
    Supplier<DataComponentType<Integer>>               SELECTED_SLOT    = register("selected_slot", Codec.INT, ByteBufCodecs.VAR_INT);
    Supplier<DataComponentType<Holder<SkillPoint>>>    SKILL_POINT      = register("skill_point", ArsMagicaAPI.get().getSkillPointRegistry().holderByNameCodec(), ByteBufCodecs.holderRegistry(SkillPoint.REGISTRY_KEY));
    Supplier<DataComponentType<ISpell>>                SPELL            = register("spell", ISpell.CODEC, ISpell.STREAM_CODEC);
    Supplier<DataComponentType<ResourceLocation>>      SPELL_ICON       = register("spell_icon", ResourceLocation.CODEC, ResourceLocation.STREAM_CODEC);
    Supplier<DataComponentType<Component>>             SPELL_NAME       = register("spell_name", ComponentSerialization.CODEC, ComponentSerialization.STREAM_CODEC);
    Supplier<DataComponentType<ISpell>>                SPELL_RECIPE     = register("spell_recipe", ISpell.CODEC, ISpell.STREAM_CODEC);
    Supplier<DataComponentType<ItemContainerContents>> SPELLS           = register("spells", ItemContainerContents.CODEC, ItemContainerContents.STREAM_CODEC);
    Supplier<DataComponentType<Integer>>               TIER             = register("tier", Codec.INT, ByteBufCodecs.VAR_INT);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
    
    static <T> Supplier<DataComponentType<T>> register(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return DATA_COMPONENT_TYPES.registerComponentType(name, builder -> builder.persistent(codec).networkSynchronized(streamCodec));
    }
}
