package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ColorUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ObeliskScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RiftScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RuneBagScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.SpellBookScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable.InscriptionTableScreen;
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
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMFluids;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticleTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMTalents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMWoodTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.AMVanillaParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Chain;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpellBookNextSpellPacket;
import com.github.minecraftschurlimods.betterhudlib.HUDManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        modEventBus.addListener(ClientInit::registerParticleProviders);
        modEventBus.addListener(ClientInit::modelRegister);
        modEventBus.addListener(ClientInit::modelBake);
        modEventBus.addListener(ClientInit::registerLayerDefinitions);
        modEventBus.addListener(ClientInit::registerRenderers);
        modEventBus.addListener(ClientInit::itemColors);
        modEventBus.addListener(ClientInit::registerHUDs);
        modEventBus.addListener(AMShaders::init);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(ClientInit::movementInputUpdate);
        forgeBus.addListener(ClientInit::mouseScroll);
        forgeBus.addListener(ClientInit::entityRenderPre);
        forgeBus.addListener(ClientInit::entityRenderPost);
        forgeBus.addListener(ClientInit::renderHand);
        forgeBus.addListener(ClientInit::renderLevelStage);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> Sheets.addWoodType(AMWoodTypes.WITCHWOOD));
        registerMenuScreens();
        CompatManager.clientInit(event);
        ItemBlockRenderTypes.setRenderLayer(AMFluids.LIQUID_ESSENCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(AMFluids.FLOWING_LIQUID_ESSENCE.get(), RenderType.translucent());
        SpellParticleSpawners.init();
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

    private static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AMParticleTypes.NONE_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.WATER_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.FIRE_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.EARTH_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.AIR_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.ICE_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.LIGHTNING_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.NATURE_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.LIFE_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.ARCANE_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.ENDER_HAND.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.ARCANE.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.CLOCK.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.EMBER.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.EXPLOSION.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.GHOST.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.LEAF.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.LENS_FLARE.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.LIGHTS.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.PLANT.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.PULSE.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.ROCK.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.ROTATING_RINGS.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.STARDUST.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.WATER_BALL.get(), AMVanillaParticle.Provider::new);
        event.registerSpriteSet(AMParticleTypes.WIND.get(), AMVanillaParticle.Provider::new);
        for (Map.Entry<Integer, RegistryObject<SimpleParticleType>> symbol : AMParticleTypes.SYMBOLS.values().entrySet()) {
            event.registerSpriteSet(symbol.getValue().get(), AMVanillaParticle.Provider::new);
        }
    }

    private static void modelRegister(ModelEvent.RegisterAdditional event) {
        var api = ArsMagicaAPI.get();
        IForgeRegistry<Affinity> affinities = api.getAffinityRegistry();
        for (Item item : ForgeRegistries.ITEMS) {
            ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
            if (itemId == null) continue;
            if (item instanceof IAffinityItem affinityItem) {
                for (Affinity affinity : affinities) {
                    if (Affinity.NONE.equals(affinities.getKey(affinity)) && !affinityItem.hasNoneVariant()) continue;
                    event.register(new ResourceLocation(affinity.getId().getNamespace(), "item/" + itemId.getPath() + "_" + affinity.getId().getPath()));
                }
            }
            if (item instanceof ISkillPointItem) {
                for (SkillPoint skillPoint : api.getSkillPointRegistry()) {
                    event.register(new ResourceLocation(skillPoint.getId().getNamespace(), "item/" + itemId.getPath() + "_" + skillPoint.getId().getPath()));
                }
            }
            if (item instanceof ISpellItem) {
                for (Affinity affinity : affinities) {
                    event.register(new ResourceLocation(affinity.getId().getNamespace(), "item/" + itemId.getPath() + "_" + affinity.getId().getPath()));
                }
            }
            if (item instanceof SpellBookItem) {
                event.register(new ResourceLocation(itemId.getNamespace(), "item/" + itemId.getPath() + "_handheld"));
            }
        }
    }

    private static void modelBake(ModelEvent.ModifyBakingResult event) {
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
        event.registerEntityRenderer(AMEntities.PROJECTILE.get(), EmptyRenderer::new);
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
        event.registerBlockEntityRenderer(AMBlockEntities.WITCHWOOD_HANGING_SIGN.get(), HangingSignRenderer::new);
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

    private static void itemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex == 0 && stack.getItem() instanceof DyeableLeatherItem dyeable ? dyeable.getColor(stack) : -1, AMItems.SPELL_BOOK.get());
        event.register((stack, tintIndex) -> {
            EtheriumType type = ArsMagicaAPI.get().getEtheriumHelper().getEtheriumType(stack);
            return ColorUtil.argbToRgb(tintIndex == 0 && type != null ? type.getColor() : EtheriumType.NEUTRAL.getColor());
        }, AMItems.ETHERIUM_PLACEHOLDER.get());
    }

    private static void movementInputUpdate(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        if (player.isUsingItem() && (player.getUseItem().is(AMItems.SPELL.get()) || player.getUseItem().is(AMItems.SPELL_BOOK.get())) && ArsMagicaAPI.get().getSkillHelper().knows(player, AMTalents.SPELL_MOTION)) {
            event.getInput().forwardImpulse *= 5f;
            event.getInput().leftImpulse *= 5f;
        }
    }

    private static void mouseScroll(InputEvent.MouseScrollingEvent event) {
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
        if (!(p instanceof LocalPlayer player) || p.isInvisible() || !ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) return;
        ItemStack itemStack = event.getItemStack();
        if (!itemStack.is(AMItems.SPELL.get()) && !(itemStack.getItem() instanceof SpellBookItem && !SpellBookItem.getSelectedSpell(itemStack).isEmpty()))
            return;
        float swing = event.getSwingProgress();
        float swingSqrt = Mth.sqrt(swing);
        boolean isRightHand = (event.getHand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite()) != HumanoidArm.LEFT;
        int armMultiplier = isRightHand ? 1 : -1;
        PoseStack stack = event.getPoseStack();
        stack.pushPose();
        RenderSystem.setShaderTexture(0, player.getSkinTextureLocation());
        stack.translate(armMultiplier * (-0.3 * Mth.sin((float) (swingSqrt * Math.PI)) + 0.64), 0.4 * Mth.sin((float) (swingSqrt * (Math.PI * 2))) - 0.6 + event.getEquipProgress() * -0.6, -0.4 * Mth.sin((float) (swing * Math.PI)) - 0.72);
        stack.mulPose(Axis.YP.rotationDegrees(armMultiplier * 45));
        stack.mulPose(Axis.YP.rotationDegrees(armMultiplier * Mth.sin((float) (swingSqrt * Math.PI)) * 70));
        stack.mulPose(Axis.ZP.rotationDegrees(armMultiplier * Mth.sin((float) (swing * swing * Math.PI)) * -20));
        stack.translate(-armMultiplier, 3.6, 3.5);
        stack.mulPose(Axis.ZP.rotationDegrees(armMultiplier * 120));
        stack.mulPose(Axis.XP.rotationDegrees(200));
        stack.mulPose(Axis.YP.rotationDegrees(armMultiplier * -135));
        stack.translate(armMultiplier * 5.6, 0, 0);
        if (isRightHand) {
            ((PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player)).renderRightHand(stack, event.getMultiBufferSource(), event.getPackedLight(), player);
        } else {
            ((PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player)).renderLeftHand(stack, event.getMultiBufferSource(), event.getPackedLight(), player);
        }
        stack.popPose();
    }

    private static void renderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        Player player = Objects.requireNonNull(ClientHelper.getLocalPlayer());
        Level level = Objects.requireNonNull(Minecraft.getInstance().level);
        var helper = ArsMagicaAPI.get().getSpellHelper();
        float ticks = event.getPartialTick();
        PoseStack poseStack = event.getPoseStack();
        int dist = Minecraft.getInstance().options.getEffectiveRenderDistance() * 8;
        for (Player p : level.players()) {
            if (player.distanceTo(p) > dist || !p.isUsingItem()) continue;
            InteractionHand hand = InteractionHand.MAIN_HAND;
            ItemStack stack = helper.getSpellItemStackInHand(p, hand);
            if (!(stack.getItem() instanceof ISpellItem)) {
                hand = InteractionHand.OFF_HAND;
                stack = helper.getSpellItemStackInHand(p, hand);
                if (!(stack.getItem() instanceof ISpellItem)) continue;
            }
            ISpell spell = helper.getSpell(stack);
            Pair<ISpellShape, List<ISpellModifier>> pair = spell.currentShapeGroup().shapesWithModifiers().get(0);
            ISpellPart part = pair.getFirst();
            List<ISpellModifier> modifiers = pair.getSecond();
            int color = helper.getColor(modifiers, spell, p, 1, spell.primaryAffinity().color());
            if (part == AMSpellParts.BEAM.get()) {
                HitResult hitResult = helper.trace(p, level, 64, true, helper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, p, null, 1) > 0);
                if (hitResult.getType() == HitResult.Type.MISS) continue;
                BeamRenderer.drawBeam(poseStack, p, hitResult.getLocation(), hand, color, ticks);
            } else if (part == AMSpellParts.CHAIN.get()) {
                HitResult hitResult = helper.trace(p, level, 16, true, helper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, p, null, 1) > 0);
                if (hitResult.getType() == HitResult.Type.MISS) continue;
                BeamRenderer.drawBeam(poseStack, p, hitResult.getLocation(), hand, color, ticks);
                if (hitResult instanceof EntityHitResult ehr) {
                    List<Entity> list = Chain.getEntities(ehr.getEntity(), helper.getModifiedStat(4, SpellPartStats.RANGE, modifiers, spell, p, ehr, 1), p);
                    for (int i = 0; i < list.size() - 1; i++) {
                        BeamRenderer.drawBeam(poseStack, list.get(i), list.get(i + 1).getPosition(ticks).add(0, list.get(i + 1).getBbHeight() / 2f, 0), hand, color, ticks);
                    }
                }
            }
        }
    }
}
