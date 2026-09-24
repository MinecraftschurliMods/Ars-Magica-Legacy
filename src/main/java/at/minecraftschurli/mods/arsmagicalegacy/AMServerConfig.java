package at.minecraftschurli.mods.arsmagicalegacy;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jspecify.annotations.Nullable;

public final class AMServerConfig {
    public static final ModConfigSpec.ConfigValue<String> MAGIC_ADVANCEMENT;
    public static final ModConfigSpec.DoubleValue MANA_TO_BURNOUT_RATIO;
    public static final ModConfigSpec.IntValue ALTAR_CHECK_INTERVAL;
    public static final ModConfigSpec.BooleanValue INSCRIPTION_TABLE_IN_WORLD_UPGRADING;
    public static final ModConfigSpec.IntValue OBELISK_MAX_ETHERIUM;
    public static final ModConfigSpec.IntValue CELESTIAL_PRISM_MAX_ETHERIUM;
    public static final ModConfigSpec.IntValue BLACK_AUREM_MAX_ETHERIUM;
    public static final ModConfigSpec.DoubleValue REDSTONE_INLAY_SPEED_MULTIPLIER;
    public static final ModConfigSpec.IntValue GOLD_INLAY_RANGE;
    public static final ModConfigSpec.IntValue ARCANE_COMPENDIUM_CONVERSION_CHECK_INTERVAL;
    public static final ModConfigSpec.IntValue ARCANE_COMPENDIUM_CONVERSION_DURATION;
    public static final ModConfigSpec.IntValue ARCANE_COMPENDIUM_CONVERSION_HORIZONTAL_RANGE;
    public static final ModConfigSpec.IntValue ARCANE_COMPENDIUM_CONVERSION_VERTICAL_RANGE;
    public static final ModConfigSpec.DoubleValue ARCANE_SPELL_BOOK_MANA_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue ARCANE_SPELL_BOOK_STAT_MULTIPLIER;
    public static final ModConfigSpec.BooleanValue LIFE_WARD_ENABLE_IN_INVENTORY;
    public static final ModConfigSpec.IntValue LIFE_WARD_COOLDOWN;
    public static final ModConfigSpec.IntValue LIFE_WARD_INTERVAL;
    public static final ModConfigSpec.DoubleValue LIFE_WARD_MAX_HEALTH;
    public static final ModConfigSpec.BooleanValue LIGHTNING_CHARM_ENABLE_IN_INVENTORY;
    public static final ModConfigSpec.DoubleValue LIGHTNING_CHARM_RANGE;
    public static final ModConfigSpec.DoubleValue ENDER_BOOTS_FALL_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue BOSS_PLAYER_CHECK_DISTANCE;
    public static final ModConfigSpec.IntValue BOSS_PLAYER_CHECK_INTERVAL;
    public static final ModConfigSpec.IntValue DRYAD_GROW_INTERVAL;
    public static final ModConfigSpec.DoubleValue DRYAD_GROW_CHANCE;
    public static final ModConfigSpec.IntValue DRYAD_GROW_RADIUS;
    public static final ModConfigSpec.IntValue DRYAD_KILL_COOLDOWN;
    public static final ModConfigSpec.IntValue DRYAD_KILLS_FOR_NATURE_GUARDIAN_SPAWN;
    public static final ModConfigSpec.DoubleValue MANA_VORTEX_DAMAGE;
    public static final ModConfigSpec.DoubleValue MANA_VORTEX_MAX_DAMAGE;
    public static final ModConfigSpec.DoubleValue MANA_VORTEX_RANGE;
    public static final ModConfigSpec.DoubleValue MANA_VORTEX_STEAL;
    public static final ModConfigSpec.DoubleValue MANA_BASE;
    public static final ModConfigSpec.DoubleValue MANA_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MANA_REGENERATION;
    public static final ModConfigSpec.DoubleValue BURNOUT_BASE;
    public static final ModConfigSpec.DoubleValue BURNOUT_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue BURNOUT_REGENERATION;
    public static final ModConfigSpec.DoubleValue LEVEL_BASE;
    public static final ModConfigSpec.DoubleValue LEVEL_MULTIPLIER;
    public static final ModConfigSpec.IntValue EXTRA_SKILL_POINTS;
    public static final ModConfigSpec.DoubleValue AFFINITY_TO_XP_RATIO;
    public static final ModConfigSpec.DoubleValue CONTINUOUS_MODIFIER;
    public static final ModConfigSpec.DoubleValue DIRECT_OPPOSITE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MAJOR_OPPOSITE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MINOR_OPPOSITE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue ADJACENT_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue AFFINITY_GAINS_MODIFIER;
    public static final ModConfigSpec.DoubleValue AFFINITY_GAINS_XP_MODIFIER;
    public static final ModConfigSpec.DoubleValue AFFINITY_TOME_SHIFT;
    public static final ModConfigSpec.DoubleValue AFFINITY_TOME_REDUCTION;
    public static final ModConfigSpec.DoubleValue DAMAGE_DAMAGE;
    public static final ModConfigSpec.DoubleValue EFFECT_DURATION;
    public static final ModConfigSpec.BooleanValue EFFECT_PARTICLES;
    public static final ModConfigSpec.DoubleValue ATTRACT_RANGE;
    public static final ModConfigSpec.DoubleValue ATTRACT_SPEED;
    public static final ModConfigSpec.DoubleValue BANISH_RAIN_DURATION;
    public static final ModConfigSpec.DoubleValue BLINK_RANGE;
    public static final ModConfigSpec.DoubleValue BLIZZARD_DAMAGE;
    public static final ModConfigSpec.DoubleValue BLIZZARD_DURATION;
    public static final ModConfigSpec.DoubleValue BLIZZARD_RANGE;
    public static final ModConfigSpec.DoubleValue BLIZZARD_HEIGHT;
    public static final ModConfigSpec.DoubleValue BLIZZARD_FROST_DURATION;
    public static final ModConfigSpec.IntValue BLIZZARD_TICK_INTERVAL;
    public static final ModConfigSpec.DoubleValue DIG_MANA_FACTOR;
    public static final ModConfigSpec.IntValue DIG_TOOL_TIER;
    public static final ModConfigSpec.DoubleValue EXPLOSION_RANGE;
    public static final ModConfigSpec.DoubleValue FALLING_STAR_DAMAGE;
    public static final ModConfigSpec.DoubleValue FALLING_STAR_RANGE;
    public static final ModConfigSpec.DoubleValue FALLING_STAR_SPEED;
    public static final ModConfigSpec.DoubleValue FALLING_STAR_HEIGHT;
    public static final ModConfigSpec.DoubleValue FALLING_STAR_SPAWN_HEIGHT;
    public static final ModConfigSpec.DoubleValue FIRE_RAIN_DAMAGE;
    public static final ModConfigSpec.DoubleValue FIRE_RAIN_DURATION;
    public static final ModConfigSpec.DoubleValue FIRE_RAIN_RANGE;
    public static final ModConfigSpec.DoubleValue FIRE_RAIN_HEIGHT;
    public static final ModConfigSpec.DoubleValue FIRE_RAIN_FIRE_DURATION;
    public static final ModConfigSpec.IntValue FIRE_RAIN_TICK_INTERVAL;
    public static final ModConfigSpec.DoubleValue FLING_SPEED;
    public static final ModConfigSpec.BooleanValue FORGE_SMELTS_VILLAGERS;
    public static final ModConfigSpec.DoubleValue FROST_DURATION;
    public static final ModConfigSpec.DoubleValue LIFE_DRAIN_DAMAGE;
    public static final ModConfigSpec.DoubleValue LIFE_TAP_DAMAGE;
    public static final ModConfigSpec.DoubleValue LIFE_TAP_FACTOR;
    public static final ModConfigSpec.DoubleValue MANA_BLAST_FACTOR;
    public static final ModConfigSpec.DoubleValue MANA_DRAIN_MAX;
    public static final ModConfigSpec.DoubleValue MELT_ARMOR_FACTOR;
    public static final ModConfigSpec.IntValue RANDOM_TELEPORT_MAX_TRIES;
    public static final ModConfigSpec.DoubleValue RANDOM_TELEPORT_RANGE;
    public static final ModConfigSpec.DoubleValue REPEL_RANGE;
    public static final ModConfigSpec.DoubleValue REPEL_SPEED;
    public static final ModConfigSpec.DoubleValue STORM_DURATION;
    public static final ModConfigSpec.DoubleValue STORM_RANGE;
    public static final ModConfigSpec.DoubleValue STORM_LIGHTNING_BOLT_CHANCE;
    public static final ModConfigSpec.DoubleValue STORM_LIGHTNING_BOLT_TARGET_CHANCE;
    public static final ModConfigSpec.IntValue SUMMON_COUNT;
    public static final ModConfigSpec.DoubleValue SUMMON_MANA_COST;
    public static final ModConfigSpec.DoubleValue WIZARDS_AUTUMN_RANGE;
    public static final ModConfigSpec.DoubleValue BEAM_RANGE;
    public static final ModConfigSpec.DoubleValue CHAIN_RANGE;
    public static final ModConfigSpec.IntValue CHAIN_EXTRA_TARGETS;
    public static final ModConfigSpec.DoubleValue CHAIN_EXTRA_TARGETS_RANGE;
    public static final ModConfigSpec.DoubleValue PROJECTILE_DURATION;
    public static final ModConfigSpec.DoubleValue PROJECTILE_GRAVITY;
    public static final ModConfigSpec.DoubleValue PROJECTILE_SPEED;
    public static final ModConfigSpec.DoubleValue WALL_DURATION;
    public static final ModConfigSpec.DoubleValue WALL_RANGE;
    public static final ModConfigSpec.DoubleValue WALL_HEIGHT;
    public static final ModConfigSpec.IntValue WALL_TICK_INTERVAL;
    public static final ModConfigSpec.DoubleValue WAVE_DURATION;
    public static final ModConfigSpec.DoubleValue WAVE_GRAVITY;
    public static final ModConfigSpec.DoubleValue WAVE_RANGE;
    public static final ModConfigSpec.DoubleValue WAVE_SPEED;
    public static final ModConfigSpec.IntValue WAVE_TICK_INTERVAL;
    public static final ModConfigSpec.DoubleValue ZONE_DURATION;
    public static final ModConfigSpec.DoubleValue ZONE_GRAVITY;
    public static final ModConfigSpec.DoubleValue ZONE_RANGE;
    public static final ModConfigSpec.DoubleValue ZONE_HEIGHT;
    public static final ModConfigSpec.IntValue ZONE_TICK_INTERVAL;
    public static final ModConfigSpec.DoubleValue AUGMENTED_CASTING_MULTIPLIER;
    public static final ModConfigSpec.IntValue EXTRA_SUMMONS_COUNT;
    public static final ModConfigSpec.DoubleValue MANA_REGENERATION_1_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MANA_REGENERATION_2_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MANA_REGENERATION_3_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue SHIELD_OVERLOAD_MULTIPLIER;
    static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        MAGIC_ADVANCEMENT = builder
            .comment("Completing this advancement will unlock magic for the player. Leave empty to not require an advancement and have magic unlocked from the start.")
            .translation(AMTranslations.CONFIG_KEY + "magic_advancement")
            .define("magic_advancement", ArsMagicaApi.id("root").toString(), AMServerConfig::isValidIdentifierOrEmpty);
        MANA_TO_BURNOUT_RATIO = builder
            .comment("The default mana to burnout ratio, used in calculating spell costs.")
            .translation(AMTranslations.CONFIG_KEY + "mana_to_burnout_ratio")
            .defineInRange("mana_to_burnout_ratio", 0.5, 0, 10);
        builder.comment("Configuration for the various blocks.").push("blocks");
        ALTAR_CHECK_INTERVAL = builder
            .comment("The time in ticks between multiblock checks for the altar.")
            .translation(AMTranslations.CONFIG_KEY + "altar_check_interval")
            .defineInRange("altar_check_interval", 20, 1, 200);
        INSCRIPTION_TABLE_IN_WORLD_UPGRADING = builder
            .comment("Whether inscription table upgrading is allowed in-world. If disabled, the upgrades must be applied through crafting.")
            .translation(AMTranslations.CONFIG_KEY + "inscription_table_in_world_upgrading")
            .define("inscription_table_in_world_upgrading", true);
        OBELISK_MAX_ETHERIUM = builder
            .comment("The maximum etherium an Obelisk can store.")
            .translation(AMTranslations.CONFIG_KEY + "obelisk_max_etherium")
            .defineInRange("obelisk_max_etherium", 5000, 1, Integer.MAX_VALUE);
        CELESTIAL_PRISM_MAX_ETHERIUM = builder
            .comment("The maximum etherium a Celestial Prism can store.")
            .translation(AMTranslations.CONFIG_KEY + "celestial_prism_max_etherium")
            .defineInRange("celestial_prism_max_etherium", 5000, 1, Integer.MAX_VALUE);
        BLACK_AUREM_MAX_ETHERIUM = builder
            .comment("The maximum etherium a Black Aurem can store.")
            .translation(AMTranslations.CONFIG_KEY + "black_aurem_max_etherium")
            .defineInRange("black_aurem_max_etherium", 5000, 1, Integer.MAX_VALUE);
        REDSTONE_INLAY_SPEED_MULTIPLIER = builder
            .comment("The speed multiplier used by the Redstone Inlay, multiplied by the regular rail's speed.")
            .translation(AMTranslations.CONFIG_KEY + "redstone_inlay_speed_multiplier")
            .defineInRange("redstone_inlay_speed_multiplier", 2.5, 1, 16);
        GOLD_INLAY_RANGE = builder
            .comment("The teleportation range of the Gold Inlay, in blocks.")
            .translation(AMTranslations.CONFIG_KEY + "gold_inlay_range")
            .defineInRange("gold_inlay_range", 8, 2, 32);
        builder.pop();
        builder.comment("Configuration for the various items.").push("items");
        ARCANE_COMPENDIUM_CONVERSION_CHECK_INTERVAL = builder
            .comment("The time in ticks between checks for the Arcane Compendium conversion. Set to 0 to disable the conversion entirely.")
            .translation(AMTranslations.CONFIG_KEY + "arcane_compendium_conversion_check_interval")
            .defineInRange("arcane_compendium_conversion_check_interval", 20, 0, 200);
        ARCANE_COMPENDIUM_CONVERSION_DURATION = builder
            .comment("The time in ticks that the Arcane Compendium conversion takes.")
            .translation(AMTranslations.CONFIG_KEY + "arcane_compendium_conversion_duration")
            .defineInRange("arcane_compendium_conversion_duration", 50, 1, 1200);
        ARCANE_COMPENDIUM_CONVERSION_HORIZONTAL_RANGE = builder
            .comment("The horizontal range of the Arcane Compendium conversion.")
            .translation(AMTranslations.CONFIG_KEY + "arcane_compendium_conversion_horizontal_range")
            .defineInRange("arcane_compendium_conversion_horizontal_range", 3, 1, 16);
        ARCANE_COMPENDIUM_CONVERSION_VERTICAL_RANGE = builder
            .comment("The vertical range of the Arcane Compendium conversion.")
            .translation(AMTranslations.CONFIG_KEY + "arcane_compendium_conversion_vertical_range")
            .defineInRange("arcane_compendium_conversion_vertical_range", 2, 1, 16);
        ARCANE_SPELL_BOOK_MANA_MULTIPLIER = builder
            .comment("The mana multiplier applied when using the Arcane Spell Book.")
            .translation(AMTranslations.CONFIG_KEY + "arcane_spell_book_mana_multiplier")
            .defineInRange("arcane_spell_book_mana_multiplier", 0.8, 0, 1);
        ARCANE_SPELL_BOOK_STAT_MULTIPLIER = builder
            .comment("The stat multiplier applied when using the Arcane Spell Book.")
            .translation(AMTranslations.CONFIG_KEY + "arcane_spell_book_stat_multiplier")
            .defineInRange("arcane_spell_book_stat_multiplier", 1.4, 1, 8);
        LIFE_WARD_ENABLE_IN_INVENTORY = builder
            .comment("Whether to enable the Life Ward functionality from the inventory, if Curios is installed. If Curios is not installed, this will always be considered true.")
            .translation(AMTranslations.CONFIG_KEY + "life_ward_enable_in_inventory")
            .define("life_ward_enable_in_inventory", true);
        LIFE_WARD_COOLDOWN = builder
            .comment("The amount of ticks to wait until the Life Ward starts to regenerate.")
            .translation(AMTranslations.CONFIG_KEY + "life_ward_cooldown")
            .defineInRange("life_ward_cooldown", 100, 1, 1200);
        LIFE_WARD_INTERVAL = builder
            .comment("The amount of ticks to wait until the Life Ward regenerates half a heart, after the initial cooldown.")
            .translation(AMTranslations.CONFIG_KEY + "life_ward_interval")
            .defineInRange("life_ward_interval", 20, 1, 1200);
        LIFE_WARD_MAX_HEALTH = builder
            .comment("The max extra health the Life Ward can provide.")
            .translation(AMTranslations.CONFIG_KEY + "life_ward_max_health")
            .defineInRange("life_ward_max_health", 20., 0, Short.MAX_VALUE);
        LIGHTNING_CHARM_ENABLE_IN_INVENTORY = builder
            .comment("Whether to enable the Lightning Charm functionality from the inventory, if Curios is installed. If Curios is not installed, this will always be considered true.")
            .translation(AMTranslations.CONFIG_KEY + "lightning_charm_enable_in_inventory")
            .define("lightning_charm_enable_in_inventory", true);
        LIGHTNING_CHARM_RANGE = builder
            .comment("The range of the Lightning Charm's effect.")
            .translation(AMTranslations.CONFIG_KEY + "lightning_charm_range")
            .defineInRange("lightning_charm_range", 16., 1, 64);
        ENDER_BOOTS_FALL_DAMAGE_MULTIPLIER = builder
            .comment("The fall damage multiplier applied when wearing the Ender Boots.")
            .translation(AMTranslations.CONFIG_KEY + "ender_boots_fall_damage_multiplier")
            .defineInRange("ender_boots_fall_damage_multiplier", 0.5, 0, 1);
        builder.pop();
        builder.comment("Configuration for the various entities.").push("entities");
        BOSS_PLAYER_CHECK_DISTANCE = builder
            .comment("The distance from a boss within which the boss bar will be shown.")
            .translation(AMTranslations.CONFIG_KEY + "boss_player_check_distance")
            .defineInRange("boss_player_check_distance", 32., 1, 128);
        BOSS_PLAYER_CHECK_INTERVAL = builder
            .comment("The time in ticks between the boss checking for players in its range to show the boss bar to.")
            .translation(AMTranslations.CONFIG_KEY + "boss_player_check_interval")
            .defineInRange("boss_player_check_interval", 20, 1, 1200);
        DRYAD_GROW_INTERVAL = builder
            .comment("The time in ticks between a Dryad growing nearby plants.")
            .translation(AMTranslations.CONFIG_KEY + "dryad_grow_interval")
            .defineInRange("dryad_grow_interval", 200, 1, 72000);
        DRYAD_GROW_CHANCE = builder
            .comment("The chance of a Dryad growing nearby plants successfully.")
            .translation(AMTranslations.CONFIG_KEY + "dryad_grow_chance")
            .defineInRange("dryad_grow_chance", 0.01, 0, 1);
        DRYAD_GROW_RADIUS = builder
            .comment("The radius of a Dryad's growing effect.")
            .translation(AMTranslations.CONFIG_KEY + "dryad_grow_radius")
            .defineInRange("dryad_grow_radius", 2, 1, Short.MAX_VALUE);
        DRYAD_KILL_COOLDOWN = builder
            .comment("If enough dryads are killed during this amount of time in ticks, the Nature Guardian will spawn. Set to 0 to disable this way of summoning the Nature Guardian.")
            .translation(AMTranslations.CONFIG_KEY + "dryad_kill_cooldown")
            .defineInRange("dryad_kill_cooldown", 1200, 0, 1000000);
        DRYAD_KILLS_FOR_NATURE_GUARDIAN_SPAWN = builder
            .comment("The amount of dryads to be killed within the cooldown in order for the Nature Guardian to spawn.")
            .translation(AMTranslations.CONFIG_KEY + "dryad_kills_for_nature_guardian_spawn")
            .defineInRange("dryad_kills_for_nature_guardian_spawn", 20, 1, Short.MAX_VALUE);
        MANA_VORTEX_DAMAGE = builder
            .comment("The amount of damage the Mana Vortex deals per stolen mana point.")
            .translation(AMTranslations.CONFIG_KEY + "mana_vortex_damage")
            .defineInRange("damage", 0.01, 0, 10);
        MANA_VORTEX_MAX_DAMAGE = builder
            .comment("The maximum damage the Mana Vortex can deal.")
            .translation(AMTranslations.CONFIG_KEY + "mana_vortex_max_damage")
            .defineInRange("max_damage", 100., 1, 1000000);
        MANA_VORTEX_RANGE = builder
            .comment("The range of the Mana Vortex.")
            .translation(AMTranslations.CONFIG_KEY + "mana_vortex_range")
            .defineInRange("range", 4., 1, 16);
        MANA_VORTEX_STEAL = builder
            .comment("The amount of mana a Mana Vortex will steal each tick, as a multiplier of the target's max mana.")
            .translation(AMTranslations.CONFIG_KEY + "mana_vortex_steal")
            .defineInRange("steal", 0.01, 0, 1);
        builder.pop();
        builder.comment("Configuration for the mana leveling and regeneration of players.").push("mana");
        MANA_BASE = builder
            .comment("The base value for mana calculation. Mana is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "mana_base")
            .worldRestart()
            .defineInRange("base", 200., 0, 1000000);
        MANA_MULTIPLIER = builder
            .comment("The multiplier for mana calculation. Mana is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "mana_multiplier")
            .worldRestart()
            .defineInRange("multiplier", 25., 0, 1000000);
        MANA_REGENERATION = builder
            .comment("The multiplier for mana regeneration. Mana regeneration is calculated as (base + multiplier * (level - 1)) * regeneration.")
            .translation(AMTranslations.CONFIG_KEY + "mana_regeneration")
            .worldRestart()
            .defineInRange("regeneration", 0.001, 0, 1000000);
        builder.pop();
        builder.comment("Configuration for the burnout leveling and regeneration of players.").push("burnout");
        BURNOUT_BASE = builder
            .comment("The base value for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "burnout_base")
            .worldRestart()
            .defineInRange("base", 200., 0, 1000000);
        BURNOUT_MULTIPLIER = builder
            .comment("The multiplier for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "burnout_multiplier")
            .worldRestart()
            .defineInRange("multiplier", 25., 0, 1000000);
        BURNOUT_REGENERATION = builder
            .comment("The multiplier for burnout regeneration. Burnout regeneration is calculated as (base + multiplier * (level - 1)) * regeneration.")
            .translation(AMTranslations.CONFIG_KEY + "burnout_regeneration")
            .worldRestart()
            .defineInRange("regeneration", 0.001, 0, 1000000);
        builder.pop();
        builder.comment("Configuration for the magic leveling of players.").push("level");
        LEVEL_BASE = builder
            .comment("The base value for leveling calculation. XP cost is calculated as multiplier * base ^ (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "level_base")
            .worldRestart()
            .defineInRange("base", 1.1, 0, 10000);
        LEVEL_MULTIPLIER = builder
            .comment("The multiplier for leveling calculation. XP cost is calculated as multiplier * base ^ (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "level_multiplier")
            .worldRestart()
            .defineInRange("multiplier", 2.5, 0, 10000);
        EXTRA_SKILL_POINTS = builder
            .comment("The extra blue skill points a player gets at level 1, in addition to the one they already get.")
            .translation(AMTranslations.CONFIG_KEY + "extra_skill_points")
            .defineInRange("extra_skill_points", 2, 0, Short.MAX_VALUE);
        builder.pop();
        builder.comment("Configuration for the affinity shifting of players.").push("affinity");
        AFFINITY_TO_XP_RATIO = builder
            .comment("The affinity to xp ratio. When awarding xp, the amount of used affinities will be multiplied with this modifier.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_to_xp_ratio")
            .defineInRange("affinity_to_xp_ratio", 1., 0, 10);
        CONTINUOUS_MODIFIER = builder
            .comment("By what factor affinity and xp gain will be amplified when a continuous spell shape is used.")
            .translation(AMTranslations.CONFIG_KEY + "continuous_modifier")
            .defineInRange("continuous_modifier", 0.25, 0, 1);
        DIRECT_OPPOSITE_MULTIPLIER = builder
            .comment("When an affinity shift is applied, what portion of it is subtracted from the direct opposite affinity.")
            .translation(AMTranslations.CONFIG_KEY + "direct_opposite_multiplier")
            .defineInRange("direct_opposite_multiplier", 0.75, 0, 1);
        MAJOR_OPPOSITE_MULTIPLIER = builder
            .comment("When an affinity shift is applied, what portion of it is subtracted from the major opposite affinities.")
            .translation(AMTranslations.CONFIG_KEY + "major_opposite_multiplier")
            .defineInRange("major_opposite_multiplier", 0.5, 0, 1);
        MINOR_OPPOSITE_MULTIPLIER = builder
            .comment("When an affinity shift is applied, what portion of it is subtracted from the minor opposite affinities.")
            .translation(AMTranslations.CONFIG_KEY + "minor_opposite_multiplier")
            .defineInRange("minor_opposite_multiplier", 0.25, 0, 1);
        ADJACENT_MULTIPLIER = builder
            .comment("When an affinity shift is applied, what portion of it is added to the adjacent affinities.")
            .translation(AMTranslations.CONFIG_KEY + "adjacent_multiplier")
            .defineInRange("adjacent_multiplier", 0.25, 0, 1);
        AFFINITY_GAINS_MODIFIER = builder
            .comment("When the Affinity Gains talent is learned, by what factor affinity gain will be amplified.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_gains_modifier")
            .defineInRange("affinity_gains_modifier", 1.1, 1, 100);
        AFFINITY_GAINS_XP_MODIFIER = builder
            .comment("When the Affinity Gains talent is learned, by what factor XP gain will be amplified.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_gains_xp_modifier")
            .defineInRange("affinity_gains_xp_modifier", 0.9, 0, 1);
        AFFINITY_TOME_SHIFT = builder
            .comment("The amount to add to an affinity when using an Affinity Tome.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_tome_shift")
            .defineInRange("affinity_tome_shift", 0.1, 0, 1);
        AFFINITY_TOME_REDUCTION = builder
            .comment("The amount to subtract from all other affinities when using an Affinity Tome.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_tome_reduction")
            .defineInRange("affinity_tome_reduction", 0.1, 0, 1);
        builder.pop();
        builder.comment("Configuration of various component-specific values.").push("components");
        DAMAGE_DAMAGE = builder
            .comment("The damage of damage-based components, in half hearts. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "damage_damage")
            .defineInRange("damage_damage", 4., 0, Short.MAX_VALUE);
        EFFECT_DURATION = builder
            .comment("The duration of effect-based components, in ticks. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "effect_duration")
            .defineInRange("effect_duration", 600., 1, Short.MAX_VALUE);
        EFFECT_PARTICLES = builder
            .comment("Whether to show effect particles for effect-based components.")
            .translation(AMTranslations.CONFIG_KEY + "effect_particles")
            .define("effect_particles", false);
        ATTRACT_RANGE = builder
            .comment("The range of the Attract component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "attract_range")
            .defineInRange("attract_range", 4., 1, 16);
        ATTRACT_SPEED = builder
            .comment("The speed of the Attract component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "attract_speed")
            .defineInRange("attract_speed", 1., 1, 16);
        BANISH_RAIN_DURATION = builder
            .comment("The duration used by the Banish Rain component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "banish_rain_duration")
            .defineInRange("banish_rain_duration", 24000., 1, Integer.MAX_VALUE);
        BLINK_RANGE = builder
            .comment("The range of the Blink component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "blink_range")
            .defineInRange("blink_range", 16., 1, 64);
        BLIZZARD_DAMAGE = builder
            .comment("The damage of the Blizzard component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "blizzard_damage")
            .defineInRange("blizzard_damage", 2., 1, 100);
        BLIZZARD_DURATION = builder
            .comment("The duration of the Blizzard component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "blizzard_duration")
            .defineInRange("blizzard_duration", 600., 1, Short.MAX_VALUE);
        BLIZZARD_RANGE = builder
            .comment("The range of the Blizzard component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "blizzard_range")
            .defineInRange("blizzard_range", 2., 1, 16);
        BLIZZARD_HEIGHT = builder
            .comment("The height used by the Blizzard component, relative to its width.")
            .translation(AMTranslations.CONFIG_KEY + "blizzard_height")
            .defineInRange("blizzard_height", 1., 0, 8);
        BLIZZARD_FROST_DURATION = builder
            .comment("The duration of the frost applied by the Blizzard component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "blizzard_frost_duration")
            .defineInRange("blizzard_frost_duration", 50., 1, Short.MAX_VALUE);
        BLIZZARD_TICK_INTERVAL = builder
            .comment("The tick interval used by the Blizzard component.")
            .translation(AMTranslations.CONFIG_KEY + "blizzard_tick_interval")
            .defineInRange("blizzard_tick_interval", 5, 1, 100);
        DIG_MANA_FACTOR = builder
            .comment("The mana factor of the Dig component. The mana cost factor will be multiplied with the block's hardness.")
            .translation(AMTranslations.CONFIG_KEY + "dig_mana_factor")
            .defineInRange("dig_mana_factor", 1.25, 0, Short.MAX_VALUE);
        DIG_TOOL_TIER = builder
            .comment("The tool tier of the Dig component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "dig_tool_tier")
            .defineInRange("dig_tool_tier", 2, 0, 16);
        EXPLOSION_RANGE = builder
            .comment("The range of the Explosion component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "explosion_range")
            .defineInRange("explosion_range", 2., 1, 16);
        FALLING_STAR_DAMAGE = builder
            .comment("The damage of the Falling Star component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "falling_star_damage")
            .defineInRange("falling_star_damage", 6., 1, 100);
        FALLING_STAR_RANGE = builder
            .comment("The range of the Falling Star component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "falling_star_range")
            .defineInRange("falling_star_range", 6., 1, 16);
        FALLING_STAR_SPEED = builder
            .comment("The falling speed of the Falling Star component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "falling_star_speed")
            .defineInRange("falling_star_speed", 1., 0, 8);
        FALLING_STAR_HEIGHT = builder
            .comment("The height used by the Falling Star component.")
            .translation(AMTranslations.CONFIG_KEY + "falling_star_height")
            .defineInRange("falling_star_height", 1., 0, 8);
        FALLING_STAR_SPAWN_HEIGHT = builder
            .comment("The height in which the Falling Star will spawn.")
            .translation(AMTranslations.CONFIG_KEY + "falling_star_spawn_height")
            .defineInRange("falling_star_spawn_height", 128., 1, 384);
        FIRE_RAIN_DAMAGE = builder
            .comment("The damage of the Fire Rain component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "fire_rain_damage")
            .defineInRange("fire_rain_damage", 2., 1, 100);
        FIRE_RAIN_DURATION = builder
            .comment("The duration of the Fire Rain component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "fire_rain_duration")
            .defineInRange("fire_rain_duration", 600., 1, Short.MAX_VALUE);
        FIRE_RAIN_RANGE = builder
            .comment("The range of the Fire Rain component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "fire_rain_range")
            .defineInRange("fire_rain_range", 2., 1, 16);
        FIRE_RAIN_HEIGHT = builder
            .comment("The height used by the Fire Rain component, relative to its width.")
            .translation(AMTranslations.CONFIG_KEY + "fire_rain_height")
            .defineInRange("fire_rain_height", 1., 0, 8);
        FIRE_RAIN_FIRE_DURATION = builder
            .comment("The duration of the fire applied by the Fire Rain component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "fire_rain_fire_duration")
            .defineInRange("fire_rain_fire_duration", 50., 1, Short.MAX_VALUE);
        FIRE_RAIN_TICK_INTERVAL = builder
            .comment("The tick interval used by the Fire Rain component.")
            .translation(AMTranslations.CONFIG_KEY + "fire_rain_tick_interval")
            .defineInRange("fire_rain_tick_interval", 5, 1, 100);
        FLING_SPEED = builder
            .comment("The speed of the Fling component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "fling_speed")
            .defineInRange("fling_speed", 1., 1, 16);
        FORGE_SMELTS_VILLAGERS = builder
            .comment("Whether the Forge component instantly kills villagers, dropping emeralds.")
            .translation(AMTranslations.CONFIG_KEY + "forge_smelts_villagers")
            .define("forge_smelts_villagers", true);
        FROST_DURATION = builder
            .comment("The duration of the Frost component, in ticks. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "frost_duration")
            .defineInRange("frost_duration", 600., 1, Short.MAX_VALUE);
        LIFE_DRAIN_DAMAGE = builder
            .comment("The damage of the Life Drain component, in ticks. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "life_drain_damage")
            .defineInRange("life_drain_damage", 2., 1, 100);
        LIFE_TAP_DAMAGE = builder
            .comment("The damage of the Life Tap component, in ticks. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "life_tap_damage")
            .defineInRange("life_tap_damage", 2., 1, 100);
        LIFE_TAP_FACTOR = builder
            .comment("When the Life Tap component is cast, the caster regenerates the damage dealt, times their max mana, times this factor.")
            .translation(AMTranslations.CONFIG_KEY + "life_tap_factor")
            .defineInRange("life_tap_factor", 0.01, 0, 1);
        MANA_BLAST_FACTOR = builder
            .comment("When the Mana Blast component is cast, the damage is the caster's current mana, times this factor, potentially amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "mana_blast_factor")
            .defineInRange("mana_blast_factor", 0.04, 0, 1);
        MANA_DRAIN_MAX = builder
            .comment("The maximum amount of mana drained by the Mana Drain component.")
            .translation(AMTranslations.CONFIG_KEY + "mana_drain_max")
            .defineInRange("mana_drain_max", 250., 0, Short.MAX_VALUE);
        MELT_ARMOR_FACTOR = builder
            .comment("When the Melt Armor component is cast, what factor the armor's durability will be multiplied with.")
            .translation(AMTranslations.CONFIG_KEY + "melt_armor_factor")
            .defineInRange("melt_armor_factor", 0.75, 0, 1);
        RANDOM_TELEPORT_MAX_TRIES = builder
            .comment("How many times the Random Teleport component will try to find a position.")
            .translation(AMTranslations.CONFIG_KEY + "random_teleport_max_tries")
            .defineInRange("random_teleport_max_tries", 64, 1, Short.MAX_VALUE);
        RANDOM_TELEPORT_RANGE = builder
            .comment("The range of the Random Teleport component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "random_teleport_range")
            .defineInRange("random_teleport_range", 16., 1, 64);
        REPEL_RANGE = builder
            .comment("The range of the Repel component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "repel_range")
            .defineInRange("repel_range", 4., 1, 16);
        REPEL_SPEED = builder
            .comment("The speed of the Repel component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "repel_speed")
            .defineInRange("repel_speed", 1., 1, 16);
        STORM_DURATION = builder
            .comment("The duration used by the Storm component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "storm_duration")
            .defineInRange("storm_duration", 72000., 1, Integer.MAX_VALUE);
        STORM_RANGE = builder
            .comment("The range used by the Storm component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "storm_range")
            .defineInRange("storm_range", 64., 1, 256);
        STORM_LIGHTNING_BOLT_CHANCE = builder
            .comment("The chance for the Storm component to summon a lightning bolt somewhere in range.")
            .translation(AMTranslations.CONFIG_KEY + "storm_lightning_bolt_chance")
            .defineInRange("storm_lightning_bolt_chance", 0.2, 0, 1);
        STORM_LIGHTNING_BOLT_TARGET_CHANCE = builder
            .comment("The chance for the Storm component to summon a target-seeking lightning bolt somewhere in range.")
            .translation(AMTranslations.CONFIG_KEY + "storm_lightning_bolt_target_chance")
            .defineInRange("storm_lightning_bolt_target_chance", 0.2, 0, 1);
        SUMMON_COUNT = builder
            .comment("The amount of summons a player can have at the same time.")
            .translation(AMTranslations.CONFIG_KEY + "summon_count")
            .defineInRange("summon_count", 1, 1, 1000000);
        SUMMON_MANA_COST = builder
            .comment("The amount of mana, multiplied by the summons's health, that is consumed when using the Summon component.")
            .translation(AMTranslations.CONFIG_KEY + "summon_mana_cost")
            .defineInRange("summon_mana_cost", 20., 0, 1000000);
        WIZARDS_AUTUMN_RANGE = builder
            .comment("The range used by the Wizard's Autumn component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "wizards_autumn_range")
            .defineInRange("wizards_autumn_range", 2., 1, 64);
        builder.pop();
        builder.comment("Configuration of various shape-specific values.").push("shapes");
        BEAM_RANGE = builder
            .comment("The range used by the Beam shape.")
            .translation(AMTranslations.CONFIG_KEY + "beam_range")
            .defineInRange("beam_range", 64., 1, 256);
        CHAIN_RANGE = builder
            .comment("The range used by the Chain shape.")
            .translation(AMTranslations.CONFIG_KEY + "chain_range")
            .defineInRange("chain_range", 16., 1, 256);
        CHAIN_EXTRA_TARGETS = builder
            .comment("The amount of extra targets the Chain shape can hit.")
            .translation(AMTranslations.CONFIG_KEY + "chain_extra_targets")
            .defineInRange("chain_extra_targets", 4, 1, 16);
        CHAIN_EXTRA_TARGETS_RANGE = builder
            .comment("The range used by the Chain shape when seeking extra targets. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "chain_extra_targets_range")
            .defineInRange("chain_extra_targets_range", 4., 1, 64);
        PROJECTILE_DURATION = builder
            .comment("The duration used by the Projectile shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "projectile_duration")
            .defineInRange("projectile_duration", 30., 1, Short.MAX_VALUE);
        PROJECTILE_GRAVITY = builder
            .comment("If a Gravity modifier is present on the Projectile, by how much gravity will be increased.")
            .translation(AMTranslations.CONFIG_KEY + "projectile_gravity")
            .defineInRange("projectile_gravity", 0.025, 0, 1);
        PROJECTILE_SPEED = builder
            .comment("The speed used by the Projectile shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "projectile_speed")
            .defineInRange("projectile_speed", 0.5, 0, 10);
        WALL_DURATION = builder
            .comment("The duration used by the Wall shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "wall_duration")
            .defineInRange("wall_duration", 200., 1, Short.MAX_VALUE);
        WALL_RANGE = builder
            .comment("The range used by the Wall shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "wall_range")
            .defineInRange("wall_range", 2.5, 0, 16);
        WALL_HEIGHT = builder
            .comment("The height used by the Wall shape, relative to its width.")
            .translation(AMTranslations.CONFIG_KEY + "wall_height")
            .defineInRange("wall_height", 0.8, 0, 8);
        WALL_TICK_INTERVAL = builder
            .comment("The time in ticks between the Wall shape applying its effect.")
            .translation(AMTranslations.CONFIG_KEY + "wall_tick_interval")
            .defineInRange("wall_tick_interval", 5, 1, 100);
        WAVE_DURATION = builder
            .comment("The duration used by the Wave shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "wave_duration")
            .defineInRange("wave_duration", 80., 1, Short.MAX_VALUE);
        WAVE_GRAVITY = builder
            .comment("If a Gravity modifier is present on the Wave, by how much gravity will be increased.")
            .translation(AMTranslations.CONFIG_KEY + "wave_gravity")
            .defineInRange("wave_gravity", 0.025, 0, 1);
        WAVE_RANGE = builder
            .comment("The range used by the Wave shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "wave_range")
            .defineInRange("wave_range", 1., 0, 16);
        WAVE_SPEED = builder
            .comment("The speed used by the Wave shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "wave_speed")
            .defineInRange("wave_speed", 0.5, 0, 8);
        WAVE_TICK_INTERVAL = builder
            .comment("The tick interval used by the Wave shape.")
            .translation(AMTranslations.CONFIG_KEY + "wave_tick_interval")
            .defineInRange("wave_tick_interval", 5, 1, 100);
        ZONE_DURATION = builder
            .comment("The duration used by the Zone shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "zone_duration")
            .defineInRange("zone_duration", 200., 1, Short.MAX_VALUE);
        ZONE_GRAVITY = builder
            .comment("If a Gravity modifier is present on the Zone, by how much gravity will be increased.")
            .translation(AMTranslations.CONFIG_KEY + "zone_gravity")
            .defineInRange("zone_gravity", 0.025, 0, 1);
        ZONE_RANGE = builder
            .comment("The range used by the Zone shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "zone_range")
            .defineInRange("zone_range", 1., 0, 16);
        ZONE_HEIGHT = builder
            .comment("The height used by the Zone shape.")
            .translation(AMTranslations.CONFIG_KEY + "zone_height")
            .defineInRange("zone_height", 0.25, 0, 8);
        ZONE_TICK_INTERVAL = builder
            .comment("The tick interval used by the Zone shape.")
            .translation(AMTranslations.CONFIG_KEY + "zone_tick_interval")
            .defineInRange("zone_tick_interval", 5, 1, 100);
        builder.pop();
        builder.comment("Configuration of various talent-specific values.").push("talents");
        AUGMENTED_CASTING_MULTIPLIER = builder
            .comment("The multiplier to various stats used by the Augmented Casting talent.")
            .translation(AMTranslations.CONFIG_KEY + "augmented_casting_multiplier")
            .defineInRange("augmented_casting_multiplier", 1.4, 1, 8);
        EXTRA_SUMMONS_COUNT = builder
            .comment("The amount of additional summons a player can have at the same time when they have the Extra Summons talent.")
            .translation(AMTranslations.CONFIG_KEY + "extra_summons_count")
            .defineInRange("extra_summons_count", 1, 1, 1000000);
        MANA_REGENERATION_1_MULTIPLIER = builder
            .comment("The multiplier to mana regeneration used by the Mana Regeneration 1 talent.")
            .translation(AMTranslations.CONFIG_KEY + "mana_regeneration_1_multiplier")
            .defineInRange("mana_regeneration_1_multiplier", 1.05, 1, 10);
        MANA_REGENERATION_2_MULTIPLIER = builder
            .comment("The multiplier to mana regeneration used by the Mana Regeneration 2 talent.")
            .translation(AMTranslations.CONFIG_KEY + "mana_regeneration_2_multiplier")
            .defineInRange("mana_regeneration_2_multiplier", 1.1, 1, 10);
        MANA_REGENERATION_3_MULTIPLIER = builder
            .comment("The multiplier to mana regeneration used by the Mana Regeneration 3 talent.")
            .translation(AMTranslations.CONFIG_KEY + "mana_regeneration_3_multiplier")
            .defineInRange("mana_regeneration_3_multiplier", 1.15, 1, 10);
        SHIELD_OVERLOAD_MULTIPLIER = builder
            .comment("If the player is at full mana and has the Shield Overload talent, the multiplier to incoming damage that will be applied.")
            .translation(AMTranslations.CONFIG_KEY + "shield_overload_multiplier")
            .defineInRange("shield_overload_multiplier", 0.95, 0, 1);
        builder.pop();
        SPEC = builder.build();
    }

    private static boolean isValidIdentifierOrEmpty(@Nullable Object o) {
        if (o == null) return false;
        String s = o.toString();
        if (s.isEmpty()) return true;
        if (!s.contains(":")) return Identifier.isValidPath(s);
        String[] split = s.split(":");
        return split.length == 2 && Identifier.isValidNamespace(split[0]) && Identifier.isValidPath(split[1]);
    }
}
