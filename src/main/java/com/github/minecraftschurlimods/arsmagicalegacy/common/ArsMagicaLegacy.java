package com.github.minecraftschurlimods.arsmagicalegacy.common;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttachments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCreativeTabs;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriterionTriggers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMFluids;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMGrowthTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMLoot;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenus;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticles;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRecipes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRituals;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMWorldgen;
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
