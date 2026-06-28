package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.Objects;

public class DivineIntervention extends SpellComponent.CastEntity {
    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        Level level = context.level();
        Entity entity = hitResult.getEntity();
        Component cancel = AMUtil.cancelTeleport(entity, context.caster());
        if (cancel != null) return SpellComponentCastResult.failure(spell, cancel);
        ResourceKey<Level> dimension = level.dimension();
        if (dimension == Level.NETHER) return SpellComponentCastResult.failure(spell, AMTranslations.NO_TELEPORT_NETHER);
        if (dimension == Level.OVERWORLD) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_DIVINE_INTERVENTION);
        if (!(level instanceof ServerLevel server)) return SpellComponentCastResult.pass(spell);
        TeleportTransition transition;
        if (entity instanceof ServerPlayer player) {
            transition = player.findRespawnPositionAndUseSpawnBlock(false, TeleportTransition.DO_NOTHING);
        } else {
            LevelData.RespawnData respawnData = server.getRespawnData();
            ServerLevel serverLevel = Objects.requireNonNull(server.getServer()).getLevel(respawnData.dimension());
            if (serverLevel == null) return SpellComponentCastResult.pass(spell);
            transition = new TeleportTransition(serverLevel, respawnData.pos().getBottomCenter(), entity.getDeltaMovement(), respawnData.yaw(), respawnData.pitch(), TeleportTransition.DO_NOTHING);
        }
        entity.teleport(transition);
        return SpellComponentCastResult.success(spell);
    }
}
