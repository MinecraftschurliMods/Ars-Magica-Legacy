package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.spellcustomization;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ArsMagicaClientApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.screen.SpellPartCustomizationScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class SpellPartButton<T> extends Button {
    public static final int SIZE = 16;
    private final Holder<SpellPart> spellPart;
    private final TextureAtlasSprite sprite;
    @Nullable
    private Function<DataComponentType<T>, @Nullable T> valueGetter;
    @Nullable
    private BiConsumer<DataComponentType<T>, @Nullable T> valueSetter;

    private SpellPartButton(int x, int y, Holder<SpellPart> spellPart, TextureAtlasSprite sprite) {
        super(x, y, SIZE, SIZE, Component.empty(), _ -> {}, DEFAULT_NARRATION);
        this.spellPart = spellPart;
        this.sprite = sprite;
    }

    @SuppressWarnings("DataFlowIssue")
    public static <T> SpellPartButton<T> create(int x, int y, Holder<SpellPart> spellPart, SpellCustomizationScreen screen, int index) {
        Holder<Skill> skill = AMUtil.skill(spellPart, true);
        SpellPartButton<T> button = new SpellPartButton<>(x, y, spellPart, SkillAtlasHolder.getSprite(skill.value()));
        if (spellPart.value().getDataComponentType() != null) {
            button.valueGetter = type -> (index == -1 ? screen.getSpell().dataComponents().grammar() : screen.getSpell().dataComponents().shapeGroups().get(index)).get(type);
            button.valueSetter = (type, value) -> screen.setSpell(screen.getSpell().updateDataComponents(components -> components.update(index, map -> {
                if (value == null) {
                    map.remove(type);
                } else {
                    map.set(type, value);
                }
            })));
        }
        button.active = ArsMagicaClientApi.spellPartCustomizationScreen(spellPart) != null;
        Component name = Skill.getName(skill);
        button.setTooltip(Tooltip.create(button.active ? Component.translatable(AMTranslations.SPELL_CUSTOMIZATION_ACTIVE_KEY, name) : name));
        return button;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        AMClientUtil.blit(graphics, sprite, getX(), getY(), SIZE, SIZE, active ? -1 : 0xff7f7f7f);
    }

    @SuppressWarnings({"unchecked", "DataFlowIssue"})
    @Override
    public void onPress(InputWithModifiers input) {
        if (!active) return;
        SpellPartCustomizationScreen.Factory<T, ?> factory = (SpellPartCustomizationScreen.Factory<T, ?>) ArsMagicaClientApi.spellPartCustomizationScreen(spellPart);
        if (valueGetter != null && valueSetter != null) {
            AMClientUtil.mc().pushGuiLayer(factory.create(valueGetter, valueSetter));
        }
    }
}
