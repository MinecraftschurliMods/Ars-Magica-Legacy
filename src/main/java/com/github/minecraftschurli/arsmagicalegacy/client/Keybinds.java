package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.betterkeybindlib.ItemInHandKeyConflictContext;
import com.github.minecraftschurlimods.betterkeybindlib.Keybind;
import com.github.minecraftschurlimods.betterkeybindlib.KeybindManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

public class Keybinds {
    public static final KeybindManager KEYBIND_MANAGER = KeybindManager.get(ArsMagicaAPI.MOD_ID);

    public static final Keybind CONFIGURE_SPELL = KEYBIND_MANAGER.keybind("configure_spell", InputConstants.KEY_M)
                                                                 .withModifier(KeyModifier.CONTROL)
                                                                 .withContext(ItemInHandKeyConflictContext.from(AMItems.SPELL.get()))
                                                                 .withContext(KeyConflictContext.IN_GAME)
                                                                 .withCallback(ctx -> {
                                                                     SpellItem.openIconPickGui(
                                                                             ctx.get("level"),
                                                                             ctx.get("player"),
                                                                             ctx.get("stack")
                                                                     );
                                                                     return true;
                                                                 })
                                                                 .build();

    static void init() {}
}
