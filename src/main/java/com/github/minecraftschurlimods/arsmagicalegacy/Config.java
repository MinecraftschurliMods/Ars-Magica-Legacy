package com.github.minecraftschurlimods.arsmagicalegacy;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.betterhudlib.HUDElement;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.ApiStatus.Internal;

public final class Config {
    public static final Client CLIENT;
    public static final Server SERVER;
    private static final ForgeConfigSpec clientSpec;
    private static final ForgeConfigSpec serverSpec;
    private static boolean init;

    static {
        final Pair<Client, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = clientPair.getRight();
        CLIENT = clientPair.getLeft();
        final Pair<Server, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = serverPair.getRight();
        SERVER = serverPair.getLeft();
    }

    private Config() {
    }

    @Internal
    static synchronized void init() {
        if (init) return;
        init = true;
        ModLoadingContext context = ModLoadingContext.get();
        context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
        context.registerConfig(ModConfig.Type.SERVER, serverSpec);
    }

    /**
     * Class holding the client config values.
     */
    public static final class Client {
        public final ForgeConfigSpec.IntValue MANA_X;
        public final ForgeConfigSpec.IntValue MANA_Y;
        public final ForgeConfigSpec.EnumValue<HUDElement.AnchorX> MANA_ANCHOR_X;
        public final ForgeConfigSpec.EnumValue<HUDElement.AnchorY> MANA_ANCHOR_Y;
        public final ForgeConfigSpec.IntValue BURNOUT_X;
        public final ForgeConfigSpec.IntValue BURNOUT_Y;
        public final ForgeConfigSpec.EnumValue<HUDElement.AnchorX> BURNOUT_ANCHOR_X;
        public final ForgeConfigSpec.EnumValue<HUDElement.AnchorY> BURNOUT_ANCHOR_Y;
        public final ForgeConfigSpec.IntValue XP_X;
        public final ForgeConfigSpec.IntValue XP_Y;
        public final ForgeConfigSpec.EnumValue<HUDElement.AnchorX> XP_ANCHOR_X;
        public final ForgeConfigSpec.EnumValue<HUDElement.AnchorY> XP_ANCHOR_Y;
        public final ForgeConfigSpec.IntValue SPELL_BOOK_X;
        public final ForgeConfigSpec.IntValue SPELL_BOOK_Y;
        public final ForgeConfigSpec.EnumValue<HUDElement.AnchorX> SPELL_BOOK_ANCHOR_X;
        public final ForgeConfigSpec.EnumValue<HUDElement.AnchorY> SPELL_BOOK_ANCHOR_Y;

