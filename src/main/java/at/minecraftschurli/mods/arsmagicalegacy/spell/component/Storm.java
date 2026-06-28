package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Objects;

public class Storm extends SpellComponent {
    public Storm() {
        super(AMSpells.DURATION_STAT, AMSpells.RANGE_STAT);
    }

    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        LivingEntity caster = context.caster();
        Entity directEntity = context.directEntity();
        if (!(level.getRainLevel(1f) > 0.9)) {
            Objects.requireNonNull(level.getServer()).setWeatherParameters(0, (int) helper.getModifiedStat(AMServerConfig.STORM_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context), true, true);
        }
        if (directEntity == null) return SpellComponentCastResult.success(spell);
        int range = (int) helper.getModifiedStat(AMServerConfig.STORM_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context);
        RandomSource random = level.getRandom();
        double randomValue = random.nextDouble();
        if (randomValue < AMServerConfig.STORM_LIGHTNING_BOLT_CHANCE.get()) {
            double x = directEntity.getX() + random.nextDouble() * range - range / 2.;
            double z = directEntity.getZ() + random.nextDouble() * range - range / 2.;
            double y = directEntity.getY();
            while (!level.canSeeSky(BlockPos.containing(x, y, z))) {
                y++;
            }
            while (level.getBlockState(BlockPos.containing(x, y - 1, z)).getBlock().equals(Blocks.AIR)) {
                y--;
            }
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.MOB_SUMMONED);
            if (bolt != null) {
                bolt.setPos(x, y, z);
                bolt.setVisualOnly(false);
                level.addFreshEntity(bolt);
            }
        } else if (randomValue < AMServerConfig.STORM_LIGHTNING_BOLT_TARGET_CHANCE.get()) {
            List<Entity> entities = level.getEntities(caster, directEntity.getBoundingBox().inflate(range / 2., range / 2., range / 2.));
            if (entities.isEmpty()) return SpellComponentCastResult.success(spell);
            Entity entity = entities.get(random.nextInt(entities.size()));
            if (!level.canSeeSky(entity.blockPosition())) return SpellComponentCastResult.success(spell);
            if (caster instanceof Player player) {
                entity.hurtServer(level, level.damageSources().playerAttack(player), 1);
            }
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.MOB_SUMMONED);
            if (bolt != null) {
                bolt.setPos(entity.position());
                bolt.setVisualOnly(false);
                level.addFreshEntity(bolt);
            }
        }
        return SpellComponentCastResult.success(spell);
    }
}
