package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ExecuteRandomSpellGoal<T extends AbstractBoss> extends ExecuteBossSpellGoal<T> {
    private final List<Spell> spells;

    public ExecuteRandomSpellGoal(T caster, List<Spell> spells, int duration) {
        super(caster, null, duration);
        this.spells = spells;
    }

    @Override
    @Nullable
    protected Spell getSpell(T caster) {
        return spells.get(caster.level().getRandom().nextInt(spells.size()));
    }
}
