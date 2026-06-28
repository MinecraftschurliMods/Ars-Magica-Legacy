package at.minecraftschurli.mods.arsmagicalegacy.ability;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.EventTriggeredAbilityEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public record JumpBoostAbilityEffect(double min, double max) implements EventTriggeredAbilityEffect<LivingEvent.LivingJumpEvent> {
    public static final MapCodec<JumpBoostAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.DOUBLE.fieldOf("min").forGetter(JumpBoostAbilityEffect::min),
        Codec.DOUBLE.fieldOf("max").forGetter(JumpBoostAbilityEffect::max)
    ).apply(inst, JumpBoostAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void apply(LivingEvent.LivingJumpEvent event, Player player, Holder<Ability> ability) {
        player.setDeltaMovement(player.getDeltaMovement().add(0, ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max), 0));
    }
}
