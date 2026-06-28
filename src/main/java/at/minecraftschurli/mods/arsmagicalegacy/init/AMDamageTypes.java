package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public interface AMDamageTypes {
    // @formatter:off
    ResourceKey<DamageType> SPELL_DROWNING        = damageType("spell_drowning");
    ResourceKey<DamageType> SPELL_FIRE            = damageType("spell_fire");
    ResourceKey<DamageType> SPELL_FROST           = damageType("spell_frost");
    ResourceKey<DamageType> SPELL_LIGHTNING       = damageType("spell_lightning");
    ResourceKey<DamageType> SPELL_MAGIC           = damageType("spell_magic");
    ResourceKey<DamageType> SPELL_PHYSICAL        = damageType("spell_physical");
    ResourceKey<DamageType> SPELL_PHYSICAL_PLAYER = damageType("spell_physical_player");
    ResourceKey<DamageType> FALLING_STAR          = damageType("falling_star");
    ResourceKey<DamageType> NATURE_SCYTHE         = damageType("nature_scythe");
    ResourceKey<DamageType> SHOCKWAVE             = damageType("shockwave");
    ResourceKey<DamageType> THROWN_ROCK           = damageType("thrown_rock");
    ResourceKey<DamageType> WHIRLWIND             = damageType("whirlwind");
    // @formatter:on

    private static ResourceKey<DamageType> damageType(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ArsMagicaApi.id(name));
    }
}
