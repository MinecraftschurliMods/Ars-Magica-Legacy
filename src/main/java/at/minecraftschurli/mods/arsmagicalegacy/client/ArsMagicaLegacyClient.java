package at.minecraftschurli.mods.arsmagicalegacy.client;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ArsMagicaApi.MOD_ID, dist = Dist.CLIENT)
public final class ArsMagicaLegacyClient {
    public ArsMagicaLegacyClient(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, AMClientConfig.SPEC);
        if (!ModList.get().isLoaded("configured")) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }
}
