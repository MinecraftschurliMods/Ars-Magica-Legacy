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

    static {
        final Pair<Client, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = clientPair.getRight();
        CLIENT = clientPair.getLeft();
        final Pair<Server, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = serverPair.getRight();
        SERVER = serverPair.getLeft();
    }

    private static boolean init;

    @Internal
    static synchronized void init() {
        if (init) return;
        init = true;
        ModLoadingContext context = ModLoadingContext.get();
        context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
        context.registerConfig(ModConfig.Type.SERVER, serverSpec);
    }

    private Config() {
    }

    /**
     * Class holding the client config values
     */
    public static final class Client {
        private Client(ForgeConfigSpec.Builder builder) {
        }
    }

    /**
     * Class holding the server config values
     */
    public static final class Server {
        public final ForgeConfigSpec.DoubleValue BURNOUT_RATIO;
        public final ForgeConfigSpec.DoubleValue DEFAULT_MAX_MANA;
        public final ForgeConfigSpec.DoubleValue DEFAULT_MAX_BURNOUT;
        public final ForgeConfigSpec.IntValue CRAFTING_ALTAR_CHECK_TIME;
        public final ForgeConfigSpec.IntValue MAX_SHAPE_GROUPS;
        public final ForgeConfigSpec.IntValue EXTRA_STARTING_BLUE_POINTS;
        public final ForgeConfigSpec.IntValue EFFECT_DURATION;
        public final ForgeConfigSpec.IntValue DAMAGE;

        private Server(ForgeConfigSpec.Builder builder) {
            DEFAULT_MAX_MANA = builder
                    .comment("The default maximum mana for the player.")
                    .translation(TranslationConstants.CONFIG + "max_mana")
                    .worldRestart()
                    .defineInRange("max_mana", 100, 0., 10000);
            DEFAULT_MAX_BURNOUT = builder
                    .comment("The default maximum burnout for the player.")
                    .translation(TranslationConstants.CONFIG + "max_burnout")
                    .worldRestart()
                    .defineInRange("max_burnout", 100, 0., 10000);
            BURNOUT_RATIO = builder
                    .comment("The mana to burnout ratio.")
                    .translation(TranslationConstants.CONFIG + "burnout_ratio")
                    .defineInRange("burnout_ratio", 0.5, 0, 10.0);
            CRAFTING_ALTAR_CHECK_TIME = builder
                    .comment("The time in ticks between multiblock validation checks for the crafting altar.")
                    .translation(TranslationConstants.CONFIG + "crafting_altar_check_time")
                    .defineInRange("crafting_altar_check_time", 20, 1, 200);
            MAX_SHAPE_GROUPS = builder
                    .comment("The maximum number of shape groups allowed for new spells.")
                    .translation(TranslationConstants.CONFIG + "max_shape_groups")
                    .defineInRange("max_shape_groups", 5, 0, 5);
            EXTRA_STARTING_BLUE_POINTS = builder
                    .comment("The extra skill points a player gets on level 1.")
                    .translation(TranslationConstants.CONFIG + "extra_starting_blue_points")
                    .defineInRange("extra_starting_blue_points", 2, 0, 100);
            EFFECT_DURATION = builder
                    .comment("Effect duration of effect-based components, in ticks.")
                    .translation(TranslationConstants.CONFIG + "effect_duration")
                    .defineInRange("effect_duration", 600, 1, 3600);
            DAMAGE = builder
                    .comment("Damage of damage-based components, in half hearts.")
                    .translation(TranslationConstants.CONFIG + "damage")
                    .defineInRange("damage", 6, 1, 100);
        }
    }
}
