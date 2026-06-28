package at.minecraftschurli.mods.arsmagicalegacy.ability;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.EventTriggeredAbilityEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Optional;

public record ExtraDamageAbilityEffect(double min, double max, Optional<HolderSet<EntityType<?>>> entities) implements EventTriggeredAbilityEffect<LivingDamageEvent.Pre> {
    public static final MapCodec<ExtraDamageAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.DOUBLE.fieldOf("min").forGetter(ExtraDamageAbilityEffect::min),
        Codec.DOUBLE.fieldOf("max").forGetter(ExtraDamageAbilityEffect::max),
        HolderSetCodec.create(Registries.ENTITY_TYPE, BuiltInRegistries.ENTITY_TYPE.holderByNameCodec(), false).optionalFieldOf("entities").forGetter(ExtraDamageAbilityEffect::entities)
    ).apply(inst, ExtraDamageAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void apply(LivingDamageEvent.Pre event, Player player, Holder<Ability> ability) {
        if (entities.isPresent() && !entities.get().contains(event.getEntity().getType().builtInRegistryHolder())) return;
        event.setNewDamage((float) (event.getOriginalDamage() + ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max)));
    }
}
