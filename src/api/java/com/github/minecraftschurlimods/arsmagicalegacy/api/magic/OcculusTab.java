package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ArsMagicaClientApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.Util;

/// Represents an occulus tab.
///
/// @param width    The width of the tab.
/// @param height   The height of the tab.
/// @param startX   The default X position of the tab.
/// @param startY   The default Y position of the tab.
/// @param index    The index of the tab in relation to other tabs.
/// @param renderer The id of the renderer type to use. Get an actual renderer only on the client using [ArsMagicaClientApi#occulusTabRendererFactory(Holder)].
@SuppressWarnings("DataFlowIssue")
public record OcculusTab(int width, int height, int startX, int startY, int index, Identifier renderer) {
    public static final Codec<OcculusTab> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.INT.fieldOf("width").forGetter(OcculusTab::width),
        Codec.INT.fieldOf("height").forGetter(OcculusTab::height),
        Codec.INT.fieldOf("start_x").forGetter(OcculusTab::startX),
        Codec.INT.fieldOf("start_y").forGetter(OcculusTab::startY),
        Codec.INT.fieldOf("index").forGetter(OcculusTab::index),
        Identifier.CODEC.fieldOf("renderer").forGetter(OcculusTab::renderer)
    ).apply(inst, OcculusTab::new));
    public static final Codec<Holder<OcculusTab>> CODEC = RegistryFileCodec.create(AMRegistries.Keys.OCCULUS_TAB, DIRECT_CODEC);

    /// @param holder The occulus tab [Holder] to query.
    /// @return The background [Identifier] for the given occulus tab.
    public static Identifier getBackground(Holder<OcculusTab> holder) {
        Identifier id = holder.getKey().identifier();
        return Identifier.fromNamespaceAndPath(id.getNamespace(), "textures/gui/occulus/background/" + id.getPath() + ".png");
    }

    /// @param holder The occulus tab [Holder] to query.
    /// @return The icon [Identifier] for the given occulus tab.
    public static Identifier getIcon(Holder<OcculusTab> holder) {
        Identifier id = holder.getKey().identifier();
        return Identifier.fromNamespaceAndPath(id.getNamespace(), "textures/gui/occulus/icon/" + id.getPath() + ".png");
    }

    /// @param holder The occulus tab [Holder] to query.
    /// @return The display name of the given occulus tab.
    public static MutableComponent getName(Holder<OcculusTab> holder) {
        return Component.translatable(Util.makeDescriptionId("occulus_tab", holder.getKey().identifier()));
    }
}
