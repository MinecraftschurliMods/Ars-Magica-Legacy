package at.minecraftschurli.mods.arsmagicalegacy.ability;

import at.minecraftschurli.mods.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public record EffectAbilityEffect(Holder<MobEffect> effect, int duration, int amplifier, boolean visible) implements AbilityEffect {
    public static final MapCodec<EffectAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        MobEffect.CODEC.fieldOf("effect").forGetter(EffectAbilityEffect::effect),
        Codec.INT.fieldOf("duration").forGetter(EffectAbilityEffect::duration),
        Codec.INT.optionalFieldOf("amplifier", 0).forGetter(EffectAbilityEffect::amplifier),
        Codec.BOOL.optionalFieldOf("visible", false).forGetter(EffectAbilityEffect::visible)
    ).apply(inst, EffectAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void tick(Player player, Holder<Ability> ability) {
        player.addEffect(new MobEffectInstance(effect, duration, amplifier, false, visible, visible));
    }
}
