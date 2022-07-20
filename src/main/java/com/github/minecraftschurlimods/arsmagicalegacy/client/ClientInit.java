package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.InscriptionTableScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ObeliskScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RiftScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RuneBagScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.SpellBookScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.BurnoutHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.ManaHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.ShapeGroupHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.SpellBookHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.XpHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.AffinityOverrideModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.AltarCoreModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.SkillPointOverrideModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.SpellBookModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.SpellItemModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.AirGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.ArcaneGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.DryadModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.EarthGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.EnderGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.FireGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.IceGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.LifeGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.LightningGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.NatureGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.WaterGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.AltarViewBER;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.BlackAuremBER;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.SpellRuneBER;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.AirGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.ArcaneGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.DryadRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.EarthGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.EmptyRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.EnderGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.FireGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.IceGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.LifeGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.LightningGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.ManaCreeperRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.NatureGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.ProjectileRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.WaterGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMFluids;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpellBookNextSpellPacket;
import com.github.minecraftschurlimods.betterhudlib.HUDManager;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.ModelEvent.BakingCompleted;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Map;

public final class ClientInit {

    /**
     * Registers the client event handlers.
     */
    @Internal
    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Keybinds.init(modEventBus);
        HUDManager.enableKeybind();
        modEventBus.addListener(ClientInit::clientSetup);
        modEventBus.addListener(ClientInit::registerClientReloadListeners);
        modEventBus.addListener(ClientInit::modelRegister);
        modEventBus.addListener(ClientInit::modelBake);
        modEventBus.addListener(ClientInit::registerLayerDefinitions);
        modEventBus.addListener(ClientInit::registerRenderers);
        modEventBus.addListener(ClientInit::itemColors);
        modEventBus.addListener(ClientInit::registerHUDs);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(ClientInit::mouseScroll);
        forgeBus.addListener(ClientInit::entityRenderPre);
        forgeBus.addListener(ClientInit::entityRenderPost);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        registerMenuScreens();
        CompatManager.clientInit(event);
        ItemBlockRenderTypes.setRenderLayer(AMFluids.LIQUID_ESSENCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(AMFluids.FLOWING_LIQUID_ESSENCE.get(), RenderType.translucent());
    }

    private static void registerMenuScreens() {
        MenuScreens.register(AMMenuTypes.INSCRIPTION_TABLE.get(), InscriptionTableScreen::new);
        MenuScreens.register(AMMenuTypes.RIFT.get(), RiftScreen::new);
        MenuScreens.register(AMMenuTypes.RUNE_BAG.get(), RuneBagScreen::new);
        MenuScreens.register(AMMenuTypes.OBELISK.get(), ObeliskScreen::new);
        MenuScreens.register(AMMenuTypes.SPELL_BOOK.get(), SpellBookScreen::new);
    }

    private static void registerHUDs(RegisterGuiOverlaysEvent event) {
        event.registerBelowAll("mana_hud", new ManaHUD());
        event.registerBelowAll("burnout_hud", new BurnoutHUD());
        event.registerBelowAll("xp_hud", new XpHUD());
        event.registerBelowAll("shape_group_hud", new ShapeGroupHUD());
        event.registerBelowAll("spell_book_hud", new SpellBookHUD());
    }

