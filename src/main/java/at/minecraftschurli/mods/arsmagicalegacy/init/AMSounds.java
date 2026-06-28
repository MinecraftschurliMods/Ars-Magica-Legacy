package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMSounds {
    DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<SoundEvent, SoundEvent> ARCANE_GUARDIAN_AMBIENT          = register("entity.arcane_guardian.ambient");
    DeferredHolder<SoundEvent, SoundEvent> ARCANE_GUARDIAN_ATTACK           = register("entity.arcane_guardian.attack");
    DeferredHolder<SoundEvent, SoundEvent> ARCANE_GUARDIAN_DEATH            = register("entity.arcane_guardian.death");
    DeferredHolder<SoundEvent, SoundEvent> ARCANE_GUARDIAN_HURT             = register("entity.arcane_guardian.hurt");
    DeferredHolder<SoundEvent, SoundEvent> EARTH_GUARDIAN_AMBIENT           = register("entity.earth_guardian.ambient");
    DeferredHolder<SoundEvent, SoundEvent> EARTH_GUARDIAN_ATTACK            = register("entity.earth_guardian.attack");
    DeferredHolder<SoundEvent, SoundEvent> EARTH_GUARDIAN_DEATH             = register("entity.earth_guardian.death");
    DeferredHolder<SoundEvent, SoundEvent> EARTH_GUARDIAN_HURT              = register("entity.earth_guardian.hurt");
    DeferredHolder<SoundEvent, SoundEvent> ENDER_GUARDIAN_AMBIENT           = register("entity.ender_guardian.ambient");
    DeferredHolder<SoundEvent, SoundEvent> ENDER_GUARDIAN_ATTACK            = register("entity.ender_guardian.attack");
    DeferredHolder<SoundEvent, SoundEvent> ENDER_GUARDIAN_DEATH             = register("entity.ender_guardian.death");
    DeferredHolder<SoundEvent, SoundEvent> ENDER_GUARDIAN_HURT              = register("entity.ender_guardian.hurt");
    DeferredHolder<SoundEvent, SoundEvent> FIRE_GUARDIAN_AMBIENT            = register("entity.fire_guardian.ambient");
    DeferredHolder<SoundEvent, SoundEvent> FIRE_GUARDIAN_ATTACK             = register("entity.fire_guardian.attack");
    DeferredHolder<SoundEvent, SoundEvent> FIRE_GUARDIAN_DEATH              = register("entity.fire_guardian.death");
    DeferredHolder<SoundEvent, SoundEvent> FIRE_GUARDIAN_HURT               = register("entity.fire_guardian.hurt");
    DeferredHolder<SoundEvent, SoundEvent> ICE_GUARDIAN_AMBIENT             = register("entity.ice_guardian.ambient");
    DeferredHolder<SoundEvent, SoundEvent> ICE_GUARDIAN_DEATH               = register("entity.ice_guardian.death");
    DeferredHolder<SoundEvent, SoundEvent> LIFE_GUARDIAN_AMBIENT            = register("entity.life_guardian.ambient");
    DeferredHolder<SoundEvent, SoundEvent> LIFE_GUARDIAN_ATTACK             = register("entity.life_guardian.attack");
    DeferredHolder<SoundEvent, SoundEvent> LIFE_GUARDIAN_DEATH              = register("entity.life_guardian.death");
    DeferredHolder<SoundEvent, SoundEvent> LIFE_GUARDIAN_HURT               = register("entity.life_guardian.hurt");
    DeferredHolder<SoundEvent, SoundEvent> LIGHTNING_GUARDIAN_AMBIENT       = register("entity.lightning_guardian.ambient");
    DeferredHolder<SoundEvent, SoundEvent> LIGHTNING_GUARDIAN_ATTACK        = register("entity.lightning_guardian.attack");
    DeferredHolder<SoundEvent, SoundEvent> LIGHTNING_GUARDIAN_DEATH         = register("entity.lightning_guardian.death");
    DeferredHolder<SoundEvent, SoundEvent> LIGHTNING_GUARDIAN_HURT          = register("entity.lightning_guardian.hurt");
    DeferredHolder<SoundEvent, SoundEvent> NATURE_GUARDIAN_AMBIENT          = register("entity.nature_guardian.ambient");
    DeferredHolder<SoundEvent, SoundEvent> NATURE_GUARDIAN_ATTACK           = register("entity.nature_guardian.attack");
    DeferredHolder<SoundEvent, SoundEvent> NATURE_GUARDIAN_DEATH            = register("entity.nature_guardian.death");
    DeferredHolder<SoundEvent, SoundEvent> NATURE_GUARDIAN_HURT             = register("entity.nature_guardian.hurt");
    DeferredHolder<SoundEvent, SoundEvent> WATER_GUARDIAN_AMBIENT           = register("entity.water_guardian.ambient");
    DeferredHolder<SoundEvent, SoundEvent> WATER_GUARDIAN_DEATH             = register("entity.water_guardian.death");
    DeferredHolder<SoundEvent, SoundEvent> ENDER_GUARDIAN_FLAP              = register("entity.ender_guardian.flap");
    DeferredHolder<SoundEvent, SoundEvent> ENDER_GUARDIAN_ROAR              = register("entity.ender_guardian.roar");
    DeferredHolder<SoundEvent, SoundEvent> FIRE_GUARDIAN_FLAMETHROWER       = register("entity.fire_guardian.flamethrower");
    DeferredHolder<SoundEvent, SoundEvent> FIRE_GUARDIAN_NOVA               = register("entity.fire_guardian.nova");
    DeferredHolder<SoundEvent, SoundEvent> ICE_GUARDIAN_LAUNCH_ARM          = register("entity.ice_guardian.launch_arm");
    DeferredHolder<SoundEvent, SoundEvent> LIFE_GUARDIAN_HEAL               = register("entity.life_guardian.heal");
    DeferredHolder<SoundEvent, SoundEvent> LIGHTNING_GUARDIAN_LIGHTNING_ROD = register("entity.lightning_guardian.lightning_rod");
    DeferredHolder<SoundEvent, SoundEvent> LIGHTNING_GUARDIAN_STATIC        = register("entity.lightning_guardian.static");
    DeferredHolder<SoundEvent, SoundEvent> CAST_AIR                         = register("spell.cast.air");
    DeferredHolder<SoundEvent, SoundEvent> CAST_ARCANE                      = register("spell.cast.arcane");
    DeferredHolder<SoundEvent, SoundEvent> CAST_EARTH                       = register("spell.cast.earth");
    DeferredHolder<SoundEvent, SoundEvent> CAST_ENDER                       = register("spell.cast.ender");
    DeferredHolder<SoundEvent, SoundEvent> CAST_FIRE                        = register("spell.cast.fire");
    DeferredHolder<SoundEvent, SoundEvent> CAST_ICE                         = register("spell.cast.ice");
    DeferredHolder<SoundEvent, SoundEvent> CAST_LIFE                        = register("spell.cast.life");
    DeferredHolder<SoundEvent, SoundEvent> CAST_LIGHTNING                   = register("spell.cast.lightning");
    DeferredHolder<SoundEvent, SoundEvent> CAST_NATURE                      = register("spell.cast.nature");
    DeferredHolder<SoundEvent, SoundEvent> CAST_NONE                        = register("spell.cast.none");
    DeferredHolder<SoundEvent, SoundEvent> CAST_WATER                       = register("spell.cast.water");
    DeferredHolder<SoundEvent, SoundEvent> LOOP_AIR                         = register("spell.loop.air");
    DeferredHolder<SoundEvent, SoundEvent> LOOP_ARCANE                      = register("spell.loop.arcane");
    DeferredHolder<SoundEvent, SoundEvent> LOOP_EARTH                       = register("spell.loop.earth");
    DeferredHolder<SoundEvent, SoundEvent> LOOP_ENDER                       = register("spell.loop.ender");
    DeferredHolder<SoundEvent, SoundEvent> LOOP_FIRE                        = register("spell.loop.fire");
    DeferredHolder<SoundEvent, SoundEvent> LOOP_ICE                         = register("spell.loop.ice");
    DeferredHolder<SoundEvent, SoundEvent> LOOP_LIFE                        = register("spell.loop.life");
    DeferredHolder<SoundEvent, SoundEvent> LOOP_LIGHTNING                   = register("spell.loop.lightning");
    DeferredHolder<SoundEvent, SoundEvent> LOOP_NATURE                      = register("spell.loop.nature");
    DeferredHolder<SoundEvent, SoundEvent> LOOP_WATER                       = register("spell.loop.water");
    DeferredHolder<SoundEvent, SoundEvent> CONTINGENCY                      = register("contingency");
    DeferredHolder<SoundEvent, SoundEvent> FALLING_STAR                     = register("falling_star");
    DeferredHolder<SoundEvent, SoundEvent> INFINITY_ORB                     = register("infinity_orb");
    DeferredHolder<SoundEvent, SoundEvent> LEVEL_UP                         = register("level_up");
    DeferredHolder<SoundEvent, SoundEvent> MANA_SHIELD                      = register("mana_shield");
    DeferredHolder<SoundEvent, SoundEvent> RUNE                             = register("rune");
    DeferredHolder<SoundEvent, SoundEvent> SPELLCRAFTING_ADD_INGREDIENT     = register("spellcrafting_add_ingredient");
    DeferredHolder<SoundEvent, SoundEvent> SPELLCRAFTING_FINISH             = register("spellcrafting_finish");
    DeferredHolder<SoundEvent, SoundEvent> TAKE_BOOK                        = register("take_book");
    // @formatter:on

    private static DeferredHolder<SoundEvent, SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(ArsMagicaApi.id(id)));
    }
}
