package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public abstract class AbstractOwnableEntity extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> OWNER = SynchedEntityData.defineId(AbstractOwnableEntity.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    private static final String OWNER_KEY = "owner";

    public AbstractOwnableEntity(EntityType<? extends AbstractOwnableEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        entityData.define(OWNER, Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        input.child(ArsMagicaApi.MOD_ID).ifPresent(child -> entityData.set(OWNER, Optional.ofNullable(EntityReference.readWithOldOwnerConversion(child, OWNER_KEY, level()))));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        ValueOutput child = output.child(ArsMagicaApi.MOD_ID);
        EntityReference.store(entityData.get(OWNER).orElse(null), child, OWNER_KEY);
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        return EntityReference.getLivingEntity(entityData.get(OWNER).orElse(null), level());
    }

    public void setOwner(@Nullable LivingEntity owner) {
        entityData.set(OWNER, owner == null ? Optional.empty() : Optional.of(EntityReference.of(owner)));
    }

    public DamageSource damageSource(ResourceKey<DamageType> type) {
        return new DamageSource(registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(type), getOwner(), this);
    }
}
