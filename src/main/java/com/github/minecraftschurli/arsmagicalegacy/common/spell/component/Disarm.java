package com.github.minecraftschurli.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Disarm extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        Entity entity = target.getEntity();
        if (entity instanceof LivingEntity living) {
            if (entity instanceof Player && ((ServerLevel) level).getServer().isPvpAllowed())
                return SpellCastResult.EFFECT_FAILED;
            ItemStack main = living.getMainHandItem().copy();
            if (main != ItemStack.EMPTY) {
                ItemEntity item = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), main);
                item.setDefaultPickUpDelay();
                level.addFreshEntity(item);
                living.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                return SpellCastResult.SUCCESS;
            }
            ItemStack off = living.getMainHandItem().copy();
            if (off != ItemStack.EMPTY) {
                ItemEntity item = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), off);
                item.setDefaultPickUpDelay();
                level.addFreshEntity(item);
                living.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                return SpellCastResult.SUCCESS;
            }
        }
        if (entity instanceof EnderMan enderman) {
            BlockState state = enderman.getCarriedBlock();
            if (state != null) {
                enderman.setCarriedBlock(null);
                ItemStack stack = new ItemStack(state.getBlock());
                ItemEntity item = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), stack);
                level.addFreshEntity(item);
            }
            enderman.setTarget(caster);
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
