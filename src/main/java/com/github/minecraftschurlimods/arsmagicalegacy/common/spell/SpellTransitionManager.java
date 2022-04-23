package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Optional;

public class SpellTransitionManager extends CodecDataManager<SpellTransitionManager.SpellTransition> {
    private static final Lazy<SpellTransitionManager> INSTANCE = Lazy.concurrentOf(SpellTransitionManager::new);

    private SpellTransitionManager() {
        super("spell_transitions", SpellTransition.CODEC, SpellTransition.NETWORK_CODEC, LogManager.getLogger());
        subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    }

    /**
     * @return The only instance of this class.
     */
    public static SpellTransitionManager instance() {
        return INSTANCE.get();
    }

    /**
     * @param block     The block to check the transition for.
     * @param spellPart The spell part to check the transition for.
     * @return The spell transition for the given block and spell part.
     */
    public Optional<SpellTransition> getTransitionFor(Block block, ResourceLocation spellPart) {
        return Optional.empty();//TODO
    }

    /**
     * @param block     The block to check the transition for.
     * @param spellPart The spell part to check the transition for.
     * @return Whether there is a spell transition for the given block and spell part or not.
     */
    public boolean hasTransition(Block block, ResourceLocation spellPart) {
        return false;//TODO
    }

    public List<SpellTransition> getAllTransitionsFor(ResourceLocation spellPart) {
        return values()
                .stream()
                .filter(e -> e.spellPart() == spellPart)
                .toList();
    }

    //TODO change to Block
    public record SpellTransition(Ingredient from, Ingredient to, ResourceLocation spellPart) {
        public static final Codec<SpellTransition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CodecHelper.INGREDIENT.fieldOf("from").forGetter(SpellTransition::from),
                CodecHelper.INGREDIENT.fieldOf("to").forGetter(SpellTransition::to),
                ResourceLocation.CODEC.fieldOf("spell_part").forGetter(SpellTransition::spellPart)
        ).apply(instance, SpellTransition::new));
        public static final Codec<SpellTransition> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CodecHelper.NETWORK_INGREDIENT.fieldOf("from").forGetter(SpellTransition::from),
                CodecHelper.NETWORK_INGREDIENT.fieldOf("to").forGetter(SpellTransition::to),
                ResourceLocation.CODEC.fieldOf("spell_part").forGetter(SpellTransition::spellPart)
        ).apply(instance, SpellTransition::new));
    }
}
