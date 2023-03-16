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

public abstract class SpellTransformationProvider extends AbstractDataProvider<SpellTransformation, SpellTransformationProvider.Builder> {
    protected SpellTransformationProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(SpellTransformation.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "Spell Transformations[" + namespace + "]";
    }

    /**
     * Adds a new spell transformation.
     *
     * @param id   The id of the transformation.
     * @param from The block to apply the transformation to.
     * @param to   The block state to transform to.
     * @param part The spell part this transformation is for.
     */
    public Builder builder(ResourceLocation id, RuleTest from, BlockState to, RegistryObject<? extends ISpellPart> part) {
        return new Builder(id).setFrom(from).setTo(to).setPart(part);
    }

    /**
     * Adds a new spell transformation.
     *
     * @param name The name of the transformation.
     * @param from The block to apply the transformation to.
     * @param to   The block state to transform to.
     * @param part The spell part this transformation is for.
     */
    public Builder builder(String name, RuleTest from, BlockState to, RegistryObject<? extends ISpellPart> part) {
        return new Builder(new ResourceLocation(namespace, name)).setFrom(from).setTo(to).setPart(part);
    }

    public static class Builder extends AbstractDataBuilder<SpellTransformation, Builder> {
        private RuleTest from;
        private BlockState to;
        private ResourceLocation part;

        public Builder(ResourceLocation id) {
            super(id);
        }

        /**
         * Sets the base test to use.
         *
         * @param from The base test to use.
         * @return This builder, for chaining.
         */
        public Builder setFrom(RuleTest from) {
            this.from = from;
            return this;
        }

        /**
         * Sets the result state to use.
         *
         * @param to The result state to use.
         * @return This builder, for chaining.
         */
        public Builder setTo(BlockState to) {
            this.to = to;
            return this;
        }

        /**
         * Sets the spell part to use.
         *
         * @param part The spell part to use.
         * @return This builder, for chaining.
         */
        public Builder setPart(ResourceLocation part) {
            this.part = part;
            return this;
        }

        /**
         * Sets the spell part to use.
         *
         * @param part The spell part to use.
         * @return This builder, for chaining.
         */
        public Builder setPart(RegistryObject<? extends ISpellPart> part) {
            return setPart(part.getId());
        }

        @Override
        protected SpellTransformation build() {
            return new SpellTransformation(from, to, part);
        }
    }
}
