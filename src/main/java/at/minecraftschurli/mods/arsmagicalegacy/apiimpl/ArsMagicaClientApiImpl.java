package at.minecraftschurli.mods.arsmagicalegacy.apiimpl;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.MagitechGogglesOverlayRenderState;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.event.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.event.RegisterParticleControllersEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.event.RegisterSpellPartCustomizationScreensEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ControlledParticle;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleController;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleSpawner;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.screen.SpellPartCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.AMParticle;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.MagitechGogglesOverlayRenderStateImpl;
import at.minecraftschurli.mods.arsmagicalegacy.compat.curios.AMCuriosHelper;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoader;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ArsMagicaClientApiImpl extends ArsMagicaClientApi {
    private static final Map<Identifier, OcculusTabRenderer.Factory> OCCULUS_TAB_RENDERERS = new HashMap<>();
    private static final Map<Identifier, ParticleController.Type> PARTICLE_CONTROLLERS = new HashMap<>();
    private static final Map<Holder<SpellPart>, SpellPartCustomizationScreen.Factory<?, ?>> SPELL_PART_CUSTOMIZATION_SCREENS = new HashMap<>();

    @Override
    protected OcculusTabRenderer.@Nullable Factory getOcculusTabRendererFactory(Holder<OcculusTab> tab) {
        return OCCULUS_TAB_RENDERERS.get(tab.value().renderer());
    }

    @Override
    protected ParticleController.@Nullable Type getParticleController(Identifier id) {
        return PARTICLE_CONTROLLERS.get(id);
    }

    @Override
    protected SpellPartCustomizationScreen.@Nullable Factory<?, ?> getSpellPartCustomizationScreen(Holder<SpellPart> spellPart) {
        return SPELL_PART_CUSTOMIZATION_SCREENS.get(spellPart);
    }

    @Override
    protected MagitechGogglesOverlayRenderState doCreateMagitechGogglesOutlineRenderState() {
        return new MagitechGogglesOverlayRenderStateImpl();
    }

    @Override
    protected boolean doShouldRenderMagitechGogglesOutline() {
        LocalPlayer player = AMClientUtil.player();
        return player != null && (player.getItemBySlot(EquipmentSlot.HEAD).is(AMItems.MAGITECH_GOGGLES) || ModList.get().isLoaded("curios") && AMCuriosHelper.hasItemEquipped(player, AMItems.MAGITECH_GOGGLES.get()));
    }

    @Override
    protected List<? extends ControlledParticle> doSpawnParticles(ParticleSpawner spawner, Vec3 position, int color, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        ClientLevel level = AMClientUtil.level();
        return level == null ? List.of() : AMParticle.spawn(level, position.x(), position.y(), position.z(), spawner, color, caster, directEntity, hitResult);
    }

    public static void postEvents() {
        OCCULUS_TAB_RENDERERS.putAll(ModLoader.postEventWithReturn(new RegisterOcculusTabRenderersEvent()).getRenderers());
        PARTICLE_CONTROLLERS.putAll(ModLoader.postEventWithReturn(new RegisterParticleControllersEvent()).getControllers());
        SPELL_PART_CUSTOMIZATION_SCREENS.putAll(ModLoader.postEventWithReturn(new RegisterSpellPartCustomizationScreensEvent()).getScreens());
    }
}
