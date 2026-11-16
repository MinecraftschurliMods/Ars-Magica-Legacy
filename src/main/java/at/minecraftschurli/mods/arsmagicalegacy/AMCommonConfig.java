package at.minecraftschurli.mods.arsmagicalegacy;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AMCommonConfig {
    public static final ModConfigSpec.IntValue WITCHWOOD_FOREST_WEIGHT;
    static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        WITCHWOOD_FOREST_WEIGHT = builder
            .comment("The spawn weight of the Witchwood Forest biome. Higher value means more common.")
            .translation(AMTranslations.CONFIG_KEY + "witchwood_forest_weight")
            .defineInRange("witchwood_forest_weight", 2, 1, Short.MAX_VALUE);
        SPEC = builder.build();
    }
}
