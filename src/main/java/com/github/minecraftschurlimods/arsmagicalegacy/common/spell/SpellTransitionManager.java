package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import java.util.Optional;

public class SpellTransitionManager extends CodecDataManager<SpellTransitionManager.SpellTransition> {
    private static final Lazy<SpellTransitionManager> INSTANCE = Lazy.concurrentOf(SpellTransitionManager::new);

    private SpellTransitionManager() {
        super("spell_transitions", SpellTransition.CODEC, LogManager.getLogger());
    }

    /**
     * @return The only instance of this class.
     */
    public static SpellTransitionManager instance() {
        return INSTANCE.get();
    }

    /**
     * @param block     The block to check the transition for.
     * @param level     The level to check the transition for.
     * @param spellPart The spell part to check the transition for.
     * @return The spell transition for the given block and spell part.
     */
    public Optional<BlockState> getTransitionFor(BlockState block, Level level, ResourceLocation spellPart) {
        if (level.isClientSide()) return Optional.empty();
        return values().stream().filter(spellTransition -> spellTransition.spellPart.equals(spellPart) && spellTransition.from().test(block, level.random)).findFirst().map(SpellTransition::to);
    }

    public record SpellTransition(RuleTest from, BlockState to, ResourceLocation spellPart) {
        public static final Codec<SpellTransition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RuleTest.CODEC.fieldOf("from").forGetter(SpellTransition::from),
                BlockState.CODEC.fieldOf("to").forGetter(SpellTransition::to),
                ResourceLocation.CODEC.fieldOf("spell_part").forGetter(SpellTransition::spellPart)
        ).apply(instance, SpellTransition::new));
    }
}
