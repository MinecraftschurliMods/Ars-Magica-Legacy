package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.item.CrystalPhylacteryItem;
import at.minecraftschurli.mods.arsmagicalegacy.spell.SpellDamage;
import at.minecraftschurli.mods.arsmagicalegacy.util.GlobalVec3;
import com.mojang.serialization.Codec;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public interface AMDataComponents {
    DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<DataComponentType<?>, DataComponentType<Holder<Affinity>>>               AFFINITY                    = register("affinity",                    Affinity.CODEC,                       ByteBufCodecs.holderRegistry(AMRegistries.Keys.AFFINITY));
    DeferredHolder<DataComponentType<?>, DataComponentType<Double>>                         BONUS_MANA_MULTIPLIER       = register("bonus_mana_multiplier",       Codec.DOUBLE,                         ByteBufCodecs.DOUBLE);
    DeferredHolder<DataComponentType<?>, DataComponentType<Double>>                         BONUS_STAT_MULTIPLIER       = register("bonus_stat_multiplier",       Codec.DOUBLE,                         ByteBufCodecs.DOUBLE);
    DeferredHolder<DataComponentType<?>, DataComponentType<CrystalPhylacteryItem.Contents>> CRYSTAL_PHYLACTERY_CONTENTS = register("crystal_phylactery_contents", CrystalPhylacteryItem.Contents.CODEC, CrystalPhylacteryItem.Contents.STREAM_CODEC);
    DeferredHolder<DataComponentType<?>, DataComponentType<Holder<EtheriumType>>>           ETHERIUM_TYPE               = register("etherium_type",               EtheriumType.CODEC,                   ByteBufCodecs.holderRegistry(AMRegistries.Keys.ETHERIUM_TYPE));
    DeferredHolder<DataComponentType<?>, DataComponentType<Double>>                         MANA_REPAIR_COST            = register("mana_repair_cost",            Codec.DOUBLE,                         ByteBufCodecs.DOUBLE);
    DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>                        SELECTED_INDEX              = register("selected_index",              Codec.INT,                            ByteBufCodecs.INT);
    DeferredHolder<DataComponentType<?>, DataComponentType<Holder<SkillPoint>>>             SKILL_POINT                 = register("skill_point",                 SkillPoint.CODEC,                     ByteBufCodecs.holderRegistry(AMRegistries.Keys.SKILL_POINT));
    DeferredHolder<DataComponentType<?>, DataComponentType<Spell>>                          SPELL                       = register("spell",                       Spell.CODEC,                          Spell.STREAM_CODEC);
    DeferredHolder<DataComponentType<?>, DataComponentType<List<GlobalPos>>>                STORED_POSITIONS            = register("stored_positions",            GlobalPos.CODEC.listOf(),             GlobalPos.STREAM_CODEC.apply(ByteBufCodecs.list()));
    DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>                        TIER                        = register("tier",                        Codec.INT,                            ByteBufCodecs.INT);

    DeferredHolder<DataComponentType<?>, DataComponentType<Block>>         SPELL_BLOCK           = register("spell_block",           BuiltInRegistries.BLOCK.byNameCodec(),       ByteBufCodecs.registry(Registries.BLOCK));
    DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>       SPELL_COLOR           = register("spell_color",           Codec.INT,                                   ByteBufCodecs.INT);
    DeferredHolder<DataComponentType<?>, DataComponentType<SpellDamage>>   SPELL_DAMAGE          = register("spell_damage",          SpellDamage.CODEC,                           SpellDamage.STREAM_CODEC);
    DeferredHolder<DataComponentType<?>, DataComponentType<GlobalVec3>>    SPELL_RECALL_POSITION = register("spell_recall_position", GlobalVec3.CODEC,                            GlobalVec3.STREAM_CODEC);
    DeferredHolder<DataComponentType<?>, DataComponentType<EntityType<?>>> SPELL_SUMMON          = register("spell_summon",          BuiltInRegistries.ENTITY_TYPE.byNameCodec(), ByteBufCodecs.registry(Registries.ENTITY_TYPE));
    // @formatter:on

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return DATA_COMPONENTS.registerComponentType(name, builder -> builder.persistent(codec).networkSynchronized(streamCodec));
    }
}
