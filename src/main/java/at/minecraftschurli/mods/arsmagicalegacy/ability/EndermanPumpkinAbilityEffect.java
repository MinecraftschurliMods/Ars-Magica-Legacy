package at.minecraftschurli.mods.arsmagicalegacy.ability;

import at.minecraftschurli.mods.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.EventTriggeredAbilityEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;

public record EndermanPumpkinAbilityEffect() implements EventTriggeredAbilityEffect<EnderManAngerEvent> {
    public static final EndermanPumpkinAbilityEffect INSTANCE = new EndermanPumpkinAbilityEffect();
    public static final MapCodec<EndermanPumpkinAbilityEffect> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public void apply(EnderManAngerEvent event, Player player, Holder<Ability> ability) {
        event.setCanceled(true);
    }

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }
}
