package at.minecraftschurli.mods.arsmagicalegacy.client.model.item;

import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.MissingItemModel;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;

public record DataComponentOverridesModel<T>(DataComponentType<T> type, ItemModel fallback) implements ItemModel {    
    @Override
    public void update(ItemStackRenderState output, ItemStack item, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        output.appendModelIdentityElement(this);
        Identifier id = switch (item.get(type)) {
            case Holder.Reference<?> reference -> reference.key().identifier();
            case ResourceKey<?> resourceKey -> resourceKey.identifier();
            case Identifier identifier -> identifier;
            case null, default -> null;
        };
        ResourceKey<Item> itemKey = item.typeHolder().getKey();
        if (id != null && itemKey != null) {
            Identifier identifier = id.withPrefix(itemKey.identifier().getPath() + "/");
            ItemModel itemModel = AMClientUtil.mc().getModelManager().getItemModel(identifier);
            if (!(itemModel instanceof MissingItemModel)) {
                itemModel.update(output, item, resolver, displayContext, level, owner, seed);
                return;
            }
        }
        fallback.update(output, item, resolver, displayContext, level, owner, seed);
    }

    public record Unbaked<T>(DataComponentType<T> componentType, ItemModel.Unbaked fallback) implements ItemModel.Unbaked {
        public static final MapCodec<DataComponentOverridesModel.Unbaked<?>> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            DataComponentType.CODEC.fieldOf("component_type").forGetter(DataComponentOverridesModel.Unbaked::componentType),
            ItemModels.CODEC.fieldOf("fallback").forGetter(DataComponentOverridesModel.Unbaked::fallback)
        ).apply(instance, DataComponentOverridesModel.Unbaked::new));

        @Override
        public MapCodec<DataComponentOverridesModel.Unbaked<?>> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc transformation) {
            return new DataComponentOverridesModel<>(componentType, fallback.bake(context, transformation));
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            fallback.resolveDependencies(resolver);
        }
    }
}
