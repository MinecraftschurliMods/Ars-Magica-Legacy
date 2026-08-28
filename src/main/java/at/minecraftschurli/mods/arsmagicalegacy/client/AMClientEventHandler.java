package at.minecraftschurli.mods.arsmagicalegacy.client;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.event.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.event.RegisterParticleControllersEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.event.RegisterSpellPartCustomizationScreensEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.mods.arsmagicalegacy.apiimpl.ArsMagicaClientApiImpl;
import at.minecraftschurli.mods.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.mods.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.RiftScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.RuneBagScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.SpellBookScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.inscriptiontable.InscriptionTableScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.occulus.AffinityTabRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.occulus.SkillTreeTabRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.PlaceBlockCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.RecallCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.SpellCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.SummonCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.color.ColorCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.layer.BarsLayer;
import at.minecraftschurli.mods.arsmagicalegacy.client.layer.ShapeGroupsLayer;
import at.minecraftschurli.mods.arsmagicalegacy.client.layer.SpellBookLayer;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.AMEntityModel;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.AMModelLayers;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.AltarCoreModel;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.CrystalPhylacteryItemTintSource;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.CrystalPhylacteryRangeSelectItemModelProperty;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.CrystalWrenchActiveItemModelProperty;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.DataComponentOverridesModel;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.EtheriumTypeItemTintSource;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.SpellItemModel;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.ParticleSpawnerManager;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.SimpleParticleProvider;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.SymbolsParticleProvider;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.ApproachEntityController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.ArcToEntityController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.ChangeSizeController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.FadeOutController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.FloatUpwardController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.LeaveTrailController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.MoveInKnockbackDirectionController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.MoveInViewDirectionController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.OrbitPointController;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.BeamRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.block.AltarCoreRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.block.BlackAuremRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.block.EtheriumBlockEntityRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.block.SpellRuneRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity.BossRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity.DryadRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity.ManaCreeperRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity.SimpleModelEntityRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.compat.curios.AMCuriosHelper;
import at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli.SpellPartPage;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMFluids;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMParticles;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.item.EnderBootsItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.FireAntennaeItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.SpellBookItem;
import at.minecraftschurli.mods.arsmagicalegacy.packet.EnderBootsJumpPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SetActiveShapeGroupPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SpellBookScrollPacket;
import at.minecraftschurli.mods.arsmagicalegacy.spell.shape.Chain;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.InitializeClientRegistriesEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterBlockStateModels;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterItemModelsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;
import net.neoforged.neoforge.client.event.RegisterTextureAtlasesEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID, value = Dist.CLIENT)
final class AMClientEventHandler {
    private static final KeyMapping.Category KEY_CATEGORY = new KeyMapping.Category(ArsMagicaApi.id("main"));
    private static final KeyMapping NEXT_SHAPE_GROUP = new KeyMapping(AMTranslations.KEY_NEXT_SHAPE_GROUP_KEY, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.KEY_PERIOD, KEY_CATEGORY);
    private static final KeyMapping PREV_SHAPE_GROUP = new KeyMapping(AMTranslations.KEY_PREV_SHAPE_GROUP_KEY, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.KEY_COMMA, KEY_CATEGORY);
    private static final KeyMapping SPELL_CUSTOMIZATION = new KeyMapping(AMTranslations.KEY_SPELL_CUSTOMIZATION_KEY, KeyConflictContext.IN_GAME, KeyModifier.SHIFT, InputConstants.Type.KEYSYM, InputConstants.KEY_C, KEY_CATEGORY);

