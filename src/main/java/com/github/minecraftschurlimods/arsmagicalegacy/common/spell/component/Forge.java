package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.Optional;

public class Forge extends AbstractComponent {
    public Forge() {
        super();
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (target.getEntity() instanceof Villager villager) {
            if (!level.isClientSide()) {
                level.addFreshEntity(new ItemEntity(level, villager.position().x(), villager.position().y(), villager.position().z(), new ItemStack(Items.EMERALD)));
            }
            villager.hurt(caster instanceof Player player ? DamageSource.playerAttack(player) : DamageSource.mobAttack(caster), 5000);
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        if (!(caster instanceof Player)) return SpellCastResult.EFFECT_FAILED;
        BlockPos pos = target.getBlockPos();
        Block block = level.getBlockState(pos).getBlock();
        if (level.getBlockState(pos).isAir()) return SpellCastResult.EFFECT_FAILED;
        Optional<SmeltingRecipe> recipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(new ItemStack(block)), level);
        if (recipe.isEmpty()) return SpellCastResult.EFFECT_FAILED;
        ItemStack smelted = recipe.get().getResultItem();
        if (!level.isClientSide()) {
            if (smelted.getItem() instanceof BlockItem) level.setBlock(pos, ((BlockItem) smelted.getItem()).getBlock().defaultBlockState(), Block.UPDATE_ALL);
            else {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, smelted.copy()));
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            }
        }
        return SpellCastResult.SUCCESS;
    }
}
