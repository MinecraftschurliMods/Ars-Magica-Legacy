package at.minecraftschurli.mods.arsmagicalegacy.compat.jei;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

final class SkillIngredientHelper implements IIngredientHelper<Skill> {
    @Override
    public IIngredientType<Skill> getIngredientType() {
        return AMJeiPlugin.SKILL_TYPE;
    }

    @Override
    public String getDisplayName(Skill skill) {
        return Skill.getName(AMRegistries.skills(true).wrapAsHolder(skill)).getString();
    }

    @Override
    public Object getUid(Skill skill, UidContext context) {
        return getIdentifier(skill);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Identifier getIdentifier(Skill skill) {
        return AMRegistries.skills(true).getKey(skill);
    }

    @Override
    public Skill copyIngredient(Skill skill) {
        return skill;
    }

    @Override
    public String getErrorInfo(@Nullable Skill skill) {
        return skill == null ? "Unknown skill" : skill.toString();
    }

    @Override
    public boolean isValidIngredient(Skill skill) {
        return AMRegistries.skills(true).getKey(skill) != null;
    }
}
