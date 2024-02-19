package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chain extends AbstractShape {
    public Chain() {
        super(SpellPartStats.RANGE, SpellPartStats.TARGET_NON_SOLID);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        HitResult hitResult = helper.trace(caster, level, 16, true, helper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, caster, hit, index) > 0);
        if (hitResult instanceof BlockHitResult bhr)
            return helper.invoke(spell, caster, level, bhr, ticksUsed, index, awardXp);
        SpellCastResult result = SpellCastResult.EFFECT_FAILED;
        if (hitResult instanceof EntityHitResult ehr) {
            result = helper.invoke(spell, caster, level, ehr, ticksUsed, index, awardXp);
            for (Entity e : getEntities(ehr.getEntity(), helper.getModifiedStat(4, SpellPartStats.RANGE, modifiers, spell, caster, hit, index), caster)) {
                SpellCastResult currentResult = helper.invoke(spell, caster, level, new EntityHitResult(e), ticksUsed, index, awardXp);
                result = result == SpellCastResult.SUCCESS ? SpellCastResult.SUCCESS : currentResult;
            }
        }
        return result;
    }

    @Override
    public boolean isContinuous() {
        return true;
    }


    @Override
    public boolean needsToComeFirst() {
        return true;
    }

    public static List<Entity> getEntities(Entity initial, double range, Entity... ignore) {
        List<Entity> list = new ArrayList<>();
        Entity castFrom = initial;
        Entity temp = null;
        for (int i = 0; i < 4; i++) {
            for (Entity e : initial.level().getEntities(castFrom, new AABB(castFrom.position().subtract(range, range, range), castFrom.position().add(range, range, range)))) {
                if (list.contains(e) || Arrays.stream(ignore).anyMatch(p -> p == e)) continue;
                if (e instanceof LivingEntity living && living.isDeadOrDying()) continue;
                if (temp != null && temp.getType() != e.getType()) continue;
                if (temp == null || temp.distanceTo(castFrom) > e.distanceTo(castFrom)) {
                    temp = e;
                }
            }
            if (temp == null) {
                for (Entity e : initial.level().getEntities(castFrom, new AABB(castFrom.position().subtract(range, range, range), castFrom.position().add(range, range, range)))) {
                    if (list.contains(e) || Arrays.stream(ignore).anyMatch(p -> p == e)) continue;
                    if (e instanceof LivingEntity living && living.isDeadOrDying()) continue;
                    if (temp == null || temp.distanceTo(castFrom) > e.distanceTo(castFrom)) {
                        temp = e;
                    }
                }
            }
            if (temp == null) break;
            list.add(temp);
            castFrom = temp;
            temp = null;
        }
        return list;
    }
}
