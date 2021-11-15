package com.github.minecraftschurli.arsmagicalegacy;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.ApiStatus.Internal;

public final class Config {
    public static final String PREFIX = ArsMagicaAPI.MOD_ID + ".config.";
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

        private Server(ForgeConfigSpec.Builder builder) {
            builder.push("magicvalues");
            DEFAULT_MAX_MANA = builder.comment("The default maximum mana for the player.").translation(PREFIX + "max_mana").worldRestart().defineInRange("max_mana", 100, 0., 10000);
            DEFAULT_MAX_BURNOUT = builder.comment("The default maximum burnout for the player.").translation(PREFIX + "max_burnout").worldRestart().defineInRange("max_burnout", 100, 0., 10000);
            BURNOUT_RATIO = builder.comment("The mana to burnout ratio.").translation(PREFIX + "burnout_ratio").defineInRange("burnout_ratio", 0.5, 0, 10.0);
            builder.pop();
        }
    }
}
