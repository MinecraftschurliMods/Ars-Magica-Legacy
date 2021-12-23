package com.github.minecraftschurli.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class RandomTeleport extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        double range = (double) ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.RANGE.getId()) * 4 + 5;
        Entity entity = target.getEntity();
        boolean validPosition;
        Vec3 vec;
        int i = 0;
        do {
            if (i == 100) return SpellCastResult.EFFECT_FAILED;
            vec = new Vec3(entity.getX() + level.random.nextDouble(range) - range / 2f, entity.getY() + level.random.nextDouble(range) - range / 2f, entity.getZ() + level.random.nextDouble(range) - range / 2f);
            BlockPos pos = new BlockPos(vec);
            validPosition = level.getBlockState(pos).isAir() && level.getBlockState(pos.below()).canOcclude();
            i++;
        } while (!validPosition);
        entity.moveTo(vec);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
