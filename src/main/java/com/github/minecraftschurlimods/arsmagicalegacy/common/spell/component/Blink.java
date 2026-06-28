package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Blink extends SpellComponent.CastEntity {
    public Blink() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        Entity entity = hitResult.getEntity();
        Component cancel = AMUtil.cancelTeleport(entity, context.caster());
        if (cancel != null) return SpellComponentCastResult.failure(spell, cancel);
        Level level = context.level();
        for (int i = (int) Math.round(ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.BLINK_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context)); i > 0; i--) {
            Vec3 angle = entity.getLookAngle().normalize();
            double x = entity.getX() + angle.x() * i;
            double y = entity.getY() + angle.y() * i;
            double z = entity.getZ() + angle.z() * i;
            if (level.isInsideBuildHeight((int) y) && level.getBlockState(BlockPos.containing(x, y, z)).isAir() && level.getBlockState(BlockPos.containing(x, y + 1, z)).isAir()) {
                entity.teleportTo(x, y, z);
                break;
            }
        }
        return SpellComponentCastResult.success(spell);
    }
}
