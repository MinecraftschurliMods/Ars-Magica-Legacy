package at.minecraftschurli.mods.arsmagicalegacy.apiimpl;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.EventTriggeredAbilityEffect;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class AbilityHelperImpl implements AbilityHelper {
    @Override
    public void onMagicChange(Player player, MagicAttachment oldData, MagicAttachment newData) {
        Registry<Ability> registry = AMRegistries.abilities(player.registryAccess());
        Set<Holder<Ability>> oldSet = registry.listElements()
            .filter(ability -> ability.value().test(oldData))
            .collect(Collectors.toSet());
        Set<Holder<Ability>> newSet = registry.listElements()
            .filter(ability -> ability.value().test(newData))
            .collect(Collectors.toSet());
        Set<Holder<Ability>> oldAbilities = Sets.difference(oldSet, newSet);
        Set<Holder<Ability>> newAbilities = Sets.difference(newSet, oldSet);
        if (!oldAbilities.isEmpty() || !newAbilities.isEmpty()) {
            Component message;
            if (oldAbilities.isEmpty()) {
                message = Component.translatable(newAbilities.size() == 1 ? AMTranslations.ABILITY_INTO_SINGLE_KEY : AMTranslations.ABILITY_INTO_MULTIPLE_KEY, joinAbilities(newAbilities));
            } else if (newAbilities.isEmpty()) {
                message = Component.translatable(oldAbilities.size() == 1 ? AMTranslations.ABILITY_OUT_OF_SINGLE_KEY : AMTranslations.ABILITY_OUT_OF_MULTIPLE_KEY, joinAbilities(oldAbilities));
            } else {
                message = Component.translatable(oldAbilities.size() == 1 && newAbilities.size() == 1 ? AMTranslations.ABILITY_INTO_SINGLE_OUT_OF_SINGLE_KEY
                    : oldAbilities.size() == 1 ? AMTranslations.ABILITY_INTO_MULTIPLE_OUT_OF_SINGLE_KEY
                    : newAbilities.size() == 1 ? AMTranslations.ABILITY_INTO_SINGLE_OUT_OF_MULTIPLE_KEY
                    : AMTranslations.ABILITY_INTO_MULTIPLE_OUT_OF_MULTIPLE_KEY, joinAbilities(newAbilities), joinAbilities(oldAbilities));
            }
            player.sendOverlayMessage(message);
        }
        oldSet.forEach(holder -> holder.value().effects().forEach(effect -> effect.shiftOutOf(player, holder)));
        newSet.forEach(holder -> holder.value().effects().forEach(effect -> effect.shiftInto(player, holder)));
    }

    @Override
    public Stream<? extends Holder<Ability>> getActiveAbilities(Player player) {
        return AMRegistries.abilities(player.registryAccess())
            .listElements()
            .filter(e -> e.value().test(player));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbilityEffect> Stream<? extends Pair<? extends Holder<Ability>, List<T>>> getActiveAbilitiesWithEffect(Player player, MapCodec<T> effectCodec) {
        return getActiveAbilities(player)
            .map(holder -> Pair.of(holder, holder.value()
                .effects()
                .stream()
                .filter(effect -> effect.codec() == effectCodec)
                .map(e -> (T) e)
                .toList()))
            .filter(e -> !e.getSecond().isEmpty());
    }

    @Override
    public <T extends Event> void triggerEventEffect(T event, Player player, MapCodec<? extends EventTriggeredAbilityEffect<T>> codec) {
        getActiveAbilitiesWithEffect(player, codec).forEach(pair -> pair.getSecond().forEach(effect -> effect.apply(event, player, pair.getFirst())));
    }

    @Override
    public double scaleToDepth(Player player, Ability ability, double min, double max) {
        double depth = ArsMagicaApi.magicHelper().getAffinityDepth(player, ability.affinity());
        double abilityMin = ability.bounds().min().orElse(0.);
        double abilityMax = ability.bounds().max().orElse(1.);
        return min + (max - min) * (abilityMin == abilityMax ? depth == abilityMin ? 1 : 0 : Math.clamp((depth - abilityMin) / (abilityMax - abilityMin), 0, 1));
    }

    private Component joinAbilities(Set<Holder<Ability>> set) {
        return set.stream()
            .map(holder -> Ability.getName(holder).withColor(holder.value().affinity().value().color()))
            .collect(AMUtil.joiningComponents(AMTranslations.ABILITY_SEPARATOR));
    }
}
