package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumType;
import net.minecraft.resources.ResourceKey;

public interface AMEtheriumTypes {
    // @formatter:off
    ResourceKey<EtheriumType> LIGHT   = ResourceKey.create(AMRegistries.Keys.ETHERIUM_TYPE, ArsMagicaApi.id("light"));
    ResourceKey<EtheriumType> NEUTRAL = ResourceKey.create(AMRegistries.Keys.ETHERIUM_TYPE, ArsMagicaApi.id("neutral"));
    ResourceKey<EtheriumType> DARK    = ResourceKey.create(AMRegistries.Keys.ETHERIUM_TYPE, ArsMagicaApi.id("dark"));
    // @formatter:on
}
