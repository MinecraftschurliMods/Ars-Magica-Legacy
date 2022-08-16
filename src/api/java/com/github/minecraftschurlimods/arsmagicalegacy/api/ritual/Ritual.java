package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public record Ritual(RitualTrigger trigger, List<RitualRequirement> requirements, RitualEffect effect, BlockPos offset) {
    public static final ResourceKey<Registry<Ritual>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ritual"));
    public static final Codec<Ritual> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RitualTrigger.CODEC.fieldOf("trigger").forGetter(Ritual::trigger),
            RitualRequirement.CODEC.listOf().fieldOf("requirements").forGetter(Ritual::requirements),
            RitualEffect.CODEC.fieldOf("effect").forGetter(Ritual::effect),
            BlockPos.CODEC.optionalFieldOf("offset", BlockPos.ZERO).forGetter(Ritual::offset)
    ).apply(inst, Ritual::new));
    public static final Codec<Holder<Ritual>> REFERENCE_CODEC = RegistryFileCodec.create(REGISTRY_KEY, DIRECT_CODEC);
    public static final Codec<HolderSet<Ritual>> LIST_CODEC = RegistryCodecs.homogeneousList(REGISTRY_KEY, DIRECT_CODEC);

    private static final RegistryObject<ResourceLocation> STAT = RegistryObject.create(new ResourceLocation(ArsMagicaAPI.MOD_ID, "rituals_triggered"), Registry.CUSTOM_STAT_REGISTRY, ArsMagicaAPI.MOD_ID);

    public Ritual {
        trigger.register(this);
    }

    public boolean perform(Player player, ServerLevel level, BlockPos pos, Context ctx) {
        pos = pos.offset(offset);
        for (RitualRequirement req : requirements) {
            if (!req.test(player, level, pos)) return false;
        }
        if (!this.trigger.trigger(player, level, pos, ctx)) return false;
        if (!this.effect.performEffect(player, level, pos)) return false;
        player.awardStat(STAT.get());
        return true;
    }
}
