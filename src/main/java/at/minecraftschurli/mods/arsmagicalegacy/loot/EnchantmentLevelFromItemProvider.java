package at.minecraftschurli.mods.arsmagicalegacy.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.Set;

public record EnchantmentLevelFromItemProvider(Holder<Enchantment> enchantment, LevelBasedValue value) implements NumberProvider {
    public static final MapCodec<EnchantmentLevelFromItemProvider> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Enchantment.CODEC.fieldOf("enchantment").forGetter(e -> e.enchantment),
        LevelBasedValue.CODEC.fieldOf("value").forGetter(e -> e.value)
    ).apply(inst, EnchantmentLevelFromItemProvider::new));

    @Override
    public float getFloat(LootContext lootContext) {
        if (!lootContext.hasParameter(LootContextParams.DAMAGE_SOURCE)) return 0;
        ItemStack weapon = lootContext.getParameter(LootContextParams.DAMAGE_SOURCE).getWeaponItem();
        return weapon != null ? value.calculate(weapon.getEnchantmentLevel(enchantment)) : 0;
    }

    @Override
    public MapCodec<? extends NumberProvider> codec() {
        return CODEC;
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.DAMAGE_SOURCE);
    }
}
