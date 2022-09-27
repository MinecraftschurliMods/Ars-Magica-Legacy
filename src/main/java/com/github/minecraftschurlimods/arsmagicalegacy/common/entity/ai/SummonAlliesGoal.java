package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class SummonAlliesGoal extends AbstractBossGoal<LifeGuardian> {
    private final List<Function<Level, ? extends Mob>> list;

    @SafeVarargs
    public SummonAlliesGoal(LifeGuardian boss, EntityType<? extends Mob>... entityTypes) {
        this(boss, Arrays.stream(entityTypes).<Function<Level, ? extends Mob>>map(e -> e::create).toList());
    }

    public SummonAlliesGoal(LifeGuardian boss, List<Function<Level, ? extends Mob>> list) {
        super(boss, AbstractBoss.Action.LONG_CAST, 20);
        this.list = list;
    }

    @Override
    public void perform() {
        if (boss.minions.size() > 1) return;
        for (int i = 0; i < 3; i++) {
            Mob entity = list.get(boss.getLevel().getRandom().nextInt(list.size())).apply(boss.getLevel());
            if (entity == null) continue;
            BlockPos pos = new BlockPos(boss.getX() + boss.getLevel().getRandom().nextDouble() * 4 - 2, boss.getY() + boss.getLevel().getRandom().nextDouble() * 4 - 2, boss.getZ() + boss.getLevel().getRandom().nextDouble() * 4 - 2);
            for (int j = 0; j <= 100 && !boss.getLevel().getBlockState(pos).isValidSpawn(boss.getLevel(), pos, entity.getType()); j++) {
                pos = new BlockPos(boss.getX() + boss.getLevel().getRandom().nextDouble() * 4 - 2, boss.getY() + boss.getLevel().getRandom().nextDouble() * 4 - 2, boss.getZ() + boss.getLevel().getRandom().nextDouble() * 4 - 2);
            }
            entity.moveTo(boss.getX() + boss.getLevel().getRandom().nextDouble() * 2 - 1, boss.getY(), boss.getZ() + boss.getLevel().getRandom().nextDouble() * 2 - 1);
            int amplifier = (int) Math.abs(2 * boss.getHealth() / boss.getMaxHealth() - 2);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000, amplifier));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10000, amplifier));
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10000, amplifier));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10000, amplifier));
            boss.getLevel().addFreshEntity(entity);
            boss.minions.add(entity);
        }
    }
}
