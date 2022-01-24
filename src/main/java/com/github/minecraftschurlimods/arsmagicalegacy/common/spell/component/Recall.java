package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Recall extends AbstractComponent {
    private static final String X = ArsMagicaAPI.MOD_ID + ":recall_pos_x";
    private static final String Y = ArsMagicaAPI.MOD_ID + ":recall_pos_y";
    private static final String Z = ArsMagicaAPI.MOD_ID + ":recall_pos_z";
    private static final String DIMENSION = ArsMagicaAPI.MOD_ID + ":recall_dimension";

    public Recall() {
        super();
    }

    private static boolean performRecall(LivingEntity caster, Level level, CompoundTag tag) {
        if (tag.contains(DIMENSION) && tag.getString(DIMENSION).equals(level.dimension().location().toString()) && tag.contains(X) && tag.contains(Y) && tag.contains(Z)) {
            caster.moveTo(tag.getInt(X) + 0.5f, tag.getInt(Y) + 0.5f, tag.getInt(Z) + 0.5f);
            return true;
        }
        return false;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (caster.hasEffect(AMMobEffects.ASTRAL_DISTORTION.get()) || target.getEntity() instanceof LivingEntity living && living.hasEffect(AMMobEffects.ASTRAL_DISTORTION.get()))
            return SpellCastResult.EFFECT_FAILED;
        return performRecall(caster, level, AMUtil.getSpellStack(caster).getOrCreateTag()) ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        ItemStack stack = AMUtil.getSpellStack(caster);
        CompoundTag tag = stack.getOrCreateTag();
        if (caster.isShiftKeyDown()) {
            BlockPos pos = target.getBlockPos();
            if (level.getBlockState(target.getBlockPos()).isSolidRender(level, target.getBlockPos())) {
                pos = pos.offset(target.getDirection().getNormal());
                if (target.getDirection().getAxis() != Direction.Axis.Y) {
                    pos = pos.below();
                }
            }
            tag.putInt(X, pos.getX());
            tag.putInt(Y, pos.getY());
            tag.putInt(Z, pos.getZ());
            tag.putString(DIMENSION, level.dimension().location().toString());
            stack.setTag(tag);
            return SpellCastResult.SUCCESS;
        } else return performRecall(caster, level, tag) ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }
}
