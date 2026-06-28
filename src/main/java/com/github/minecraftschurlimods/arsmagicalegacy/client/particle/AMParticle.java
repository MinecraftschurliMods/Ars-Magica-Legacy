package com.github.minecraftschurlimods.arsmagicalegacy.client.particle;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ControlledParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleController;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleControllerInstance;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleSpawner;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleResources;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AMParticle extends SimpleAnimatedParticle implements ControlledParticle {
    private final List<ParticleControllerInstance> controllers = new ArrayList<>();

    @SuppressWarnings("DataFlowIssue")
    private AMParticle(ClientLevel level, double x, double y, double z, @Nullable SpriteSet sprites) {
        super(level, x, y, z, sprites, 0);
    }

    public static List<AMParticle> spawn(ClientLevel level, double x, double y, double z, ParticleSpawner spawner, int color, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        ParticleEngine particleEngine = AMClientUtil.mc().particleEngine;
        Particle vanillaParticle = particleEngine.createParticle(spawner.particle(), x, y, z, 0, 0, 0);
        if (vanillaParticle == null) return List.of();
        SpriteSet sprites = vanillaParticle instanceof SimpleAnimatedParticle particle ? particle.sprites : null;
        TextureAtlasSprite sprite = switch (vanillaParticle) {
            case SimpleAnimatedParticle ignored -> sprites.get(0, 1);
            case SingleQuadParticle particle -> particle.sprite;
            default -> null;
        };
        vanillaParticle.remove();
        if (sprite == null) return List.of();
        if (sprites == null) {
            ParticleResources.MutableSpriteSet mutableSprites = new ParticleResources.MutableSpriteSet();
            mutableSprites.rebind(List.of(sprite));
            sprites = mutableSprites;
        }
        List<AMParticle> list = new ArrayList<>();
        for (int i = 0; i < spawner.count(); i++) {
            AMParticle particle = new AMParticle(level, x, y, z, sprites);
            particle.setSprite(sprite);
            particle.setLifetime(particle.random().nextIntBetweenInclusive(spawner.minLifetime(), spawner.maxLifetime()));
            particle.addOffset(spawner.minOffset(), spawner.maxOffset());
            particle.setSpeed(spawner.minSpeed(), spawner.maxSpeed());
            particle.gravity = spawner.gravity();
            particle.scale(spawner.scale());
            if (color != -1) {
                particle.setColor(color);
            } else if (spawner.color() != -1) {
                particle.setColor(spawner.color());
            }
            particle.setAlpha(spawner.alpha());
            spawner.controllers().forEach(controller -> particle.addController(controller, caster, directEntity, hitResult));
            particleEngine.add(particle);
            list.add(particle);
        }
        return list;
    }

    public void addOffset(Vec3 minOffset, Vec3 maxOffset) {
        setPos(x + Mth.lerp(random.nextDouble(), minOffset.x, maxOffset.x), y + Mth.lerp(random.nextDouble(), minOffset.y, maxOffset.y), z + Mth.lerp(random.nextDouble(), minOffset.z, maxOffset.z));
    }

    public void setSpeed(Vec3 minSpeed, Vec3 maxSpeed) {
        setParticleSpeed(Mth.lerp(random.nextDouble(), minSpeed.x, maxSpeed.x), Mth.lerp(random.nextDouble(), minSpeed.y, maxSpeed.y), Mth.lerp(random.nextDouble(), minSpeed.z, maxSpeed.z));
    }

    public void addController(ParticleController controller, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        controllers.add(new ParticleControllerInstance(this, controller, caster, directEntity, hitResult));
    }

    @Override
    public boolean isRemoved() {
        return removed;
    }

    @Override
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public ClientLevel level() {
        return level;
    }

    @Override
    public RandomSource random() {
        return random;
    }

    @Override
    public double horizontalDistanceTo(Vec3 vec) {
        double x = Math.abs(vec.x - x());
        double z = Math.abs(vec.z - z());
        return Math.sqrt(x * x + z * z);
    }

    @Override
    public void setPos(double x, double y, double z) {
        super.setPos(x, y, z);
        float f = bbWidth / 2;
        setBoundingBox(new AABB(x - f, y, z - f, x + f, y + bbWidth, z + f));
    }

    @Override
    public int getColor() {
        return (int) (rCol * 255) << 16 | (int) (gCol * 255) << 8 | (int) (bCol * 255);
    }

    @Override
    public float getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public Particle scale(float scale) {
        setSize(0.2f * scale, 0.2f * scale);
        return this;
    }

    @Override
    protected void setSize(float width, float height) {
        if (width == bbWidth && height == bbHeight) return;
        bbWidth = width;
        bbHeight = height;
        AABB aabb = getBoundingBox();
        double x = (aabb.minX + aabb.maxX - width) / 2;
        double z = (aabb.minZ + aabb.maxZ - width) / 2;
        setBoundingBox(new AABB(x, aabb.minY, z, x + width, aabb.minY + height, z + width));
    }

    @Override
    protected void setLocationFromBoundingbox() {
        AABB aabb = getBoundingBox();
        setPos((aabb.minX + aabb.maxX) / 2, aabb.minY, (aabb.minZ + aabb.maxZ) / 2);
    }

    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;
        age++;
        if (age >= lifetime && lifetime > -1) {
            setRemoved(true);
            return;
        }
        for (ParticleControllerInstance controller : controllers) {
            controller.tick();
            if (!controller.isFinished() && controller.controller.stopOtherControllers()) break;
        }
        yd -= 0.04 * gravity;
        move(xd, yd, zd);
        if (speedUpWhenYMotionIsBlocked && y == yo) {
            xd *= 1.1;
            zd *= 1.1;
        }
        xd *= friction;
        yd *= friction;
        zd *= friction;
        if (onGround) {
            xd *= 0.7;
            zd *= 0.7;
        }
        setSpriteFromAge(sprites);
    }
}
