package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class RandomTeleport extends SpellComponent.CastEntity {
    public RandomTeleport() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        LivingEntity caster = context.caster();
        Entity entity = hitResult.getEntity();
        Component cancel = AMUtil.cancelTeleport(entity, caster);
        if (cancel != null) return SpellComponentCastResult.failure(spell, cancel);
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.success(spell);
        RandomSource random = level.getRandom();
        double range = ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.RANDOM_TELEPORT_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context);
        for (int i = 0; i < AMServerConfig.RANDOM_TELEPORT_MAX_TRIES.get(); i++) {
            Vec3 vec = entity.position().add(random.nextDouble() * range - range / 2, random.nextDouble() * range - range / 2, random.nextDouble() * range - range / 2);
            BlockPos pos = BlockPos.containing(vec);
            if (level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir() && level.getBlockState(pos.below()).canOcclude()) {
                entity.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                return SpellComponentCastResult.success(spell);
            }
        }
        return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_RANDOM_TELEPORT);
    }
}
