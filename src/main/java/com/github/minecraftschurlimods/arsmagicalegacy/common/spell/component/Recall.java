package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Recall extends AbstractComponent {

    private static boolean performRecall(Entity target, Level level, ItemStack stack) {
        if (stack.has(AMDataComponents.RECALL_POSITION)) {
            GlobalPos pos = stack.get(AMDataComponents.RECALL_POSITION);
            assert pos != null;
            if (level.dimension() == pos.dimension()) {
                target.moveTo(pos.pos().getCenter());
                return true;
            }
        }
        return false;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (caster.hasEffect(AMMobEffects.ASTRAL_DISTORTION) || target.getEntity() instanceof LivingEntity living && living.hasEffect(AMMobEffects.ASTRAL_DISTORTION))
            return SpellCastResult.EFFECT_FAILED;
        return performRecall(target.getEntity(), level, ArsMagicaAPI.get().getSpellHelper().getSpellItemStackFromEntity(caster)) ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        if (!caster.isShiftKeyDown()) return SpellCastResult.EFFECT_FAILED;
        ItemStack stack = ArsMagicaAPI.get().getSpellHelper().getSpellItemStackFromEntity(caster);
        BlockPos pos = target.getBlockPos();
        if (level.getBlockState(target.getBlockPos()).isSolidRender(level, target.getBlockPos())) {
            pos = pos.offset(target.getDirection().getNormal());
            if (target.getDirection().getAxis() != Direction.Axis.Y) {
                pos = pos.below();
            }
        }
        stack.set(AMDataComponents.RECALL_POSITION, new GlobalPos(level.dimension(), pos));
        return SpellCastResult.SUCCESS;
    }
}
