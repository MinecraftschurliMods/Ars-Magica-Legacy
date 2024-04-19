package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.network.NextShapeGroupPacket;
import com.github.minecraftschurlimods.betterkeybindlib.ItemInHandKeyConflictContext;
import com.github.minecraftschurlimods.betterkeybindlib.Keybind;
import com.github.minecraftschurlimods.betterkeybindlib.KeybindManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.network.PacketDistributor;

public final class Keybinds {
    public static final KeybindManager KEYBIND_MANAGER = KeybindManager.get(ArsMagicaAPI.MOD_ID);
    public static final Keybind CONFIGURE_SPELL = KEYBIND_MANAGER.keybind("configure_spell", InputConstants.KEY_M)
            .withModifier(KeyModifier.CONTROL)
            .withContext(ItemInHandKeyConflictContext.from(AMItems.SPELL.getId()))
            .withContext(KeyConflictContext.IN_GAME)
            .withCallback(ctx -> {
                ArsMagicaAPI.get().openSpellCustomizationGui(ctx.get("level"), ctx.get("player"), ctx.get("stack"));
                return true;
            }).build();
    public static final Keybind NEXT_SHAPE_GROUP = KEYBIND_MANAGER.keybind("next_shape_group", InputConstants.KEY_PERIOD)
            .withContext(ItemInHandKeyConflictContext.from(AMItems.SPELL.getId()))
            .withContext(KeyConflictContext.IN_GAME)
            .withCallback(ctx -> {
                PacketDistributor.SERVER.noArg().send(new NextShapeGroupPacket(ctx.get("hand"), false));
                return true;
            }).build();
    public static final Keybind PREV_SHAPE_GROUP = KEYBIND_MANAGER.keybind("prev_shape_group", InputConstants.KEY_COMMA)
            .withContext(ItemInHandKeyConflictContext.from(AMItems.SPELL.getId()))
            .withContext(KeyConflictContext.IN_GAME)
            .withCallback(ctx -> {
                PacketDistributor.SERVER.noArg().send(new NextShapeGroupPacket(ctx.get("hand"), true));
                return true;
            }).build();

    /**
     * Registers the keybind manager to the given mod event bus.
     *
     * @param modBus The mod event bus to register the keybind manager to.
     */
    static void init(IEventBus modBus) {
        KEYBIND_MANAGER.register(modBus);
    }
}
