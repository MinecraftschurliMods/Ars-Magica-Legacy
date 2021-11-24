package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.network.NextShapeGroupPacket;
import com.github.minecraftschurlimods.betterkeybindlib.ItemInHandKeyConflictContext;
import com.github.minecraftschurlimods.betterkeybindlib.Keybind;
import com.github.minecraftschurlimods.betterkeybindlib.KeybindManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.IEventBus;

public final class Keybinds {
    public static final KeybindManager KEYBIND_MANAGER = KeybindManager.get(ArsMagicaAPI.MOD_ID);
    public static final Keybind CONFIGURE_SPELL = KEYBIND_MANAGER.keybind("configure_spell", InputConstants.KEY_M)
             .withModifier(KeyModifier.CONTROL)
             .withContext(ItemInHandKeyConflictContext.from(AMItems.SPELL.getId()))
             .withContext(KeyConflictContext.IN_GAME)
             .withCallback(ctx -> {
                 ArsMagicaAPI.get().openSpellCustomizationGui(
                         ctx.get("level"),
                         ctx.get("player"),
                         ctx.get("stack"));
                 return true;
             }).build();
    public static final Keybind NEXT_SHAPE_GROUP = KEYBIND_MANAGER.keybind("next_shape_group", InputConstants.KEY_C)
            .withModifier(KeyModifier.SHIFT)
            .withContext(ItemInHandKeyConflictContext.from(AMItems.SPELL.getId()))
            .withContext(KeyConflictContext.IN_GAME)
            .withCallback(ctx -> {
                ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new NextShapeGroupPacket((InteractionHand) ctx.get("hand")));
                return true;
            }).build();

    static void init(IEventBus modBus) {
        KEYBIND_MANAGER.register(modBus);
    }
}
