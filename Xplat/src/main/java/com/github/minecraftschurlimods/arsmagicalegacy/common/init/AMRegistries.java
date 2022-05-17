package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbility;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMRegistries {
    RegistrationProvider<Block>               BLOCKS             = RegistrationProvider.create(Registry.BLOCK_REGISTRY,             ArsMagicaAPI.MOD_ID);
    RegistrationProvider<Fluid>               FLUIDS             = RegistrationProvider.create(Registry.FLUID_REGISTRY,             ArsMagicaAPI.MOD_ID);
    RegistrationProvider<Item>                ITEMS              = RegistrationProvider.create(Registry.ITEM_REGISTRY,              ArsMagicaAPI.MOD_ID);
    RegistrationProvider<MobEffect>           MOB_EFFECTS        = RegistrationProvider.create(Registry.MOB_EFFECT_REGISTRY,        ArsMagicaAPI.MOD_ID);
    RegistrationProvider<Attribute>           ATTRIBUTES         = RegistrationProvider.create(Registry.ATTRIBUTE_REGISTRY,         ArsMagicaAPI.MOD_ID);
    RegistrationProvider<SoundEvent>          SOUND_EVENTS       = RegistrationProvider.create(Registry.SOUND_EVENT_REGISTRY,       ArsMagicaAPI.MOD_ID);
    RegistrationProvider<Potion>              POTIONS            = RegistrationProvider.create(Registry.POTION_REGISTRY,            ArsMagicaAPI.MOD_ID);
    RegistrationProvider<Enchantment>         ENCHANTMENTS       = RegistrationProvider.create(Registry.ENCHANTMENT_REGISTRY,       ArsMagicaAPI.MOD_ID);
    RegistrationProvider<EntityType<?>>       ENTITIES           = RegistrationProvider.create(Registry.ENTITY_TYPE_REGISTRY,       ArsMagicaAPI.MOD_ID);
    RegistrationProvider<BlockEntityType<?>>  BLOCK_ENTITIES     = RegistrationProvider.create(Registry.BLOCK_ENTITY_TYPE_REGISTRY, ArsMagicaAPI.MOD_ID);
    RegistrationProvider<ParticleType<?>>     PARTICLE_TYPES     = RegistrationProvider.create(Registry.PARTICLE_TYPE_REGISTRY,     ArsMagicaAPI.MOD_ID);
    RegistrationProvider<MenuType<?>>         MENU_TYPES         = RegistrationProvider.create(Registry.MENU_REGISTRY,              ArsMagicaAPI.MOD_ID);
    RegistrationProvider<RecipeSerializer<?>> RECIPE_SERIALIZERS = RegistrationProvider.create(Registry.RECIPE_SERIALIZER_REGISTRY, ArsMagicaAPI.MOD_ID);
    RegistrationProvider<StatType<?>>         STAT_TYPES         = RegistrationProvider.create(Registry.STAT_TYPE_REGISTRY,         ArsMagicaAPI.MOD_ID);
    RegistrationProvider<ISkillPoint>         SKILL_POINTS       = RegistrationProvider.create(ISkillPoint.REGISTRY_KEY,            ArsMagicaAPI.MOD_ID);
    RegistrationProvider<IAffinity>           AFFINITIES         = RegistrationProvider.create(IAffinity.REGISTRY_KEY,              ArsMagicaAPI.MOD_ID);
    RegistrationProvider<ISpellPart>          SPELL_PARTS        = RegistrationProvider.create(ISpellPart.REGISTRY_KEY,             ArsMagicaAPI.MOD_ID);
    RegistrationProvider<IAbility>            ABILITIES          = RegistrationProvider.create(IAbility.REGISTRY_KEY,               ArsMagicaAPI.MOD_ID);

    static void registerRegistries() {
        registerDefaulted(ISkillPoint.REGISTRY_KEY, ArsMagicaAPI.MOD_ID + ":none", Lifecycle.stable());
        registerDefaulted(IAffinity.REGISTRY_KEY, IAffinity.NONE.toString(), Lifecycle.stable());
        registerSimple(ISpellPart.REGISTRY_KEY, Lifecycle.stable());
        registerSimple(IAbility.REGISTRY_KEY, Lifecycle.stable());
    }

    static <T> void registerSimple(ResourceKey<? extends Registry<T>> registryKey, Lifecycle lifecycle) {
        internalRegister(registryKey, new MappedRegistry<>(registryKey, lifecycle, null), lifecycle);
    }

    private static <T> void registerDefaulted(ResourceKey<? extends Registry<T>> registryKey, String defaultKey, Lifecycle lifecycle) {
        internalRegister(registryKey, new DefaultedRegistry<>(defaultKey, registryKey, lifecycle, null), lifecycle);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T, R extends WritableRegistry<T>> void internalRegister(ResourceKey<? extends Registry<T>> registryKey, R registry, Lifecycle lifecycle) {
        ((WritableRegistry)Registry.REGISTRY).register(registryKey, registry, lifecycle);
    }
}
