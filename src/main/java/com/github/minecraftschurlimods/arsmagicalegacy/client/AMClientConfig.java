package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.client.layer.LayerAnchor;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AMClientConfig {
    public static final ModConfigSpec.IntValue CRYSTAL_PHYLACTERY_MODEL_QUALITY;
    public static final ModConfigSpec.IntValue BARS_X;
    public static final ModConfigSpec.IntValue BARS_Y;
    public static final ModConfigSpec.EnumValue<LayerAnchor.X> BARS_X_ANCHOR;
    public static final ModConfigSpec.EnumValue<LayerAnchor.Y> BARS_Y_ANCHOR;
    public static final ModConfigSpec.BooleanValue RENDER_LEVEL_AT_TOP;
    public static final ModConfigSpec.BooleanValue SHOW_VALUES;
    public static final ModConfigSpec.IntValue SHAPE_GROUPS_X;
    public static final ModConfigSpec.IntValue SHAPE_GROUPS_Y;
    public static final ModConfigSpec.EnumValue<LayerAnchor.X> SHAPE_GROUPS_X_ANCHOR;
    public static final ModConfigSpec.EnumValue<LayerAnchor.Y> SHAPE_GROUPS_Y_ANCHOR;
    public static final ModConfigSpec.IntValue SPELL_BOOK_X;
    public static final ModConfigSpec.IntValue SPELL_BOOK_Y;
    public static final ModConfigSpec.EnumValue<LayerAnchor.X> SPELL_BOOK_X_ANCHOR;
    public static final ModConfigSpec.EnumValue<LayerAnchor.Y> SPELL_BOOK_Y_ANCHOR;
    static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        CRYSTAL_PHYLACTERY_MODEL_QUALITY = builder
            .comment("The 'quality' value of the algorithm that calculates the crystal phylactery colors. Lower value means more accurate results and more calculation cost, however lower values have diminishing returns.")
            .translation(AMTranslations.CONFIG_KEY + "crystal_phylactery_model_quality")
            .defineInRange("crystal_phylactery_model_quality", 5, 1, 10);
        builder.comment("Configuration for the various GUI layers in this mod.").push("gui_layers");
        builder.comment("Configuration for the mana, burnout and level bars. The size of the layer is 80x40.").push("bars");
        BARS_X = builder
            .comment("Horizontal position of the mana, burnout and level bars.")
            .translation(AMTranslations.CONFIG_KEY + "bars_x")
            .defineInRange("x", 6, Short.MIN_VALUE, Short.MAX_VALUE);
        BARS_Y = builder
            .comment("Vertical position of the mana, burnout and level bars.")
            .translation(AMTranslations.CONFIG_KEY + "bars_y")
            .defineInRange("y", -44, Short.MIN_VALUE, Short.MAX_VALUE);
        BARS_X_ANCHOR = builder
            .comment("Horizontal anchor of the mana, burnout and level bars.")
            .translation(AMTranslations.CONFIG_KEY + "bars_anchor_x")
            .defineEnum("anchor_x", LayerAnchor.X.LEFT);
        BARS_Y_ANCHOR = builder
            .comment("Vertical anchor of the mana, burnout and level bars.")
            .translation(AMTranslations.CONFIG_KEY + "bars_anchor_y")
            .defineEnum("anchor_y", LayerAnchor.Y.BOTTOM);
        RENDER_LEVEL_AT_TOP = builder
            .comment("If true, renders the bars in order level number -> level bar -> mana bar -> burnout bar.")
            .comment("If false, renders the bars in order mana bar -> burnout bar -> level bar -> level number.")
            .translation(AMTranslations.CONFIG_KEY + "render_level_at_top")
            .define("render_level_at_top", true);
        SHOW_VALUES = builder
            .comment("Whether to show the exact values for mana, burnout and xp.")
            .translation(AMTranslations.CONFIG_KEY + "show_values")
            .define("show_values", false);
        builder.pop();
        builder.comment("Configuration for the shape groups GUI layer. The size of the layer is 180x36.").push("shape_groups");
        SHAPE_GROUPS_X = builder
            .comment("Horizontal position of the shape groups GUI layer.")
            .translation(AMTranslations.CONFIG_KEY + "shape_groups_x")
            .defineInRange("x", 2, Short.MIN_VALUE, Short.MAX_VALUE);
        SHAPE_GROUPS_Y = builder
            .comment("Vertical position of the shape groups GUI layer.")
            .translation(AMTranslations.CONFIG_KEY + "shape_groups_y")
            .defineInRange("y", 2, Short.MIN_VALUE, Short.MAX_VALUE);
        SHAPE_GROUPS_X_ANCHOR = builder
            .comment("Horizontal anchor of the shape groups GUI layer.")
            .translation(AMTranslations.CONFIG_KEY + "shape_groups_anchor_x")
            .defineEnum("anchor_x", LayerAnchor.X.LEFT);
        SHAPE_GROUPS_Y_ANCHOR = builder
            .comment("Vertical anchor of the shape groups GUI layer.")
            .translation(AMTranslations.CONFIG_KEY + "shape_groups_anchor_y")
            .defineEnum("anchor_y", LayerAnchor.Y.TOP);
        builder.pop();
        builder.comment("Configuration for the shape groups GUI layer. The size of the layer is 111x17.").push("spell_book");
        SPELL_BOOK_X = builder
            .comment("Horizontal position of the shape groups GUI layer.")
            .translation(AMTranslations.CONFIG_KEY + "spell_book_x")
            .defineInRange("x", 94, Short.MIN_VALUE, Short.MAX_VALUE);
        SPELL_BOOK_Y = builder
            .comment("Vertical position of the shape groups GUI layer.")
            .translation(AMTranslations.CONFIG_KEY + "spell_book_y")
            .defineInRange("y", -20, Short.MIN_VALUE, Short.MAX_VALUE);
        SPELL_BOOK_X_ANCHOR = builder
            .comment("Horizontal anchor of the shape groups GUI layer.")
            .translation(AMTranslations.CONFIG_KEY + "spell_book_anchor_x")
            .defineEnum("anchor_x", LayerAnchor.X.CENTER);
        SPELL_BOOK_Y_ANCHOR = builder
            .comment("Vertical anchor of the shape groups GUI layer.")
            .translation(AMTranslations.CONFIG_KEY + "spell_book_anchor_y")
            .defineEnum("anchor_y", LayerAnchor.Y.BOTTOM);
        builder.pop();
        SPEC = builder.build();
    }
}
