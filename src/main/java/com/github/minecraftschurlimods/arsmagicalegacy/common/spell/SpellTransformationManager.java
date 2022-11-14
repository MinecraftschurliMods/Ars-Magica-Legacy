package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellTransformationManager;
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

public final class SpellTransformationManager extends CodecDataManager<SpellTransformationManager.SpellTransformation> implements ISpellTransformationManager {
    private static final Lazy<SpellTransformationManager> INSTANCE = Lazy.concurrentOf(SpellTransformationManager::new);

    private SpellTransformationManager() {
        super("spell_transformations", SpellTransformation.CODEC, LogManager.getLogger());
    }

    /**
     * @return The only instance of this class.
     */
    public static SpellTransformationManager instance() {
        return INSTANCE.get();
    }

    @Override
    public Optional<BlockState> getTransformationFor(BlockState block, Level level, ResourceLocation spellPart) {
        if (level.isClientSide()) return Optional.empty();
        return values().stream().filter(spellTransformation -> spellTransformation.spellPart.equals(spellPart) && spellTransformation.from().test(block, level.random)).findFirst().map(SpellTransformation::to);
    }

    public record SpellTransformation(RuleTest from, BlockState to, ResourceLocation spellPart) {
        public static final Codec<SpellTransformation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RuleTest.CODEC.fieldOf("from").forGetter(SpellTransformation::from),
                BlockState.CODEC.fieldOf("to").forGetter(SpellTransformation::to),
                ResourceLocation.CODEC.fieldOf("spell_part").forGetter(SpellTransformation::spellPart)
        ).apply(instance, SpellTransformation::new));
    }
}
