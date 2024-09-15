package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BanishRain extends AbstractComponent.OnTarget {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, HitResult target, int index, int ticksUsed) {
        if (!level.isClientSide() && level.getLevelData().isRaining()) {
            ((ServerLevel) level).setWeatherParameters(24000, 0, false, false);
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
