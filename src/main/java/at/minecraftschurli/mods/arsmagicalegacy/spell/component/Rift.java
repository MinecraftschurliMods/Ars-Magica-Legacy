package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.menu.RiftMenu;
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
