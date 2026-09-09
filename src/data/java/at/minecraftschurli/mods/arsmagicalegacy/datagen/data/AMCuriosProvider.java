package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public final class AMCuriosProvider extends CuriosDataProvider {
    public AMCuriosProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(ArsMagicaApi.MOD_ID, output, registries);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        createEntities("player").addPlayer().addSlots("head", "belt", "charm");
    }
}
