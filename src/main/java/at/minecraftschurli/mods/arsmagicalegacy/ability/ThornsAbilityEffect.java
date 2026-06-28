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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Optional;

public record ThornsAbilityEffect(double min, double max, Optional<HolderSet<EntityType<?>>> entities) implements EventTriggeredAbilityEffect<LivingDamageEvent.Post> {
    public static final MapCodec<ThornsAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.DOUBLE.fieldOf("min").forGetter(ThornsAbilityEffect::min),
        Codec.DOUBLE.fieldOf("max").forGetter(ThornsAbilityEffect::max),
        HolderSetCodec.create(Registries.ENTITY_TYPE, BuiltInRegistries.ENTITY_TYPE.holderByNameCodec(), false).optionalFieldOf("entities").forGetter(ThornsAbilityEffect::entities)
    ).apply(inst, ThornsAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void apply(LivingDamageEvent.Post event, Player player, Holder<Ability> ability) {
        if (!(event.getSource().getEntity() instanceof LivingEntity living) || !(living.level() instanceof ServerLevel level)) return;
        living.hurtServer(level, level.damageSources().indirectMagic(player, null), (float) ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max));
    }
}
