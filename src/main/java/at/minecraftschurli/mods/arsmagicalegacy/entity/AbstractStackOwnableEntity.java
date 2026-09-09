package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class AbstractStackOwnableEntity extends AbstractOwnableEntity {
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(AbstractStackOwnableEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final String STACK_KEY = "stack";

    public AbstractStackOwnableEntity(EntityType<? extends AbstractOwnableEntity> type, Level level) {
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
    public ItemStack getPickResult() {
        return getStack();
    }

    public ItemStack getStack() {
        return entityData.get(STACK);
    }

    public void setStack(ItemStack stack) {
        entityData.set(STACK, stack);
    }
}
