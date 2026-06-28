package at.minecraftschurli.mods.arsmagicalegacy.ability;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.ReplaceDisk;

public record FrostWalkerAbilityEffect(int min, int max, ReplaceDisk effect) implements AbilityEffect {
    public static final MapCodec<FrostWalkerAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.INT.fieldOf("min").forGetter(FrostWalkerAbilityEffect::min),
        Codec.INT.fieldOf("max").forGetter(FrostWalkerAbilityEffect::max),
        ReplaceDisk.CODEC.fieldOf("effect").forGetter(FrostWalkerAbilityEffect::effect)
    ).apply(inst, FrostWalkerAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void tick(Player player, Holder<Ability> ability) {
        if (player.level() instanceof ServerLevel serverLevel && player.onGround()) {
            effect.apply(serverLevel, (int) ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max), new EnchantedItemInUse(ItemStack.EMPTY, null, null, _ -> {}), player, player.position());
        }
    }
}