        private Client(ForgeConfigSpec.Builder builder) {
            builder.comment("HUD Elements").push("hud_elements");
            builder.push("mana");
            MANA_X = builder
                    .comment("Horizontal position of the mana bar.")
                    .translation(TranslationConstants.CONFIG + "mana_x")
                    .defineInRange("mana_x", 210, Short.MIN_VALUE, Short.MAX_VALUE);
            MANA_Y = builder
                    .comment("Vertical position of the mana bar.")
                    .translation(TranslationConstants.CONFIG + "mana_y")
                    .defineInRange("mana_y", 23, Short.MIN_VALUE, Short.MAX_VALUE);
            MANA_ANCHOR_X = builder
                    .comment("Horizontal anchor of the mana bar.")
                    .translation(TranslationConstants.CONFIG + "mana_anchor_x")
                    .defineEnum("mana_anchor_x", HUDElement.AnchorX.CENTER);
            MANA_ANCHOR_Y = builder
                    .comment("Horizontal anchor of the mana bar.")
                    .translation(TranslationConstants.CONFIG + "mana_anchor_y")
                    .defineEnum("mana_anchor_y", HUDElement.AnchorY.BOTTOM);
            builder.pop();
            builder.push("burnout");
            BURNOUT_X = builder
                    .comment("Horizontal position of the burnout bar.")
                    .translation(TranslationConstants.CONFIG + "burnout_x")
                    .defineInRange("burnout_x", 210, Short.MIN_VALUE, Short.MAX_VALUE);
            BURNOUT_Y = builder
                    .comment("Vertical position of the burnout bar.")
                    .translation(TranslationConstants.CONFIG + "burnout_y")
                    .defineInRange("burnout_y", 13, Short.MIN_VALUE, Short.MAX_VALUE);
            BURNOUT_ANCHOR_X = builder
                    .comment("Horizontal anchor of the burnout bar.")
                    .translation(TranslationConstants.CONFIG + "burnout_anchor_x")
                    .defineEnum("burnout_anchor_x", HUDElement.AnchorX.CENTER);
            BURNOUT_ANCHOR_Y = builder
                    .comment("Horizontal anchor of the burnout bar.")
                    .translation(TranslationConstants.CONFIG + "burnout_anchor_y")
                    .defineEnum("burnout_anchor_y", HUDElement.AnchorY.BOTTOM);
            builder.pop();
            builder.push("xp");
            XP_X = builder
                    .comment("Horizontal position of the magic xp bar.")
                    .translation(TranslationConstants.CONFIG + "xp_x")
                    .defineInRange("xp_x", 210, Short.MIN_VALUE, Short.MAX_VALUE);
            XP_Y = builder
                    .comment("Vertical position of the magic xp bar.")
                    .translation(TranslationConstants.CONFIG + "xp_y")
                    .defineInRange("xp_y", 33, Short.MIN_VALUE, Short.MAX_VALUE);
            XP_ANCHOR_X = builder
                    .comment("Horizontal anchor of the magic xp bar.")
                    .translation(TranslationConstants.CONFIG + "xp_anchor_x")
                    .defineEnum("xp_anchor_x", HUDElement.AnchorX.CENTER);
            XP_ANCHOR_Y = builder
                    .comment("Horizontal anchor of the magic xp bar.")
                    .translation(TranslationConstants.CONFIG + "xp_anchor_y")
                    .defineEnum("xp_anchor_y", HUDElement.AnchorY.BOTTOM);
            builder.pop();
            builder.push("spell_book");
            SPELL_BOOK_X = builder
                    .comment("Horizontal position of the spell book hud.")
                    .translation(TranslationConstants.CONFIG + "spell_book_x")
                    .defineInRange("spell_book_x", 100, Short.MIN_VALUE, Short.MAX_VALUE);
            SPELL_BOOK_Y = builder
                    .comment("Vertical position of the spell book hud.")
                    .translation(TranslationConstants.CONFIG + "spell_book_y")
                    .defineInRange("spell_book_y", 19, Short.MIN_VALUE, Short.MAX_VALUE);
            SPELL_BOOK_ANCHOR_X = builder
                    .comment("Horizontal anchor of the spell book hud.")
                    .translation(TranslationConstants.CONFIG + "spell_book_anchor_x")
                    .defineEnum("spell_book_anchor_x", HUDElement.AnchorX.CENTER);
            SPELL_BOOK_ANCHOR_Y = builder
                    .comment("Horizontal anchor of the spell book hud.")
                    .translation(TranslationConstants.CONFIG + "spell_book_anchor_y")
                    .defineEnum("spell_book_anchor_y", HUDElement.AnchorY.BOTTOM);
            builder.pop();
            builder.pop();
        }

        public void save() {
            clientSpec.save();
        }
    }

