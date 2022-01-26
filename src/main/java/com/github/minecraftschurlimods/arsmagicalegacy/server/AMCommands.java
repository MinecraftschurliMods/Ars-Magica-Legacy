package com.github.minecraftschurlimods.arsmagicalegacy.server;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.server.commands.SkillCommand;
import com.mojang.brigadier.Command;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

public final class AMCommands {
    static void registerCommands(RegisterCommandsEvent event) {
        SkillCommand.register(event.getDispatcher());
        if (!FMLEnvironment.production) {
            event.getDispatcher().register(Commands.literal("givetestspell1").executes(context -> {
                ItemStack stack = new ItemStack(AMItems.SPELL.get());
                SpellItem.saveSpell(stack, Spell.of(
                        SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get()),
                        ShapeGroup.of(AMSpellParts.SELF.get())
                ));
                context.getSource().getPlayerOrException().addItem(stack);
                return Command.SINGLE_SUCCESS;
            }));
            event.getDispatcher().register(Commands.literal("givetestspell2").executes(context -> {
                ItemStack stack = new ItemStack(AMItems.SPELL.get());
                SpellItem.saveSpell(stack, Spell.of(
                        SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get()),
                        ShapeGroup.of(AMSpellParts.PROJECTILE.get())
                ));
                context.getSource().getPlayerOrException().addItem(stack);
                return Command.SINGLE_SUCCESS;
            }));
        }
    }
}
