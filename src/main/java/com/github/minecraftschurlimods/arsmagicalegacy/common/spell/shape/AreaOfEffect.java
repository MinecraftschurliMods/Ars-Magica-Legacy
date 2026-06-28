package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SecondarySpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AreaOfEffect extends SecondarySpellShape {
    public AreaOfEffect() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        SpellCastResult result = new SpellCastResult(context.spell());
        Entity directEntity = context.directEntity();
        if (directEntity == null || context.isHitResultNullOrMiss()) return result;
        double range = ArsMagicaApi.spellHelper().getModifiedStat(1, AMSpells.RANGE_STAT, modifiers, context);
        HitResult hitResult = context.hitResult();
        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            doBlockAoe(result, context, pos, (int) range, direction, blockHitResult.isInside());
            doEntityAoe(result, context, Vec3.atCenterOf(pos).add(Vec3.atLowerCornerOf(direction.getUnitVec3i()).scale(0.5)), range);
        } else if (hitResult instanceof EntityHitResult entityHitResult) {
            float xRot = directEntity.getXRot();
            Entity entity = entityHitResult.getEntity();
            doBlockAoe(result, context, entity.blockPosition(), (int) range, Math.abs(xRot) > 45 ? xRot > 0 ? Direction.DOWN : Direction.UP : directEntity.getDirection(), false);
            doEntityAoe(result, context, entity.position(), range);
        }
        return result;
    }

    private void doBlockAoe(SpellCastResult result, SpellCastContext context, BlockPos pos, int range, Direction direction, boolean inside) {
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                BlockPos currentPos = switch (direction.getAxis()) {
                    case X -> pos.offset(0, i, j);
                    case Y -> pos.offset(i, 0, j);
                    case Z -> pos.offset(i, j, 0);
                };
                updateResult(result, ArsMagicaApi.spellHelper().castGrammar(context.setSpell(result.getSpell()).setHitResult(new BlockHitResult(Vec3.atCenterOf(currentPos), direction, currentPos, inside))));
            }
        }
    }

    private void doEntityAoe(SpellCastResult result, SpellCastContext context, Vec3 location, double range) {
        LivingEntity caster = context.caster();
        Entity directEntity = context.directEntity();
        for (Entity entity : context.level().getEntities(null, new AABB(location.subtract(range, range, range), location.add(range, range, range)))) {
            int id = entity.getId();
            if (caster != null && id == caster.getId() || directEntity != null && id == directEntity.getId()) continue;
            updateResult(result, ArsMagicaApi.spellHelper().castGrammar(context.setSpell(result.getSpell()).setHitResult(new EntityHitResult(entity))));
        }
    }

    private void updateResult(SpellCastResult result, SpellCastResult grammarResult) {
        result.setSpell(grammarResult.getSpell());
        Component message = grammarResult.getMessage();
        if (message != null) {
            result.setMessage(message);
        }
        if (grammarResult.isSuccess()) {
            result.setSuccess();
        }
    }
}
