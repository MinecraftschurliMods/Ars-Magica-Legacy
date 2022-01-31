package com.github.minecraftschurlimods.arsmagicalegacy.server;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;

public final class AMPermissions {
    public static final PermissionNode<Boolean> CAN_CAST_SPELL                  = new PermissionNode<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "can_cast_spell"), PermissionTypes.BOOLEAN, (player, playerUUID, context) -> true);
    public static final PermissionNode<Boolean> CAN_EXECUTE_AFFINITY_COMMAND    = new PermissionNode<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "can_execute_affinity_command"), PermissionTypes.BOOLEAN, (player, playerUUID, context) -> player.createCommandSourceStack().hasPermission(2));
    public static final PermissionNode<Boolean> CAN_EXECUTE_MAGIC_XP_COMMAND    = new PermissionNode<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "can_execute_magic_xp_command"), PermissionTypes.BOOLEAN, (player, playerUUID, context) -> player.createCommandSourceStack().hasPermission(2));
    public static final PermissionNode<Boolean> CAN_EXECUTE_SKILL_COMMAND       = new PermissionNode<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "can_execute_skill_command"), PermissionTypes.BOOLEAN, (player, playerUUID, context) -> player.createCommandSourceStack().hasPermission(2));
    public static final PermissionNode<Boolean> CAN_EXECUTE_SKILL_POINT_COMMAND = new PermissionNode<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "can_execute_skill_point_command"), PermissionTypes.BOOLEAN, (player, playerUUID, context) -> player.createCommandSourceStack().hasPermission(2));
    public static final PermissionNode<Integer> MAX_RIFT_SIZE                   = new PermissionNode<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "max_rift_size"), PermissionTypes.INTEGER, (player, playerUUID, context) -> 54);

    static void registerPermissionNodes(PermissionGatherEvent.Nodes event) {
        event.addNodes(CAN_CAST_SPELL, MAX_RIFT_SIZE);
    }
}
