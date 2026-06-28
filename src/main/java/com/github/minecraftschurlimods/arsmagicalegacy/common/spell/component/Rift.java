package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import com.github.minecraftschurlimods.arsmagicalegacy.common.menu.RiftMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Rift extends SpellComponent.CastEntity {
    public Rift() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!(hitResult.getEntity() instanceof LivingEntity entity)) return SpellComponentCastResult.pass(spell);
        if (!(context.caster() instanceof ServerPlayer player)) return SpellComponentCastResult.pass(spell);
        int entityId = entity.getId();
        int size = (int) modifiers.stream()
            .filter(e -> e.getStats().contains(AMSpells.RANGE_STAT))
            .count() * 9 + 9;
        player.openMenu(new SimpleMenuProvider((id, inventory, _) -> new RiftMenu(id, inventory, entityId, size), AMTranslations.RIFT), buf -> {
            buf.writeInt(entityId);
            buf.writeInt(size);
        });
        return SpellComponentCastResult.success(spell);
    }
}
