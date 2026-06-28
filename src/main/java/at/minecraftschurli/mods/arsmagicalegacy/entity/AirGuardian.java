package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.HurricaneGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.WhirlwindGoal;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
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
