package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.SOUND_EVENTS;

@NonExtendable
public interface AMSounds {
    RegistryObject<SoundEvent> ARCANE_GUARDIAN_AMBIENT          = register("entity.arcane_guardian.ambient");
    RegistryObject<SoundEvent> ARCANE_GUARDIAN_ATTACK           = register("entity.arcane_guardian.attack");
    RegistryObject<SoundEvent> ARCANE_GUARDIAN_DEATH            = register("entity.arcane_guardian.death");
    RegistryObject<SoundEvent> ARCANE_GUARDIAN_HURT             = register("entity.arcane_guardian.hurt");
    RegistryObject<SoundEvent> EARTH_GUARDIAN_AMBIENT           = register("entity.earth_guardian.ambient");
    RegistryObject<SoundEvent> EARTH_GUARDIAN_ATTACK            = register("entity.earth_guardian.attack");
    RegistryObject<SoundEvent> EARTH_GUARDIAN_DEATH             = register("entity.earth_guardian.death");
    RegistryObject<SoundEvent> EARTH_GUARDIAN_HURT              = register("entity.earth_guardian.hurt");
    RegistryObject<SoundEvent> ENDER_GUARDIAN_AMBIENT           = register("entity.ender_guardian.ambient");
    RegistryObject<SoundEvent> ENDER_GUARDIAN_ATTACK            = register("entity.ender_guardian.attack");
    RegistryObject<SoundEvent> ENDER_GUARDIAN_DEATH             = register("entity.ender_guardian.death");
    RegistryObject<SoundEvent> ENDER_GUARDIAN_HURT              = register("entity.ender_guardian.hurt");
    RegistryObject<SoundEvent> FIRE_GUARDIAN_AMBIENT            = register("entity.fire_guardian.ambient");
    RegistryObject<SoundEvent> FIRE_GUARDIAN_ATTACK             = register("entity.fire_guardian.attack");
    RegistryObject<SoundEvent> FIRE_GUARDIAN_DEATH              = register("entity.fire_guardian.death");
    RegistryObject<SoundEvent> FIRE_GUARDIAN_HURT               = register("entity.fire_guardian.hurt");
    RegistryObject<SoundEvent> ICE_GUARDIAN_AMBIENT             = register("entity.ice_guardian.ambient");
    RegistryObject<SoundEvent> ICE_GUARDIAN_DEATH               = register("entity.ice_guardian.death");
    RegistryObject<SoundEvent> LIFE_GUARDIAN_AMBIENT            = register("entity.life_guardian.ambient");
    RegistryObject<SoundEvent> LIFE_GUARDIAN_ATTACK             = register("entity.life_guardian.attack");
    RegistryObject<SoundEvent> LIFE_GUARDIAN_DEATH              = register("entity.life_guardian.death");
    RegistryObject<SoundEvent> LIFE_GUARDIAN_HURT               = register("entity.life_guardian.hurt");
    RegistryObject<SoundEvent> LIGHTNING_GUARDIAN_AMBIENT       = register("entity.lightning_guardian.ambient");
    RegistryObject<SoundEvent> LIGHTNING_GUARDIAN_ATTACK        = register("entity.lightning_guardian.attack");
    RegistryObject<SoundEvent> LIGHTNING_GUARDIAN_DEATH         = register("entity.lightning_guardian.death");
    RegistryObject<SoundEvent> LIGHTNING_GUARDIAN_HURT          = register("entity.lightning_guardian.hurt");
    RegistryObject<SoundEvent> NATURE_GUARDIAN_AMBIENT          = register("entity.nature_guardian.ambient");
    RegistryObject<SoundEvent> NATURE_GUARDIAN_ATTACK           = register("entity.nature_guardian.attack");
    RegistryObject<SoundEvent> NATURE_GUARDIAN_DEATH            = register("entity.nature_guardian.death");
    RegistryObject<SoundEvent> NATURE_GUARDIAN_HURT             = register("entity.nature_guardian.hurt");
    RegistryObject<SoundEvent> WATER_GUARDIAN_AMBIENT           = register("entity.water_guardian.ambient");
    RegistryObject<SoundEvent> WATER_GUARDIAN_DEATH             = register("entity.water_guardian.death");
    RegistryObject<SoundEvent> ENDER_GUARDIAN_FLAP              = register("entity.ender_guardian.flap");
    RegistryObject<SoundEvent> ENDER_GUARDIAN_ROAR              = register("entity.ender_guardian.roar");
    RegistryObject<SoundEvent> FIRE_GUARDIAN_FLAMETHROWER       = register("entity.fire_guardian.flamethrower");
    RegistryObject<SoundEvent> FIRE_GUARDIAN_NOVA               = register("entity.fire_guardian.nova");
    RegistryObject<SoundEvent> ICE_GUARDIAN_LAUNCH_ARM          = register("entity.ice_guardian.launch_arm");
    RegistryObject<SoundEvent> LIFE_GUARDIAN_HEAL               = register("entity.life_guardian.heal");
    RegistryObject<SoundEvent> LIGHTNING_GUARDIAN_LIGHTNING_ROD = register("entity.lightning_guardian.lightning_rod");
    RegistryObject<SoundEvent> LIGHTNING_GUARDIAN_STATIC        = register("entity.lightning_guardian.static");
    RegistryObject<SoundEvent> CAST_AIR                         = register("spell.cast.air");
    RegistryObject<SoundEvent> CAST_ARCANE                      = register("spell.cast.arcane");
    RegistryObject<SoundEvent> CAST_EARTH                       = register("spell.cast.earth");
    RegistryObject<SoundEvent> CAST_ENDER                       = register("spell.cast.ender");
    RegistryObject<SoundEvent> CAST_FIRE                        = register("spell.cast.fire");
    RegistryObject<SoundEvent> CAST_ICE                         = register("spell.cast.ice");
    RegistryObject<SoundEvent> CAST_LIFE                        = register("spell.cast.life");
    RegistryObject<SoundEvent> CAST_LIGHTNING                   = register("spell.cast.lightning");
    RegistryObject<SoundEvent> CAST_NATURE                      = register("spell.cast.nature");
    RegistryObject<SoundEvent> CAST_NONE                        = register("spell.cast.none");
    RegistryObject<SoundEvent> CAST_WATER                       = register("spell.cast.water");
    RegistryObject<SoundEvent> LOOP_AIR                         = register("spell.loop.air");
    RegistryObject<SoundEvent> LOOP_ARCANE                      = register("spell.loop.arcane");
    RegistryObject<SoundEvent> LOOP_EARTH                       = register("spell.loop.earth");
    RegistryObject<SoundEvent> LOOP_ENDER                       = register("spell.loop.ender");
    RegistryObject<SoundEvent> LOOP_FIRE                        = register("spell.loop.fire");
    RegistryObject<SoundEvent> LOOP_ICE                         = register("spell.loop.ice");
    RegistryObject<SoundEvent> LOOP_LIFE                        = register("spell.loop.life");
    RegistryObject<SoundEvent> LOOP_LIGHTNING                   = register("spell.loop.lightning");
    RegistryObject<SoundEvent> LOOP_NATURE                      = register("spell.loop.nature");
    RegistryObject<SoundEvent> LOOP_WATER                       = register("spell.loop.water");
    RegistryObject<SoundEvent> CONTINGENCY                      = register("spell.contingency");
    RegistryObject<SoundEvent> MANA_SHIELD                      = register("spell.mana_shield");
    RegistryObject<SoundEvent> RUNE                             = register("spell.rune");
    RegistryObject<SoundEvent> STARSTRIKE                       = register("spell.starstrike");
    RegistryObject<SoundEvent> CRAFTING_ALTAR_ADD_INGREDIENT    = register("block.crafting_altar.add_ingredient");
    RegistryObject<SoundEvent> CRAFTING_ALTAR_FINISH            = register("block.crafting_altar.finish");
    RegistryObject<SoundEvent> INSCRIPTION_TABLE_TAKE_BOOK      = register("block.inscription_table.take_book");
    RegistryObject<SoundEvent> GET_KNOWLEDGE_POINT              = register("misc.get_knowledge_point");
    RegistryObject<SoundEvent> MAGIC_LEVEL_UP                   = register("misc.magic_level_up");

    private static RegistryObject<SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id, () -> new SoundEvent(new ResourceLocation(ArsMagicaAPI.MOD_ID, id)));
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