    private static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SpellIconAtlas.instance());
        event.registerReloadListener(SkillIconAtlas.instance());
    }

    private static void modelRegister(ModelEvent.RegisterAdditional event) {
        for (Item item : ForgeRegistries.ITEMS) {
            ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
            if (itemId == null) continue;
            var api = ArsMagicaAPI.get();
            if (item instanceof IAffinityItem) {
                IForgeRegistry<Affinity> affinities = api.getAffinityRegistry();
                for (Affinity affinity : affinities) {
                    if (!Affinity.NONE.equals(affinities.getKey(affinity))) {
                        event.register(new ResourceLocation(affinity.getId().getNamespace(), "item/" + itemId.getPath() + "_" + affinity.getId().getPath()));
                    }
                }
            }
            if (item instanceof ISkillPointItem) {
                for (SkillPoint skillPoint : api.getSkillPointRegistry()) {
                    event.register(new ResourceLocation(skillPoint.getId().getNamespace(), "item/" + itemId.getPath() + "_" + skillPoint.getId().getPath()));
                }
            }
        }
    }

    private static void modelBake(BakingCompleted event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();
        for (Item item : ForgeRegistries.ITEMS) {
            ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
            if (itemId == null) continue;
            if (item instanceof IAffinityItem) {
                modelRegistry.computeIfPresent(new ModelResourceLocation(itemId, "inventory"), ($, model) -> new AffinityOverrideModel(model));
            }
            if (item instanceof ISkillPointItem) {
                modelRegistry.computeIfPresent(new ModelResourceLocation(itemId, "inventory"), ($, model) -> new SkillPointOverrideModel(model));
            }
        }
        modelRegistry.computeIfPresent(new ModelResourceLocation(AMItems.SPELL.getId(), "inventory"), ($, model) -> new SpellItemModel(model));
        modelRegistry.computeIfPresent(new ModelResourceLocation(AMItems.SPELL_BOOK.getId(), "inventory"), ($, model) -> new SpellBookModel(model));
        modelRegistry.computeIfPresent(BlockModelShaper.stateToModelLocation(AMBlocks.ALTAR_CORE.get().getStateDefinition().any().setValue(AltarCoreBlock.FORMED, true)), ($, model) -> new AltarCoreModel(model));
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AirGuardianModel.LAYER_LOCATION, AirGuardianModel::createBodyLayer);
        event.registerLayerDefinition(ArcaneGuardianModel.LAYER_LOCATION, ArcaneGuardianModel::createBodyLayer);
        event.registerLayerDefinition(EarthGuardianModel.LAYER_LOCATION, EarthGuardianModel::createBodyLayer);
        event.registerLayerDefinition(EnderGuardianModel.LAYER_LOCATION, EnderGuardianModel::createBodyLayer);
        event.registerLayerDefinition(FireGuardianModel.LAYER_LOCATION, FireGuardianModel::createBodyLayer);
        event.registerLayerDefinition(IceGuardianModel.LAYER_LOCATION, IceGuardianModel::createBodyLayer);
        event.registerLayerDefinition(LifeGuardianModel.LAYER_LOCATION, LifeGuardianModel::createBodyLayer);
        event.registerLayerDefinition(LightningGuardianModel.LAYER_LOCATION, LightningGuardianModel::createBodyLayer);
        event.registerLayerDefinition(NatureGuardianModel.LAYER_LOCATION, NatureGuardianModel::createBodyLayer);
        event.registerLayerDefinition(WaterGuardianModel.LAYER_LOCATION, WaterGuardianModel::createBodyLayer);
        event.registerLayerDefinition(DryadModel.LAYER_LOCATION, DryadModel::createBodyLayer);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AMEntities.PROJECTILE.get(), ProjectileRenderer::new);
        event.registerEntityRenderer(AMEntities.WALL.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WAVE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.ZONE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.AIR_GUARDIAN.get(), AirGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.ARCANE_GUARDIAN.get(), ArcaneGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.EARTH_GUARDIAN.get(), EarthGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.ENDER_GUARDIAN.get(), EnderGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.FIRE_GUARDIAN.get(), FireGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.ICE_GUARDIAN.get(), IceGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.LIFE_GUARDIAN.get(), LifeGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.LIGHTNING_GUARDIAN.get(), LightningGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.NATURE_GUARDIAN.get(), NatureGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.WATER_GUARDIAN.get(), WaterGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.MANA_CREEPER.get(), ManaCreeperRenderer::new);
        event.registerEntityRenderer(AMEntities.DRYAD.get(), DryadRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.ALTAR_VIEW.get(), AltarViewBER::new);
        event.registerBlockEntityRenderer(AMBlockEntities.BLACK_AUREM.get(), BlackAuremBER::new);
        event.registerBlockEntityRenderer(AMBlockEntities.SPELL_RUNE.get(), SpellRuneBER::new);
    }

    private static void entityRenderPre(RenderLivingEvent.Pre<?, ?> pre) {
        pre.getPoseStack().pushPose();
        float factor = (float) pre.getEntity().getAttributeValue(AMAttributes.SCALE.get());
        if (factor == 1) return;
        pre.getPoseStack().scale(factor, factor, factor);
    }

    private static void entityRenderPost(RenderLivingEvent.Post<?, ?> post) {
        post.getPoseStack().popPose();
    }

    private static void itemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex == 0 && stack.getItem() instanceof DyeableLeatherItem dyeable ? dyeable.getColor(stack) : -1, AMItems.SPELL_BOOK.get());
    }

    private static void mouseScroll(InputEvent.MouseScrollingEvent event) {
        Player player = ClientHelper.getLocalPlayer();
        if (player == null || !player.isCrouching()) return;
        ItemStack item = player.getMainHandItem();
        if (item.isEmpty() || !(item.getItem() instanceof SpellBookItem)) {
            item = player.getOffhandItem();
        }
        if (item.isEmpty() || !(item.getItem() instanceof SpellBookItem)) return;
        double delta = event.getScrollDelta();
        if (delta == 0) return;
        ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new SpellBookNextSpellPacket(delta > 0));
        event.setCanceled(true);
    }
}
