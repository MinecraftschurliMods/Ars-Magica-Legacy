package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import org.jspecify.annotations.Nullable;

public class HealGoal<T extends AbstractBoss> extends ExecuteBossSpellGoal<T> {
    private static final ResourceKey<Spell> SPELL = ResourceKey.create(AMRegistries.Keys.SPELL_PREFAB, ArsMagicaApi.id("heal_self"));

    public HealGoal(T caster) {
        super(caster, caster.registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB).getValueOrThrow(SPELL), 0);
    }

    @Override
    public boolean canUse() {
        return caster.getHealth() != caster.getMaxHealth() && super.canUse();
    }

    @Override
    @Nullable
    protected SoundEvent getAttackSound() {
        return AMSounds.LIFE_GUARDIAN_HEAL.value();
    }
}
