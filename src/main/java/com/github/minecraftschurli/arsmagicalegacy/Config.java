package com.github.minecraftschurli.arsmagicalegacy;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Minecraftschurli
 * @version 2021-08-02
 */
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

    static synchronized void init(final ModLoadingContext context) {
        if (init) return;
        init = true;
        context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
        context.registerConfig(ModConfig.Type.SERVER, serverSpec);
    }

    private Config() {}

    /**
     * Class holding the client config values
     */
    public static class Client {
        Client(ForgeConfigSpec.Builder builder) {
        }
    }

    /**
     * Class holding the server config values
     */
    public static class Server {
        Server(ForgeConfigSpec.Builder builder) {
        }
    }
}
