package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Explosion extends SpellComponent {
    public Explosion() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (context.isHitResultNullOrMiss()) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_HIT);
        HitResult hitResult = context.hitResult();
        if (hitResult != null) {
            Vec3 location = hitResult.getLocation();
            context.level().explode(context.directEntity(), location.x(), location.y(), location.z(), (float) ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.EXPLOSION_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context), context.caster() instanceof Player ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.MOB);
        }
        return SpellComponentCastResult.success(spell);
    }
}
