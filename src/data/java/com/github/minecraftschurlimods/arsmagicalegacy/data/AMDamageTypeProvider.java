package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AbstractRegistryDataProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.DatapackRegistryProvider;
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
        register(AMDamageSources.NATURE_SCYTHE, 0.1F);
        register(AMDamageSources.SHOCKWAVE, 0.1F);
        register(AMDamageSources.THROWN_ROCK, 0.1F);
        register(AMDamageSources.WIND, 0.0F);
        register(AMDamageSources.FALLING_STAR, 0.1F);
    }

    protected void register(ResourceKey<DamageType> key, DamageScaling scaling, float exhaustion, DamageEffects effects, DeathMessageType deathMessageType) {
        super.add(key.location(), new DamageType(key.location().getPath(), scaling, exhaustion, effects, deathMessageType));
    }

    protected void register(ResourceKey<DamageType> key, DamageScaling scaling, float exhaustion) {
        super.add(key.location(), new DamageType(key.location().getPath(), scaling, exhaustion));
    }

    protected void register(ResourceKey<DamageType> key, DamageScaling scaling, float exhaustion, DamageEffects effects) {
        super.add(key.location(), new DamageType(key.location().getPath(), scaling, exhaustion, effects));
    }

    protected void register(ResourceKey<DamageType> key, float exhaustion, DamageEffects effects) {
        super.add(key.location(), new DamageType(key.location().getPath(), exhaustion, effects));
    }

    protected void register(ResourceKey<DamageType> key, float exhaustion) {
        super.add(key.location(), new DamageType(key.location().getPath(), exhaustion));
    }
}
