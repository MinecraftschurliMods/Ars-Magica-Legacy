package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.advancement.AffinityChangeTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.advancement.LevelChangeTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.advancement.SkillChangeTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMCriterionTriggers {
    DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES = DeferredRegister.create(Registries.TRIGGER_TYPE, ArsMagicaApi.MOD_ID);

    // @formatter:off
    DeferredHolder<CriterionTrigger<?>, AffinityChangeTrigger> AFFINITY_CHANGE = TRIGGER_TYPES.register("affinity_change", AffinityChangeTrigger::new);
    DeferredHolder<CriterionTrigger<?>, LevelChangeTrigger>    LEVEL_CHANGE    = TRIGGER_TYPES.register("level_change",    LevelChangeTrigger::new);
    DeferredHolder<CriterionTrigger<?>, SkillChangeTrigger>    SKILL_CHANGE    = TRIGGER_TYPES.register("skill_change",    SkillChangeTrigger::new);
    // @formatter:on
}
