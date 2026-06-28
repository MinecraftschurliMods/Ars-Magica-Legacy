package at.minecraftschurli.mods.arsmagicalegacy.spell;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.mods.arsmagicalegacy.blockentity.AltarCoreBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public record EtheriumSpellIngredient(Optional<Holder<EtheriumType>> etheriumType, int count) implements SpellIngredient {
    public static final MapCodec<EtheriumSpellIngredient> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        EtheriumType.CODEC.optionalFieldOf("etherium_type").forGetter(EtheriumSpellIngredient::etheriumType),
        Codec.INT.fieldOf("count").forGetter(EtheriumSpellIngredient::count)
    ).apply(inst, EtheriumSpellIngredient::new));

    public EtheriumSpellIngredient(Holder<EtheriumType> etheriumType, int count) {
        this(Optional.of(etheriumType), count);
    }

    public EtheriumSpellIngredient(int count) {
        this(Optional.empty(), count);
    }

    @Override
    public MapCodec<? extends SpellIngredient> codec() {
        return AMSpells.ETHERIUM_SPELL_INGREDIENT.get();
    }

    @Override
    public List<Component> tooltip(@Nullable Level level) {
        return List.of(etheriumType.<Component>map(EtheriumType::getName).orElse(AMTranslations.ANY_ETHERIUM), Component.translatable(AMTranslations.SPELL_INGREDIENT_COUNT_KEY, count));
    }

    @Override
    public boolean canCombine(SpellIngredient other) {
        if (!(other instanceof EtheriumSpellIngredient that)) return false;
        Optional<Holder<EtheriumType>> thisType = this.etheriumType();
        Optional<Holder<EtheriumType>> thatType = that.etheriumType();
        return thisType.isEmpty() && thatType.isEmpty() || thisType.isPresent() && thatType.isPresent() && thisType.get().getKey() == thatType.get().getKey();
    }

    @Override
    @Nullable
    public SpellIngredient combine(SpellIngredient other) {
        return canCombine(other) ? new EtheriumSpellIngredient(etheriumType, count + other.count()) : null;
    }

    @Override
    public boolean consume(Level level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof AltarCoreBlockEntity altar && altar.consumeEtherium(this);
    }

    @Override
    public List<ItemStack> asItemStacks() {
        return etheriumType.map(holder -> List.of(etheriumPlaceholder(holder))).orElseGet(() -> AMRegistries.etheriumTypes(true)
            .listElements()
            .map(this::etheriumPlaceholder)
            .toList());
    }

    private ItemStack etheriumPlaceholder(Holder<EtheriumType> holder) {
        ItemStack stack = AMItems.ETHERIUM_PLACEHOLDER.toStack(count);
        stack.set(AMDataComponents.ETHERIUM_TYPE, holder);
        return stack;
    }
}
