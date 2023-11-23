package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class AbstractSpellEntity extends Entity implements OwnableEntity {
    public AbstractSpellEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public abstract void setOwner(LivingEntity owner);

    public abstract int getDuration();

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return getOwner() instanceof Player player ? player.getUUID() : null;
    }

    @Override
    public void tick() {
        if (tickCount > getDuration() || getOwner() == null) {
            remove(RemovalReason.KILLED);
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        Entity entity = getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? 0 : entity.getId());
    }

    protected static boolean tryReflect(Entity e) {
        if (!(e instanceof LivingEntity living)) return true;
        if (!living.hasEffect(AMMobEffects.REFLECT.get())) return true;
        MobEffectInstance reflect = living.getEffect(AMMobEffects.REFLECT.get());
        if (reflect.getAmplifier() == 0) {
            living.removeEffect(AMMobEffects.REFLECT.get());
        } else {
            MobEffectInstance effect = new MobEffectInstance(reflect.getEffect(), reflect.getDuration(), reflect.getAmplifier(), reflect.isAmbient(), reflect.isVisible(), reflect.showIcon());
            living.removeEffect(AMMobEffects.REFLECT.get());
            living.addEffect(effect);
        }
        return false;
    }

    protected void forAllInRange(float radius, boolean skipOwner, Consumer<LivingEntity> consumer) {
        double x = getX(), y = getY(), z = getZ();
        for (Entity e : level.getEntities(this, new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius))) {
            if (e == this) continue;
            if (e instanceof PartEntity<?> part) {
                e = part.getParent();
            }
            if (skipOwner && e == getOwner()) continue;
            if (e instanceof Player player && player.isCreative()) continue;
            if (e instanceof AbstractSpellEntity) continue;
            if (tryReflect(e) && e instanceof LivingEntity living && !living.isDeadOrDying()) {
                consumer.accept(living);
            }
        }
    }
}
