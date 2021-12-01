package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.RuneBagScreen;
import com.github.minecraftschurli.arsmagicalegacy.client.hud.BurnoutHUD;
import com.github.minecraftschurli.arsmagicalegacy.client.hud.ManaHUD;
import com.github.minecraftschurli.arsmagicalegacy.client.model.AffinityOverrideModel;
import com.github.minecraftschurli.arsmagicalegacy.client.model.SpellItemModel;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.ApiStatus.Internal;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public final class ClientInit {
    public static IIngameOverlay MANA_HUD;
    public static IIngameOverlay BURNOUT_HUD;

    @Internal
    public static void init() {
        Keybinds.init(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    static void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(AMMenuTypes.RUNE_BAG.get(), RuneBagScreen::new);
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WITCHWOOD_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WITCHWOOD_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WITCHWOOD_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.AUM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.CERUBLOSSOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.DESERT_NOVA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.TARMA_ROOT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WAKEBLOOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.VINTEUM_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.VINTEUM_WALL_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WIZARDS_CHALK.get(), RenderType.cutout());
        MANA_HUD = OverlayRegistry.registerOverlayBottom("mana_hud", new ManaHUD());
        BURNOUT_HUD = OverlayRegistry.registerOverlayBottom("burnout_hud", new BurnoutHUD());
    }

    @SubscribeEvent
    static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SpellIconAtlas.instance());
    }

    @SubscribeEvent
    static void preStitch(TextureStitchEvent.Pre event) {
        //if (!event.getMap().location().equals(InventoryMenu.BLOCK_ATLAS)) return;
        //event.addSprite(CraftingAltarModel.OVERLAY_LOC);
    }

    @SubscribeEvent
    static void postStitch(TextureStitchEvent.Post event) {
        //if (!event.getMap().location().equals(InventoryMenu.BLOCK_ATLAS)) return;
        //CraftingAltarModel.OVERLAY = event.getMap().getSprite(CraftingAltarModel.OVERLAY_LOC);
    }

    @SubscribeEvent
    static void modelRegister(ModelRegistryEvent event) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (!(item instanceof IAffinityItem)) continue;
            var itemId = item.getRegistryName();
            if (itemId == null) continue;
            for (IAffinity affinity : ArsMagicaAPI.get().getAffinityRegistry()) {
                if (IAffinity.NONE.equals(affinity.getRegistryName())) continue;
                ForgeModelBakery.addSpecialModel(new ResourceLocation(affinity.getId().getNamespace(), "item/" + itemId.getPath() + "_" + affinity.getId().getPath()));
            }
        }
    }

    @SubscribeEvent
    static void modelBake(ModelBakeEvent event) {
        var modelRegistry = event.getModelRegistry();
        for (Item item : ForgeRegistries.ITEMS) {
            if (!(item instanceof IAffinityItem)) continue;
            var itemId = item.getRegistryName();
            if (itemId == null) continue;
            modelRegistry.computeIfPresent(new ModelResourceLocation(itemId, "inventory"), (rl, model) -> new AffinityOverrideModel(model));
        }
        modelRegistry.computeIfPresent(new ModelResourceLocation(AMItems.SPELL.getId(), "inventory"), (rl, model) -> new SpellItemModel(model));
    }
}
