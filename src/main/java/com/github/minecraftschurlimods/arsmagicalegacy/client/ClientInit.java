package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ColorUtil;
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
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.block.AltarCoreModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.AMGeckolibHeadModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.AMGeckolibModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.DryadModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.EarthGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.IceGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.NatureGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.NatureScytheModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.ThrownRockModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.WintersGraspModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.item.AffinityOverrideModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.item.SkillPointOverrideModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.item.SpellBookItemModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.item.SpellItemModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.block.AltarViewBER;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.block.BlackAuremBER;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.block.SpellRuneBER;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.AMGeckolibRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.DryadRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.EmptyRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.ManaCreeperRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.NatureScytheRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.ThrownRockRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.WhirlwindRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.WintersGraspRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.spell.BeamRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMWoodTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpellBookNextSpellPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ClientInit {
    public static IIngameOverlay XP_HUD;
    public static IIngameOverlay MANA_HUD;
    public static IIngameOverlay BURNOUT_HUD;
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
        modEventBus.addListener(ClientInit::itemColors);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(ClientInit::mouseScroll);
        forgeBus.addListener(ClientInit::entityRenderPre);
        forgeBus.addListener(ClientInit::entityRenderPost);
        forgeBus.addListener(ClientInit::renderHand);
        forgeBus.addListener(ClientInit::renderLevelLast);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> Sheets.addWoodType(AMWoodTypes.WITCHWOOD));
        registerMenuScreens();
        registerRenderTypes();
        registerHUDs();
        CompatManager.clientInit(event);
    }

    private static void registerMenuScreens() {
        MenuScreens.register(AMMenuTypes.INSCRIPTION_TABLE.get(), InscriptionTableScreen::new);
        MenuScreens.register(AMMenuTypes.RIFT.get(), RiftScreen::new);
        MenuScreens.register(AMMenuTypes.RUNE_BAG.get(), RuneBagScreen::new);
        MenuScreens.register(AMMenuTypes.OBELISK.get(), ObeliskScreen::new);
        MenuScreens.register(AMMenuTypes.SPELL_BOOK.get(), SpellBookScreen::new);
    }

    private static void registerRenderTypes() {
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.MAGIC_WALL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.ALTAR_CORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.WIZARDS_CHALK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.SPELL_RUNE.get(), RenderType.cutout());
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
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.IRON_INLAY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.REDSTONE_INLAY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMBlocks.GOLD_INLAY.get(), RenderType.cutout());
    }

    private static void registerHUDs() {
        XP_HUD = OverlayRegistry.registerOverlayBottom("xp_hud", new XpHUD());
        MANA_HUD = OverlayRegistry.registerOverlayBottom("mana_hud", new ManaHUD());
        BURNOUT_HUD = OverlayRegistry.registerOverlayBottom("burnout_hud", new BurnoutHUD());
        SHAPE_GROUP_HUD = OverlayRegistry.registerOverlayBottom("shape_group_hud", new ShapeGroupHUD());
        SPELL_BOOK_HUD = OverlayRegistry.registerOverlayBottom("spell_book_hud", new SpellBookHUD());
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
            if (item instanceof IAffinityItem) {
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
            if (item instanceof ISpellItem) {
                for (IAffinity affinity : api.getAffinityRegistry()) {
                    ForgeModelBakery.addSpecialModel(new ResourceLocation(affinity.getId().getNamespace(), "item/" + itemId.getPath() + "_" + affinity.getId().getPath()));
                }
            }
            if (item instanceof SpellBookItem) {
                ForgeModelBakery.addSpecialModel(new ResourceLocation(itemId.getNamespace(), "item/" + itemId.getPath() + "_handheld"));
            }
        }
    }

    private static void modelBake(ModelBakeEvent event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModelRegistry();
        for (Item item : ForgeRegistries.ITEMS) {
            ResourceLocation itemId = item.getRegistryName();
            if (itemId == null) continue;
            if (item instanceof IAffinityItem) {
                modelRegistry.computeIfPresent(new ModelResourceLocation(itemId, "inventory"), ($, model) -> new AffinityOverrideModel(model));
            }
            if (item instanceof ISkillPointItem) {
                modelRegistry.computeIfPresent(new ModelResourceLocation(itemId, "inventory"), ($, model) -> new SkillPointOverrideModel(model));
            }
        }
        modelRegistry.computeIfPresent(new ModelResourceLocation(AMItems.SPELL.getId(), "inventory"), ($, model) -> new SpellItemModel(model));
        modelRegistry.computeIfPresent(new ModelResourceLocation(AMItems.SPELL_BOOK.getId(), "inventory"), ($, model) -> new SpellBookItemModel(model));
        modelRegistry.computeIfPresent(BlockModelShaper.stateToModelLocation(AMBlocks.ALTAR_CORE.get().getStateDefinition().any().setValue(AltarCoreBlock.FORMED, true)), ($, model) -> new AltarCoreModel(model));
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DryadModel.LAYER_LOCATION, DryadModel::createBodyLayer);
        event.registerLayerDefinition(NatureScytheModel.LAYER_LOCATION, NatureScytheModel::createBodyLayer);
        event.registerLayerDefinition(ThrownRockModel.LAYER_LOCATION, ThrownRockModel::createBodyLayer);
        event.registerLayerDefinition(WintersGraspModel.LAYER_LOCATION, WintersGraspModel::createBodyLayer);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AMEntities.PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AMEntities.WALL.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WAVE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.ZONE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.BLIZZARD.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.FIRE_RAIN.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.FALLING_STAR.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WATER_GUARDIAN.get(), context -> new AMGeckolibRenderer<>(context, new AMGeckolibModel<>("water_guardian")));
        event.registerEntityRenderer(AMEntities.FIRE_GUARDIAN.get(), context -> new AMGeckolibRenderer<>(context, new AMGeckolibHeadModel<>("fire_guardian")));
        event.registerEntityRenderer(AMEntities.EARTH_GUARDIAN.get(), context -> new AMGeckolibRenderer<>(context, new EarthGuardianModel()));
        event.registerEntityRenderer(AMEntities.AIR_GUARDIAN.get(), context -> new AMGeckolibRenderer<>(context, new AMGeckolibHeadModel<>("air_guardian")));
        event.registerEntityRenderer(AMEntities.ICE_GUARDIAN.get(), context -> new AMGeckolibRenderer<>(context, new IceGuardianModel()));
        event.registerEntityRenderer(AMEntities.LIGHTNING_GUARDIAN.get(), context -> new AMGeckolibRenderer<>(context, new AMGeckolibHeadModel<>("lightning_guardian")));
        event.registerEntityRenderer(AMEntities.NATURE_GUARDIAN.get(), context -> new AMGeckolibRenderer<>(context, new NatureGuardianModel()));
        event.registerEntityRenderer(AMEntities.LIFE_GUARDIAN.get(), context -> new AMGeckolibRenderer<>(context, new AMGeckolibModel<>("life_guardian")));
        event.registerEntityRenderer(AMEntities.ARCANE_GUARDIAN.get(), context -> new AMGeckolibRenderer<>(context, new AMGeckolibHeadModel<>("arcane_guardian")));
        event.registerEntityRenderer(AMEntities.ENDER_GUARDIAN.get(), context -> new AMGeckolibRenderer<>(context, new AMGeckolibHeadModel<>("ender_guardian")));
        event.registerEntityRenderer(AMEntities.WINTERS_GRASP.get(), WintersGraspRenderer::new);
        event.registerEntityRenderer(AMEntities.NATURE_SCYTHE.get(), NatureScytheRenderer::new);
        event.registerEntityRenderer(AMEntities.THROWN_ROCK.get(), ThrownRockRenderer::new);
        event.registerEntityRenderer(AMEntities.SHOCKWAVE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WHIRLWIND.get(), WhirlwindRenderer::new);
        event.registerEntityRenderer(AMEntities.DRYAD.get(), DryadRenderer::new);
        event.registerEntityRenderer(AMEntities.MAGE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.MANA_CREEPER.get(), ManaCreeperRenderer::new);
        event.registerEntityRenderer(AMEntities.MANA_VORTEX.get(), EmptyRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.ALTAR_VIEW.get(), AltarViewBER::new);
        event.registerBlockEntityRenderer(AMBlockEntities.BLACK_AUREM.get(), BlackAuremBER::new);
        event.registerBlockEntityRenderer(AMBlockEntities.SPELL_RUNE.get(), SpellRuneBER::new);
        event.registerBlockEntityRenderer(AMBlockEntities.WITCHWOOD_SIGN.get(), SignRenderer::new);
    }

    private static void entityRenderPre(RenderLivingEvent.Pre<?, ?> pre) {
        pre.getPoseStack().pushPose();
        if (pre.getEntity().getAttribute(AMAttributes.SCALE.get()) == null) return;
        float factor = (float) pre.getEntity().getAttributeValue(AMAttributes.SCALE.get());
        if (factor == 1) return;
        pre.getPoseStack().scale(factor, factor, factor);
    }

    private static void entityRenderPost(RenderLivingEvent.Post<?, ?> post) {
        post.getPoseStack().popPose();
    }

    private static void itemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 0 && stack.getItem() instanceof DyeableLeatherItem dyeable ? dyeable.getColor(stack) : -1, AMItems.SPELL_BOOK.get());
        event.getItemColors().register((stack, tintIndex) -> {
            EtheriumType type = ArsMagicaAPI.get().getEtheriumHelper().getEtheriumType(stack);
            return ColorUtil.argbToRgb(tintIndex == 0 && type != null ? type.getColor() : EtheriumType.NEUTRAL.getColor());
        }, AMItems.ETHERIUM_PLACEHOLDER.get());
    }

    private static void mouseScroll(InputEvent.MouseScrollEvent event) {
        Player player = ClientHelper.getLocalPlayer();
        if (player == null || !player.isShiftKeyDown()) return;
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

    /**
     * Adapted from ItemInHandRenderer#renderArmWithItem
     */
    private static void renderHand(RenderHandEvent event) {
        Player p = ClientHelper.getLocalPlayer();
        if (!(p instanceof LocalPlayer player) || p.isInvisible()) return;
        ItemStack itemStack = event.getItemStack();
        if (!itemStack.is(AMItems.SPELL.get()) && !(itemStack.getItem() instanceof SpellBookItem && !SpellBookItem.getSelectedSpell(itemStack).isEmpty())) return;
        float swing = event.getSwingProgress();
        float swingSqrt = Mth.sqrt(swing);
        boolean isRightHand = (event.getHand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite()) != HumanoidArm.LEFT;
        int armMultiplier = isRightHand ? 1 : -1;
        PoseStack stack = event.getPoseStack();
        stack.pushPose();
        RenderSystem.setShaderTexture(0, player.getSkinTextureLocation());
        stack.translate(armMultiplier * (-0.3 * Mth.sin((float) (swingSqrt * Math.PI)) + 0.64), 0.4 * Mth.sin((float) (swingSqrt * (Math.PI * 2))) - 0.6 + event.getEquipProgress() * -0.6, -0.4 * Mth.sin((float) (swing * Math.PI)) - 0.72);
        stack.mulPose(Vector3f.YP.rotationDegrees(armMultiplier * 45));
        stack.mulPose(Vector3f.YP.rotationDegrees(armMultiplier * Mth.sin((float) (swingSqrt * Math.PI)) * 70));
        stack.mulPose(Vector3f.ZP.rotationDegrees(armMultiplier * Mth.sin((float) (swing * swing * Math.PI)) * -20));
        stack.translate(-armMultiplier, 3.6, 3.5);
        stack.mulPose(Vector3f.ZP.rotationDegrees(armMultiplier * 120));
        stack.mulPose(Vector3f.XP.rotationDegrees(200));
        stack.mulPose(Vector3f.YP.rotationDegrees(armMultiplier * -135));
        stack.translate(armMultiplier * 5.6, 0, 0);
        if (isRightHand) {
            ((PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player)).renderRightHand(stack, event.getMultiBufferSource(), event.getPackedLight(), player);
        } else {
            ((PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player)).renderLeftHand(stack, event.getMultiBufferSource(), event.getPackedLight(), player);
        }
        stack.popPose();
    }

    private static void renderLevelLast(RenderLevelLastEvent event) {
        Player player = Objects.requireNonNull(ClientHelper.getLocalPlayer());
        Level level = Objects.requireNonNull(Minecraft.getInstance().level);
        var helper = ArsMagicaAPI.get().getSpellHelper();
        float ticks = event.getPartialTick();
        int dist = Minecraft.getInstance().options.getEffectiveRenderDistance() * 8;
        for (Player p : level.players()) {
            if (player.distanceTo(p) > dist || !p.isUsingItem()) continue;
            ItemStack stack = player.getMainHandItem();
            InteractionHand hand = InteractionHand.MAIN_HAND;
            if (!(stack.getItem() instanceof ISpellItem)) {
                stack = player.getOffhandItem();
                if (!(stack.getItem() instanceof ISpellItem)) continue;
                hand = InteractionHand.OFF_HAND;
            }
            ISpell spell = helper.getSpell(stack);
            Pair<ISpellShape, List<ISpellModifier>> pair = spell.currentShapeGroup().shapesWithModifiers().get(0);
            if (!pair.getFirst().isContinuous()) continue;
            if (pair.getFirst() == AMSpellParts.BEAM.get()) {
                BeamRenderer.drawBeams(event.getPoseStack(), p, hand, player.getEyePosition(ticks), helper.trace(p, level, 64, true, helper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, pair.getSecond(), spell, p, null) > 0).getLocation(), 1, 0, 0, ticks);
            }
        }
    }
}
