package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredientType;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.EtheriumSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.IngredientSpellIngredient;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.SPELL_INGREDIENT_TYPES;

@NonExtendable
public interface AMSpellIngredientTypes {
    RegistryObject<SpellIngredientType<IngredientSpellIngredient>> INGREDIENT = SPELL_INGREDIENT_TYPES.register("ingredient", () -> new SpellIngredientType<>(IngredientSpellIngredient.CODEC, IngredientSpellIngredient.NETWORK_CODEC, IngredientSpellIngredient.IngredientSpellIngredientRenderer::new));
    RegistryObject<SpellIngredientType<EtheriumSpellIngredient>>   ETHERIUM   = SPELL_INGREDIENT_TYPES.register("etherium", () -> new SpellIngredientType<>(EtheriumSpellIngredient.CODEC, EtheriumSpellIngredient.EtheriumSpellIngredientRenderer::new));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
