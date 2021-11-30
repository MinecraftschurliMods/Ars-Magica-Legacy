package com.github.minecraftschurli.arsmagicalegacy.common.util;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface TranslationConstants {
    String SKILL_COMMAND_FORGET_ALL_SUCCESS     = "commands." + ArsMagicaAPI.MOD_ID + ".skill.forget_all.success";
    String SKILL_COMMAND_LEARN_ALL_SUCCESS      = "commands." + ArsMagicaAPI.MOD_ID + ".skill.learn_all.success";
    String SKILL_COMMAND_FORGET_SUCCESS         = "commands." + ArsMagicaAPI.MOD_ID + ".skill.forget.success";
    String SKILL_COMMAND_LEARN_SUCCESS          = "commands." + ArsMagicaAPI.MOD_ID + ".skill.learn.success";
    String SKILL_COMMAND_NOT_KNOWN_SKILL        = "commands." + ArsMagicaAPI.MOD_ID + ".skill.skill_unknown";
    String SKILL_COMMAND_ALREADY_KNOWN          = "commands." + ArsMagicaAPI.MOD_ID + ".skill.skill_already_known";
    String SKILL_COMMAND_UNKNOWN_SKILL          = "commands." + ArsMagicaAPI.MOD_ID + ".skill.skill_not_found";
    String SKILL_COMMAND_EMPTY                  = "commands." + ArsMagicaAPI.MOD_ID + ".skill.empty";
    String OCCULUS_MISSING_REQUIREMENTS         = "message."  + ArsMagicaAPI.MOD_ID + ".occulus.missingRequirements";
    String HOLD_SHIFT_FOR_DETAILS               = "message."  + ArsMagicaAPI.MOD_ID + ".hold_shift_for_details";
    String SPELL_CAST_RESULT                    = "message."  + ArsMagicaAPI.MOD_ID + ".spell_cast.";
    String MAGIC_UNKNOWN_MESSAGE                = "message."  + ArsMagicaAPI.MOD_ID + ".prevent";
    String LOW_POWER                            = "message."  + ArsMagicaAPI.MOD_ID + ".altar.low_power";
    String CONFIG_PREFIX                        = "config."   + ArsMagicaAPI.MOD_ID + ".";
    String INSCRIPTION_TABLE_DEFAULT_NAME_VALUE = "screen."   + ArsMagicaAPI.MOD_ID + ".inscription_table.default_name";
    String INSCRIPTION_TABLE_SEARCH_BAR_LABEL   = "screen."   + ArsMagicaAPI.MOD_ID + ".inscription_table.search";
    String INSCRIPTION_TABLE_CONTAINER_TITLE    = "screen."   + ArsMagicaAPI.MOD_ID + ".inscription_table.title";
    String INSCRIPTION_TABLE_NAME_LABEL         = "screen."   + ArsMagicaAPI.MOD_ID + ".inscription_table.name";
    String NAME_FIELD_MESSAGE                   = "screen."   + ArsMagicaAPI.MOD_ID + ".spell_customization_screen.name_box";
    String UNNAMED_SPELL                        = "item."     + ArsMagicaAPI.MOD_ID + ".spell.unnamed";
    String UNKNOWN_ITEM                         = "item."     + ArsMagicaAPI.MOD_ID + ".spell.unknown";
    String UNKNOWN_ITEM_DESC                    = "item."     + ArsMagicaAPI.MOD_ID + ".spell.unknown.description";
    String INVALID_SPELL                        = "item."     + ArsMagicaAPI.MOD_ID + ".spell.invalid";
    String INVALID_SPELL_DESC                   = "item."     + ArsMagicaAPI.MOD_ID + ".spell.invalid.description";
    String MANA_COST_TOOLTIP                    = "item."     + ArsMagicaAPI.MOD_ID + ".spell.mana_cost";
    String BURNOUT_TOOLTIP                      = "item."     + ArsMagicaAPI.MOD_ID + ".spell.burnout";
    String REAGENTS_TOOLTIP                     = "item."     + ArsMagicaAPI.MOD_ID + ".spell.reagents";
}
