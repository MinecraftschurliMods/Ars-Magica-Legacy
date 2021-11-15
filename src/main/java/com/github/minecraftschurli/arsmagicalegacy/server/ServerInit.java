package com.github.minecraftschurli.arsmagicalegacy.server;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurli.arsmagicalegacy.server.commands.SkillCommand;
import com.mojang.brigadier.Command;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ArsMagicaAPI.MOD_ID)
public final class ServerInit {
    @SubscribeEvent
    static void registerCommands(RegisterCommandsEvent event) {
        SkillCommand.register(event.getDispatcher());
        event.getDispatcher().register(Commands.literal("givetestspell").executes(context -> {
            ItemStack stack = new ItemStack(AMItems.SPELL.get());
            SpellItem.saveSpell(stack, Spell.of(SpellStack.of(AMSpellParts.DIG.get()), ShapeGroup.of(AMSpellParts.TOUCH.get())));
            context.getSource().getPlayerOrException().addItem(stack);
            return Command.SINGLE_SUCCESS;
        }));
    }
}
