package com.github.minecraftschurli.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.Projectile;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AoE extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (hit == null) return SpellCastResult.EFFECT_FAILED;
        double radius = 1 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.RANGE.getId());
        boolean appliedToAtLeastOneEntity = false;
        for (Entity e : level.getEntities(caster, new AABB(hit.getLocation().x() - radius, hit.getLocation().y() - radius, hit.getLocation().z() - radius, hit.getLocation().x() + radius, hit.getLocation().y() + radius, hit.getLocation().z() + radius))) {
            if (e == caster || e instanceof ItemEntity || e instanceof Projectile) continue;
            if (e instanceof PartEntity && ((PartEntity<?>) e).getParent() != null) {
                e = ((PartEntity<?>) e).getParent();
            }
            if (ArsMagicaAPI.get().getSpellHelper().invoke(spell, caster, level, new EntityHitResult(e), ticksUsed, index, awardXp) == SpellCastResult.SUCCESS) {
                appliedToAtLeastOneEntity = true;
            }
        }
        if (appliedToAtLeastOneEntity) return SpellCastResult.SUCCESS;
        BlockPos pos = new BlockPos(hit.getLocation());
        BlockPos lookPos = pos;
        for (int x = (int) -Math.floor(radius); x <= radius; x++) {
            for (int y = (int) -Math.floor(radius); y <= radius; y++) {
                for (int z = (int) -Math.floor(radius); z <= radius; z++) {
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        lookPos = switch (((BlockHitResult) hit).getDirection().getAxis()) {
                            case X -> pos.offset(0, y, z);
                            case Y -> pos.offset(x, 0, z);
                            case Z -> pos.offset(x, y, 0);
                        };
                    } else {
                        int gravityMagnitude = ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.GRAVITY.getId());
                        lookPos = pos.offset(x, 0, z);
                        if (gravityMagnitude > 0) {
                            for (int i = 0; level.getBlockState(lookPos).isAir() && i < gravityMagnitude; i++) {
                                lookPos = lookPos.below();
                            }
                        }
                    }
                }
            }
            if (level.getBlockState(lookPos).isAir()) continue;
            SpellCastResult result = ArsMagicaAPI.get().getSpellHelper().invoke(spell, caster, level, new BlockHitResult(hit.getLocation(), ((BlockHitResult) hit).getDirection(), lookPos, ((BlockHitResult) hit).isInside()), ticksUsed, index, awardXp);
            if (result != SpellCastResult.SUCCESS) return result;
        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public boolean isBeginnShape() {
        return false;
    }
}
