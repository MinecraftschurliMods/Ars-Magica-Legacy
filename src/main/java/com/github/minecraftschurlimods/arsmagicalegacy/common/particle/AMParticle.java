package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AMParticle extends TextureSheetParticle {
    private final List<ParticleController> controllers = new ArrayList<>();
    private boolean stoppedByCollision = false;

    private AMParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
        Minecraft.getInstance().particleEngine.add(this);
    }

    public AMParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet sprite) {
        this(pLevel, pX, pY, pZ);
        pickSprite(sprite);
    }

    public AMParticle(ClientLevel pLevel, double pX, double pY, double pZ, ParticleOptions options) {
        this(pLevel, pX, pY, pZ);
        if (Minecraft.getInstance().particleEngine.createParticle(options, pX, pY, pZ, 0, 0, 0) instanceof TextureSheetParticle tsp) {
            sprite = tsp.sprite;
        }
    }

    //@formatter:off
    public double getX() {return x;}
    public void setX(double x) {this.x = x;}
    public double getY() {return y;}
    public void setY(double y) {this.y = y;}
    public double getZ() {return z;}
    public void setZ(double z) {this.z = z;}
    public double getXOld() {return xo;}
    public void setXOld(double xo) {this.xo = xo;}
    public double getYOld() {return yo;}
    public void setYOld(double yo) {this.yo = yo;}
    public double getZOld() {return zo;}
    public void setZOld(double zo) {this.zo = zo;}
    public double getXSpeed() {return xd;}
    public void setXSpeed(double xd) {this.xd = xd;}
    public double getYSpeed() {return yd;}
    public void setYSpeed(double yd) {this.yd = yd;}
    public double getZSpeed() {return zd;}
    public void setZSpeed(double zd) {this.zd = zd;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public float getGravity() {return gravity;}
    public void setGravity(float gravity) {this.gravity = gravity;}
    public float getFriction() {return friction;}
    public void setFriction(float friction) {this.friction = friction;}
    public float getWidth() {return bbWidth;}
    public void setWidth(float width) {this.bbWidth = width;}
    public float getHeight() {return bbHeight;}
    public void setHeight(float height) {this.bbHeight = height;}
    public float getRed() {return rCol;}
    public void setRed(float red) {this.rCol = red;}
    public void setRed(int red) {setRed(red / 255f);}
    public float getGreen() {return gCol;}
    public void setGreen(float green) {this.gCol = green;}
    public void setGreen(int green) {setGreen(green / 255f);}
    public float getBlue() {return bCol;}
    public void setBlue(float blue) {this.bCol = blue;}
    public void setBlue(int blue) {setBlue(blue / 255f);}
    public float getAlpha() {return alpha;}
    public void setAlpha(float alpha) {this.alpha = alpha;}
    public void setAlpha(int alpha) {setAlpha(alpha / 255f);}
    public boolean isRemoved() {return removed;}
    public void setRemoved(boolean removed) {this.removed = removed;}
    public boolean isPhysics() {return hasPhysics;}
    public void setPhysics(boolean physics) {this.hasPhysics = physics;}
    public boolean isOnGround() {return onGround;}
    public void setOnGround(boolean onGround) {this.onGround = onGround;}
    public boolean isEvadeCeiling() {return speedUpWhenYMotionIsBlocked;}
    public void setEvadeCeiling(boolean evadeCeiling) {this.speedUpWhenYMotionIsBlocked = evadeCeiling;}
    public AABB getBoundingBox() {return super.getBoundingBox();}
    public void setBoundingBox(AABB boundingBox) {super.setBoundingBox(boundingBox);}
    //@formatter:on

    public void setPosition(double x, double y, double z) {
        setPos(x, y, z);
    }

    public void setPositionOld(double xo, double yo, double zo) {
        setXOld(xo);
        setYOld(yo);
        setZOld(zo);
    }

    public void setSpeed(double xd, double yd, double zd) {
        setXSpeed(xd);
        setYSpeed(yd);
        setZSpeed(zd);
    }

    public void setNoGravity() {
        setGravity(0);
    }

    public void setColor(int color) {
        setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF);
    }

    public void setColor(int red, int green, int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    public void setColor(float red, float green, float blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    public void setColor(int red, int green, int blue, int alpha) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
        setAlpha(alpha);
    }

    public void setColor(float red, float green, float blue, float alpha) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
        setAlpha(alpha);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void addController(ParticleController controller) {
        controllers.add(controller);
    }

    public RandomSource random() {
        return random;
    }

    public ClientLevel level() {
        return level;
    }

    @Override
    public boolean isAlive() {
        return !isRemoved();
    }

    @Override
    public void move(double pX, double pY, double pZ) {
        if (stoppedByCollision) return;
        double dx = pX;
        double dy = pY;
        double dz = pZ;
        if (isPhysics() && (pX != 0 || pY != 0 || pZ != 0) && pX * pX + pY * pY + pZ * pZ < 10000) {
            Vec3 vec3 = Entity.collideBoundingBox(null, new Vec3(pX, pY, pZ), getBoundingBox(), level, List.of());
            pX = vec3.x;
            pY = vec3.y;
            pZ = vec3.z;
        }
        if (pX != 0 || pY != 0 || pZ != 0) {
            setBoundingBox(getBoundingBox().move(pX, pY, pZ));
            setLocationFromBoundingbox();
        }
        if (Math.abs(dy) >= 1E-5 && Math.abs(pY) < 1E-5) {
            stoppedByCollision = true;
        }
        setOnGround(dy != pY && dy < 0);
        if (dx != pX) {
            setXSpeed(0);
        }
        if (dz != pZ) {
            setZSpeed(0);
        }
    }

    @Override
    public void remove() {
        setRemoved(true);
    }

    @Override
    public Particle scale(float pScale) {
        setSize(0.2f * pScale, 0.2f * pScale);
        return this;
    }

    @Override
    public void setParticleSpeed(double pXd, double pYd, double pZd) {
        setSpeed(pXd, pYd, pZd);
    }

    @Override
    public void setPos(double pX, double pY, double pZ) {
        setX(pX);
        setY(pY);
        setZ(pZ);
        float f = getWidth() / 2;
        setBoundingBox(new AABB(pX - f, pY, pZ - f, pX + f, pY + getHeight(), pZ + f));
    }

    @Override
    public Particle setPower(float pMultiplier) {
        setSpeed(getXSpeed() * pMultiplier, (getYSpeed() - 0.1) * pMultiplier + 0.1, getZSpeed() * pMultiplier);
        return this;
    }

    @Override
    public void tick() {
        setXOld(getX());
        setYOld(getY());
        setZOld(getZ());
        setAge(getAge() + 1);
        if (getAge() >= getLifetime()) {
            remove();
            return;
        }
        for (ParticleController controller : controllers) {
            controller.baseTick();
            if (!controller.isFinished() && controller.stopOtherControllers) break;
        }
        setYSpeed(getYSpeed() - 0.04 * getGravity());
        move(getXSpeed(), getYSpeed(), getZSpeed());
        if (isEvadeCeiling() && getY() == getYOld()) {
            setXSpeed(getXSpeed() * 1.1);
            setZSpeed(getZSpeed() * 1.1);
        }
        setXSpeed(getXSpeed() * getFriction());
        setYSpeed(getYSpeed() * getFriction());
        setZSpeed(getZSpeed() * getFriction());
        if (isOnGround()) {
            setXSpeed(getXSpeed() * 0.7);
            setZSpeed(getZSpeed() * 0.7);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected int getLightColor(float pPartialTick) {
        BlockPos blockpos = new BlockPos(getX(), getY(), getZ());
        return level.hasChunkAt(blockpos) ? LevelRenderer.getLightColor(level, blockpos) : 0;
    }

    @Override
    protected void setLocationFromBoundingbox() {
        AABB aabb = getBoundingBox();
        setX((aabb.minX + aabb.maxX) / 2);
        setY(aabb.minY);
        setZ((aabb.minZ + aabb.maxZ) / 2);
    }

    @Override
    protected void setSize(float pWidth, float pHeight) {
        if (pWidth != getWidth() || pHeight != getHeight()) {
            setWidth(pWidth);
            setHeight(pHeight);
            AABB aabb = getBoundingBox();
            double d0 = (aabb.minX + aabb.maxX - pWidth) / 2;
            double d1 = (aabb.minZ + aabb.maxZ - pWidth) / 2;
            setBoundingBox(new AABB(d0, aabb.minY, d1, d0 + pWidth, aabb.minY + pHeight, d1 + pWidth));
        }
    }

    public void addRandomOffset(double x, double y, double z) {
        setPosition(getX() + random.nextDouble() * x - x / 2, getY() + random.nextDouble() * y - y / 2, getZ() + random.nextDouble() * z - z / 2);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new AMParticle(pLevel, pX, pY, pZ, sprite);
        }
    }
}
