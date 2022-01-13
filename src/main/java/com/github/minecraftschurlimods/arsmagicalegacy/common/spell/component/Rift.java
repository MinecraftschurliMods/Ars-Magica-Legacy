package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.rift.RiftMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.arsmagicalegacy.server.Permissions;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.List;

public class Rift extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (target.getEntity() instanceof ServerPlayer sp) {
            int size = Math.min(Math.round(Math.max(1, ArsMagicaAPI.get().getSpellHelper().getModifiedStat(1, SpellPartStats.POWER, modifiers, spell, caster, target))), PermissionAPI.getPermission(sp, Permissions.MAX_RIFT_SIZE));
            NetworkHooks.openGui(sp, new SimpleMenuProvider((id, inv, player) -> RiftMenu.rift(id, inv, sp, size), new TranslatableComponent(TranslationConstants.RIFT_TITLE)), buf -> {
                buf.writeUUID(sp.getUUID());
                buf.writeInt(size);
            });
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
