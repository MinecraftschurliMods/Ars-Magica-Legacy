package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.ExecuteBossSpellGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.HealGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.SummonAlliesGoal;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class LifeGuardian extends AbstractBoss {
    private static final String MINIONS_KEY = "minions";
    public final Set<LivingEntity> minions = new HashSet<>();

    public LifeGuardian(EntityType<? extends LifeGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN, AMTags.DamageTypes.LIFE_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.LIFE_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.LIFE_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 400)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 2500)
            .add(AMAttributes.MAX_BURNOUT, 2500);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.child(ArsMagicaApi.MOD_ID).ifPresent(child -> child.list(MINIONS_KEY, UUIDUtil.CODEC)
            .map(ValueInput.TypedInputList::stream)
            .orElseGet(Stream::of)
            .map(level()::getEntity)
            .filter(LivingEntity.class::isInstance)
            .map(LivingEntity.class::cast)
            .forEach(minions::add));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        ValueOutput.TypedOutputList<UUID> list = output.child(ArsMagicaApi.MOD_ID).list(MINIONS_KEY, UUIDUtil.CODEC);
        minions.stream()
            .map(Entity::getUUID)
            .forEach(list::add);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.LIFE_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.LIFE_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.LIFE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.LIFE_GUARDIAN_HURT.get();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if (source.getEntity() instanceof LivingEntity living) {
            for (LivingEntity minion : minions) {
                minion.setLastHurtByMob(living);
            }
        }
        return source.is(DamageTypes.FELL_OUT_OF_WORLD) && super.hurtServer(level, source, damage);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        //TODO goalSelector.addGoal(1, new SummonAlliesGoal(this, AMEntities.EARTH_ELEMENTAL.get(), AMEntities.FIRE_ELEMENTAL.get(), AMEntities.MANA_ELEMENTAL.get(), AMEntities.DARKLING.get()));
        goalSelector.addGoal(1, new SummonAlliesGoal(this, List.of(EntityType.PILLAGER, EntityType.VINDICATOR, EntityType.WITCH)));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB).getValue(ArsMagicaApi.id("nausea")), 30));
        goalSelector.addGoal(1, new HealGoal<>(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level() instanceof ServerLevel level) {
            Set<LivingEntity> toRemove = new HashSet<>();
            for (LivingEntity e : minions) {
                if (e.tickCount > 1200 || e.isRemoved()) {
                    e.remove(RemovalReason.KILLED);
                    toRemove.add(e);
                }
                if (e.isDeadOrDying() && !toRemove.contains(e)) {
                    hurtServer(level, damageSources().fellOutOfWorld(), e.getMaxHealth());
                    toRemove.add(e);
                }
            }
            minions.removeAll(toRemove);
        }
    }
}
