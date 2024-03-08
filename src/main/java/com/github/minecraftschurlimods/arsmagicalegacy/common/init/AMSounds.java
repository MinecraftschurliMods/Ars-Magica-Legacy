package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.SOUND_EVENTS;

@NonExtendable
public interface AMSounds {
    Holder<SoundEvent> ARCANE_GUARDIAN_AMBIENT          = register("entity.arcane_guardian.ambient");
    Holder<SoundEvent> ARCANE_GUARDIAN_ATTACK           = register("entity.arcane_guardian.attack");
    Holder<SoundEvent> ARCANE_GUARDIAN_DEATH            = register("entity.arcane_guardian.death");
    Holder<SoundEvent> ARCANE_GUARDIAN_HURT             = register("entity.arcane_guardian.hurt");
    Holder<SoundEvent> EARTH_GUARDIAN_AMBIENT           = register("entity.earth_guardian.ambient");
    Holder<SoundEvent> EARTH_GUARDIAN_ATTACK            = register("entity.earth_guardian.attack");
    Holder<SoundEvent> EARTH_GUARDIAN_DEATH             = register("entity.earth_guardian.death");
    Holder<SoundEvent> EARTH_GUARDIAN_HURT              = register("entity.earth_guardian.hurt");
    Holder<SoundEvent> ENDER_GUARDIAN_AMBIENT           = register("entity.ender_guardian.ambient");
    Holder<SoundEvent> ENDER_GUARDIAN_ATTACK            = register("entity.ender_guardian.attack");
    Holder<SoundEvent> ENDER_GUARDIAN_DEATH             = register("entity.ender_guardian.death");
    Holder<SoundEvent> ENDER_GUARDIAN_HURT              = register("entity.ender_guardian.hurt");
    Holder<SoundEvent> FIRE_GUARDIAN_AMBIENT            = register("entity.fire_guardian.ambient");
    Holder<SoundEvent> FIRE_GUARDIAN_ATTACK             = register("entity.fire_guardian.attack");
    Holder<SoundEvent> FIRE_GUARDIAN_DEATH              = register("entity.fire_guardian.death");
    Holder<SoundEvent> FIRE_GUARDIAN_HURT               = register("entity.fire_guardian.hurt");
    Holder<SoundEvent> ICE_GUARDIAN_AMBIENT             = register("entity.ice_guardian.ambient");
    Holder<SoundEvent> ICE_GUARDIAN_DEATH               = register("entity.ice_guardian.death");
    Holder<SoundEvent> LIFE_GUARDIAN_AMBIENT            = register("entity.life_guardian.ambient");
    Holder<SoundEvent> LIFE_GUARDIAN_ATTACK             = register("entity.life_guardian.attack");
    Holder<SoundEvent> LIFE_GUARDIAN_DEATH              = register("entity.life_guardian.death");
    Holder<SoundEvent> LIFE_GUARDIAN_HURT               = register("entity.life_guardian.hurt");
    Holder<SoundEvent> LIGHTNING_GUARDIAN_AMBIENT       = register("entity.lightning_guardian.ambient");
    Holder<SoundEvent> LIGHTNING_GUARDIAN_ATTACK        = register("entity.lightning_guardian.attack");
    Holder<SoundEvent> LIGHTNING_GUARDIAN_DEATH         = register("entity.lightning_guardian.death");
    Holder<SoundEvent> LIGHTNING_GUARDIAN_HURT          = register("entity.lightning_guardian.hurt");
    Holder<SoundEvent> NATURE_GUARDIAN_AMBIENT          = register("entity.nature_guardian.ambient");
    Holder<SoundEvent> NATURE_GUARDIAN_ATTACK           = register("entity.nature_guardian.attack");
    Holder<SoundEvent> NATURE_GUARDIAN_DEATH            = register("entity.nature_guardian.death");
    Holder<SoundEvent> NATURE_GUARDIAN_HURT             = register("entity.nature_guardian.hurt");
    Holder<SoundEvent> WATER_GUARDIAN_AMBIENT           = register("entity.water_guardian.ambient");
    Holder<SoundEvent> WATER_GUARDIAN_DEATH             = register("entity.water_guardian.death");
    Holder<SoundEvent> ENDER_GUARDIAN_FLAP              = register("entity.ender_guardian.flap");
    Holder<SoundEvent> ENDER_GUARDIAN_ROAR              = register("entity.ender_guardian.roar");
    Holder<SoundEvent> FIRE_GUARDIAN_FLAMETHROWER       = register("entity.fire_guardian.flamethrower");
    Holder<SoundEvent> FIRE_GUARDIAN_NOVA               = register("entity.fire_guardian.nova");
    Holder<SoundEvent> ICE_GUARDIAN_LAUNCH_ARM          = register("entity.ice_guardian.launch_arm");
    Holder<SoundEvent> LIFE_GUARDIAN_HEAL               = register("entity.life_guardian.heal");
    Holder<SoundEvent> LIGHTNING_GUARDIAN_LIGHTNING_ROD = register("entity.lightning_guardian.lightning_rod");
    Holder<SoundEvent> LIGHTNING_GUARDIAN_STATIC        = register("entity.lightning_guardian.static");
    Holder<SoundEvent> CAST_AIR                         = register("spell.cast.air");
    Holder<SoundEvent> CAST_ARCANE                      = register("spell.cast.arcane");
    Holder<SoundEvent> CAST_EARTH                       = register("spell.cast.earth");
    Holder<SoundEvent> CAST_ENDER                       = register("spell.cast.ender");
    Holder<SoundEvent> CAST_FIRE                        = register("spell.cast.fire");
    Holder<SoundEvent> CAST_ICE                         = register("spell.cast.ice");
    Holder<SoundEvent> CAST_LIFE                        = register("spell.cast.life");
    Holder<SoundEvent> CAST_LIGHTNING                   = register("spell.cast.lightning");
    Holder<SoundEvent> CAST_NATURE                      = register("spell.cast.nature");
    Holder<SoundEvent> CAST_NONE                        = register("spell.cast.none");
    Holder<SoundEvent> CAST_WATER                       = register("spell.cast.water");
    Holder<SoundEvent> LOOP_AIR                         = register("spell.loop.air");
    Holder<SoundEvent> LOOP_ARCANE                      = register("spell.loop.arcane");
    Holder<SoundEvent> LOOP_EARTH                       = register("spell.loop.earth");
    Holder<SoundEvent> LOOP_ENDER                       = register("spell.loop.ender");
    Holder<SoundEvent> LOOP_FIRE                        = register("spell.loop.fire");
    Holder<SoundEvent> LOOP_ICE                         = register("spell.loop.ice");
    Holder<SoundEvent> LOOP_LIFE                        = register("spell.loop.life");
    Holder<SoundEvent> LOOP_LIGHTNING                   = register("spell.loop.lightning");
    Holder<SoundEvent> LOOP_NATURE                      = register("spell.loop.nature");
    Holder<SoundEvent> LOOP_WATER                       = register("spell.loop.water");
    Holder<SoundEvent> CONTINGENCY                      = register("spell.contingency");
    Holder<SoundEvent> MANA_SHIELD                      = register("spell.mana_shield");
    Holder<SoundEvent> RUNE                             = register("spell.rune");
    Holder<SoundEvent> STARSTRIKE                       = register("spell.starstrike");
    Holder<SoundEvent> CRAFTING_ALTAR_ADD_INGREDIENT    = register("block.crafting_altar.add_ingredient");
    Holder<SoundEvent> CRAFTING_ALTAR_FINISH            = register("block.crafting_altar.finish");
    Holder<SoundEvent> INSCRIPTION_TABLE_TAKE_BOOK      = register("block.inscription_table.take_book");
    Holder<SoundEvent> GET_KNOWLEDGE_POINT              = register("misc.get_knowledge_point");
    Holder<SoundEvent> MAGIC_LEVEL_UP                   = register("misc.magic_level_up");

    private static Holder<SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ArsMagicaAPI.MOD_ID, id)));
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
