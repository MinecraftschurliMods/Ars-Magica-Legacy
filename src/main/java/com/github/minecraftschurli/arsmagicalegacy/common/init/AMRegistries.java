package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    DeferredRegister<Attribute>           ATTRIBUTES           = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ArsMagicaAPI.MOD_ID);

    DeferredRegister<ISkillPoint>         SKILL_POINTS         = DeferredRegister.create(ISkillPoint.class, ArsMagicaAPI.MOD_ID);
    Supplier<IForgeRegistry<ISkillPoint>> SKILL_POINT_REGISTRY = SKILL_POINTS.makeRegistry("skill_point", RegistryBuilder::new);

    DeferredRegister<IAffinity>           AFFINITIES           = DeferredRegister.create(IAffinity.class, ArsMagicaAPI.MOD_ID);
    Supplier<IForgeRegistry<IAffinity>>   AFFINITY_REGISTRY    = AFFINITIES.makeRegistry("affinity", () -> new RegistryBuilder<IAffinity>().setDefaultKey(IAffinity.NONE));

    DeferredRegister<ISpellPart>          SPELL_PARTS          = DeferredRegister.create(ISpellPart.class, ArsMagicaAPI.MOD_ID);
    Supplier<IForgeRegistry<ISpellPart>>  SPELL_PART_REGISTRY  = SPELL_PARTS.makeRegistry("spell_part", () -> new RegistryBuilder<ISpellPart>().addCallback(new SpellPartCallback()));


    /**
     * @param bus the mods event bus
     */
    @Internal
    static void init(IEventBus bus) {
        AMBlocks.register();
        AMItems.register();
        AMContainers.register();
        AMAttributes.register();
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
        ATTRIBUTES.register(bus);
        SKILL_POINTS.register(bus);
        AFFINITIES.register(bus);
    }

    class SpellPartCallback implements IForgeRegistry.AddCallback<ISpellPart>, IForgeRegistry.ClearCallback<ISpellPart>, IForgeRegistry.CreateCallback<ISpellPart> {
        private static final ResourceLocation SPELL_COMPONENTS = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_components");
        private static final ResourceLocation SPELL_MODIFIERS = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_modifiers");
        private static final ResourceLocation SPELL_SHAPES = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_shapes");

        @SuppressWarnings("unchecked")
        @Override
        public void onAdd(IForgeRegistryInternal<ISpellPart> owner, RegistryManager stage, int id, ISpellPart obj, @Nullable ISpellPart oldObj) {
            if (obj instanceof ISpellPart.ISpellComponent component) {
                ((List<ISpellPart.ISpellComponent>) owner.getSlaveMap(SPELL_COMPONENTS, List.class)).add(component);
            }
            if (obj instanceof ISpellPart.ISpellModifier modifier) {
                ((List<ISpellPart.ISpellModifier>) owner.getSlaveMap(SPELL_MODIFIERS, List.class)).add(modifier);
            }
            if (obj instanceof ISpellPart.ISpellShape shape) {
                ((List<ISpellPart.ISpellShape>) owner.getSlaveMap(SPELL_SHAPES, List.class)).add(shape);
            }
        }

        @Override
        public void onClear(IForgeRegistryInternal<ISpellPart> owner, RegistryManager stage) {
            owner.getSlaveMap(SPELL_COMPONENTS, List.class).clear();
            owner.getSlaveMap(SPELL_MODIFIERS, List.class).clear();
            owner.getSlaveMap(SPELL_SHAPES, List.class).clear();
        }

        @Override
        public void onCreate(IForgeRegistryInternal<ISpellPart> owner, RegistryManager stage) {
            owner.setSlaveMap(SPELL_COMPONENTS, new ArrayList<>());
            owner.setSlaveMap(SPELL_MODIFIERS, new ArrayList<>());
            owner.setSlaveMap(SPELL_SHAPES, new ArrayList<>());
        }
    }
}
