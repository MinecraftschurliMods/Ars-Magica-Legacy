package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class Storm extends AbstractComponent {
    public Storm() {
        super(SpellPartStats.DURATION);
    }

    private static SpellCastResult performStorm(LivingEntity caster, Level level, List<ISpellModifier> modifiers, ISpell spell, HitResult target, int index) {
        if (!level.isClientSide()) {
            if (level.getRainLevel(1f) > 0.9) {
                int random = level.random.nextInt(100);
                if (random < 20) {
                    double posX = caster.getX() + level.getRandom().nextDouble() * 100 - 50;
                    double posZ = caster.getZ() + level.getRandom().nextDouble() * 100 - 50;
                    double posY = caster.getY();
                    while (!level.canSeeSky(BlockPos.containing(posX, posY, posZ))) {
                        posY++;
                    }
                    while (level.getBlockState(BlockPos.containing(posX, posY - 1, posZ)).getBlock().equals(Blocks.AIR)) {
                        posY--;
                    }
                    LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    bolt.setPos(posX, posY, posZ);
                    bolt.setVisualOnly(false);
                    level.addFreshEntity(bolt);
                } else if (random < 80) {
                    List<Entity> entities = level.getEntities(caster, caster.getBoundingBox().inflate(50, 10, 50));
                    if (entities.isEmpty()) return SpellCastResult.EFFECT_FAILED;
                    Entity entity = entities.get(level.random.nextInt(entities.size()));
                    if (entity != null && level.canSeeSky(entity.blockPosition())) {
                        if (caster instanceof Player) {
                            entity.hurt(level.damageSources().playerAttack((Player) caster), 1);
                        }
                        LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                        bolt.setPos(entity.position());
                        bolt.setVisualOnly(false);
                        level.addFreshEntity(bolt);
                    }
                }
            } else {
                ((ServerLevel) level).setWeatherParameters(0, (int) ArsMagicaAPI.get().getSpellHelper().getModifiedStat(200000, SpellPartStats.DURATION, modifiers, spell, caster, target, index), true, true);
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        return performStorm(caster, level, modifiers, spell, target, index);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return performStorm(caster, level, modifiers, spell, target, index);
    }
}
