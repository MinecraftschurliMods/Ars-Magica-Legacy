package at.minecraftschurli.mods.arsmagicalegacy;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAbilities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMCreativeTabs;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMCriterionTriggers;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMFluids;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMGrowthTypes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMLoot;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMParticles;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMRecipes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMRituals;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMWorldgen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ArsMagicaApi.MOD_ID)
public final class ArsMagicaLegacy {
    public ArsMagicaLegacy(ModContainer container, IEventBus bus) {
        container.registerConfig(ModConfig.Type.SERVER, AMServerConfig.SPEC);
        register(bus);
    }

    /// Registers the [DeferredRegister]s.
    ///
    /// @param bus The [IEventBus] to use.
    private void register(IEventBus bus) {
        AMBlocks.BLOCKS.register(bus);
        AMBlocks.BLOCKS.addAlias(ArsMagicaApi.id("witchwood"), AMBlocks.WITCHWOOD_WOOD.getId());
        AMBlocks.BLOCKS.addAlias(ArsMagicaApi.id("stripped_witchwood"), AMBlocks.STRIPPED_WITCHWOOD_WOOD.getId());
        AMItems.ITEMS.register(bus);
        AMFluids.FLUIDS.register(bus);
        AMFluids.FLUID_TYPES.register(bus);
        AMDataComponents.DATA_COMPONENTS.register(bus);
        AMAttributes.ATTRIBUTES.register(bus);
        AMBlockEntities.BLOCK_ENTITIES.register(bus);
        AMCreativeTabs.CREATIVE_TABS.register(bus);
        AMCriterionTriggers.TRIGGER_TYPES.register(bus);
        AMEntities.ENTITIES.register(bus);
        AMMenus.MENUS.register(bus);
        AMMobEffects.MOB_EFFECTS.register(bus);
        AMParticles.PARTICLES.register(bus);
        AMMobEffects.POTIONS.register(bus);
        AMSounds.SOUND_EVENTS.register(bus);
        AMWorldgen.FEATURES.register(bus);
        AMWorldgen.RULE_TESTS.register(bus);
        AMLoot.LOOT_CONDITIONS.register(bus);
        AMLoot.NUMBER_PROVIDERS.register(bus);
        AMLoot.GLOBAL_LOOT_MODIFIERS.register(bus);
        AMRecipes.RECIPE_SERIALIZERS.register(bus);
        AMRecipes.RECIPE_TYPES.register(bus);
        AMAttachments.ATTACHMENTS.register(bus);
        AMSpells.DATA_SERIALIZERS.register(bus);
        AMAbilities.ABILITY_EFFECTS.register(bus);
        AMGrowthTypes.GROWTH_TYPES.register(bus);
        AMRituals.RITUAL_EFFECTS.register(bus);
        AMRituals.RITUAL_REQUIREMENTS.register(bus);
        AMRituals.RITUAL_TRIGGERS.register(bus);
        AMSpells.SPELL_INGREDIENTS.register(bus);
        AMSpells.SPELL_PARTS.register(bus);
    }
}
