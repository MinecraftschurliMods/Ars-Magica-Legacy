package at.minecraftschurli.mods.arsmagicalegacy.client.model.item;

import at.minecraftschurli.mods.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.item.CrystalPhylacteryItem;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.MapCodec;
import de.androidpit.colorthief.MMCQ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public record CrystalPhylacteryItemTintSource() implements ItemTintSource {
    public static final CrystalPhylacteryItemTintSource INSTANCE = new CrystalPhylacteryItemTintSource();
    public static final MapCodec<CrystalPhylacteryItemTintSource> CODEC = MapCodec.unit(INSTANCE);
    private static final Map<EntityType<?>, Integer> TINTS = new HashMap<>();

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        CrystalPhylacteryItem.Contents contents = itemStack.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        return contents == null || contents.amount() == 0 ? -1 : TINTS.computeIfAbsent(contents.type(), CrystalPhylacteryItemTintSource::getColor);
    }

    @Override
    public MapCodec<CrystalPhylacteryItemTintSource> type() {
        return CODEC;
    }

    public static void clearCache() {
        TINTS.clear();
    }

    private static int getColor(EntityType<?> type) {
        Optional<Holder<Item>> optional = SpawnEggItem.byId(type);
        if (optional.isEmpty()) return -1;
        ItemStack spawnEgg = new ItemStack(optional.get());
        if (!spawnEgg.has(DataComponents.ITEM_MODEL)) return -1;
        Minecraft mc = AMClientUtil.mc();
        ItemModel model = mc.getModelManager().getItemModel(Objects.requireNonNull(spawnEgg.get(DataComponents.ITEM_MODEL)));
        ItemStackRenderState state = new ItemStackRenderState();
        model.update(state, spawnEgg, mc.getItemModelResolver(), ItemDisplayContext.GUI, null, null, 42);
        return Arrays.stream(state.layers)
            .map(ItemStackRenderState.LayerRenderState::prepareQuadList)
            .flatMap(Collection::stream)
            .map(BakedQuad::materialInfo)
            .map(BakedQuad.MaterialInfo::sprite)
            .map(TextureAtlasSprite::contents)
            .map(SpriteContents::getOriginalImage)
            .filter(image -> image.format() == NativeImage.Format.RGBA)
            .findFirst()
            .map(CrystalPhylacteryItemTintSource::getColor)
            .orElse(-1);
    }

    private static int getColor(NativeImage image) {
        int quality = AMClientConfig.CRYSTAL_PHYLACTERY_MODEL_QUALITY.get();
        int width = image.getWidth();
        int height = image.getHeight();
        int size = width * height;
        int used = 0;
        int[][] array = new int[(size + quality - 1) / quality][];
        for (int i = 0; i < size; i++) {
            int pixel = image.getPixel(i % width, i / width);
            if (ARGB.alpha(pixel) < 125) continue;
            array[used] = new int[]{ARGB.red(pixel), ARGB.green(pixel), ARGB.blue(pixel)};
            used++;
            i += quality - 1;
        }
        int[][] palette = MMCQ.quantize(Arrays.copyOfRange(array, 0, used), 255).palette();
        if (palette.length == 0) return -1;
        int[] color = palette[0];
        return color.length != 3 ? -1 : 0xff000000 | (color[0] & 0xff) << 16 | (color[1] & 0xff) << 8 | color[2];
    }
}
