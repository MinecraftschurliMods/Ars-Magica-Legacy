package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.Optional;

public class Forge extends SpellComponent.CastBoth {
    @Override
    public SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult) {
        Spell spell = context.spell();
        Level level = context.level();
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_HIT);
        if (!(level instanceof ServerLevel serverLevel)) return SpellComponentCastResult.pass(spell);
        SingleRecipeInput input = new SingleRecipeInput(new ItemStack(state.getBlock()));
        Optional<RecipeHolder<SmeltingRecipe>> recipe = serverLevel.recipeAccess().getRecipeFor(RecipeType.SMELTING, input, level);
        if (recipe.isEmpty()) return SpellComponentCastResult.pass(spell);
        ItemStack stack = recipe.get().value().assemble(input);
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        if (stack.getItem() instanceof BlockItem blockItem) {
            Direction direction = hitResult.getDirection();
            Vec3i normal = direction.getUnitVec3i();
            blockItem.place(new BlockPlaceContext(level, context.caster() instanceof Player player ? player : null, InteractionHand.MAIN_HAND, stack, new BlockHitResult(hitResult.getLocation().add(normal.getX(), normal.getY(), normal.getZ()), direction, pos.offset(normal), hitResult.isInside())));
        } else {
            ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            item.setDefaultPickUpDelay();
            level.addFreshEntity(item);
        }
        return SpellComponentCastResult.success(spell);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!AMServerConfig.FORGE_SMELTS_VILLAGERS.get() || !(hitResult.getEntity() instanceof Villager villager)) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_HIT);
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        LivingEntity caster = context.caster();
        ItemEntity item = new ItemEntity(level, villager.getX(), villager.getY(), villager.getZ(), new ItemStack(Items.EMERALD));
        item.setDefaultPickUpDelay();
        level.addFreshEntity(item);
        villager.hurtServer(level, caster instanceof Player player ? level.damageSources().playerAttack(player) : caster != null ? level.damageSources().mobAttack(caster) : level.damageSources().onFire(), 5000);
        return SpellComponentCastResult.success(spell);
    }
}
