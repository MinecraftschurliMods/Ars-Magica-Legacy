package com.github.minecraftschurli.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Storm extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (!level.isClientSide()) {
            if (level.getRainLevel(1f) > 0.9) {
                int random = level.random.nextInt(100);
                if (random < 20) {
                    double posX = caster.getX() + level.random.nextDouble(100) - 50;
                    double posZ = caster.getZ() + level.random.nextDouble(100) - 50;
                    double posY = caster.getY();
                    while (!level.canSeeSky(new BlockPos(posX, posY, posZ))) {
                        posY++;
                    }
                    while (level.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock().equals(Blocks.AIR)) {
                        posY--;
                    }
                    LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    bolt.setPos(posX, posY, posZ);
                    bolt.setVisualOnly(false);
                    level.addFreshEntity(bolt);
                } else if (random < 80) {
                    List<Entity> entities = level.getEntities(caster, caster.getBoundingBox().inflate(50, 10, 50));
                    if (entities.size() <= 0) return SpellCastResult.EFFECT_FAILED;
                    Entity entity = entities.get(level.random.nextInt(entities.size()));
                    if (entity != null && level.canSeeSky(entity.blockPosition())) {
                        if (caster instanceof Player) {
                            entity.hurt(DamageSource.playerAttack((Player) caster), 1);
                        }
                        LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                        bolt.setPos(entity.position());
                        bolt.setVisualOnly(false);
                        level.addFreshEntity(bolt);
                    }
                }
            } else {
                ((ServerLevel) level).setWeatherParameters(0, 200000 * (1 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId())), true, true);
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        if (!level.isClientSide()) {
            if (level.getRainLevel(1f) > 0.9) {
                int random = level.random.nextInt(100);
                if (random < 20) {
                    double posX = caster.getX() + level.random.nextDouble(100) - 50;
                    double posZ = caster.getZ() + level.random.nextDouble(100) - 50;
                    double posY = caster.getY();
                    while (!level.canSeeSky(new BlockPos(posX, posY, posZ))) {
                        posY++;
                    }
                    while (level.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock().equals(Blocks.AIR)) {
                        posY--;
                    }
                    LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    bolt.setPos(posX, posY, posZ);
                    bolt.setVisualOnly(false);
                    level.addFreshEntity(bolt);
                } else if (random < 80) {
                    List<Entity> entities = level.getEntities(caster, caster.getBoundingBox().inflate(50, 10, 50));
                    if (entities.size() <= 0) return SpellCastResult.EFFECT_FAILED;
                    Entity entity = entities.get(level.random.nextInt(entities.size()));
                    if (entity != null && level.canSeeSky(entity.blockPosition())) {
                        if (caster instanceof Player) {
                            entity.hurt(DamageSource.playerAttack((Player) caster), 1);
                        }
                        LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                        bolt.setPos(entity.position());
                        bolt.setVisualOnly(false);
                        level.addFreshEntity(bolt);
                    }
                }
            } else {
                ((ServerLevel) level).setWeatherParameters(0, 200000 * (1 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId())), true, true);
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
