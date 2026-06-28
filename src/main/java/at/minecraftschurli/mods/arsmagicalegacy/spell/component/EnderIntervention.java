package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.EndPlatformFeature;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.Objects;

public class EnderIntervention extends SpellComponent.CastEntity {
    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        Level level = context.level();
        Entity entity = hitResult.getEntity();
        Component cancel = AMUtil.cancelTeleport(entity, context.caster());
        if (cancel != null) return SpellComponentCastResult.failure(spell, cancel);
        ResourceKey<Level> dimension = level.dimension();
        if (dimension == Level.NETHER) return SpellComponentCastResult.failure(spell, AMTranslations.NO_TELEPORT_NETHER);
        if (dimension == Level.END) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_ENDER_INTERVENTION);
        if (!(level instanceof ServerLevel server)) return SpellComponentCastResult.pass(spell);
        ServerLevel end = Objects.requireNonNull(server.getServer()).getLevel(Level.END);
        if (end != null) {
            BlockPos pos = ServerLevel.END_SPAWN_POINT;
            EndPlatformFeature.createEndPlatform(end, pos.below(), true);
            entity.teleport(new TeleportTransition(end, pos.getBottomCenter(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), TeleportTransition.DO_NOTHING));
        }
        return SpellComponentCastResult.success(spell);
    }
}
