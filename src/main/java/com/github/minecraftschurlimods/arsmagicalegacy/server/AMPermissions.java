package com.github.minecraftschurlimods.arsmagicalegacy.server;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.ProfileResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContextKey;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import net.neoforged.neoforge.server.permission.nodes.PermissionType;
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes;

import java.util.Collection;
import java.util.HashSet;

public final class AMPermissions {
    private static final Collection<PermissionNode<?>> NODES = new HashSet<>();

    public static final PermissionNode<Boolean> CAN_CAST_SPELL                  = registerPermissionNode("can_cast_spell", PermissionTypes.BOOLEAN, DefaultPermissions.ALLOW);
    public static final PermissionNode<Boolean> CAN_EXECUTE_AFFINITY_COMMAND    = registerPermissionNode("can_execute_affinity_command", PermissionTypes.BOOLEAN, DefaultPermissions.permissionLevel(2));
    public static final PermissionNode<Boolean> CAN_EXECUTE_MAGIC_XP_COMMAND    = registerPermissionNode("can_execute_magic_xp_command", PermissionTypes.BOOLEAN, DefaultPermissions.permissionLevel(2));
    public static final PermissionNode<Boolean> CAN_EXECUTE_SKILL_COMMAND       = registerPermissionNode("can_execute_skill_command", PermissionTypes.BOOLEAN, DefaultPermissions.permissionLevel(2));
    public static final PermissionNode<Boolean> CAN_EXECUTE_SKILL_POINT_COMMAND = registerPermissionNode("can_execute_skill_point_command", PermissionTypes.BOOLEAN, DefaultPermissions.permissionLevel(2));
    public static final PermissionNode<Integer> MAX_RIFT_SIZE                   = registerPermissionNode("max_rift_size", PermissionTypes.INTEGER, DefaultPermissions.staticValue(54));

    private static <T> PermissionNode<T> registerPermissionNode(String name, PermissionType<T> type, PermissionNode.PermissionResolver<T> defaultResolver, PermissionDynamicContextKey<?>... contextKeys) {
        return registerPermissionNode(new ResourceLocation(ArsMagicaAPI.MOD_ID, name), type, defaultResolver, contextKeys);
    }

    private static <T> PermissionNode<T> registerPermissionNode(ResourceLocation nodeName, PermissionType<T> type, PermissionNode.PermissionResolver<T> defaultResolver, PermissionDynamicContextKey<?>... contextKeys) {
        PermissionNode<T> node = new PermissionNode<>(nodeName, type, defaultResolver, contextKeys);
        NODES.add(node);
        return node;
    }

    static void registerPermissionNodes(PermissionGatherEvent.Nodes event) {
        event.addNodes(NODES);
    }

    public static final class DefaultPermissions {
        private DefaultPermissions() {}

        public static final PermissionNode.PermissionResolver<Boolean> ALLOW = staticValue(true);
        public static final PermissionNode.PermissionResolver<Boolean> DENY = staticValue(false);

        public static PermissionNode.PermissionResolver<Boolean> permissionLevel(int level) {
            return (player, playerUUID, context) -> {
                if (player != null) {
                    return player.createCommandSourceStack().hasPermission(level);
                }
                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                ProfileResult result = server.getSessionService().fetchProfile(playerUUID, true);
                if (result == null) return false;
                GameProfile gameProfile = result.profile();
                return server.getProfilePermissions(gameProfile) >= level;
            };
        }

        public static PermissionNode.PermissionResolver<Boolean> op() {
            return (player, playerUUID, context) -> {
                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                int level = server.getOperatorUserPermissionLevel();
                if (player != null) {
                    return player.createCommandSourceStack().hasPermission(level);
                }
                ProfileResult result = server.getSessionService().fetchProfile(playerUUID, true);
                if (result == null) return false;
                GameProfile gameProfile = result.profile();
                return server.getProfilePermissions(gameProfile) >= level;
            };
        }

        public static <T> PermissionNode.PermissionResolver<T> staticValue(T value) {
            return (player, playerUUID, context) -> value;
        }
    }
}
