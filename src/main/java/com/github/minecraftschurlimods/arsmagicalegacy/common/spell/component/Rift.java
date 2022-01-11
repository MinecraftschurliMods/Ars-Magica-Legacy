package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.rift.RiftMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.rift.RiftContainer;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class Rift extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (target.getEntity() instanceof ServerPlayer sp) {
            int size = ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.EFFECT_POWER.getId());
            ItemStackHandler rift = ArsMagicaAPI.get().getRiftHelper().getRift(sp);
            NetworkHooks.openGui(sp, new SimpleMenuProvider((id, inv, player) -> switch (size) {
                default -> RiftMenu.rift1(id, inv, new RiftContainer(rift));
                case 1 -> RiftMenu.rift2(id, inv, new RiftContainer(rift));
                case 2 -> RiftMenu.rift3(id, inv, new RiftContainer(rift));
                case 3 -> RiftMenu.rift4(id, inv, new RiftContainer(rift));
                case 4 -> RiftMenu.rift5(id, inv, new RiftContainer(rift));
                case 5 -> RiftMenu.rift6(id, inv, new RiftContainer(rift));
            }, new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_TITLE)), buf -> buf.writeUUID(sp.getUUID()));
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
