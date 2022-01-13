package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Zone extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Zone zone = new com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Zone(AMEntities.ZONE.get(), level);
            zone.setPos(caster.getX(), caster.getY(), caster.getZ());
            zone.setDeltaMovement(caster.getDeltaMovement());
            ISpellHelper spellHelper = ArsMagicaAPI.get().getSpellHelper();
            boolean tns = spellHelper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, caster, hit) > 0;
            int duration = 200 + (int) spellHelper.getModifiedStat(100, SpellPartStats.DURATION, modifiers, spell, caster, hit);
            float gravity = 0.025f * spellHelper.getModifiedStat(0, SpellPartStats.GRAVITY, modifiers, spell, caster, hit);
            float radius = spellHelper.getModifiedStat(1, SpellPartStats.SIZE, modifiers, spell, caster, hit);
            if (tns) {
                zone.setTargetNonSolid();
            }
            zone.setDuration(duration);
            zone.setIndex(index);
            zone.setOwner(caster);
            zone.setGravity(gravity);
            zone.setRadius(radius);
            zone.setStack(caster.getMainHandItem());
            level.addFreshEntity(zone);
        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public boolean needsPrecedingShape() {
        return true;
    }

    @Override
    public boolean isEndShape() {
        return true;
    }
}
