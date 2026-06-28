package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Light extends SpellComponent.CastBoth {
    public Light() {
        super(AMSpells.DURATION_STAT);
    }

    @Override
    public SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult) {
        Level level = context.level();
        Direction direction = hitResult.getDirection();
        BlockPos pos = hitResult.getBlockPos().offset(direction.getUnitVec3i());
        if (level.getBlockState(pos).isAir()) {
            level.setBlockAndUpdate(pos, AMBlocks.SPELL_LIGHT.get().defaultBlockState());
        }
        return SpellComponentCastResult.success(context.spell());
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!(hitResult.getEntity() instanceof LivingEntity living)) return SpellComponentCastResult.pass(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        living.addEffect(new MobEffectInstance(AMMobEffects.ILLUMINATION, (int) helper.getModifiedStat(AMServerConfig.EFFECT_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context)));
        return SpellComponentCastResult.success(spell);
    }
}
