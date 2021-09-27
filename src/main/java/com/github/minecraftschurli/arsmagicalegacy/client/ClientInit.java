package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.RuneBagScreen;
import com.github.minecraftschurli.arsmagicalegacy.client.model.WaterGuardianModel;
import com.github.minecraftschurli.arsmagicalegacy.client.render.WaterGuardianRenderer;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMContainers;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public final class ClientInit {
    @SubscribeEvent
    static void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(AMContainers.RUNE_BAG.get(), RuneBagScreen::new);
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
    }

    @SubscribeEvent
    static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WaterGuardianModel.LAYER_LOCATION, WaterGuardianModel::createBodyLayer);
    }

    @SubscribeEvent
    static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AMEntities.WATER_GUARDIAN.get(), WaterGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.MANA_CREEPER.get(), CreeperRenderer::new);
    }

//    @SubscribeEvent
//    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
//        ModSpawnEggItem.initSpawnEggs();
//    }

    @SubscribeEvent
    static void preStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().location().equals(InventoryMenu.BLOCK_ATLAS)) return;
        //event.addSprite(CraftingAltarModel.OVERLAY_LOC);
    }

    @SubscribeEvent
    static void postStitch(TextureStitchEvent.Post event) {
        if (!event.getMap().location().equals(InventoryMenu.BLOCK_ATLAS)) return;
        //CraftingAltarModel.OVERLAY = event.getMap().getSprite(CraftingAltarModel.OVERLAY_LOC);
    }

    @SubscribeEvent
    static void modelRegister(ModelRegistryEvent event) {
        for (IAffinity affinity : ArsMagicaAPI.get().getAffinityRegistry()) {
            if (IAffinity.NONE.equals(affinity.getRegistryName())) continue;
            ModelLoader.addSpecialModel(new ResourceLocation(affinity.getId().getNamespace(), "affinity_essence_"+affinity.getId().getPath()));
            ModelLoader.addSpecialModel(new ResourceLocation(affinity.getId().getNamespace(), "affinity_tome_"+affinity.getId().getPath()));
        }
    static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WaterGuardianModel.LAYER_LOCATION, WaterGuardianModel::createBodyLayer);
    }

//    @SubscribeEvent
//    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
//        ModSpawnEggItem.initSpawnEggs();
//    }
}
