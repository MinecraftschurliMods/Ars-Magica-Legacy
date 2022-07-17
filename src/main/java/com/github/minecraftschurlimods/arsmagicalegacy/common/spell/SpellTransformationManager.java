package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellTransformationManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellTransformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Lazy;

import java.util.Optional;

public final class SpellTransformationManager implements ISpellTransformationManager {
    private static final Lazy<SpellTransformationManager> INSTANCE = Lazy.concurrentOf(SpellTransformationManager::new);

    private SpellTransformationManager() {}

    /**
     * @return The only instance of this class.
     */
    public static SpellTransformationManager instance() {
        return INSTANCE.get();
    }

    @Override
    public Optional<BlockState> getTransformationFor(BlockState block, Level level, ResourceLocation spellPart) {
        if (level.isClientSide()) return Optional.empty();
        return ArsMagicaAPI.get().getSpellTransformationRegistry().getValues().stream().filter(spellTransformation -> spellTransformation.spellPart().equals(spellPart) && spellTransformation.from().test(block, level.random)).findFirst().map(SpellTransformation::to);
    }
}
