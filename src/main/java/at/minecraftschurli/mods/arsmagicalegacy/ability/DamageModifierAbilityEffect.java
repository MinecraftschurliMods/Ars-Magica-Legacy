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
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public record DamageModifierAbilityEffect(HolderSet<DamageType> damageTypes, double min, double max) implements EventTriggeredAbilityEffect<LivingDamageEvent.Pre> {
    public static final MapCodec<DamageModifierAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        HolderSetCodec.create(Registries.DAMAGE_TYPE, DamageType.CODEC, false).fieldOf("damage_types").forGetter(DamageModifierAbilityEffect::damageTypes),
        Codec.DOUBLE.fieldOf("min").forGetter(DamageModifierAbilityEffect::min),
        Codec.DOUBLE.fieldOf("max").forGetter(DamageModifierAbilityEffect::max)
    ).apply(inst, DamageModifierAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void apply(LivingDamageEvent.Pre event, Player player, Holder<Ability> ability) {
        event.setNewDamage((float) (event.getNewDamage() * ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max)));
    }
}
