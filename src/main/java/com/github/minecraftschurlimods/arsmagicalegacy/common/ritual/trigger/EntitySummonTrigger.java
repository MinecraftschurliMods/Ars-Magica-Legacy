package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Context;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualTrigger;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import java.util.Map;

public record EntitySummonTrigger(EntityPredicate predicate) implements RitualTrigger {
    public static final Codec<EntitySummonTrigger> CODEC = RecordCodecBuilder.create(inst -> inst.group(CodecHelper.ENTITY_PREDICATE.fieldOf("entity").forGetter(EntitySummonTrigger::predicate)).apply(inst, EntitySummonTrigger::new));

    public EntitySummonTrigger(EntityType<?> entityType) {
        this(EntityPredicate.Builder.entity().of(entityType).build());
    }

    public EntitySummonTrigger(TagKey<EntityType<?>> entityTypeTag) {
        this(EntityPredicate.Builder.entity().of(entityTypeTag).build());
    }

    @Override
    public void register(final Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((LivingSpawnEvent event) -> {// TODO: find a better event to do this
            if (!(event.getEntity().getLevel() instanceof ServerLevel serverLevel)) return;
            LivingEntity entity = event.getEntityLiving();
            for (final Player player : serverLevel.getEntitiesOfClass(Player.class, AABB.ofSize(entity.position(), 5, 5, 5))) {
                if (ritual.perform(player, serverLevel, event.getEntity().blockPosition(), new Context.MapContext(Map.of("entity", entity)))) {
                    return;
                }
            }
        });
    }

    @Override
    public boolean trigger(final Player player, final ServerLevel level, final BlockPos pos, Context ctx) {
        LivingEntity entity = ctx.get("entity", LivingEntity.class);
        if (entity == null || !predicate.matches(level, Vec3.atCenterOf(pos), entity)) return false;
        entity.kill();
        return true;
    }

    @Override
    public Codec<? extends RitualTrigger> codec() {
        return CODEC;
    }
}
