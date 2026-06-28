package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDamageTypes;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.entity.PartEntity;

public class NatureScythe extends AbstractOwnableEntity {
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(NatureScythe.class, EntityDataSerializers.ITEM_STACK);
    private static final String STACK_KEY = "stack";
    private boolean hasHit = false;
    private int hitTicks = -1;

    public NatureScythe(EntityType<? extends NatureScythe> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(STACK, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        input.child(ArsMagicaApi.MOD_ID).ifPresent(child -> entityData.set(STACK, child.read(STACK_KEY, ItemStack.CODEC).orElse(ItemStack.EMPTY)));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        ValueOutput child = output.child(ArsMagicaApi.MOD_ID);
        child.store(STACK_KEY, ItemStack.CODEC, entityData.get(STACK));
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    @Override
    public float getXRot(float partialTicks) {
        return super.getXRot(partialTicks) + (tickCount + partialTicks) * 36;
    }

    @Override
    public void tick() {
        if (getOwner() == null) {
            remove(RemovalReason.KILLED);
            return;
        }
        if (hitTicks != -1 && tickCount / 2 > hitTicks) {
            returnToOwner();
        } else if (tickCount > 50) {
            setHasHit();
        }
        HitResult result = AMUtil.getHitResult(position(), position().add(getDeltaMovement()), this, false);
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) result).getEntity();
            while (entity instanceof PartEntity<?> part) {
                entity = part.getParent();
            }
            if (entity instanceof LivingEntity living && entity != getOwner()) {
                if (level() instanceof ServerLevel level) {
                    living.hurtServer(level, damageSource(AMDamageTypes.NATURE_SCYTHE), 12);
                }
                setHasHit();
            }
            if (hasHit && distanceTo(getOwner()) < 4) {
                returnToOwner();
            }
        } else if (result.getType() == HitResult.Type.BLOCK) {
            setHasHit();
        }
        setPos(position().add(getDeltaMovement()));
    }

    public ItemStack getStack() {
        return entityData.get(STACK);
    }

    public void setStack(ItemStack stack) {
        entityData.set(STACK, stack);
    }

    private void setHasHit() {
        if (!hasHit) {
            setDeltaMovement(getDeltaMovement().multiply(-1, -1, -1));
            hasHit = true;
            hitTicks = tickCount;
        }
    }

    private void returnToOwner() {
        LivingEntity owner = getOwner();
        if (owner instanceof NatureGuardian guardian) {
            guardian.setHasScythe(true);
        } else if (owner instanceof Player player && !player.addItem(getStack())) {
            ItemEntity item = new ItemEntity(level(), player.getX(), player.getY(), player.getZ(), getStack());
            level().addFreshEntity(item);
        }
        remove(RemovalReason.KILLED);
    }
}
