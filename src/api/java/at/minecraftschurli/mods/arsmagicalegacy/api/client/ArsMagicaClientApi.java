package at.minecraftschurli.mods.arsmagicalegacy.api.client;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ControlledParticle;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleController;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleSpawner;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.screen.SpellPartCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.ServiceLoader;

/// The client entrypoint for the Ars Magica: Legacy API.
@NonExtendable
public abstract class ArsMagicaClientApi {
    /// A [Lazy] that holds the [ArsMagicaClientApi] instance retrieved from the [ServiceLoader]. DO NOT ACCESS YOURSELF!
    private static final Lazy<ArsMagicaClientApi> INSTANCE = Lazy.of(() -> ServiceLoader.load(FMLLoader.getCurrent().getGameLayer(), ArsMagicaClientApi.class).findFirst().orElseThrow());

    /// @param tab The [Holder] to get the [OcculusTabRenderer.Factory] for.
    /// @return The [OcculusTabRenderer.Factory] for the specified [Holder].
    public static OcculusTabRenderer.@Nullable Factory occulusTabRendererFactory(Holder<OcculusTab> tab) {
        return INSTANCE.get().getOcculusTabRendererFactory(tab);
    }

    /// @param id The id of the [ParticleController] to get.
    /// @return The [ParticleController] for the given id.
    public static ParticleController.@Nullable Type particleController(Identifier id) {
        return INSTANCE.get().getParticleController(id);
    }

    /// @param spellPart The [SpellPart] to get the [SpellPartCustomizationScreen.Factory] for.
    /// @return The [SpellPartCustomizationScreen.Factory] for the given [SpellPart].
    public static SpellPartCustomizationScreen.@Nullable Factory<?, ?> spellPartCustomizationScreen(Holder<SpellPart> spellPart) {
        return INSTANCE.get().getSpellPartCustomizationScreen(spellPart);
    }

    /// @return A new [MagitechGogglesOverlayRenderState].
    public static MagitechGogglesOverlayRenderState createMagitechGogglesOutlineRenderState() {
        return INSTANCE.get().doCreateMagitechGogglesOutlineRenderState();
    }

    /// @return Whether the Magitech Goggles' outlines should be rendered or not.
    public static boolean shouldRenderMagitechGogglesOutline() {
        return INSTANCE.get().doShouldRenderMagitechGogglesOutline();
    }

    /// @param spawner      The [ParticleSpawner] to use.
    /// @param position     The position of the particles.
    /// @param color        The particle color to use. Use -1 to not set a color.
    /// @param caster       The [LivingEntity] casting the [Spell]. May be null if this is not called from a spell cast.
    /// @param directEntity The entity applying the [Spell], e.g. a projectile. May or may not be identical to the caster. May be null if this is not called from a spell cast.
    /// @param hitResult    The [HitResult] of the spell cast. May be null if this is not called from a spell cast.
    /// @return A [List] of the [ControlledParticle]s that were created.
    public static List<? extends ControlledParticle> spawnParticles(ParticleSpawner spawner, Vec3 position, int color, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        return INSTANCE.get().doSpawnParticles(spawner, position, color, caster, directEntity, hitResult);
    }

    @Internal
    protected abstract OcculusTabRenderer.@Nullable Factory getOcculusTabRendererFactory(Holder<OcculusTab> tab);

    @Internal
    protected abstract ParticleController.@Nullable Type getParticleController(Identifier id);

    @Internal
    protected abstract SpellPartCustomizationScreen.@Nullable Factory<?, ?> getSpellPartCustomizationScreen(Holder<SpellPart> spellPart);

    @Internal
    protected abstract MagitechGogglesOverlayRenderState doCreateMagitechGogglesOutlineRenderState();

    @Internal
    protected abstract boolean doShouldRenderMagitechGogglesOutline();

    @Internal
    protected abstract List<? extends ControlledParticle> doSpawnParticles(ParticleSpawner spawner, Vec3 position, int color, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult);
}
