package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Ignition extends SpellComponent.CastBoth {
    public Ignition() {
        super(AMSpells.DURATION_STAT);
    }

    @Override
    public SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult) {
        ItemStack stack = new ItemStack(Items.FLINT_AND_STEEL);
        stack.useOn(new UseOnContext(context.level(), context.caster() instanceof Player player ? player : null, InteractionHand.MAIN_HAND, stack, hitResult));
        return SpellComponentCastResult.success(context.spell());
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        if (target instanceof Creeper creeper && !creeper.isIgnited()) {
            creeper.ignite();
        } else if (!target.isOnFire() && !target.isInWaterOrRain()) {
            target.setRemainingFireTicks((int) ArsMagicaApi.spellHelper().getModifiedStat(60, AMSpells.DURATION_STAT, modifiers, context));
        }
        return SpellComponentCastResult.success(context.spell());
    }
}
