package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.advancement.PlayerLearnedSkillTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.advancement.PlayerLevelUpTrigger;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.TRIGGER_TYPE;

@NonExtendable
public interface AMCriteriaTriggers {
    Supplier<PlayerLevelUpTrigger>      PLAYER_LEVEL_UP      = TRIGGER_TYPE.register("player_level_up", PlayerLevelUpTrigger::new);
    Supplier<PlayerLearnedSkillTrigger> PLAYER_LEARNED_SKILL = TRIGGER_TYPE.register("player_learned_skill", PlayerLearnedSkillTrigger::new);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
