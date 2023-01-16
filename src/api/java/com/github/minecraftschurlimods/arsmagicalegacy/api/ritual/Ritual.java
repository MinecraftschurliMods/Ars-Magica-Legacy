package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.event.RitualPerformEvent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public record Ritual(IRitualTrigger trigger, List<IRitualRequirement> requirements, IRitualEffect effect, BlockPos offset) {
    public static final Codec<Ritual> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            IRitualTrigger.CODEC.fieldOf("trigger").forGetter(Ritual::trigger),
            IRitualRequirement.CODEC.listOf().fieldOf("requirements").forGetter(Ritual::requirements),
            IRitualEffect.CODEC.fieldOf("effect").forGetter(Ritual::effect),
            BlockPos.CODEC.optionalFieldOf("offset", BlockPos.ZERO).forGetter(Ritual::offset)
    ).apply(inst, Ritual::new));

    public Ritual {
        trigger.register(this);
    }

    public boolean perform(Player player, ServerLevel level, BlockPos pos, IContext ctx) {
        pos = pos.offset(offset);
        if (!MinecraftForge.EVENT_BUS.post(new RitualPerformEvent.Pre(player, this, level, pos, ctx))) return false;
        for (IRitualRequirement req : requirements) {
            if (!req.test(player, level, pos)) return false;
        }
        if (!this.trigger.trigger(player, level, pos, ctx)) return false;
        if (!this.effect.performEffect(player, level, pos)) return false;
        MinecraftForge.EVENT_BUS.post(new RitualPerformEvent.Post(player, this, level, pos, ctx));
        return true;
    }
}
