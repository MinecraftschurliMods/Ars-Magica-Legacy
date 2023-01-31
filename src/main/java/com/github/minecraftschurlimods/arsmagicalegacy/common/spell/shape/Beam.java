package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Beam extends AbstractShape {
    public Beam() {
        super(SpellPartStats.TARGET_NON_SOLID);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        HitResult hitResult = helper.trace(caster, level, 64, true, helper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, caster, hit) > 0);
        if (level.isClientSide()) {
            if (caster.getMainHandItem().getItem() instanceof ISpellItem) {
                ClientHelper.drawBeam(caster, InteractionHand.MAIN_HAND, hitResult, 0xff0000);
            } else if (caster.getOffhandItem().getItem() instanceof ISpellItem) {
                ClientHelper.drawBeam(caster, InteractionHand.OFF_HAND, hitResult, 0xff0000);
            }
        }
        return helper.invoke(spell, caster, level, hitResult, ticksUsed, index, awardXp);
    }

    @Override
    public boolean isContinuous() {
        return true;
    }

    @Override
    public boolean needsPrecedingShape() {
        return false;
    }

    @Override
    public boolean needsToComeFirst() {
        return true;
    }
}
