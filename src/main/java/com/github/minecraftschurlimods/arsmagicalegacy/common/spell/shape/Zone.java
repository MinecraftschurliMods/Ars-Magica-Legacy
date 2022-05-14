package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Zone extends AbstractShape {
    public Zone() {
        super(SpellPartStats.DURATION, SpellPartStats.GRAVITY, SpellPartStats.SIZE, SpellPartStats.TARGET_NON_SOLID);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Zone zone = com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Zone.create(level);
            zone.moveTo(hit.getLocation().x(), hit.getLocation().y(), hit.getLocation().z());
            zone.setDeltaMovement(hit instanceof EntityHitResult entityHit ? entityHit.getEntity().getDeltaMovement() : Vec3.ZERO);
            var helper = ArsMagicaAPI.get().getSpellHelper();
            if (helper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, caster, hit) > 0) {
                zone.setTargetNonSolid();
            }
            zone.setDuration(200 + (int) helper.getModifiedStat(100, SpellPartStats.DURATION, modifiers, spell, caster, hit));
            zone.setIndex(index);
            zone.setOwner(caster);
            zone.setGravity(0.025f * helper.getModifiedStat(0, SpellPartStats.GRAVITY, modifiers, spell, caster, hit));
            zone.setRadius(helper.getModifiedStat(1, SpellPartStats.SIZE, modifiers, spell, caster, hit));
            zone.setSpell(spell);
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
