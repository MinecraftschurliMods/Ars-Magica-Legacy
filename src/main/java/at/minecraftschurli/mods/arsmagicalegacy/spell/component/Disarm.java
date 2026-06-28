package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Disarm extends SpellComponent.CastEntity {
    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!(hitResult.getEntity() instanceof LivingEntity entity)) return SpellComponentCastResult.pass(spell);
        Level level = context.level();
        if (entity instanceof EnderMan enderMan) {
            BlockState state = enderMan.getCarriedBlock();
            if (state != null) {
                addItemEntity(level, entity, new ItemStack(state.getBlock()));
                enderMan.setCarriedBlock(null);
            }
            LivingEntity caster = context.caster();
            if (caster != null) {
                enderMan.setTarget(caster);
            }
            return SpellComponentCastResult.success(spell);
        } else if (!entity.getMainHandItem().isEmpty()) {
            addItemEntity(level, entity, entity.getMainHandItem().copy());
            entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            return SpellComponentCastResult.success(spell);
        } else if (!entity.getOffhandItem().isEmpty()) {
            addItemEntity(level, entity, entity.getOffhandItem().copy());
            entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            return SpellComponentCastResult.success(spell);
        }
        return SpellComponentCastResult.pass(spell);
    }

    private static void addItemEntity(Level level, Entity entity, ItemStack stack) {
        ItemEntity item = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), stack);
        item.setPickUpDelay(40);
        level.addFreshEntity(item);
    }
}
