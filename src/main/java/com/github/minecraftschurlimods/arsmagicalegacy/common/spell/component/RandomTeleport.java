package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RandomTeleport extends AbstractComponent.OnEntity {
    public RandomTeleport() {
        super(SpellPartStats.RANGE);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (caster.hasEffect(AMMobEffects.ASTRAL_DISTORTION) || target.getEntity() instanceof LivingEntity living && living.hasEffect(AMMobEffects.ASTRAL_DISTORTION))
            return SpellCastResult.EFFECT_FAILED;
        float range = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(5, SpellPartStats.RANGE, modifiers, spell, caster, target, index) * 4;
        Entity entity = target.getEntity();
        boolean validPosition;
        Vec3 vec;
        int i = 0;
        do {
            if (i == 100) return SpellCastResult.EFFECT_FAILED;
            vec = new Vec3(entity.getX() + level.getRandom().nextDouble() * range - range / 2f, entity.getY() + level.getRandom().nextDouble() * range - range / 2f, entity.getZ() + level.getRandom().nextDouble() * range - range / 2f);
            BlockPos pos = BlockPos.containing(vec);
            validPosition = level.getBlockState(pos).isAir() && level.getBlockState(pos.below()).canOcclude();
            i++;
        } while (!validPosition);
        entity.moveTo(vec);
        return SpellCastResult.SUCCESS;
    }
}
