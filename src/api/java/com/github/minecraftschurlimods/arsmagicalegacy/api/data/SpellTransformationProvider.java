package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellTransformation;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public abstract class SpellTransformationProvider extends AbstractRegistryDataProvider<SpellTransformation, SpellTransformationProvider.Builder> {
    protected SpellTransformationProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(SpellTransformation.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "Spell Transformations[" + namespace + "]";
    }

    /**
     * @param id   The id of the transformation.
     * @param from The block to apply the transformation to.
     * @param to   The block state to transform to.
     * @param part The spell part this transformation is for.
     */
    public Builder builder(String id, RuleTest from, BlockState to, ResourceLocation part) {
        return new Builder(new ResourceLocation(namespace, id), this, from, to, part);
    }

    /**
     * @param id   The id of the transformation.
     * @param from The block to apply the transformation to.
     * @param to   The block state to transform to.
     * @param part The spell part this transformation is for.
     */
    public Builder builder(String id, RuleTest from, BlockState to, RegistryObject<? extends ISpellPart> part) {
        return builder(id, from, to, part.getId());
    }

    public static class Builder extends AbstractRegistryDataProvider.Builder<SpellTransformation, Builder> {
        private final RuleTest from;
        private final BlockState to;
        private final ResourceLocation part;

        public Builder(ResourceLocation id, SpellTransformationProvider provider, RuleTest from, BlockState to, ResourceLocation part) {
            super(id, provider, SpellTransformation.DIRECT_CODEC);
            this.from = from;
            this.to = to;
            this.part = part;
        }

        @Override
        protected SpellTransformation get() {
            return new SpellTransformation(from, to, part);
        }
    }
}
