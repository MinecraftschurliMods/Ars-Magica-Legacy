package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.CONTINGENCY_TYPE;

@NonExtendable
public interface AMContingencyTypes {
    RegistryObject<ContingencyType> NONE   = CONTINGENCY_TYPE.register("none",   ContingencyType::new);
    RegistryObject<ContingencyType> DEATH  = CONTINGENCY_TYPE.register("death",  ContingencyType::new);
    RegistryObject<ContingencyType> DAMAGE = CONTINGENCY_TYPE.register("damage", ContingencyType::new);
    RegistryObject<ContingencyType> FALL   = CONTINGENCY_TYPE.register("fall",   ContingencyType::new);
    RegistryObject<ContingencyType> FIRE   = CONTINGENCY_TYPE.register("fire",   ContingencyType::new);
    RegistryObject<ContingencyType> HEALTH = CONTINGENCY_TYPE.register("health", ContingencyType::new);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
