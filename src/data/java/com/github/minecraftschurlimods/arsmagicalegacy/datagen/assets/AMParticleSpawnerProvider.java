package com.github.minecraftschurlimods.arsmagicalegacy.datagen.assets;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleSpawnerBuilder;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleSpawnerProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.ParticleUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller.ApproachEntityController;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller.ArcToEntityController;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller.ChangeSizeController;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller.FadeOutController;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller.FloatUpwardController;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller.LeaveTrailController;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller.MoveInKnockbackDirectionController;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller.MoveInViewDirectionController;
import com.github.minecraftschurlimods.arsmagicalegacy.client.particle.controller.OrbitPointController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FallingStar;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ManaVortex;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Whirlwind;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticles;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component.Heal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component.Transplace;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;

public final class AMParticleSpawnerProvider extends ParticleSpawnerProvider {
    public AMParticleSpawnerProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        builder(ParticleUtil.ARCANE_COMPENDIUM_CONVERSION, ParticleTypes.ENCHANT, 1, 10, 20)
            .offset(-0.5, 0.5, 0.375, 0.5, -0.5, 0.5)
            .scale(0.5f);
        builder(ParticleUtil.ARCANE_COMPENDIUM_CONVERSION_FINISH, ParticleTypes.ENCHANT, 24, 20)
            .speed(-0.1, 0.1, -0.1, 0.1, -0.1, 0.1)
            .scale(0.5f);
        builder(ManaVortex.PARTICLES, AMParticles.EMBER.get(), 1, 10, 20)
            .offset(-0.2, 0.2, -0.2, 0.2, -0.2, 0.2)
            .color(0x3d3dcc);
        builder(ManaVortex.PARTICLES_DEATH, AMParticles.EMBER.get(), 72, 20)
            .offset(-0.2, 0.2, -0.2, 0.2, -0.2, 0.2)
            .speed(-0.1, 0.1, -0.1, 0.1, -0.1, 0.1)
            .color(0x3d3dcc)
            .controller(new FadeOutController(false, true, 0.05f));
        builder(Whirlwind.PARTICLES, AMParticles.WIND.get(), 1, 10)
            .scale(10f);
        builder(AMSpells.BLIZZARD.getId(), ParticleTypes.SNOWFLAKE, 20, 40)
            .speed(-0.1, 0.1, -0.05, 0.05, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f)
            .alpha(0.6f);
        builder(FallingStar.FALL_PARTICLES, AMParticles.EMBER.get(), 1, 5)
            .controller(new ChangeSizeController());
        builder(FallingStar.GROUND_PARTICLES, AMParticles.EMBER.get(), 24, 5)
            .offset(-0.25, 0.25, -0.25, 0.25, -0.25, 0.25)
            .controller(new ChangeSizeController());
        builder(AMSpells.FIRE_RAIN.getId(), AMParticles.EXPLOSION.get(), 20, 40)
            .speed(-0.1, 0.1, -0.05, 0.05, -0.1, 0.1)
            .gravity(1);
        builder(AMSpells.PROJECTILE.getId(), ParticleTypes.CRIT, 1, 5)
            .offset(-0.05, 0.05, -0.05, 0.05, -0.05, 0.05)
            .scale(0.25f)
            .controller(new FadeOutController(0.2f))
            .controller(new FloatUpwardController(0.05, 0));
        builder(AMSpells.WALL.getId(), ParticleTypes.CRIT, 2, 20)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .scale(0.75f)
            .controller(new FloatUpwardController(0.07));
        builder(AMSpells.WAVE.getId(), ParticleTypes.CRIT, 1, 20)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .scale(0.75f)
            .controller(new MoveInViewDirectionController(0.07, 0.07));
        builder(AMSpells.ZONE.getId(), ParticleTypes.CRIT, 3, 20)
            .offset(-0.5, 0.5, 0, 0.25, -0.5, 0.5)
            .scale(0.75f)
            .controller(new FloatUpwardController(0.07));
        builder(AMSpells.ABSORPTION, AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .color(0x007fff)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, true));
        builder(AMSpells.BLINDNESS, AMParticles.LENS_FLARE.get(), 15, 25, 35)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0)
            .controller(new OrbitPointController(0.1, 0.5, 1.5, true));
        builder(AMSpells.HASTE, AMParticles.LIGHTS.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.1, 0.3, 0.6, false))
            .controller(new FadeOutController(false, true, 0.05f));
        builder(AMSpells.INVISIBILITY, AMParticles.EMBER.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, false))
            .controller(new FadeOutController(false, true, 0.05f));
        builder(AMSpells.JUMP_BOOST, AMParticles.WIND.get(), 15, 15)
            .offset(-0.5, 0.5, -1.25, -0.75, -0.5, 0.5)
            .speed(-0.05, 0.05, 0, 0.2, -0.05, 0.05)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.LEVITATION, AMParticles.EMBER.get(), 15, 40)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .scale(0.5f)
            .color(0x333399)
            .controller(new OrbitPointController(0.1, 0.2, 0.4, 0.8, true));
        builder(AMSpells.NIGHT_VISION, AMParticles.LIGHTS.get(), 8, 30)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .scale(0.5f)
            .color(0x337f33)
            .controller(new OrbitPointController(0.1, 0.2, 0.4, 0.8, true));
        builder(AMSpells.REGENERATION, AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .color(0x19ffcc)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, true));
        builder(AMSpells.RESISTANCE, AMParticles.SYMBOLS.get(), 25, 10)
            .offset(0, -1, 0)
            .scale(0.5f)
            .controller(new OrbitPointController(0.2, 1, 1, true));
        builder(AMSpells.SLOWNESS, AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, 0, 2, -0.5, 0.5)
            .controller(new FloatUpwardController(-0.1))
            .controller(new OrbitPointController(0.2, 0.3, 0.6, true));
        builder(AMSpells.SLOW_FALLING, AMParticles.WIND.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, true));
        builder(AMSpells.SWIFTNESS, AMParticles.STARDUST.get(), 15, 25, 35)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .controller(new OrbitPointController(0.1, 1, 1.5, true));
        builder(AMSpells.WATER_BREATHING, AMParticles.WATER_BALL.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, true));
        builder(AMSpells.ASTRAL_DISTORTION, AMParticles.PULSE.get(), 10, 25, 35)
            .offset(-2.5, 2.5, -2, 2, -2.5, 2.5)
            .color(0xb233e5)
            .controller(new FloatUpwardController(0.2, 0));
        builder(AMSpells.ENTANGLE, AMParticles.PLANT.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.5f)
            .controller(new ApproachEntityController(0.15, 0.4));
        builder(AMSpells.FLIGHT, AMParticles.WIND.get(), 15, 20)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .scale(0.5f)
            .controller(new OrbitPointController(0.2, 0.4, true));
        builder(AMSpells.FROST, ParticleTypes.SNOWFLAKE, 5, 10)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0.3, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.FURY, AMParticles.PULSE.get(), 10, 10)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0xff0000)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.15, 1, 2, true));
        builder(AMSpells.GRAVITY_WELL, AMParticles.PULSE.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.05f)
            .controller(new LeaveTrailController(new ParticleSpawnerBuilder(ArsMagicaApi.id("gravity_well_trail"), AMParticles.PULSE.get(), 1, 5)
                .color(0xb233e5)
                .controller(new FloatUpwardController(-0.3))
                .build()))
            .controller(new OrbitPointController(0.2, true))
            .controller(new FadeOutController(false, true, 0.05f));
        builder(AMSpells.REFLECT, AMParticles.LENS_FLARE.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5);
        builder(AMSpells.SWIFT_SWIM, AMParticles.WATER_BALL.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FadeOutController())
            .controller(new MoveInViewDirectionController(0.1, 0.6));
        builder(AMSpells.TEMPORAL_ANCHOR, AMParticles.CLOCK.get(), 25, 40)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FadeOutController())
            .controller(new OrbitPointController(0.1, 0.2, true));
        builder(AMSpells.TRUE_SIGHT, AMParticles.STARDUST.get(), 25, 40)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .color(0xb219b2)
            .controller(new OrbitPointController(0.1, 1, 1, true));
        builder(AMSpells.WATERY_GRAVE, AMParticles.WATER_BALL.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.05f)
            .controller(new FadeOutController())
            .controller(new LeaveTrailController(new ParticleSpawnerBuilder(ArsMagicaApi.id("watery_grave"), AMParticles.WATER_BALL.get(), 1, 5)
                .color(0xffffff)
                .controller(new FloatUpwardController(-0.3))
                .build()))
            .controller(new OrbitPointController(0.2, true));
        builder(AMSpells.DROWNING_DAMAGE, ParticleTypes.BUBBLE, 25, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.FIRE_DAMAGE, AMParticles.EXPLOSION.get(), 5, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.FROST_DAMAGE, ParticleTypes.SNOWFLAKE, 25, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.LIGHTNING_DAMAGE, ParticleTypes.ELECTRIC_SPARK, 5, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.MAGIC_DAMAGE, AMParticles.ARCANE.get(), 5, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.PHYSICAL_DAMAGE, AMParticles.EMBER.get(), 5, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f)
            .color(0xcc3333);
        builder(AMSpells.ATTRACT, AMParticles.ARCANE.get(), 5, 20)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0xcc4cb2);
        builder(AMSpells.BANISH_RAIN, AMParticles.WATER_BALL.get(), 25, 25, 35)
            .offset(-2.5, 2.5, -2, 2, -2.5, 2.5)
            .controller(new FloatUpwardController(0.5));
        builder(AMSpells.BLINK, AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .controller(new FadeOutController())
            .controller(new MoveInViewDirectionController(0.1, 0.6));
        builder(AMSpells.CHARM, ParticleTypes.HEART, 10, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .controller(new FloatUpwardController(0, 0.1, 0.15));
        builder(AMSpells.CREATE_WATER, ParticleTypes.BUBBLE, 15, 10)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .speed(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5);
        builder(AMSpells.DISARM, ParticleTypes.ELECTRIC_SPARK, 25, 40)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .gravity(1)
            .scale(0.5f)
            .color(0xb2b219)
            .controller(new FadeOutController())
            .controller(new MoveInViewDirectionController(0.1, 0.6));
        builder(AMSpells.DISPEL, ParticleTypes.ELECTRIC_SPARK, 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.5f)
            .color(0xb219b2)
            .controller(new OrbitPointController(0.1, 0.2, true));
        builder(AMSpells.DIVINE_INTERVENTION, AMParticles.ARCANE.get(), 100, 25, 35)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .controller(new OrbitPointController(0.1, 0.5, 1.5, true));
        builder(AMSpells.DROUGHT, AMParticles.EMBER.get(), 25, 40)
            .offset(-0.5, 0.5, 1, 1, -0.5, 0.5)
            .gravity(1)
            .scale(0.5f)
            .color(0xe5cc7f)
            .controller(new FadeOutController())
            .controller(new FloatUpwardController(0.1));
        builder(AMSpells.ENDER_INTERVENTION, AMParticles.GHOST.get(), 100, 25, 35)
            .offset(-0.5, 0.5, -2, 0, -0.5, 0.5)
            .color(0xb23333)
            .controller(new FloatUpwardController(0.1));
        builder(AMSpells.FLING, AMParticles.WIND.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .controller(new FloatUpwardController(0, 0.3, 0.6));
        builder(AMSpells.FORGE, AMParticles.LIGHTS.get(), 1, 20)
            .scale(1.5f)
            .alpha(0.1f);
        builder(AMSpells.GROW, AMParticles.PLANT.get(), 25, 20)
            .offset(-0.5, 0.5, 0.5, 1.5, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FadeOutController())
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.1, 0.3, 0.6, false));
        builder(AMSpells.HARVEST, AMParticles.PLANT.get(), 25, 20)
            .offset(-0.5, 0.5, 0.5, 1.5, -0.5, 0.5)
            .gravity(1)
            .scale(0.5f)
            .color(0xb23319)
            .controller(new FloatUpwardController(0.3));
        builder(AMSpells.HEAL, AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .color(0x19ff19)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, true));
        builder(Heal.UNDEAD_PARTICLES, AMParticles.SYMBOLS.get(), 25, 50)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FadeOutController(0.02f))
            .controller(new FloatUpwardController(-0.01));
        builder(AMSpells.IGNITION, AMParticles.EXPLOSION.get(), 25, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0.3, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.KNOCKBACK, AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .controller(new FadeOutController())
            .controller(new MoveInKnockbackDirectionController(0.1, 0.6));
        builder(AMSpells.LIFE_DRAIN, AMParticles.EMBER.get(), 15, 100)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0xff3333)
            .alpha(0.5f)
            .controller(new ArcToEntityController());
        builder(AMSpells.LIFE_TAP, ParticleTypes.ELECTRIC_SPARK, 25, 15)
            .offset(-1, 1, -0.25, 0.25, -1, 1)
            .scale(0.5f)
            .color(0x66197f)
            .controller(new ApproachEntityController(0.1, 0.1));
        builder(AMSpells.LIGHT, ParticleTypes.ELECTRIC_SPARK, 5, 20)
            .offset(-0.5, 0.5, -0.25, 1.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .color(0x9933cc);
        builder(AMSpells.MANA_BLAST, ParticleTypes.ELECTRIC_SPARK, 100, 10)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .color(0x9900e5)
            .controller(new ApproachEntityController(0.15, 0.1))
            .controller(new FadeOutController(0.1f));
        builder(AMSpells.MANA_DRAIN, AMParticles.STARDUST.get(), 15, 100)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0x0066ff)
            .alpha(0.5f)
            .controller(new ArcToEntityController());
        builder(AMSpells.MELT_ARMOR, AMParticles.LIGHTS.get(), 1, 20)
            .scale(1.5f)
            .color(0xb26633)
            .alpha(0.1f);
        builder(AMSpells.REPLANT, AMParticles.PLANT.get(), 15, 20)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .speed(-0.1, 0.1, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.PLOW, AMParticles.ROCK.get(), 10, 20)
            .offset(-0.5, 0.5, 0.5, 1.5, -0.5, 0.5)
            .speed(-0.1, 0.1, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.25f);
        builder(AMSpells.RECALL, AMParticles.ARCANE.get(), 25, 20)
            .offset(-1.5, 1.5, -2, 0, -1.5, 1.5)
            .controller(new ApproachEntityController(0.3, 0.1));
        builder(AMSpells.REPEL, AMParticles.STARDUST.get(), 1, 20)
            .controller(new FadeOutController());
        builder(AMSpells.TRANSPLACE, ParticleTypes.ELECTRIC_SPARK, 15, 40)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0xff0000)
            .controller(new ArcToEntityController());
        builder(Transplace.CASTER_PARTICLES, ParticleTypes.ELECTRIC_SPARK, 15, 40)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0x0000ff)
            .controller(new ArcToEntityController());
    }

    public ParticleSpawnerBuilder builder(DeferredHolder<SpellPart, ?> part, ParticleOptions particle, int count, int lifetime) {
        return builder(part.getId().withPrefix("component/"), particle, count, lifetime);
    }

    public ParticleSpawnerBuilder builder(DeferredHolder<SpellPart, ?> part, ParticleOptions particle, int count, int minLifetime, int maxLifetime) {
        return builder(part.getId().withPrefix("component/"), particle, count, minLifetime, maxLifetime);
    }
}
