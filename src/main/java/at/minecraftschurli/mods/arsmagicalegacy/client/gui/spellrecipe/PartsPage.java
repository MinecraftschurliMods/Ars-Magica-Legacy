package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

import java.util.List;

class PartsPage extends Page<Holder<SpellPart>> {
    private final Component title;

    public PartsPage(List<SpellPart> spellParts, Component title) {
        super(3, 11, 32, 4, 3, spellParts.stream()
            .map(AMRegistries.SPELL_PARTS::wrapAsHolder)
            .toList());
        this.title = title;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public void extractElement(Holder<SpellPart> element, int index, GuiGraphicsExtractor graphics, int x, int y) {
        AMClientUtil.blit(graphics, SkillAtlasHolder.getSprite(skill(element).value()), x + index % maxPerLine * (size + spacing), y + index / maxPerLine * (size + spacing), size, size);
    }

    @Override
    public List<Component> getElementTooltip(Holder<SpellPart> element) {
        return List.of(Skill.getName(skill(element)));
    }

    @SuppressWarnings({"DataFlowIssue", "OptionalGetWithoutIsPresent"})
    private static Holder<Skill> skill(Holder<SpellPart> spellPart) {
        return AMRegistries.skills(true).get(spellPart.getKey().identifier()).get();
    }
}
