package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.advancement.PlayerLearnedSkillTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.advancement.PlayerLevelUpTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMCriteriaTriggers {
    PlayerLevelUpTrigger PLAYER_LEVEL_UP = CriteriaTriggers.register(new PlayerLevelUpTrigger());
    PlayerLearnedSkillTrigger PLAYER_LEARNED_SKILL = CriteriaTriggers.register(new PlayerLearnedSkillTrigger());

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
