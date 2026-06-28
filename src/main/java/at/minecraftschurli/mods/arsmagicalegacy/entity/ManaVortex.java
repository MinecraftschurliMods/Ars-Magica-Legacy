package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class ManaVortex extends Entity {
    public static final Identifier PARTICLES = ArsMagicaApi.id("mana_vortex");
    public static final Identifier PARTICLES_DEATH = ArsMagicaApi.id("mana_vortex_death");
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(ManaVortex.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> MANA = SynchedEntityData.defineId(ManaVortex.class, EntityDataSerializers.FLOAT);

    public ManaVortex(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DURATION, 50 + level().getRandom().nextInt(250));
        builder.define(MANA, 0f);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        ValueInput child = input.childOrEmpty(ArsMagicaApi.MOD_ID);
        entityData.set(DURATION, child.getIntOr("duration", 50));
        entityData.set(MANA, child.getFloatOr("mana", 0f));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        ValueOutput child = output.child(ArsMagicaApi.MOD_ID);
        child.putInt("duration", getDuration());
        child.putFloat("mana", entityData.get(MANA));
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        return source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
    }

    @Override
    public void tick() {
        super.tick();
        if (!(level() instanceof ServerLevel level)) {
            AMClientUtil.spawnManaVortexParticles(this);
            return;
        }
        int duration = getDuration();
        double range = AMServerConfig.MANA_VORTEX_RANGE.getAsDouble();
        if (duration - tickCount > 30) {
            ManaHelper helper = ArsMagicaApi.manaHelper();
            double mana = entityData.get(MANA);
            double steal = AMServerConfig.MANA_VORTEX_STEAL.getAsDouble();
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(range), e -> e.getAttribute(AMAttributes.MAX_MANA) != null)) {
                double stolen = Math.min(helper.getMana(entity), helper.getMaxMana(entity) * steal);
                mana += stolen;
                helper.setMana(entity, helper.getMana(entity) - stolen);
                setDeltaMovement(entity.getEyePosition().subtract(getEyePosition()).normalize().scale(0.075));
            }
            entityData.set(MANA, (float) mana);
            snapTo(position().add(getDeltaMovement()));
        } else if (duration - tickCount <= 20) {
            setBoundingBox(getBoundingBox().inflate(-0.05));
            if (duration - tickCount <= 5) {
                float damage = (float) Math.min(AMServerConfig.MANA_VORTEX_MAX_DAMAGE.getAsDouble(), entityData.get(MANA) * AMServerConfig.MANA_VORTEX_DAMAGE.getAsDouble());
                for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(range))) {
                    entity.hurtServer(level, damageSources().magic(), damage);
                }
                setRemoved(RemovalReason.KILLED);
            }
        }
    }

    public int getDuration() {
        return entityData.get(DURATION);
    }
}
