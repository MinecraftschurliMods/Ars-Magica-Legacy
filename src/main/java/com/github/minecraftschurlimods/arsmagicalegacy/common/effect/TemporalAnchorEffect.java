package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.Objects;
import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ATTACHMENT_TYPES;

public class TemporalAnchorEffect extends AMMobEffect {
    private static final Supplier<AttachmentType<StateSnapshot>> TEMPORAL_ANCHOR_SNAPSHOT = ATTACHMENT_TYPES.register("temporal_anchor", () -> AttachmentType.<StateSnapshot>builder(() -> null).serialize(StateSnapshot.CODEC, Objects::nonNull).copyOnDeath().build());

    public TemporalAnchorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xa2a2a2);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        entity.setData(TEMPORAL_ANCHOR_SNAPSHOT, StateSnapshot.from(entity));
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        StateSnapshot snapshot = entity.removeData(TEMPORAL_ANCHOR_SNAPSHOT);
        if (snapshot != null) {
            snapshot.apply(entity);
        }
    }

    public record StateSnapshot(
            Vec3 position,
            float pitch,
            float yaw,
            float headYaw,
            float mana,
            float burnout,
            float health
    ) {
        public static Codec<StateSnapshot> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Vec3.CODEC.fieldOf("position").forGetter(StateSnapshot::position),
                Codec.FLOAT.fieldOf("pitch").forGetter(StateSnapshot::pitch),
                Codec.FLOAT.fieldOf("yaw").forGetter(StateSnapshot::yaw),
                Codec.FLOAT.fieldOf("headYaw").forGetter(StateSnapshot::headYaw),
                Codec.FLOAT.fieldOf("mana").forGetter(StateSnapshot::mana),
                Codec.FLOAT.fieldOf("burnout").forGetter(StateSnapshot::burnout),
                Codec.FLOAT.fieldOf("health").forGetter(StateSnapshot::health)
        ).apply(inst, StateSnapshot::new));

        public static StateSnapshot from(LivingEntity entity) {
            var api = ArsMagicaAPI.get();
            return new StateSnapshot(
                    entity.position(),
                    entity.getXRot(),
                    entity.getYRot(),
                    entity.getYHeadRot(),
                    api.getManaHelper().getMana(entity),
                    api.getBurnoutHelper().getBurnout(entity),
                    entity.getHealth()
            );
        }

        public void apply(LivingEntity entity) {
            var api = ArsMagicaAPI.get();
            entity.setPos(position());
            entity.setXRot(pitch());
            entity.setYRot(yaw());
            entity.setYHeadRot(headYaw());
            api.getManaHelper().setMana(entity, mana());
            api.getBurnoutHelper().setBurnout(entity, burnout());
            entity.setHealth(health());
        }
    }
}
