package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AbstractRegistryDataProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageSources;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;

public class AMDamageTypeProvider extends AbstractRegistryDataProvider<DamageType> {
    public AMDamageTypeProvider() {
        super(Registries.DAMAGE_TYPE, ArsMagicaAPI.MOD_ID);
    }

    @Override
    public void generate() {
        register(AMDamageSources.SPELL_DROWNING, 0.0f, "drown", DamageEffects.DROWNING);
        register(AMDamageSources.SPELL_FIRE, 0.1f, "inFire", DamageEffects.BURNING);
        register(AMDamageSources.SPELL_FROST, 0.1f, "freeze", DamageEffects.FREEZING);
        register(AMDamageSources.SPELL_LIGHTNING, 0.1f, "lightningBolt");
        register(AMDamageSources.SPELL_MAGIC, 0.0f, "magic");
        register(AMDamageSources.SPELL_PHYSICAL, 0.1f, "mob");
        register(AMDamageSources.SPELL_PHYSICAL_PLAYER, 0.1f, "player");
        register(AMDamageSources.NATURE_SCYTHE, 0.1f);
        register(AMDamageSources.SHOCKWAVE, 0.1f);
        register(AMDamageSources.THROWN_ROCK, 0.1f);
        register(AMDamageSources.WIND, 0.0f);
        register(AMDamageSources.FALLING_STAR, 0.1f);
    }


    protected void register(ResourceKey<DamageType> key, float exhaustion, String messageId, DamageEffects effects) {
        super.add(key.location(), new DamageType(messageId, exhaustion, effects));
    }

    protected void register(ResourceKey<DamageType> key, float exhaustion, String messageId) {
        super.add(key.location(), new DamageType(messageId, exhaustion));
    }

    protected void register(ResourceKey<DamageType> key, float exhaustion) {
        super.add(key.location(), new DamageType(key.location().getPath(), exhaustion));
    }
}
