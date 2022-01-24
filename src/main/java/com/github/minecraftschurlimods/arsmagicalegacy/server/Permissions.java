package com.github.minecraftschurlimods.arsmagicalegacy.server;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;

public final class Permissions {
    public static final PermissionNode<Boolean> CAN_CAST_SPELL = new PermissionNode<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "can_cast_spell"), PermissionTypes.BOOLEAN, (player, playerUUID, context) -> true);
    public static final PermissionNode<Integer> MAX_RIFT_SIZE = new PermissionNode<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "max_rift_size"), PermissionTypes.INTEGER, (player, playerUUID, context) -> 54);

    static void registerPermissionNodes(PermissionGatherEvent.Nodes event) {
        event.addNodes(CAN_CAST_SPELL, MAX_RIFT_SIZE);
    }
}
