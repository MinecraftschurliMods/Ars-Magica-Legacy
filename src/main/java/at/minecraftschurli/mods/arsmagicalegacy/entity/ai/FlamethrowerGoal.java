package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.entity.FireGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.sounds.SoundEvent;

public class FlamethrowerGoal extends AbstractBossGoal<FireGuardian> {
    public FlamethrowerGoal(FireGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 20);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.FIRE_GUARDIAN_FLAMETHROWER.value();
    }

    @Override
    public void perform() {
        boss.flamethrower();
    }
}
