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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ArsMagicaAPI.MOD_ID)
public final class ServerInit {
    @SubscribeEvent
    static void registerCommands(RegisterCommandsEvent event) {
        SkillCommand.register(event.getDispatcher());
        event.getDispatcher().register(Commands.literal("givetestspell").executes(context -> {
            var stack = new ItemStack(AMItems.SPELL.get());
            SpellItem.saveSpell(stack, new Spell(List.of(ShapeGroup.of(List.of(AMSpellParts.SELF.get()))), SpellStack.of(List.of(AMSpellParts.FIRE_DAMAGE.get())), new CompoundTag()));
            //SpellItem.setSpellIcon(stack, new ResourceLocation(ArsMagicaAPI.MOD_ID, "air-burst-air-1"));
            //SpellItem.setSpellName(stack, "Test Spell");
            context.getSource().getPlayerOrException().addItem(stack);
            return Command.SINGLE_SUCCESS;
        }));
    }
}
