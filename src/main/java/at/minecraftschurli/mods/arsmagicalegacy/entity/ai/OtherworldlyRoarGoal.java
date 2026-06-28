package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.entity.EnderGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

public class OtherworldlyRoarGoal extends ExecuteBossSpellGoal<EnderGuardian> {
    private static final ResourceKey<Spell> SPELL = ResourceKey.create(AMRegistries.Keys.SPELL_PREFAB, ArsMagicaApi.id("otherworldly_roar"));

    public OtherworldlyRoarGoal(EnderGuardian caster) {
        super(caster, caster.registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB).getValueOrThrow(SPELL), 0);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ENDER_GUARDIAN_ROAR.value();
    }
}
