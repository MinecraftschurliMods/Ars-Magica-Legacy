package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCasterEntity;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.BossNearestAttackableTargetGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.DispelGoal;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.mods.arsmagicalegacy.util.BossBar;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public abstract class AbstractBoss extends Monster implements GeoEntity, SpellCasterEntity {
    protected final TagKey<DamageType> isVulnerableTo;
    protected final TagKey<DamageType> isImmuneTo;
    protected final TagKey<DamageType> isHealTo;
    private final BossEvent.BossBarColor color;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int ticksInAction = 0;
    private Action action = Action.IDLE;

    protected AbstractBoss(EntityType<? extends AbstractBoss> type, Level level, BossEvent.BossBarColor color, TagKey<DamageType> isVulnerableTo, TagKey<DamageType> isImmuneTo, TagKey<DamageType> isHealTo) {
        super(type, level);
        this.color = color;
        this.isVulnerableTo = isVulnerableTo;
        this.isImmuneTo = isImmuneTo;
        this.isHealTo = isHealTo;
    }

    public static AttributeSupplier.Builder createBossAttributes() {
        return createMonsterAttributes().add(Attributes.STEP_HEIGHT, 1.02);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericLivingController());
        controllers.add(new AnimationController<>("Action", test -> test.setAndContinue(test.getDataOrDefault(AMClientUtil.ACTION_DATA_TICKET, Action.IDLE).animation)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean canCastSpell() {
        return action == Action.IDLE;
    }

    @Override
    public boolean isCastingSpell() {
        return action == Action.CAST;
    }

    @Override
    public void setIsCastingSpell(boolean isCastingSpell) {
        if (isCastingSpell) {
            setAction(Action.CAST);
        } else if (action == Action.CAST) {
            setAction(Action.IDLE);
        }
    }

    @Override
    public boolean canBeCollidedWith(@Nullable Entity other) {
        return true;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return isAlive();
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    public boolean canRide(Entity vehicle) {
        return false;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level() instanceof ServerLevel server) {
            BossBar.getList(server).add(new BossBar(server, this, color));
        }
        ticksInAction++;
    }

    @Override
    public void handleEntityEvent(byte id) {
        for (Action a : Action.values()) {
            if (a.id == id) {
                action = a;
                break;
            }
        }
        super.handleEntityEvent(id);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if (source.typeHolder().is(DamageTypes.GENERIC_KILL)) return super.hurtServer(level, source, damage);
        if (source.getEntity() instanceof AbstractBoss) return false;
        if (source.is(DamageTypes.IN_WALL)) {
            if (!level().isClientSide()) {
                int width = Math.round(getBbWidth());
                int height = Math.round(getBbHeight());
                for (int x = -width; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        for (int z = -width; z < width; z++) {
                            level().destroyBlock(BlockPos.containing(getX() + x, getY() + y, getZ() + z), true, this);
                        }
                    }
                }
            }
            return false;
        }
        if (source.is(isHealTo)) {
            heal(damage);
            return false;
        }
        if (source.is(isImmuneTo)) return false;
        if (source.is(isVulnerableTo)) {
            damage *= 2;
        }
        level().playSound(null, this, getHurtSound(source), SoundSource.HOSTILE, 1f, 0.5f + random.nextFloat() * 0.5f);
        return super.hurtServer(level, source, damage);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DispelGoal<>(this));
        goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1, 1));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        targetSelector.addGoal(0, new HurtByTargetGoal(this));
        targetSelector.addGoal(1, new BossNearestAttackableTargetGoal<>(this, Player.class, 2, true, false, null));
    }

    @Nullable
    public SoundEvent getAttackSound() {
        return null;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
        ticksInAction = 0;
        level().broadcastEntityEvent(this, action.id);
    }

    public int getTicksInAction() {
        return ticksInAction;
    }

    public enum Action {
        IDLE(-1, DefaultAnimations.IDLE),
        CAST(-2, DefaultAnimations.ATTACK_CAST),
        LONG_CAST(-3, DefaultAnimations.ATTACK_CAST),
        SPIN(-4, DefaultAnimations.ATTACK_CHARGE),
        STOMP(-5, DefaultAnimations.ATTACK_STOMP),
        STRIKE(-6, DefaultAnimations.ATTACK_STRIKE),
        THROW(-7, DefaultAnimations.ATTACK_THROW);

        public final byte id;
        public final RawAnimation animation;

        Action(int id, RawAnimation animation) {
            this.id = (byte) id;
            this.animation = animation;
        }
    }
}