    /**
     * Class holding the server config values.
     */
    public static final class Server {
        public final ForgeConfigSpec.BooleanValue REQUIRE_COMPENDIUM_CRAFTING;
        public final ForgeConfigSpec.DoubleValue BURNOUT_RATIO;
        public final ForgeConfigSpec.IntValue CRAFTING_ALTAR_CHECK_TIME;
        public final ForgeConfigSpec.IntValue MAX_ETHERIUM_STORAGE;
        public final ForgeConfigSpec.DoubleValue AFFINITY_TOME_SHIFT;
        public final ForgeConfigSpec.BooleanValue ENABLE_INSCRIPTION_TABLE_IN_WORLD_UPGRADING;
        public final ForgeConfigSpec.DoubleValue MANA_BASE;
        public final ForgeConfigSpec.DoubleValue MANA_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue MANA_REGEN_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue BURNOUT_BASE;
        public final ForgeConfigSpec.DoubleValue BURNOUT_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue BURNOUT_REGEN_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue LEVELING_BASE;
        public final ForgeConfigSpec.DoubleValue LEVELING_MULTIPLIER;
        public final ForgeConfigSpec.IntValue EXTRA_BLUE_SKILL_POINTS;
        public final ForgeConfigSpec.DoubleValue DAMAGE;
        public final ForgeConfigSpec.IntValue DURATION;
        public final ForgeConfigSpec.DoubleValue DRYAD_BONEMEAL_CHANCE;
        public final ForgeConfigSpec.IntValue DRYAD_BONEMEAL_TIMER;
        public final ForgeConfigSpec.IntValue DRYAD_BONEMEAL_RADIUS;
        public final ForgeConfigSpec.LongValue DRYAD_KILL_COOLDOWN;
        public final ForgeConfigSpec.IntValue DRYAD_KILLS_TO_NATURE_GUARDIAN_SPAWN;

