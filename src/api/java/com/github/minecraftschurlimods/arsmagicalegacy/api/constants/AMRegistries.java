package com.github.minecraftschurlimods.arsmagicalegacy.api.constants;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.AltarMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.GrowthType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.Plant;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPartData;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus;

/// Holds all registries added by the mod, including getters for the datapack registries.
@ApiStatus.NonExtendable
public interface AMRegistries {
    /// The registry for [AbilityEffect]s.
    Registry<MapCodec<? extends AbilityEffect>> ABILITY_EFFECTS = new RegistryBuilder<>(Keys.ABILITY_EFFECT).sync(true).create();
    /// The registry for [GrowthType]s.
    Registry<MapCodec<? extends GrowthType>> GROWTH_TYPES = new RegistryBuilder<>(Keys.GROWTH_TYPE).sync(true).create();
    /// The registry for [RitualEffect]s.
    Registry<MapCodec<? extends RitualEffect>> RITUAL_EFFECTS = new RegistryBuilder<>(Keys.RITUAL_EFFECT).sync(true).create();
    /// The registry for [RitualRequirement]s.
    Registry<MapCodec<? extends RitualRequirement>> RITUAL_REQUIREMENTS = new RegistryBuilder<>(Keys.RITUAL_REQUIREMENT).sync(true).create();
    /// The registry for [RitualTrigger]s.
    Registry<MapCodec<? extends RitualTrigger<?>>> RITUAL_TRIGGERS = new RegistryBuilder<>(Keys.RITUAL_TRIGGER).sync(true).create();
    /// The registry for [SpellIngredient]s.
    Registry<MapCodec<? extends SpellIngredient>> SPELL_INGREDIENTS = new RegistryBuilder<>(Keys.SPELL_INGREDIENT).sync(true).create();
    /// The registry for [SpellPart]s.
    Registry<SpellPart> SPELL_PARTS = new RegistryBuilder<>(Keys.SPELL_PART).sync(true).create();

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Ability]s.
    static Registry<Ability> abilities(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.ABILITY);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [Ability]s.
    static Registry<Ability> abilities(boolean client) {
        return abilities(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Affinity]s.
    static Registry<Affinity> affinities(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.AFFINITY);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [Affinity]s.
    static Registry<Affinity> affinities(boolean client) {
        return affinities(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [AltarCapMaterial]s.
    static Registry<AltarCapMaterial> altarCapMaterials(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.ALTAR_CAP_MATERIAL);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [AltarCapMaterial]s.
    static Registry<AltarCapMaterial> altarCapMaterials(boolean client) {
        return altarCapMaterials(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [AltarMaterial]s.
    static Registry<AltarMaterial> altarMaterials(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.ALTAR_MATERIAL);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [AltarMaterial]s.
    static Registry<AltarMaterial> altarMaterials(boolean client) {
        return altarMaterials(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [EtheriumType]s.
    static Registry<EtheriumType> etheriumTypes(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.ETHERIUM_TYPE);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [EtheriumType]s.
    static Registry<EtheriumType> etheriumTypes(boolean client) {
        return etheriumTypes(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [OcculusTab]s.
    static Registry<OcculusTab> occulusTabs(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.OCCULUS_TAB);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [OcculusTab]s.
    static Registry<OcculusTab> occulusTabs(boolean client) {
        return occulusTabs(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Plant]s.
    static Registry<Plant> plants(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.PLANT);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [Plant]s.
    static Registry<Plant> plants(boolean client) {
        return plants(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Ritual]s.
    static Registry<Ritual<?>> rituals(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.RITUAL);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [Ritual]s.
    static Registry<Ritual<?>> rituals(boolean client) {
        return rituals(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Skill]s.
    static Registry<Skill> skills(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.SKILL);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [Skill]s.
    static Registry<Skill> skills(boolean client) {
        return skills(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [SkillPoint]s.
    static Registry<SkillPoint> skillPoints(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.SKILL_POINT);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [SkillPoint]s.
    static Registry<SkillPoint> skillPoints(boolean client) {
        return skillPoints(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [SpellPartData]s.
    static Registry<SpellPartData> spellPartData(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.SPELL_PART_DATA);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [SpellPartData]s.
    static Registry<SpellPartData> spellPartData(boolean client) {
        return spellPartData(registryAccess(client));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Spell] prefabs.
    static Registry<Spell> spellPrefabs(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.SPELL_PREFAB);
    }

    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The registry for [Spell] prefabs.
    static Registry<Spell> spellPrefabs(boolean client) {
        return spellPrefabs(registryAccess(client));
    }

    /// Returns the correct [RegistryAccess] for the current side.
    /// Note that during scenarios such as world loading, this may be unreliable, use more reliable sources there, e.g. [Level#registryAccess()].
    ///
    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The correct [RegistryAccess] for the current side.
    static RegistryAccess registryAccess(boolean client) {
        return client ? ClientRegistryAccess.get() : ServerRegistryAccess.get();
    }

    /// Holds all registry keys used by the mod.
    @ApiStatus.NonExtendable
    interface Keys {
        // @formatter:off
        // Static registries
        ResourceKey<Registry<MapCodec<? extends AbilityEffect>>>     ABILITY_EFFECT     = createKey("ability_effect");
        ResourceKey<Registry<MapCodec<? extends GrowthType>>>        GROWTH_TYPE        = createKey("growth_type");
        ResourceKey<Registry<MapCodec<? extends RitualEffect>>>      RITUAL_EFFECT      = createKey("ritual_effect");
        ResourceKey<Registry<MapCodec<? extends RitualRequirement>>> RITUAL_REQUIREMENT = createKey("ritual_requirement");
        ResourceKey<Registry<MapCodec<? extends RitualTrigger<?>>>>  RITUAL_TRIGGER     = createKey("ritual_trigger");
        ResourceKey<Registry<MapCodec<? extends SpellIngredient>>>   SPELL_INGREDIENT   = createKey("spell_ingredient");
        ResourceKey<Registry<SpellPart>>                             SPELL_PART         = createKey("spell_part");
        // Datapack registries
        ResourceKey<Registry<Ability>>          ABILITY            = createKey("ability");
        ResourceKey<Registry<Affinity>>         AFFINITY           = createKey("affinity");
        ResourceKey<Registry<AltarCapMaterial>> ALTAR_CAP_MATERIAL = createKey("altar_cap_material");
        ResourceKey<Registry<AltarMaterial>>    ALTAR_MATERIAL     = createKey("altar_material");
        ResourceKey<Registry<EtheriumType>>     ETHERIUM_TYPE      = createKey("etherium_type");
        ResourceKey<Registry<OcculusTab>>       OCCULUS_TAB        = createKey("occulus_tab");
        ResourceKey<Registry<Plant>>            PLANT              = createKey("plant");
        ResourceKey<Registry<Ritual<?>>>        RITUAL             = createKey("ritual");
        ResourceKey<Registry<Skill>>            SKILL              = createKey("skill");
        ResourceKey<Registry<SkillPoint>>       SKILL_POINT        = createKey("skill_point");
        ResourceKey<Registry<SpellPartData>>    SPELL_PART_DATA    = createKey("spell_part_data");
        ResourceKey<Registry<Spell>>            SPELL_PREFAB       = createKey("spell_prefab");
        // @formatter:on

        private static <T> ResourceKey<Registry<T>> createKey(String path) {
            return ResourceKey.createRegistryKey(ArsMagicaApi.id(path));
        }
    }
}
