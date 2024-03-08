package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.CONTINGENCY_TYPE;

@NonExtendable
public interface AMContingencyTypes {
    Holder<ContingencyType> NONE   = CONTINGENCY_TYPE.register("none",   ContingencyType::new);
    Holder<ContingencyType> DEATH  = CONTINGENCY_TYPE.register("death",  ContingencyType::new);
    Holder<ContingencyType> DAMAGE = CONTINGENCY_TYPE.register("damage", ContingencyType::new);
    Holder<ContingencyType> FALL   = CONTINGENCY_TYPE.register("fall",   ContingencyType::new);
    Holder<ContingencyType> FIRE   = CONTINGENCY_TYPE.register("fire",   ContingencyType::new);
    Holder<ContingencyType> HEALTH = CONTINGENCY_TYPE.register("health", ContingencyType::new);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
