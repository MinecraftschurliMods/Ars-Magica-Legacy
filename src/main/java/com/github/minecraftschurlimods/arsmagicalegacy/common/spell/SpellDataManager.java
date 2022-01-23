package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public final class SpellDataManager extends CodecDataManager<ISpellPartData> implements ISpellDataManager {
    private static final Map<ResourceLocation, Codec<? extends ISpellIngredient>>                          CODECS    = new HashMap<>();
    private static final Map<ResourceLocation, Codec<? extends ISpellIngredient>>                          NETWORK_CODECS    = new HashMap<>();
    private static final Map<ResourceLocation, Lazy<ISpellIngredientRenderer<? extends ISpellIngredient>>> RENDERERS = new HashMap<>();

    private static final Lazy<SpellDataManager> INSTANCE = Lazy.concurrentOf(SpellDataManager::new);

    private SpellDataManager() {
        super("spell_parts", SpellPartData.CODEC, SpellPartData.NETWORK_CODEC, LogManager.getLogger());
        subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    }

    @Override
    public ISpellPartData getDataForPart(ISpellPart part) {
        return get(part.getRegistryName());
    }

    @Override
    public <T extends ISpellIngredient> void registerSpellIngredientType(ResourceLocation type, Codec<T> codec, Supplier<ISpellIngredientRenderer<T>> renderer) {
        CODECS.putIfAbsent(type, codec);
        RENDERERS.putIfAbsent(type, Lazy.of((Supplier<ISpellIngredientRenderer<? extends ISpellIngredient>>)(Object) renderer));
    }

    @Override
    public <T extends ISpellIngredient> void registerSpellIngredientType(ResourceLocation type, Codec<T> codec, Codec<T> networkCodec, Supplier<ISpellIngredientRenderer<T>> renderer) {
        CODECS.putIfAbsent(type, codec);
        NETWORK_CODECS.putIfAbsent(type, networkCodec);
        RENDERERS.putIfAbsent(type, Lazy.of((Supplier<ISpellIngredientRenderer<? extends ISpellIngredient>>)(Object) renderer));
    }

    @Override
    public Codec<ISpellIngredient> getSpellIngredientCodec(ResourceLocation type) {
        return (Codec<ISpellIngredient>) CODECS.get(type);
    }

    @Override
    public <T extends ISpellIngredient> ISpellIngredientRenderer<T> getSpellIngredientRenderer(ResourceLocation type) {
        return (ISpellIngredientRenderer<T>) RENDERERS.get(type).get();
    }

    @Override
    public Codec<ISpellIngredient> getSpellIngredientNetworkCodec(ResourceLocation type) {
        if (NETWORK_CODECS.containsKey(type)) {
            return (Codec<ISpellIngredient>) NETWORK_CODECS.get(type);
        }
        return getSpellIngredientCodec(type);
    }

    /**
     * @return The only instance of this class.
     */
    public static SpellDataManager instance() {
        return INSTANCE.get();
    }

    private record SpellPartData(List<ISpellIngredient> recipe, Map<IAffinity, Float> affinityShifts, List<Either<Ingredient, ItemStack>> reagents, float manaCost, float burnout) implements ISpellPartData {
        public static final Codec<ISpellPartData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ISpellIngredient.CODEC.listOf().fieldOf("recipe").forGetter(ISpellPartData::recipe),
                CodecHelper.mapOf(CodecHelper.forRegistry(ArsMagicaAPI.get()::getAffinityRegistry), Codec.FLOAT).fieldOf("affinities").forGetter(ISpellPartData::affinityShifts),
                Codec.either(CodecHelper.INGREDIENT, ItemStack.CODEC).listOf().fieldOf("reagents").forGetter(ISpellPartData::reagents),
                Codec.FLOAT.fieldOf("manaCost").forGetter(ISpellPartData::manaCost),
                Codec.FLOAT.optionalFieldOf("burnout").forGetter(iSpellPartData -> Optional.of(iSpellPartData.burnout()))
        ).apply(inst, (recipe, affinities, reagents, manaCost, burnout) -> new SpellPartData(recipe, affinities, reagents, manaCost, burnout.orElse((float) (manaCost * Config.SERVER.BURNOUT_RATIO.get())))));
        public static final Codec<ISpellPartData> NETWORK_CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ISpellIngredient.NETWORK_CODEC.listOf().fieldOf("recipe").forGetter(ISpellPartData::recipe),
                CodecHelper.mapOf(CodecHelper.forRegistry(ArsMagicaAPI.get()::getAffinityRegistry), Codec.FLOAT).fieldOf("affinities").forGetter(ISpellPartData::affinityShifts),
                Codec.either(CodecHelper.INGREDIENT, ItemStack.CODEC).listOf().fieldOf("reagents").forGetter(ISpellPartData::reagents),
                Codec.FLOAT.fieldOf("manaCost").forGetter(ISpellPartData::manaCost),
                Codec.FLOAT.optionalFieldOf("burnout").forGetter(iSpellPartData -> Optional.of(iSpellPartData.burnout()))
        ).apply(inst, (recipe, affinities, reagents, manaCost, burnout) -> new SpellPartData(recipe, affinities, reagents, manaCost, burnout.orElse((float) (manaCost * Config.SERVER.BURNOUT_RATIO.get())))));

        @Override
        public Set<IAffinity> affinities() {
            return affinityShifts().keySet();
        }
    }
}
