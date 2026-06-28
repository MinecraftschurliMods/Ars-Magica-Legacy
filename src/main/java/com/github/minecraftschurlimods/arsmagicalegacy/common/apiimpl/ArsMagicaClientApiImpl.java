package com.github.minecraftschurlimods.arsmagicalegacy.common.apiimpl;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ArsMagicaClientApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.MagitechGogglesOverlayRenderState;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.event.RegisterOcculusTabRenderersEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.event.RegisterParticleControllersEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.event.RegisterSpellPartCustomizationScreensEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ControlledParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleController;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleSpawner;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.screen.SpellPartCustomizationScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.AMParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.MagitechGogglesOverlayRenderStateImpl;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.curios.AMCuriosHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
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
