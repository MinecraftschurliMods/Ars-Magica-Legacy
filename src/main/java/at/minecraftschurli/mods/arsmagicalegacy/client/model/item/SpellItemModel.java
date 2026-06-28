package at.minecraftschurli.mods.arsmagicalegacy.client.model.item;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.quad.MutableQuad;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class SpellItemModel implements ItemModel {
    private final ItemModel defaultModel;
    private final SpriteGetter sprites;
    private final Map<Identifier, BakedQuad> spriteQuads = new IdentityHashMap<>();

    public SpellItemModel(ItemModel defaultModel, SpriteGetter sprites) {
        this.defaultModel = defaultModel;
        this.sprites = sprites;
    }

    @Override
    public void update(ItemStackRenderState output, ItemStack item, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        output.appendModelIdentityElement(this);
        if (!ArsMagicaApi.magicHelper().knowsMagic(Objects.requireNonNull(AMClientUtil.player()))) {
            defaultModel.update(output, item, resolver, displayContext, level, owner, seed);
            return;
        }
        Spell spell = item.get(AMDataComponents.SPELL);
        if (spell == null) {
            defaultModel.update(output, item, resolver, displayContext, level, owner, seed);
            return;
        }
        ResourceKey<Affinity> affinity = spell.grammar().primaryAffinity(Objects.requireNonNull(AMClientUtil.level()).registryAccess());
        if (isHand(displayContext)) {
            Minecraft.getInstance()
                .getModelManager()
                .getItemModel(affinity.identifier().withPrefix("spell/"))
                .update(output, item, resolver, displayContext, level, owner, seed);
            return;
        }
        Optional<Identifier> icon = spell.icon();
        if (icon.isPresent() && displayContext == ItemDisplayContext.GUI) {
            Identifier iconId = icon.get();
            TextureAtlasSprite sprite = SpellIconAtlasHolder.getSpriteOrNull(sprites, iconId);
            if (sprite != null) {
                output.appendModelIdentityElement(iconId);
                ItemStackRenderState.LayerRenderState layer = output.newLayer();
                layer.prepareQuadList().add(spriteQuads.computeIfAbsent(iconId, _ -> bakedSpriteQuads(sprite)));
                layer.setUsesBlockLight(false);
                return;
            }
        }
        defaultModel.update(output, item, resolver, displayContext, level, owner, seed);
    }

    private static BakedQuad bakedSpriteQuads(TextureAtlasSprite sprite) {
        MutableQuad mutableQuad = new MutableQuad();
        mutableQuad.setSprite(sprite, ChunkSectionLayer.byTransparency(sprite.transparency()), RenderTypes.itemCutout(SpellIconAtlasHolder.ATLAS));
        mutableQuad.setCubeFaceFromSpriteCoords(Direction.SOUTH, 0, 0, 1, 1, 0);
        mutableQuad.bakeUvsFromPosition();
        return mutableQuad.toBakedQuad();
    }

    private static boolean isHand(ItemDisplayContext displayContext) {
        return displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || displayContext.firstPerson();
    }

    public record Unbaked(ItemModel.Unbaked defaultModel) implements ItemModel.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ItemModels.CODEC.fieldOf("default").forGetter(Unbaked::defaultModel)
        ).apply(i, Unbaked::new));

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc transformation) {
            ItemModel baked = defaultModel.bake(context, transformation);
            return new SpellItemModel(baked, context.sprites());
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            defaultModel.resolveDependencies(resolver);
        }
    }
}
