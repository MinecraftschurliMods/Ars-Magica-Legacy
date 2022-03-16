package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.InscriptionTableScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ObeliskScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RiftScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RuneBagScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.BurnoutHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.ManaHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.ShapeGroupHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.SpellBookHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.SpellHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.hud.XpHUD;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.AffinityOverrideModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.AltarCoreModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.SkillPointOverrideModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.SpellItemModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.SpellRuneModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.EarthGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.FireGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.IceGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.NatureGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.WaterGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.AltarViewBER;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.BlackAuremBER;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.SpellRenderType;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.EarthGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.EmptyRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.FireGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.IceGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.ManaCreeperRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.NatureGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.ProjectileRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.WaterGuardianRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.io.IOException;
import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public final class ClientInit {
    public static IIngameOverlay XP_HUD;
    public static IIngameOverlay MANA_HUD;
    public static IIngameOverlay BURNOUT_HUD;
    public static IIngameOverlay SPELL_HUD;
    public static IIngameOverlay SHAPE_GROUP_HUD;
    public static IIngameOverlay SPELL_BOOK_HUD;

    /**
     * Registers the client event handlers.
     */
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
        modEventBus.addListener(ClientInit::registerShaders);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(ClientInit::renderHand);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(AMMenuTypes.INSCRIPTION_TABLE.get(), InscriptionTableScreen::new);
        MenuScreens.register(AMMenuTypes.RIFT.get(), RiftScreen::new);
        MenuScreens.register(AMMenuTypes.RUNE_BAG.get(), RuneBagScreen::new);
        MenuScreens.register(AMMenuTypes.OBELISK.get(), ObeliskScreen::new);
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.MAGIC_WALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.ALTAR_CORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WITCHWOOD_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WITCHWOOD_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WITCHWOOD_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.AUM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.CERUBLOSSOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.DESERT_NOVA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.TARMA_ROOT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WAKEBLOOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.POTTED_AUM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.POTTED_CERUBLOSSOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.POTTED_DESERT_NOVA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.POTTED_TARMA_ROOT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.POTTED_WAKEBLOOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.POTTED_WITCHWOOD_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.VINTEUM_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.VINTEUM_WALL_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WIZARDS_CHALK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.SPELL_RUNE.get(), RenderType.cutout());
        XP_HUD = OverlayRegistry.registerOverlayBottom("xp_hud", new XpHUD());
        MANA_HUD = OverlayRegistry.registerOverlayBottom("mana_hud", new ManaHUD());
        BURNOUT_HUD = OverlayRegistry.registerOverlayBottom("burnout_hud", new BurnoutHUD());
        SPELL_HUD = OverlayRegistry.registerOverlayBottom("spell_hud", new SpellHUD());
        SHAPE_GROUP_HUD = OverlayRegistry.registerOverlayBottom("shape_group_hud", new ShapeGroupHUD());
        SPELL_BOOK_HUD = OverlayRegistry.registerOverlayBottom("spell_book_hud", new SpellBookHUD());
        CompatManager.clientInit(event);
    }

    private static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SpellIconAtlas.instance());
        event.registerReloadListener(SkillIconAtlas.instance());
    }

    private static void modelRegister(ModelRegistryEvent event) {
        for (Item item : ForgeRegistries.ITEMS) {
            ResourceLocation itemId = item.getRegistryName();
            if (itemId == null) continue;
            var api = ArsMagicaAPI.get();
            if (item instanceof IAffinityItem || item == AMItems.SPELL.get()) {
                for (IAffinity affinity : api.getAffinityRegistry()) {
                    if (!IAffinity.NONE.equals(affinity.getRegistryName())) {
                        ForgeModelBakery.addSpecialModel(new ResourceLocation(affinity.getId().getNamespace(), "item/" + itemId.getPath() + "_" + affinity.getId().getPath()));
                    }
                }
            }
            if (item instanceof ISkillPointItem) {
                for (ISkillPoint skillPoint : api.getSkillPointRegistry()) {
                    ForgeModelBakery.addSpecialModel(new ResourceLocation(skillPoint.getId().getNamespace(), "item/" + itemId.getPath() + "_" + skillPoint.getId().getPath()));
                }
            }
        }
    }

    private static void modelBake(ModelBakeEvent event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModelRegistry();
        for (Item item : ForgeRegistries.ITEMS) {
            ResourceLocation itemId = item.getRegistryName();
            if (itemId == null) continue;
            if (item instanceof IAffinityItem) {
                modelRegistry.computeIfPresent(new ModelResourceLocation(itemId, "inventory"), (rl, model) -> new AffinityOverrideModel(model));
            }
            if (item instanceof ISkillPointItem) {
                modelRegistry.computeIfPresent(new ModelResourceLocation(itemId, "inventory"), (rl, model) -> new SkillPointOverrideModel(model));
            }
        }
        modelRegistry.computeIfPresent(new ModelResourceLocation(AMItems.SPELL.getId(), "inventory"), (rl, model) -> new SpellItemModel(model));
        modelRegistry.computeIfPresent(BlockModelShaper.stateToModelLocation(AMBlocks.ALTAR_CORE.get().getStateDefinition().any().setValue(AltarCoreBlock.FORMED, true)), (rl, model) -> new AltarCoreModel(model));
        AMBlocks.SPELL_RUNE.get().getStateDefinition().getPossibleStates().stream()
                .map(BlockModelShaper::stateToModelLocation)
                .forEach(loc -> modelRegistry.computeIfPresent(loc, SpellRuneModel::new));
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WaterGuardianModel.LAYER_LOCATION, WaterGuardianModel::createBodyLayer);
        event.registerLayerDefinition(EarthGuardianModel.LAYER_LOCATION, EarthGuardianModel::createBodyLayer);
        event.registerLayerDefinition(IceGuardianModel.LAYER_LOCATION, IceGuardianModel::createBodyLayer);
        event.registerLayerDefinition(NatureGuardianModel.LAYER_LOCATION, NatureGuardianModel::createBodyLayer);
        event.registerLayerDefinition(FireGuardianModel.LAYER_LOCATION, FireGuardianModel::createBodyLayer);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AMEntities.PROJECTILE.get(), ProjectileRenderer::new);
        event.registerEntityRenderer(AMEntities.WALL.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WAVE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.ZONE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WATER_GUARDIAN.get(), WaterGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.EARTH_GUARDIAN.get(), EarthGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.ICE_GUARDIAN.get(), IceGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.NATURE_GUARDIAN.get(), NatureGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.FIRE_GUARDIAN.get(), FireGuardianRenderer::new);
        event.registerEntityRenderer(AMEntities.MANA_CREEPER.get(), ManaCreeperRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.ALTAR_VIEW.get(), AltarViewBER::new);
        event.registerBlockEntityRenderer(AMBlockEntities.BLACK_AUREM.get(), BlackAuremBER::new);
    }

    private static void registerShaders(RegisterShadersEvent event) {
        try {
            event.registerShader(new ShaderInstance(event.getResourceManager(), new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_shader"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> {
                SpellRenderType.spellShader = shaderInstance;
            });
        } catch (IOException e) {
            ArsMagicaLegacy.LOGGER.error(e);
        }
    }

    private static void renderHand(RenderHandEvent event) {
        if (event.getItemStack().is(AMItems.SPELL.get())) {
            LocalPlayer player = ClientHelper.getLocalPlayer();
            if (player == null) return;
            boolean isMainHand = event.getHand() == InteractionHand.MAIN_HAND;
            HumanoidArm arm = isMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
            float armMultiplier = arm == HumanoidArm.RIGHT ? 1f : -1f;
            PoseStack stack = event.getPoseStack();
            PlayerRenderer renderer = (PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);
            float equipProgress = event.getEquipProgress();
            if (equipProgress == 0) {
                equipProgress = event.getSwingProgress();
            }
            float swingProgress = event.getSwingProgress();
            float swingSqrt = Mth.sqrt(swingProgress);
            stack.translate(-armMultiplier * 0.3f * Mth.sin((float) (swingSqrt * Math.PI)) + armMultiplier * 0.64f, 0.4f * Mth.sin((float) (swingSqrt * Math.PI * 2f)) - 0.6f + equipProgress * -0.6f, -0.4f * Mth.sin((float) (swingProgress * Math.PI)) - 0.72f);
            stack.mulPose(Vector3f.YP.rotationDegrees(armMultiplier * 45f));
            stack.mulPose(Vector3f.YP.rotationDegrees(armMultiplier * Mth.sin(Mth.sqrt(swingProgress) * (float) Math.PI) * 70f));
            stack.mulPose(Vector3f.ZP.rotationDegrees(armMultiplier * Mth.sin(swingProgress * swingProgress * (float) Math.PI) * -20f));
            RenderSystem.setShaderTexture(0, player.getSkinTextureLocation());
            stack.translate(-armMultiplier, 3.6f, 3.5f);
            stack.mulPose(Vector3f.ZP.rotationDegrees(armMultiplier * 120f));
            stack.mulPose(Vector3f.XP.rotationDegrees(200f));
            stack.mulPose(Vector3f.YP.rotationDegrees(armMultiplier * -135f));
            stack.translate(armMultiplier * 5.6f, 0, 0);
            if (isMainHand) {
                renderer.renderRightHand(stack, event.getMultiBufferSource(), event.getPackedLight(), player);
            } else {
                renderer.renderLeftHand(stack, event.getMultiBufferSource(), event.getPackedLight(), player);
            }
        }
    }
}