    @SubscribeEvent
    private static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> PatchouliAPI.get().registerTemplateAsBuiltin(SpellPartPage.ID, () -> new ByteArrayInputStream(SpellPartPage.TEMPLATE.getBytes(StandardCharsets.UTF_8))));
        if (ModList.get().isLoaded("curios")) {
            AMCuriosHelper.registerMagitechGogglesRenderer();
        }
    }

    @SubscribeEvent
    private static void initializeClientRegistries(InitializeClientRegistriesEvent event) {
        ArsMagicaClientApiImpl.postEvents();
    }

    @SubscribeEvent
    private static void registerFluidModels(RegisterFluidModelsEvent event) {
        event.register(new FluidModel.Unbaked(new Material(ArsMagicaApi.id("block/liquid_etherium_still")), new Material(ArsMagicaApi.id("block/liquid_etherium_flowing")), null, null), AMFluids.LIQUID_ETHERIUM, AMFluids.FLOWING_LIQUID_ETHERIUM);
    }

    @SubscribeEvent
    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AMModelLayers.WITCHWOOD_BOAT, BoatModel::createBoatModel);
        event.registerLayerDefinition(AMModelLayers.WITCHWOOD_CHEST_BOAT, BoatModel::createChestBoatModel);
        event.registerLayerDefinition(AMModelLayers.DRYAD, AMModelLayers::createDryadLayer);
        event.registerLayerDefinition(AMModelLayers.WINTERS_GRASP, AMModelLayers::createWintersGraspLayer);
        event.registerLayerDefinition(AMModelLayers.NATURE_SCYTHE, AMModelLayers::createNatureScytheLayer);
        event.registerLayerDefinition(AMModelLayers.THROWN_ROCK, AMModelLayers::createThrownRockLayer);
    }

    @SubscribeEvent
    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AMEntities.WITCHWOOD_BOAT.get(), context -> new BoatRenderer(context, AMModelLayers.WITCHWOOD_BOAT));
        event.registerEntityRenderer(AMEntities.WITCHWOOD_CHEST_BOAT.get(), context -> new BoatRenderer(context, AMModelLayers.WITCHWOOD_CHEST_BOAT));
        event.registerEntityRenderer(AMEntities.BLIZZARD.get(), NoopRenderer::new);
        event.registerEntityRenderer(AMEntities.FALLING_STAR.get(), NoopRenderer::new);
        event.registerEntityRenderer(AMEntities.FIRE_RAIN.get(), NoopRenderer::new);
        event.registerEntityRenderer(AMEntities.PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(AMEntities.WALL.get(), NoopRenderer::new);
        event.registerEntityRenderer(AMEntities.WAVE.get(), NoopRenderer::new);
        event.registerEntityRenderer(AMEntities.ZONE.get(), NoopRenderer::new);
        event.registerEntityRenderer(AMEntities.DRYAD.get(), DryadRenderer::new);
        event.registerEntityRenderer(AMEntities.MANA_CREEPER.get(), ManaCreeperRenderer::new);
        event.registerEntityRenderer(AMEntities.MANA_VORTEX.get(), NoopRenderer::new);
        BossRenderer.register(event, AMEntities.WATER_GUARDIAN);
        BossRenderer.register(event, AMEntities.FIRE_GUARDIAN);
        BossRenderer.register(event, AMEntities.EARTH_GUARDIAN, Map.of("rock", boss -> !boss.hasRock()));
        BossRenderer.register(event, AMEntities.AIR_GUARDIAN);
        BossRenderer.register(event, AMEntities.ICE_GUARDIAN, Map.of("left_arm", boss -> boss.getArmCount() < 2, "right_arm", boss -> boss.getArmCount() < 1));
        BossRenderer.register(event, AMEntities.LIGHTNING_GUARDIAN);
        BossRenderer.register(event, AMEntities.NATURE_GUARDIAN, Map.of("scythe", boss -> !boss.hasScythe()));
        BossRenderer.register(event, AMEntities.LIFE_GUARDIAN);
        BossRenderer.register(event, AMEntities.ARCANE_GUARDIAN);
        BossRenderer.register(event, AMEntities.ENDER_GUARDIAN);
        event.registerEntityRenderer(AMEntities.WINTERS_GRASP.get(), context -> new SimpleModelEntityRenderer<>(context, AMModelLayers.WINTERS_GRASP, AMEntityModel::new, AMModelLayers.WINTERS_GRASP_TEXTURE));
        event.registerEntityRenderer(AMEntities.NATURE_SCYTHE.get(), context -> new SimpleModelEntityRenderer<>(context, AMModelLayers.NATURE_SCYTHE, AMEntityModel::new, AMModelLayers.NATURE_SCYTHE_TEXTURE));
        event.registerEntityRenderer(AMEntities.THROWN_ROCK.get(), context -> new SimpleModelEntityRenderer<>(context, AMModelLayers.THROWN_ROCK, AMEntityModel::new, AMModelLayers.THROWN_ROCK_TEXTURE));
        event.registerEntityRenderer(AMEntities.SHOCKWAVE.get(), NoopRenderer::new);
        event.registerEntityRenderer(AMEntities.WHIRLWIND.get(), NoopRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.ALTAR_CORE.get(), AltarCoreRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.BLACK_AUREM.get(), BlackAuremRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.CELESTIAL_PRISM.get(), EtheriumBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.OBELISK.get(), EtheriumBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.SPELL_RUNE.get(), SpellRuneRenderer::new);
    }

    @SubscribeEvent
    private static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(AMMenus.INSCRIPTION_TABLE.get(), InscriptionTableScreen::new);
        event.register(AMMenus.RIFT.get(), RiftScreen::new);
        event.register(AMMenus.RUNE_BAG.get(), RuneBagScreen::new);
        event.register(AMMenus.SPELL_BOOK.get(), SpellBookScreen::new);
    }

    @SubscribeEvent
    private static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerBelowAll(ArsMagicaApi.id("bars"), new BarsLayer());
        event.registerBelowAll(ArsMagicaApi.id("shape_groups"), new ShapeGroupsLayer());
        event.registerBelowAll(ArsMagicaApi.id("spell_book"), new SpellBookLayer());
    }

    @SubscribeEvent
    private static void bakingCompleted(ModelEvent.BakingCompleted event) {
        CrystalPhylacteryItemTintSource.clearCache();
    }

    @SubscribeEvent
    private static void registerItemTintSources(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(ArsMagicaApi.id("etherium_type"), EtheriumTypeItemTintSource.CODEC);
        event.register(ArsMagicaApi.id("crystal_phylactery"), CrystalPhylacteryItemTintSource.CODEC);
    }

    @SubscribeEvent
    private static void registerItemModelProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(ArsMagicaApi.id("crystal_wrench_active"), CrystalWrenchActiveItemModelProperty.CODEC);
    }

    @SubscribeEvent
    private static void registerItemModelProperties(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(ArsMagicaApi.id("crystal_phylactery_fill"), CrystalPhylacteryRangeSelectItemModelProperty.CODEC);
    }

    @SubscribeEvent
    private static void registerBlockModels(RegisterBlockStateModels event) {
        event.registerModel(ArsMagicaApi.id("altar_core"), AltarCoreModel.Unbaked.MAP_CODEC);
    }

    @SubscribeEvent
    private static void registerItemModels(RegisterItemModelsEvent event) {
        event.register(ArsMagicaApi.id("spell"), SpellItemModel.Unbaked.MAP_CODEC);
        event.register(ArsMagicaApi.id("data_component_overrides"), DataComponentOverridesModel.Unbaked.MAP_CODEC);
    }

    @SubscribeEvent
    private static void registerRenderPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(AMRenderPipelines.MAGITECH_GOGGLES);
        event.registerPipeline(AMRenderPipelines.COLOR_WHEEL);
    }

    @SubscribeEvent
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(KEY_CATEGORY);
        event.register(NEXT_SHAPE_GROUP);
        event.register(PREV_SHAPE_GROUP);
        event.register(SPELL_CUSTOMIZATION);
    }

    @SubscribeEvent
    private static void registerTextureAtlases(RegisterTextureAtlasesEvent event) {
        event.register(new AtlasManager.AtlasConfig(SkillAtlasHolder.ATLAS, SkillAtlasHolder.ATLAS_ID, false));
        event.register(new AtlasManager.AtlasConfig(SpellIconAtlasHolder.ATLAS, SpellIconAtlasHolder.ATLAS_ID, false));
    }

    @SubscribeEvent
    private static void registerClientReloadListeners(AddClientReloadListenersEvent event) {
        event.addListener(ParticleSpawnerManager.ID, ParticleSpawnerManager.INSTANCE);
    }

    @SubscribeEvent
    private static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(LiquidEtheriumClientFluidTypeExtensions.INSTANCE, AMFluids.LIQUID_ETHERIUM_TYPE);
    }

    @SubscribeEvent
    private static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        // @formatter:off
        event.registerSpriteSet(AMParticles.NONE_HAND.get(),      SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.WATER_HAND.get(),     SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.FIRE_HAND.get(),      SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.EARTH_HAND.get(),     SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.AIR_HAND.get(),       SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ICE_HAND.get(),       SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.LIGHTNING_HAND.get(), SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.NATURE_HAND.get(),    SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.LIFE_HAND.get(),      SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ARCANE_HAND.get(),    SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ENDER_HAND.get(),     SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ARCANE.get(),         SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.CLOCK.get(),          SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.EMBER.get(),          SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.EXPLOSION.get(),      SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.GHOST.get(),          SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.LEAF.get(),           SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.LENS_FLARE.get(),     SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.LIGHTS.get(),         SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.PLANT.get(),          SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.PULSE.get(),          SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ROCK.get(),           SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ROTATING_RINGS.get(), SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.STARDUST.get(),       SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.WATER_BALL.get(),     SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.WIND.get(),           SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.SYMBOLS.get(),        SymbolsParticleProvider::new);
        // @formatter:on
    }

    @SubscribeEvent
    private static void registerOcculusTabRenderers(RegisterOcculusTabRenderersEvent event) {
        event.register(ArsMagicaApi.id("skill_tree"), SkillTreeTabRenderer::new);
        event.register(ArsMagicaApi.id("affinity"), AffinityTabRenderer::new);
    }

    @SubscribeEvent
    private static void registerParticleControllers(RegisterParticleControllersEvent event) {
        // @formatter:off
        event.register(ApproachEntityController.ID,           ApproachEntityController.CODEC);
        event.register(ArcToEntityController.ID,              ArcToEntityController.CODEC);
        event.register(ChangeSizeController.ID,               ChangeSizeController.CODEC);
        event.register(FadeOutController.ID,                  FadeOutController.CODEC);
        event.register(FloatUpwardController.ID,              FloatUpwardController.CODEC);
        event.register(LeaveTrailController.ID,               LeaveTrailController.CODEC);
        event.register(MoveInKnockbackDirectionController.ID, MoveInKnockbackDirectionController.CODEC);
        event.register(MoveInViewDirectionController.ID,      MoveInViewDirectionController.CODEC);
        event.register(OrbitPointController.ID,               OrbitPointController.CODEC);
        // @formatter:on
    }

    @SubscribeEvent
    private static void registerSpellPartCustomizationScreens(RegisterSpellPartCustomizationScreensEvent event) {
        event.register(AMSpells.COLOR, ColorCustomizationScreen::new);
        event.register(AMSpells.PLACE_BLOCK, PlaceBlockCustomizationScreen::new);
        event.register(AMSpells.RECALL, RecallCustomizationScreen::new);
        event.register(AMSpells.SUMMON, SummonCustomizationScreen::new);
    }

    @SuppressWarnings("DataFlowIssue")
    @SubscribeEvent
    private static void clientTickPost(ClientTickEvent.Post event) {
        LocalPlayer player = AMClientUtil.player();
        if (player == null) return;
        Minecraft mc = AMClientUtil.mc();
        while (EnderBootsItem.isEquipped(player) && mc.options.keyJump.consumeClick()) {
            EnderBootsItem.toggle(player);
            ClientPacketDistributor.sendToServer(new EnderBootsJumpPacket());
        }
        InteractionHand hand = InteractionHand.MAIN_HAND;
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.has(AMDataComponents.SPELL)) {
            hand = InteractionHand.OFF_HAND;
            stack = player.getItemInHand(hand);
        }
        if (stack.has(AMDataComponents.SPELL)) {
            Spell originalSpell = stack.get(AMDataComponents.SPELL);
            Spell spell = originalSpell;
            while (NEXT_SHAPE_GROUP.consumeClick()) {
                spell = spell.nextShapeGroup();
            }
            while (PREV_SHAPE_GROUP.consumeClick()) {
                spell = spell.prevShapeGroup();
            }
            if (spell != originalSpell) {
                stack.set(AMDataComponents.SPELL, spell);
                ClientPacketDistributor.sendToServer(new SetActiveShapeGroupPacket(spell.activeShapeGroup()));
            }
            while (SPELL_CUSTOMIZATION.consumeClick()) {
                mc.setScreen(new SpellCustomizationScreen(spell, hand));
            }
        }
    }

    @SubscribeEvent
    private static void inputMouseScrolling(InputEvent.MouseScrollingEvent event) {
        double scroll = event.getScrollDeltaY();
        if (scroll == 0) return;
        Player player = AMClientUtil.player();
        if (player == null || !player.isSecondaryUseActive()) return;
        ItemStack stack = player.getMainHandItem();
        if (!SpellBookItem.isSpellBook(stack)) {
            stack = player.getOffhandItem();
            if (!SpellBookItem.isSpellBook(stack)) return;
        }
        ClientPacketDistributor.sendToServer(new SpellBookScrollPacket(scroll > 0));
        event.setCanceled(true);
    }

    @SubscribeEvent
    private static void renderPlayerPre(RenderPlayerEvent.Pre<?> event) {
        if (EnderBootsItem.isActive(event.getRenderState().feetEquipment)) {
            PoseStack stack = event.getPoseStack();
            stack.scale(1, -1, 1);
        }
    }

    /// Adapted from LavaFogEnvironment#setupFog
    @SubscribeEvent
    private static void renderFog(ViewportEvent.RenderFog event) {
        if (event.getType() != FogType.LAVA) return;
        LocalPlayer player = AMClientUtil.player();
        if (player == null || player.isSpectator() || !FireAntennaeItem.isEquipped(player)) return;
        float lavaVision = FireAntennaeItem.getLavaVision(player);
        event.setNearPlaneDistance(Mth.lerp(lavaVision, 0, -4));
        event.setFarPlaneDistance(Mth.lerp(lavaVision, 5, AMClientUtil.mc().options.getEffectiveRenderDistance() * 4));
    }

    /// Adapted from ItemInHandRenderer#renderArmWithItem and ItemInHandRenderer#renderPlayerArm
    @SubscribeEvent
    private static void renderHand(RenderHandEvent event) {
        LocalPlayer player = AMClientUtil.player();
        if (player == null || player.isInvisible() || !ArsMagicaApi.magicHelper().knowsMagic(player)) return;
        ItemStack item = event.getItemStack();
        if (!item.is(AMTags.Items.SHOWS_SPELL_VISUALS) || !item.has(AMDataComponents.SPELL)) return;
        float swing = event.getSwingProgress();
        float swingSqrt = Mth.sqrt(swing);
        boolean isRightHand = (event.getHand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite()) != HumanoidArm.LEFT;
        int armMultiplier = isRightHand ? 1 : -1;
        PoseStack stack = event.getPoseStack();
        stack.pushPose();
        stack.translate(armMultiplier * (-0.3 * Mth.sin((float) (swingSqrt * Math.PI)) + 0.64), 0.4 * Mth.sin((float) (swingSqrt * (Math.PI * 2))) - 0.6 + event.getEquipProgress() * -0.6, -0.4 * Mth.sin((float) (swing * Math.PI)) - 0.72);
        stack.mulPose(Axis.YP.rotationDegrees(armMultiplier * 45));
        stack.mulPose(Axis.YP.rotationDegrees(armMultiplier * Mth.sin((float) (swingSqrt * Math.PI)) * 70));
        stack.mulPose(Axis.ZP.rotationDegrees(armMultiplier * Mth.sin((float) (swing * swing * Math.PI)) * -20));
        stack.translate(-armMultiplier, 3.6, 3.5);
        stack.mulPose(Axis.ZP.rotationDegrees(armMultiplier * 120));
        stack.mulPose(Axis.XP.rotationDegrees(200));
        stack.mulPose(Axis.YP.rotationDegrees(armMultiplier * -135));
        stack.translate(armMultiplier * 5.6, 0, 0);
        AvatarRenderer<AbstractClientPlayer> avatarRenderer = AMClientUtil.mc().getEntityRenderDispatcher().getPlayerRenderer(player);
        SubmitNodeCollector submitNodeCollector = event.getSubmitNodeCollector();
        int lightCoords = event.getPackedLight();
        Identifier skinTexture = player.getSkin().body().texturePath();
        if (isRightHand) {
            avatarRenderer.renderRightHand(stack, submitNodeCollector, lightCoords, skinTexture, player.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE), player);
        } else {
            avatarRenderer.renderLeftHand(stack, submitNodeCollector, lightCoords, skinTexture, player.isModelPartShown(PlayerModelPart.LEFT_SLEEVE), player);
        }
        stack.popPose();
    }

    @SubscribeEvent
    private static void submitCustomGeometry(SubmitCustomGeometryEvent event) {
        PoseStack stack = event.getPoseStack();
        SubmitNodeCollector collector = event.getSubmitNodeCollector();
        Player player = Objects.requireNonNull(AMClientUtil.player());
        Minecraft mc = AMClientUtil.mc();
        Options options = mc.options;
        int distance = options.getEffectiveRenderDistance() * 8;
        CameraType cameraType = options.getCameraType();
        float partialTick = mc.getDeltaTracker().getGameTimeDeltaTicks();
        SpellHelper helper = ArsMagicaApi.spellHelper();
        for (Player p : Objects.requireNonNull(AMClientUtil.level()).players()) {
            boolean isLocalPlayer = p.getUUID().equals(player.getUUID());
            if (isLocalPlayer && cameraType == CameraType.THIRD_PERSON_BACK || player.distanceTo(p) > distance || !p.isUsingItem()) continue;
            Spell spell = p.getUseItem().get(AMDataComponents.SPELL);
            if (spell == null || spell.isEmpty()) continue;
            SpellShapeGroup shapeGroup = spell.currentShapeGroup();
            PrimarySpellShape shape = shapeGroup.primaryShape();
            boolean isBeam = shape == AMSpells.BEAM.get();
            boolean isChain = shape == AMSpells.CHAIN.get();
            if (!isBeam && !isChain) continue;
            int color = 0xff000000 | helper.getColor(shapeGroup.primaryModifiers(), spell, spell.activeShapeGroup());
            HitResult hitResult = AMUtil.getHitResult(p, spell, isBeam ? AMServerConfig.BEAM_RANGE.get() : AMServerConfig.CHAIN_RANGE.get(), partialTick);
            stack.pushPose();
            stack.translate(event.getLevelRenderState().cameraRenderState.pos.scale(-1));
            BeamRenderer.submit(stack, collector, true, p, hitResult.getLocation(), color, partialTick);
            if (isChain && hitResult instanceof EntityHitResult ehr) {
                List<Entity> list = Chain.getEntities(ehr.getEntity(), shapeGroup.primaryModifiers(), new SpellCastContext(spell, p.level(), p, null, hitResult, false, false, 1), p);
                for (int i = 1; i < list.size(); i++) {
                    Entity prev = list.get(i - 1);
                    Entity current = list.get(i);
                    BeamRenderer.submit(stack, collector, false, prev, current.getEyePosition(partialTick), color, partialTick);
                }
            }
            stack.popPose();
        }
    }
}
