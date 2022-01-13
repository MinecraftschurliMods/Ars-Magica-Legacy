package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Projectile;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
        float radius = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(1, SpellPartStats.SIZE, modifiers, spell, caster, hit);
        boolean appliedToAtLeastOneEntity = false;
        for (Entity e : level.getEntities(caster, AABB.ofSize(hit.getLocation(), radius*2, radius*2, radius*2))) {
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
        int rad = Math.round(radius);
        for (int x = -rad; x <= radius; x++) {
            for (int y = -rad; y <= radius; y++) {
                for (int z = -rad; z <= radius; z++) {
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        int offset = ((BlockHitResult) hit).getDirection().getAxisDirection() == Direction.AxisDirection.NEGATIVE ? 0 : -1;
                        BlockPos lookPos = switch (((BlockHitResult) hit).getDirection().getAxis()) {
                            case X -> pos.offset(offset, y, z);
                            case Y -> pos.offset(x, offset, z);
                            case Z -> pos.offset(x, y, offset);
                        };
                        if (!level.getBlockState(lookPos).isAir()) {
                            ArsMagicaAPI.get().getSpellHelper().invoke(spell, caster, level, new BlockHitResult(hit.getLocation(), ((BlockHitResult) hit).getDirection(), lookPos, ((BlockHitResult) hit).isInside()), ticksUsed, index, awardXp);
                        }
                    }
                }
            }
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
