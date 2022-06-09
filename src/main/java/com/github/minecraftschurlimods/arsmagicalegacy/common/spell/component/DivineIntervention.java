package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class DivineIntervention extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (caster.hasEffect(AMMobEffects.ASTRAL_DISTORTION.get())) return SpellCastResult.EFFECT_FAILED;
        if (target.getEntity() instanceof LivingEntity living && !living.hasEffect(AMMobEffects.ASTRAL_DISTORTION.get())) {
            if (living.hasEffect(AMMobEffects.ASTRAL_DISTORTION.get())) {
                if (living instanceof Player) {
                    living.sendSystemMessage(Component.translatable(TranslationConstants.NO_TELEPORT));
                }
            } else if (level.dimension() == Level.NETHER) {
                if (living instanceof Player) {
                    living.sendSystemMessage(Component.translatable(TranslationConstants.NO_TELEPORT_NETHER));
                }
            } else if (level.dimension() != Level.OVERWORLD && level instanceof ServerLevel server) {
                ServerLevel serverlevel = server.getServer().getLevel(Level.OVERWORLD);
                if (serverlevel != null) {
                    living.changeDimension(serverlevel);
                    return SpellCastResult.SUCCESS;
                }
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
