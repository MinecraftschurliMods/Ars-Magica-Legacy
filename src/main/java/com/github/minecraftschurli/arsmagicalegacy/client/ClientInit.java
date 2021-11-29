package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.InscriptionTableScreen;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.RuneBagScreen;
import com.github.minecraftschurli.arsmagicalegacy.client.hud.BurnoutHUD;
import com.github.minecraftschurli.arsmagicalegacy.client.hud.ManaHUD;
import com.github.minecraftschurli.arsmagicalegacy.client.hud.ShapeGroupHUD;
import com.github.minecraftschurli.arsmagicalegacy.client.hud.SpellBookHUD;
import com.github.minecraftschurli.arsmagicalegacy.client.hud.XpHUD;
import com.github.minecraftschurli.arsmagicalegacy.client.model.AffinityOverrideModel;
import com.github.minecraftschurli.arsmagicalegacy.client.model.AltarCoreModel;
import com.github.minecraftschurli.arsmagicalegacy.client.model.EarthGuardianModel;
import com.github.minecraftschurli.arsmagicalegacy.client.model.FireGuardianModel;
import com.github.minecraftschurli.arsmagicalegacy.client.model.NatureGuardianModel;
import com.github.minecraftschurli.arsmagicalegacy.client.model.SpellItemModel;
import com.github.minecraftschurli.arsmagicalegacy.client.model.SpellRuneModel;
import com.github.minecraftschurli.arsmagicalegacy.client.model.WaterGuardianModel;
import com.github.minecraftschurli.arsmagicalegacy.client.model.WinterGuardianModel;
import com.github.minecraftschurli.arsmagicalegacy.client.render.EarthGuardianRenderer;
import com.github.minecraftschurli.arsmagicalegacy.client.render.FireGuardianRenderer;
import com.github.minecraftschurli.arsmagicalegacy.client.render.ManaCreeperRenderer;
import com.github.minecraftschurli.arsmagicalegacy.client.render.NatureGuardianRenderer;
import com.github.minecraftschurli.arsmagicalegacy.client.render.ProjectileRenderer;
import com.github.minecraftschurli.arsmagicalegacy.client.render.WaterGuardianRenderer;
import com.github.minecraftschurli.arsmagicalegacy.client.render.WinterGuardianRenderer;
import com.github.minecraftschurli.arsmagicalegacy.client.render.ZoneRenderer;
import com.github.minecraftschurli.arsmagicalegacy.client.renderer.AltarViewBER;
import com.github.minecraftschurli.arsmagicalegacy.client.renderer.MagitechGogglesCurioRenderer;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public final class ClientInit {
    public static IIngameOverlay MANA_HUD;
    public static IIngameOverlay BURNOUT_HUD;
    public static IIngameOverlay XP_HUD;
    public static IIngameOverlay SHAPE_GROUP_HUD;
    public static IIngameOverlay SPELL_BOOK_HUD;

    @Internal
    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Keybinds.init(modEventBus);
        modEventBus.addListener(ClientInit::clientSetup);
        modEventBus.addListener(ClientInit::registerClientReloadListeners);
        modEventBus.addListener(ClientInit::modelRegister);
        modEventBus.addListener(ClientInit::modelBake);
        modEventBus.addListener(ClientInit::registerLayerDefinitions);
        modEventBus.addListener(ClientInit::registerRenderers);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(AMMenuTypes.INSCRIPTION_TABLE.get(), InscriptionTableScreen::new);
        MenuScreens.register(AMMenuTypes.RUNE_BAG.get(),          RuneBagScreen::new);

        ItemBlockRenderTypes.setRenderLayer(AMBlocks.MAGIC_WALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.ALTAR_CORE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WITCHWOOD_SAPLING.get(),  RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WITCHWOOD_DOOR.get(),     RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WITCHWOOD_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.AUM.get(),                RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.CERUBLOSSOM.get(),        RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.DESERT_NOVA.get(),        RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.TARMA_ROOT.get(),         RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WAKEBLOOM.get(),          RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.VINTEUM_TORCH.get(),      RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.VINTEUM_WALL_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WIZARDS_CHALK.get(),      RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.SPELL_RUNE.get(),         RenderType.cutout());

        MANA_HUD        = OverlayRegistry.registerOverlayBottom("mana_hud",        new ManaHUD());
        BURNOUT_HUD     = OverlayRegistry.registerOverlayBottom("burnout_hud",     new BurnoutHUD());
        XP_HUD          = OverlayRegistry.registerOverlayBottom("xp_hud",          new XpHUD());
        SHAPE_GROUP_HUD = OverlayRegistry.registerOverlayBottom("shape_group_hud", new ShapeGroupHUD());
        SPELL_BOOK_HUD  = OverlayRegistry.registerOverlayBottom("spell_book_hud",  new SpellBookHUD());

        Keybinds.init(FMLJavaModLoadingContext.get().getModEventBus());

        if (ModList.get().isLoaded("curios")) {
            MagitechGogglesCurioRenderer.register();
        }
    }

    private static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SpellIconAtlas.instance());
        event.registerReloadListener(SkillIconAtlas.instance());
    }

    private static void modelRegister(ModelRegistryEvent event) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (!(item instanceof IAffinityItem)) continue;
            ResourceLocation itemId = item.getRegistryName();
            if (itemId == null) continue;
            for (IAffinity affinity : ArsMagicaAPI.get().getAffinityRegistry()) {
                if (IAffinity.NONE.equals(affinity.getRegistryName())) continue;
                ModelLoader.addSpecialModel(
                        new ResourceLocation(affinity.getId().getNamespace(),
                                "item/" + itemId.getPath() + "_" + affinity.getId().getPath()
                        )
                );
            }
        }
    }

    private static void modelBake(ModelBakeEvent event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModelRegistry();
        for (Item item : ForgeRegistries.ITEMS) {
            if (!(item instanceof IAffinityItem)) continue;
            ResourceLocation itemId = item.getRegistryName();
            if (itemId == null) continue;
            modelRegistry.computeIfPresent(new ModelResourceLocation(itemId, "inventory"),
                    (rl, model) -> new AffinityOverrideModel(model));
        }
        modelRegistry.computeIfPresent(new ModelResourceLocation(AMItems.SPELL.getId(), "inventory"),
                (rl, model) -> new SpellItemModel(model));
        modelRegistry.computeIfPresent(BlockModelShaper.stateToModelLocation(
                AMBlocks.ALTAR_CORE.get().getStateDefinition().any().setValue(AltarCoreBlock.FORMED, true)
        ), (rl, model) -> new AltarCoreModel(model));

        AMBlocks.SPELL_RUNE.get()
                .getStateDefinition()
                .getPossibleStates()
                .stream()
                .map(BlockModelShaper::stateToModelLocation)
                .forEach(loc -> modelRegistry.computeIfPresent(loc, SpellRuneModel::new));
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WaterGuardianModel.LAYER_LOCATION, WaterGuardianModel::createBodyLayer);
        event.registerLayerDefinition(EarthGuardianModel.LAYER_LOCATION, EarthGuardianModel::createBodyLayer);
        event.registerLayerDefinition(WinterGuardianModel.LAYER_LOCATION, WinterGuardianModel::createBodyLayer);
        event.registerLayerDefinition(NatureGuardianModel.LAYER_LOCATION, NatureGuardianModel::createBodyLayer);
        event.registerLayerDefinition(FireGuardianModel.LAYER_LOCATION, FireGuardianModel::createBodyLayer);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AMEntities.PROJECTILE.get(), ProjectileRenderer::new);
        event.registerEntityRenderer(AMEntities.ZONE.get(), ZoneRenderer::new);
        event.registerEntityRenderer(AMEntities.WATER_GUARDIAN.get(), WaterGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.EARTH_GUARDIAN.get(), EarthGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.WINTER_GUARDIAN.get(), WinterGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.NATURE_GUARDIAN.get(), NatureGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.FIRE_GUARDIAN.get(), FireGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.MANA_CREEPER.get(), ManaCreeperRenderer::new);

        event.registerBlockEntityRenderer(AMBlockEntities.ALTAR_VIEW.get(), AltarViewBER::new);
    }

    private static void preStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().location().equals(InventoryMenu.BLOCK_ATLAS)) return;
        //event.addSprite(CraftingAltarModel.OVERLAY_LOC);
    }

    private static void postStitch(TextureStitchEvent.Post event) {
        if (!event.getMap().location().equals(InventoryMenu.BLOCK_ATLAS)) return;
        //CraftingAltarModel.OVERLAY = event.getMap().getSprite(CraftingAltarModel.OVERLAY_LOC);
    }
}