        private Server(ForgeConfigSpec.Builder builder) {
            REQUIRE_COMPENDIUM_CRAFTING = builder
                    .comment("Whether the player needs to craft the compendium before being able to use magic. If disabled, the player can use magic from the beginning.")
                    .translation(TranslationConstants.CONFIG + "require_compendium_crafting")
                    .define("require_compendium_crafting", true);
            BURNOUT_RATIO = builder
                    .comment("The default mana to burnout ratio, used in calculating spell costs.")
                    .translation(TranslationConstants.CONFIG + "burnout_ratio")
                    .defineInRange("burnout_ratio", 0.5, 0, 10);
            CRAFTING_ALTAR_CHECK_TIME = builder
                    .comment("The time in ticks between multiblock validation checks for the crafting altar.")
                    .translation(TranslationConstants.CONFIG + "crafting_altar_check_time")
                    .defineInRange("crafting_altar_check_time", 20, 1, 200);
            MAX_ETHERIUM_STORAGE = builder
                    .comment("The maximum amount of etherium that can be stored in an obelisk / celestial prism / black aurem.")
                    .translation(TranslationConstants.CONFIG + "max_etherium_storage")
                    .defineInRange("max_etherium_storage", 5000, 1, Short.MAX_VALUE);
            AFFINITY_TOME_SHIFT = builder
                    .comment("The affinity shift that should be applied by affinity tomes.")
                    .translation(TranslationConstants.CONFIG + "affinity_tome_shift")
                    .defineInRange("affinity_tome_shift", 0.1, 0.0, 1.0);
            ENABLE_INSCRIPTION_TABLE_IN_WORLD_UPGRADING = builder
                    .comment("Whether inscription table upgrading is allowed in-world. If disabled, the upgrades must be applied through crafting.")
                    .translation(TranslationConstants.CONFIG + "enable_inscription_table_in_world_upgrading")
                    .define("enable_inscription_table_in_world_upgrading", true);
            builder.push("mana");
            MANA_BASE = builder
                    .comment("The base value for mana calculation. Mana is calculated as base + multiplier * (level - 1).")
                    .translation(TranslationConstants.CONFIG + "mana.base")
                    .worldRestart()
                    .defineInRange("base", 200., 0, 1000000);
            MANA_MULTIPLIER = builder
                    .comment("The multiplier for mana calculation. Mana is calculated as base + multiplier * (level - 1).")
                    .translation(TranslationConstants.CONFIG + "mana.multiplier")
                    .worldRestart()
                    .defineInRange("multiplier", 25., 0, 1000000);
            MANA_REGEN_MULTIPLIER = builder
                    .comment("The multiplier for mana regeneration. Mana regen is calculated as (base + multiplier * (level - 1)) * regen_multiplier.")
                    .translation(TranslationConstants.CONFIG + "mana.regen_multiplier")
                    .worldRestart()
                    .defineInRange("regen_multiplier", 0.001, 0, 1000000);
            builder.pop();
            builder.push("burnout");
            BURNOUT_BASE = builder
                    .comment("The base value for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).")
                    .translation(TranslationConstants.CONFIG + "burnout.base")
                    .worldRestart()
                    .defineInRange("base", 80., 0, 1000000);
            BURNOUT_MULTIPLIER = builder
                    .comment("The multiplier for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).")
                    .translation(TranslationConstants.CONFIG + "burnout.multiplier")
                    .worldRestart()
                    .defineInRange("multiplier", 10., 0, 1000000);
            BURNOUT_REGEN_MULTIPLIER = builder
                    .comment("The multiplier for burnout regeneration. Burnout regen is calculated as (base + multiplier * (level - 1)) * regen_multiplier.")
                    .translation(TranslationConstants.CONFIG + "burnout.regen_multiplier")
                    .worldRestart()
                    .defineInRange("regen_multiplier", 0.001, 0, 1000000);
            builder.pop();
            builder.push("leveling");
            LEVELING_BASE = builder
                    .comment("The base value for leveling calculation. XP cost is calculated as multiplier * base ^ level.")
                    .translation(TranslationConstants.CONFIG + "leveling.base")
                    .worldRestart()
                    .defineInRange("base", 1.2, 0, 10000);
            LEVELING_MULTIPLIER = builder
                    .comment("The multiplier for leveling calculation. XP cost is calculated as multiplier * base ^ level.")
                    .translation(TranslationConstants.CONFIG + "leveling._base")
                    .worldRestart()
                    .defineInRange("multiplier", 2.4, 0, 10000);
            EXTRA_BLUE_SKILL_POINTS = builder
                    .comment("The extra blue skill points a player gets on level 1.")
                    .translation(TranslationConstants.CONFIG + "leveling.extra_blue_skill_points")
                    .defineInRange("extra_blue_skill_points", 2, 0, Short.MAX_VALUE);
            builder.pop();
            builder.push("spell_parts");
            DAMAGE = builder
                    .comment("Damage of damage-based components, in half hearts.")
                    .translation(TranslationConstants.CONFIG + "spell_parts.damage")
                    .defineInRange("damage", 4.0, 1, Short.MAX_VALUE);
            DURATION = builder
                    .comment("Duration of effect-based components, in ticks.")
                    .translation(TranslationConstants.CONFIG + "spell_parts.duration")
                    .defineInRange("duration", 600, 1, Short.MAX_VALUE);
            builder.pop();
            builder.push("entities");
            builder.push("dryad");
            DRYAD_BONEMEAL_TIMER = builder
                    .comment("Every X ticks, dryads have a chance to apply bonemeal to the ground around them.")
                    .translation(TranslationConstants.CONFIG + "entities.dryad.bonemeal_timer")
                    .defineInRange("bonemeal_timer", 200, 1, 72000);
            DRYAD_BONEMEAL_CHANCE = builder
                    .comment("The chance of bonemeal being applied.")
                    .translation(TranslationConstants.CONFIG + "entities.dryad.bonemeal_chance")
                    .defineInRange("bonemeal_chance", 0.01, 0, 1);
            DRYAD_BONEMEAL_RADIUS = builder
                    .comment("The radius of bonemeal application.")
                    .translation(TranslationConstants.CONFIG + "entities.dryad.bonemeal_radius")
                    .defineInRange("bonemeal_radius", 2, 1, Short.MAX_VALUE);
            DRYAD_KILL_COOLDOWN = builder
                    .comment("If enough dryads are killed during this amount of time (in seconds), the Nature Guardian will spawn. Set this to 0 to disable this way of summoning the Nature Guardian (if you have an alternate way to spawn it).")
                    .translation(TranslationConstants.CONFIG + "entities.dryad.kill_cooldown")
                    .defineInRange("kill_cooldown", 60L, 0, 36000);
            DRYAD_KILLS_TO_NATURE_GUARDIAN_SPAWN = builder
                    .comment("If this amount of dryads is killed within the required timeframe, the Nature Guardian will spawn.")
                    .translation(TranslationConstants.CONFIG + "entities.dryad.kills_to_nature_guardian_spawn")
                    .defineInRange("kills_to_nature_guardian_spawn", 20, 1, Short.MAX_VALUE);
            builder.pop();
            builder.pop();
        }
    }
}
