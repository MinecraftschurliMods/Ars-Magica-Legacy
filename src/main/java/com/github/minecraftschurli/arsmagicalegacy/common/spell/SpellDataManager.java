package com.github.minecraftschurli.arsmagicalegacy.common.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellDataManager;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurli.codeclib.CodecDataManager;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class SpellDataManager extends CodecDataManager<ISpellPartData> implements ISpellDataManager {
    private static final Map<ResourceLocation, Codec<ISpellIngredient>> CODECS = new HashMap<>();

    private static final Lazy<SpellDataManager> INSTANCE = Lazy.concurrentOf(SpellDataManager::new);

    private SpellDataManager() {
        super("spell_parts", SpellPartData.CODEC, LogManager.getLogger());
    }

    @Override
    public ISpellPartData getDataForPart(ISpellPart part) {
        return get(part.getRegistryName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ISpellIngredient> void registerSpellIngredientType(ResourceLocation type, Codec<T> codec) {
        CODECS.putIfAbsent(type, (Codec<ISpellIngredient>) codec);
    }

    @Override
    public Codec<ISpellIngredient> getSpellIngredientCodec(ResourceLocation type) {
        return CODECS.get(type);
    }

    public static SpellDataManager instance() {
        return INSTANCE.get();
    }

    private record SpellPartData(List<ISpellIngredient> recipe,
                                 Set<IAffinity> affinities,
                                 List<Either<Ingredient, ItemStack>> reagents,
                                 float manaCost,
                                 float burnout) implements ISpellPartData {
        public static final Codec<ISpellPartData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                CompoundTag.CODEC.flatXmap(tag -> {
                    var type = tag.getString("type");
                    tag.remove("type");
                    return CODECS.get(ResourceLocation.tryParse(type)).decode(NbtOps.INSTANCE, tag).map(Pair::getFirst);
                }, ingredient -> CODECS.get(ingredient.getType())
                                       .encodeStart(NbtOps.INSTANCE, ingredient)
                                       .map(CompoundTag.class::cast))
                                 .listOf()
                                 .fieldOf("recipe")
                                 .forGetter(ISpellPartData::recipe),
                CodecHelper.setOf(CodecHelper.forRegistry(ArsMagicaAPI.get()::getAffinityRegistry))
                           .fieldOf("affinities")
                           .forGetter(ISpellPartData::affinities),
                Codec.either(CodecHelper.INGREDIENT, ItemStack.CODEC)
                     .listOf()
                     .fieldOf("reagents")
                     .forGetter(ISpellPartData::reagents),
                Codec.FLOAT.fieldOf("manaCost").forGetter(ISpellPartData::manaCost),
                Codec.FLOAT.fieldOf("burnout").forGetter(ISpellPartData::burnout)
        ).apply(inst, SpellPartData::new));
    }
}
