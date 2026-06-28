package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;

public record SpellCastRitualTrigger(List<SpellPart> parts) implements RitualTrigger<Set<SpellPart>> {
    public static final MapCodec<SpellCastRitualTrigger> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        AMRegistries.SPELL_PARTS.byNameCodec().listOf().fieldOf("parts").forGetter(SpellCastRitualTrigger::parts)
    ).apply(inst, SpellCastRitualTrigger::new));

    @Override
    public MapCodec<? extends RitualTrigger<Set<SpellPart>>> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec, Set<SpellPart> context) {
        return context.containsAll(parts);
    }
}
