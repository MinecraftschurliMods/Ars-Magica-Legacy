package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.*;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

class TagsProvider {
    static void setup(final DataGenerator generator, final ExistingFileHelper existingFileHelper) {
        TagsProvider.Blocks blocks = new Blocks(generator, ArsMagicaAPI.MOD_ID, existingFileHelper);
        generator.addProvider(blocks);
        generator.addProvider(new Items(generator, blocks, ArsMagicaAPI.MOD_ID, existingFileHelper));
        generator.addProvider(new Fluids(generator, ArsMagicaAPI.MOD_ID, existingFileHelper));
        generator.addProvider(new EntityTypes(generator, ArsMagicaAPI.MOD_ID, existingFileHelper));
    }

    private static class Blocks extends BlockTagsProvider {
        public Blocks(final DataGenerator generator, final String modId, @Nullable final ExistingFileHelper existingFileHelper) {
            super(generator, modId, existingFileHelper);
        }

        @Override
        public void addTags() {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE); // add blocks mineable with pickaxe
            this.tag(BlockTags.MINEABLE_WITH_SHOVEL);  // add blocks mineable with shovel
            this.tag(BlockTags.MINEABLE_WITH_AXE);     // add blocks mineable with axe
            this.tag(BlockTags.MINEABLE_WITH_HOE);     // add blocks mineable with hoe
        }
    }

    private static class Items extends ItemTagsProvider {
        public Items(final DataGenerator generator, final BlockTagsProvider blockTagsProvider, final String modId, @Nullable final ExistingFileHelper existingFileHelper) {
            super(generator, blockTagsProvider, modId, existingFileHelper);
        }

        @Override
        public void addTags() {

        }
    }

    private static class Fluids extends FluidTagsProvider {
        public Fluids(final DataGenerator generator, final String modId, @Nullable final ExistingFileHelper existingFileHelper) {
            super(generator, modId, existingFileHelper);
        }

        @Override
        public void addTags() {

        }
    }

    private static class EntityTypes extends EntityTypeTagsProvider {
        public EntityTypes(final DataGenerator generator, final String modId, @Nullable final ExistingFileHelper existingFileHelper) {
            super(generator, modId, existingFileHelper);
        }

        @Override
        public void addTags() {

        }
    }
}
