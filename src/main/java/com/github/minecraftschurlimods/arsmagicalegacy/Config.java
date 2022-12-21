package com.github.minecraftschurlimods.arsmagicalegacy;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
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
        public final ForgeConfigSpec.IntValue HUD_HORIZONTAL_OFFSET;
        public final ForgeConfigSpec.IntValue HUD_VERTICAL_OFFSET;

        private Client(ForgeConfigSpec.Builder builder) {
            HUD_HORIZONTAL_OFFSET = builder
                    .comment("Horizontal offset of the hud, from the center of the screen.")
                    .translation(TranslationConstants.CONFIG + "hud_horizontal_offset")
                    .defineInRange("hud_horizontal_offset", -210, Short.MIN_VALUE, Short.MAX_VALUE);
            HUD_VERTICAL_OFFSET = builder
                    .comment("Vertical offset of the hud, from the bottom of the screen.")
                    .translation(TranslationConstants.CONFIG + "hud_vertical_offset")
                    .defineInRange("hud_vertical_offset", 3, 0, Short.MAX_VALUE);
        }
    }

    /**
     * Class holding the server config values.
     */
    public static final class Server {
        public final ForgeConfigSpec.DoubleValue BURNOUT_RATIO;
        public final ForgeConfigSpec.IntValue CRAFTING_ALTAR_CHECK_TIME;
        public final ForgeConfigSpec.IntValue MAX_ETHERIUM_STORAGE;
        public final ForgeConfigSpec.IntValue MAX_SHAPE_GROUPS;
        public final ForgeConfigSpec.DoubleValue MANA_BASE;
        public final ForgeConfigSpec.DoubleValue MANA_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue MANA_REGEN_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue BURNOUT_BASE;
        public final ForgeConfigSpec.DoubleValue BURNOUT_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue BURNOUT_REGEN_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue LEVELING_BASE;
        public final ForgeConfigSpec.DoubleValue LEVELING_MULTIPLIER;
        public final ForgeConfigSpec.IntValue EXTRA_BLUE_SKILL_POINTS;
        public final ForgeConfigSpec.IntValue DAMAGE;
        public final ForgeConfigSpec.IntValue DURATION;
        public final ForgeConfigSpec.DoubleValue DRYAD_BONEMEAL_CHANCE;
        public final ForgeConfigSpec.IntValue DRYAD_BONEMEAL_TIMER;
        public final ForgeConfigSpec.IntValue DRYAD_BONEMEAL_RADIUS;
        public final ForgeConfigSpec.LongValue DRYAD_KILL_COOLDOWN;
        public final ForgeConfigSpec.IntValue DRYAD_KILLS_TO_NATURE_GUARDIAN_SPAWN;

        private Server(ForgeConfigSpec.Builder builder) {
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
            MAX_SHAPE_GROUPS = builder
                    .comment("The maximum number of shape groups allowed for new spells.")
                    .translation(TranslationConstants.CONFIG + "max_shape_groups")
                    .defineInRange("max_shape_groups", 5, 0, 5);
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
                    .defineInRange("base", 200., 0, 1000000);
            BURNOUT_MULTIPLIER = builder
                    .comment("The multiplier for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).")
                    .translation(TranslationConstants.CONFIG + "burnout.multiplier")
                    .worldRestart()
                    .defineInRange("multiplier", 25., 0, 1000000);
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
                    .defineInRange("damage", 4, 1, Short.MAX_VALUE);
            DURATION = builder
                    .comment("Duration of effect-based components, in ticks.")
                    .translation(TranslationConstants.CONFIG + "spell_parts.duration")
                    .defineInRange("duration", 600, 1, Short.MAX_VALUE);
            builder.pop();
            builder.push("entities");
            builder.push("dryad");
            DRYAD_BONEMEAL_TIMER = builder
                    .comment("The time in ticks between bonemeal uses.")
                    .translation(TranslationConstants.CONFIG + "entities.dryad.bonemeal_timer")
                    .defineInRange("bonemeal_timer", 200, 1, 72000);
            DRYAD_BONEMEAL_CHANCE = builder
                    .comment("The chance of bonemeal being applied.")
                    .translation(TranslationConstants.CONFIG + "entities.dryad.bonemeal_chance")
                    .defineInRange("bonemeal_chance", 0.01, 0, 1);
            DRYAD_BONEMEAL_RADIUS = builder
                    .comment("The craterRadius of bonemeal application.")
                    .translation(TranslationConstants.CONFIG + "entities.dryad.bonemeal_radius")
                    .defineInRange("bonemeal_radius", 2, 1, Short.MAX_VALUE);
            DRYAD_KILL_COOLDOWN = builder
                    .comment("The time in seconds between killing dryads before the counter is reset.")
                    .translation(TranslationConstants.CONFIG + "entities.dryad.kill_cooldown")
                    .defineInRange("kill_cooldown", 60L, 0, 36000);
            DRYAD_KILLS_TO_NATURE_GUARDIAN_SPAWN = builder
                    .comment("The number of dryads killed before a nature guardian spawns.")
                    .translation(TranslationConstants.CONFIG + "entities.dryad.kills_to_nature_guardian_spawn")
                    .defineInRange("kills_to_nature_guardian_spawn", 20, 1, Short.MAX_VALUE);
            builder.pop();
            builder.pop();
        }
    }
}
