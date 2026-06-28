package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.SkillPoint;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.List;
import java.util.stream.Stream;

public interface AMMagic {
    // @formatter:off
    ResourceKey<Affinity>   WATER                     = affinity("water");
    ResourceKey<Affinity>   FIRE                      = affinity("fire");
    ResourceKey<Affinity>   EARTH                     = affinity("earth");
    ResourceKey<Affinity>   AIR                       = affinity("air");
    ResourceKey<Affinity>   ICE                       = affinity("ice");
    ResourceKey<Affinity>   LIGHTNING                 = affinity("lightning");
    ResourceKey<Affinity>   NATURE                    = affinity("nature");
    ResourceKey<Affinity>   LIFE                      = affinity("life");
    ResourceKey<Affinity>   ARCANE                    = affinity("arcane");
    ResourceKey<Affinity>   ENDER                     = affinity("ender");
    ResourceKey<OcculusTab> OFFENSE                   = occulusTab("offense");
    ResourceKey<OcculusTab> DEFENSE                   = occulusTab("defense");
    ResourceKey<OcculusTab> UTILITY                   = occulusTab("utility");
    ResourceKey<OcculusTab> TALENT                    = occulusTab("talent");
    ResourceKey<OcculusTab> AFFINITY                  = occulusTab("affinity");
    ResourceKey<SkillPoint> BLUE_POINT                = skillPoint("blue");
    ResourceKey<SkillPoint> GREEN_POINT               = skillPoint("green");
    ResourceKey<SkillPoint> RED_POINT                 = skillPoint("red");
    ResourceKey<Skill>      AFFINITY_GAINS_BOOST      = skill("affinity_gains_boost");
    ResourceKey<Skill>      AUGMENTED_CASTING         = skill("augmented_casting");
    ResourceKey<Skill>      EXTRA_SUMMONS             = skill("extra_summons");
    //ResourceKey<Skill>      MAGE_BAND_1               = skill("mage_band_1");
    //ResourceKey<Skill>      MAGE_BAND_2               = skill("mage_band_2");
    ResourceKey<Skill>      MANA_REGENERATION_BOOST_1 = skill("mana_regeneration_boost_1");
    ResourceKey<Skill>      MANA_REGENERATION_BOOST_2 = skill("mana_regeneration_boost_2");
    ResourceKey<Skill>      MANA_REGENERATION_BOOST_3 = skill("mana_regeneration_boost_3");
    ResourceKey<Skill>      SHIELD_OVERLOAD           = skill("shield_overload");
    ResourceKey<Skill>      SPELL_MOTION              = skill("spell_motion");
    // @formatter:on
    List<ResourceKey<Affinity>> AFFINITIES = List.of(WATER, FIRE, EARTH, AIR, ICE, LIGHTNING, NATURE, LIFE, ARCANE, ENDER);
    List<ResourceKey<Affinity>> AFFINITIES_WITH_NONE = Stream.concat(Stream.of(Affinity.NONE), AFFINITIES.stream()).toList();
    List<ResourceKey<SkillPoint>> SKILL_POINTS = List.of(BLUE_POINT, GREEN_POINT, RED_POINT);
    List<ResourceKey<Skill>> TALENTS = List.of(AFFINITY_GAINS_BOOST, AUGMENTED_CASTING, EXTRA_SUMMONS, MANA_REGENERATION_BOOST_1, MANA_REGENERATION_BOOST_2, MANA_REGENERATION_BOOST_3, SHIELD_OVERLOAD, SPELL_MOTION);

    private static ResourceKey<Affinity> affinity(String name) {
        return key(AMRegistries.Keys.AFFINITY, name);
    }

    private static ResourceKey<OcculusTab> occulusTab(String name) {
        return key(AMRegistries.Keys.OCCULUS_TAB, name);
    }

    private static ResourceKey<SkillPoint> skillPoint(String name) {
        return key(AMRegistries.Keys.SKILL_POINT, name);
    }

    private static ResourceKey<Skill> skill(String name) {
        return key(AMRegistries.Keys.SKILL, name);
    }

    private static <T> ResourceKey<T> key(ResourceKey<Registry<T>> registryKey, String name) {
        return ResourceKey.create(registryKey, ArsMagicaApi.id(name));
    }
}
