package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMStats;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 *
 */
public record Ritual(RitualTrigger trigger, List<RitualRequirement> requirements, RitualEffect effect) {
    public static final Codec<Ritual> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RitualTrigger.CODEC.fieldOf("trigger").forGetter(Ritual::trigger),
            RitualRequirement.CODEC.listOf().fieldOf("requirements").forGetter(Ritual::requirements),
            RitualEffect.CODEC.fieldOf("effect").forGetter(Ritual::effect)
    ).apply(inst, Ritual::new));

    public Ritual {
        trigger.register(this);
    }

    public boolean perform(Player player, ServerLevel level, BlockPos pos, Context ctx) {
        for (RitualRequirement req : requirements) {
            if (!req.test(player, level, pos)) return false;
        }
        if (!this.trigger.trigger(level, pos, ctx)) return false;
        if (!this.effect.performEffect(level, pos)) return false;
        player.awardStat(AMStats.RITUALS_TRIGGERED);
        return true;
    }
}
