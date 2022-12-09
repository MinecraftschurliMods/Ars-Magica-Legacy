package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public record PrefabSpell(Component name, ISpell spell, ResourceLocation icon) implements IPrefabSpell {
    public static final String SPELL_PREFAB_NAME = "item." + ArsMagicaAPI.MOD_ID + ".spell.prefab.name";
    public static final ResourceKey<Registry<PrefabSpell>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "prefab_spell"));
    public static final Codec<PrefabSpell> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.either(Codec.STRING, CodecHelper.COMPONENT).xmap(stringComponentEither -> stringComponentEither.map(Component::literal, Function.identity()), Either::right).optionalFieldOf("name", Component.translatable(SPELL_PREFAB_NAME)).forGetter(IPrefabSpell::name),
            ISpell.CODEC.fieldOf("spell").forGetter(PrefabSpell::spell),
            ResourceLocation.CODEC.fieldOf("icon").forGetter(PrefabSpell::icon)
    ).apply(inst, PrefabSpell::new));
    public static final Codec<Holder<PrefabSpell>> REFERENCE_CODEC = RegistryFileCodec.create(REGISTRY_KEY, DIRECT_CODEC);
    public static final Codec<HolderSet<PrefabSpell>> LIST_CODEC = RegistryCodecs.homogeneousList(REGISTRY_KEY, DIRECT_CODEC);

    private static final RegistryObject<Item> SPELL = RegistryObject.create(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell"), ForgeRegistries.ITEMS);

    @Override
    public ItemStack makeSpell() {
        ItemStack stack = new ItemStack(SPELL.get());
        var helper = ArsMagicaAPI.get().getSpellHelper();
        helper.setSpell(stack, spell());
        helper.setSpellName(stack, name());
        stack.setHoverName(name());
        helper.setSpellIcon(stack, icon());
        return stack;
    }
}
