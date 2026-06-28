package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.screen.AbstractSpellPartCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.mods.arsmagicalegacy.util.GlobalVec3;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RecallCustomizationScreen extends AbstractSpellPartCustomizationScreen<GlobalVec3> {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 80;
    private static final int MESSAGE_TIME = 100;
    private static final int MESSAGE_ALPHA_START = 10;
    private final TextureAtlasSprite sprite;
    private int leftPos;
    private int topPos;
    @Nullable
    private Button clearButton;
    @Nullable
    private GlobalVec3 oldValue;
    @Nullable
    private Component message;
    private long messageTime;

    public RecallCustomizationScreen(Function<DataComponentType<GlobalVec3>, @Nullable GlobalVec3> valueGetter, BiConsumer<DataComponentType<GlobalVec3>, @Nullable GlobalVec3> valueSetter) {
        super(AMTranslations.SPELL_CUSTOMIZATION_RECALL, AMDataComponents.SPELL_RECALL_POSITION.get(), valueGetter, valueSetter);
        sprite = SkillAtlasHolder.getSprite(AMSpells.RECALL.getId());
    }

    @Override
    protected void init() {
        leftPos = (width - WIDTH) / 2;
        topPos = (height - HEIGHT) / 2;
        addRenderableWidget(Button.builder(AMTranslations.SPELL_CUSTOMIZATION_RECALL_SET, _ -> {
            LocalPlayer player = Objects.requireNonNull(AMClientUtil.player());
            value = new GlobalVec3(player.level().dimension(), player.position());
            if (clearButton != null) {
                clearButton.active = true;
            }
            message = AMTranslations.SPELL_CUSTOMIZATION_RECALL_SET_SUCCESS;
            messageTime = MESSAGE_TIME;
        }).bounds(leftPos, topPos + 36, 98, 20).build());
        clearButton = addRenderableWidget(Button.builder(AMTranslations.SPELL_CUSTOMIZATION_RECALL_CLEAR, button -> {
            if (oldValue == null) {
                oldValue = value;
                value = null;
                button.setMessage(AMTranslations.SPELL_CUSTOMIZATION_RECALL_RESTORE);
                message = AMTranslations.SPELL_CUSTOMIZATION_RECALL_CLEAR_SUCCESS;
            } else {
                value = oldValue;
                oldValue = null;
                button.setMessage(AMTranslations.SPELL_CUSTOMIZATION_RECALL_CLEAR);
                message = AMTranslations.SPELL_CUSTOMIZATION_RECALL_RESTORE_SUCCESS;
            }
            messageTime = MESSAGE_TIME;
        }).bounds(leftPos + 102, topPos + 36, 98, 20).build());
        clearButton.active = value != null;
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, _ -> onClose()).bounds(leftPos, topPos + 60, 200, 20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        AMClientUtil.blit(graphics, sprite, leftPos + 84, topPos, 32, 32, 0xff7f7f7f);
        if (messageTime > 0 && message != null) {
            int alpha = messageTime > MESSAGE_ALPHA_START ? 255 : (int) Mth.lerp((messageTime - partialTick) / MESSAGE_ALPHA_START, 0, 255);
            graphics.centeredText(font, message, leftPos + 100, topPos + 11, alpha << 24 | 0xffffff);
        }
    }

    @Override
    public void tick() {
        if (messageTime >= 0) {
            messageTime--;
        }
    }
}
