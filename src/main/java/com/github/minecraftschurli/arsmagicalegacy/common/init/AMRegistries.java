package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.function.Supplier;

@NonExtendable
public interface AMRegistries {
    DeferredRegister<Block>               BLOCKS               = DeferredRegister.create(ForgeRegistries.BLOCKS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<Fluid>               FLUIDS               = DeferredRegister.create(ForgeRegistries.FLUIDS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<Item>                ITEMS                = DeferredRegister.create(ForgeRegistries.ITEMS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<MobEffect>           MOB_EFFECTS          = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<SoundEvent>          SOUND_EVENTS         = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<Potion>              POTIONS              = DeferredRegister.create(ForgeRegistries.POTIONS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<Enchantment>         ENCHANTMENTS         = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<EntityType<?>>       ENTITIES             = DeferredRegister.create(ForgeRegistries.ENTITIES, ArsMagicaAPI.MOD_ID);
    DeferredRegister<BlockEntityType<?>>  BLOCK_ENTITIES       = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ArsMagicaAPI.MOD_ID);
    DeferredRegister<ParticleType<?>>     PARTICLE_TYPES       = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ArsMagicaAPI.MOD_ID);
    DeferredRegister<MenuType<?>>         CONTAINERS           = DeferredRegister.create(ForgeRegistries.CONTAINERS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS   = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<StatType<?>>         STAT_TYPES           = DeferredRegister.create(ForgeRegistries.STAT_TYPES, ArsMagicaAPI.MOD_ID);

    DeferredRegister<ISkillPoint>         SKILL_POINTS         = DeferredRegister.create(ISkillPoint.class, ArsMagicaAPI.MOD_ID);
    Supplier<IForgeRegistry<ISkillPoint>> SKILL_POINT_REGISTRY = SKILL_POINTS.makeRegistry("skill_point", () -> new RegistryBuilder<ISkillPoint>().setDefaultKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "none")));

    DeferredRegister<IAffinity>           AFFINITIES           = DeferredRegister.create(IAffinity.class, ArsMagicaAPI.MOD_ID);
    Supplier<IForgeRegistry<IAffinity>>   AFFINITY_REGISTRY    = AFFINITIES.makeRegistry("affinity", () -> new RegistryBuilder<IAffinity>().setDefaultKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "none")));


    /**
     * @param bus the mods event bus
     */
    @Internal
    static void init(IEventBus bus) {
        AMBlocks.register();
        AMItems.register();
        AMContainers.register();
        AMEntities.register();
        AMSkillPoints.register();
        AMAffinities.register();
        BLOCKS.register(bus);
        FLUIDS.register(bus);
        ITEMS.register(bus);
        MOB_EFFECTS.register(bus);
        SOUND_EVENTS.register(bus);
        POTIONS.register(bus);
        ENCHANTMENTS.register(bus);
        ENTITIES.register(bus);
        BLOCK_ENTITIES.register(bus);
        PARTICLE_TYPES.register(bus);
        CONTAINERS.register(bus);

        RECIPE_SERIALIZERS.register(bus);
        STAT_TYPES.register(bus);
        SKILL_POINTS.register(bus);
        AFFINITIES.register(bus);
    }
}
