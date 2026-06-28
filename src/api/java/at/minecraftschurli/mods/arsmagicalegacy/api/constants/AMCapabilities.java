package at.minecraftschurli.mods.arsmagicalegacy.api.constants;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumHandler;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/// Holds the capabilities added by Ars Magica: Legacy.
@ApiStatus.NonExtendable
public interface AMCapabilities {
    BlockCapability<EtheriumHandler, @Nullable Direction> BLOCK_ETHERIUM = BlockCapability.createSided(ArsMagicaApi.id("etherium"), EtheriumHandler.class);
}
