package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.HurricaneGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.WhirlwindGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class AirGuardian extends AbstractBoss {
    public AirGuardian(EntityType<? extends AirGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.YELLOW, AMTags.DamageTypes.AIR_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.AIR_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.AIR_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 200)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 1500)
            .add(AMAttributes.MAX_BURNOUT, 1500);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new HurricaneGoal(this));
        goalSelector.addGoal(1, new WhirlwindGoal(this));
    }
}
