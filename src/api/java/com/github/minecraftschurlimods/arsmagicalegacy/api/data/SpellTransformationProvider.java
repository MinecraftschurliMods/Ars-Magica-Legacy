package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.registries.RegistryObject;

public abstract class SpellTransformationProvider extends AbstractDataProvider<SpellTransformationProvider.SpellTransformationBuilder> {
    protected SpellTransformationProvider(String namespace, DataGenerator generator) {
        super("spell_transformations", namespace, generator);
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
    public SpellTransformationBuilder builder(ResourceLocation id, RuleTest from, BlockState to, RegistryObject<? extends ISpellPart> part) {
        return new SpellTransformationBuilder(id).setFrom(from).setTo(to).setPart(part);
    }

    /**
     * Adds a new spell transformation.
     *
     * @param name The name of the transformation.
     * @param from The block to apply the transformation to.
     * @param to   The block state to transform to.
     * @param part The spell part this transformation is for.
     */
    public SpellTransformationBuilder builder(String name, RuleTest from, BlockState to, RegistryObject<? extends ISpellPart> part) {
        return new SpellTransformationBuilder(new ResourceLocation(namespace, name)).setFrom(from).setTo(to).setPart(part);
    }

    public static class SpellTransformationBuilder extends AbstractDataBuilder {
        private RuleTest from;
        private BlockState to;
        private ResourceLocation part;

        public SpellTransformationBuilder(ResourceLocation id) {
            super(id);
        }

        /**
         * Sets the base test to use.
         *
         * @param from The base test to use.
         * @return This builder, for chaining.
         */
        public SpellTransformationBuilder setFrom(RuleTest from) {
            this.from = from;
            return this;
        }

        /**
         * Sets the result state to use.
         *
         * @param to The result state to use.
         * @return This builder, for chaining.
         */
        public SpellTransformationBuilder setTo(BlockState to) {
            this.to = to;
            return this;
        }

        /**
         * Sets the spell part to use.
         *
         * @param part The spell part to use.
         * @return This builder, for chaining.
         */
        public SpellTransformationBuilder setPart(ResourceLocation part) {
            this.part = part;
            return this;
        }

        /**
         * Sets the spell part to use.
         *
         * @param part The spell part to use.
         * @return This builder, for chaining.
         */
        public SpellTransformationBuilder setPart(RegistryObject<? extends ISpellPart> part) {
            return setPart(part.getId());
        }

        @Override
        protected JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.add("from", RuleTest.CODEC.encodeStart(JsonOps.INSTANCE, from).getOrThrow(false, s -> {}));
            json.add("to", BlockState.CODEC.encodeStart(JsonOps.INSTANCE, to).getOrThrow(false, s -> {}));
            json.addProperty("spell_part", part.toString());
            return json;
        }
    }
}
