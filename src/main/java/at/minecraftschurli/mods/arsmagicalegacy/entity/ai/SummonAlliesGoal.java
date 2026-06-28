package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.entity.LifeGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;

public class SummonAlliesGoal extends AbstractBossGoal<LifeGuardian> {
    private final List<EntityType<? extends Mob>> list;

    public SummonAlliesGoal(LifeGuardian boss, List<EntityType<? extends Mob>> entityTypes) {
        super(boss, AbstractBoss.Action.LONG_CAST, 30);
        list = entityTypes;
    }

    @Override
    public void perform() {
        if (boss.minions.size() > 1) return;
        Level level = boss.level();
        if (!(level instanceof ServerLevel serverLevel)) return;
        for (int i = 0; i < 3; i++) {
            Mob entity = list.get(level.getRandom().nextInt(list.size())).create(level, EntitySpawnReason.MOB_SUMMONED);
            if (entity == null) continue;
            BlockPos pos = BlockPos.containing(boss.getX() + level.getRandom().nextDouble() * 4 - 2, boss.getY() + level.getRandom().nextDouble() * 4 - 2, boss.getZ() + level.getRandom().nextDouble() * 4 - 2);
            for (int j = 0; j <= 100 && !level.getBlockState(pos).isValidSpawn(level, pos, entity.getType()); j++) {
                pos = BlockPos.containing(boss.getX() + level.getRandom().nextDouble() * 4 - 2, boss.getY() + level.getRandom().nextDouble() * 4 - 2, boss.getZ() + level.getRandom().nextDouble() * 4 - 2);
            }
            entity.teleportTo(boss.getX() + level.getRandom().nextDouble() * 2 - 1, boss.getY(), boss.getZ() + level.getRandom().nextDouble() * 2 - 1);
            EventHooks.finalizeMobSpawn(entity, serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), EntitySpawnReason.MOB_SUMMONED, null);
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                entity.setDropChance(slot, 0);
            }
            int amplifier = (int) Math.abs(2 * boss.getHealth() / boss.getMaxHealth() - 2);
            entity.addEffect(new MobEffectInstance(MobEffects.SPEED, -1, amplifier));
            entity.addEffect(new MobEffectInstance(MobEffects.STRENGTH, -1, amplifier));
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, -1, amplifier));
            entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, -1, amplifier));
            entity.setData(AMAttachments.SUMMON_OWNER, boss.getUUID());
            level.addFreshEntity(entity);
            boss.minions.add(entity);
        }
    }
}
