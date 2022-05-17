package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Contingency extends AbstractShape {
    private final ResourceLocation type;

    public Contingency(ResourceLocation type) {
        this.type = type;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        LivingEntity target = null;
        if (hit instanceof EntityHitResult result && result.getEntity() instanceof LivingEntity entity) {
            target = entity;
        }
        if (target == null) {
            target = caster;
        }
        ArsMagicaAPI.get().getContingencyHelper().setContingency(target, type, spell);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public boolean needsToComeFirst() {
        return true;
    }
}
