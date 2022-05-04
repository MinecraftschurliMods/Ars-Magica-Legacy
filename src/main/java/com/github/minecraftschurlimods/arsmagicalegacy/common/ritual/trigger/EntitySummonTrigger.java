package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Context;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import java.util.Map;

/**
 *
 */
public record EntitySummonTrigger(EntityPredicate predicate) implements RitualTrigger {
    public static final Codec<EntitySummonTrigger> CODEC = RecordCodecBuilder.create(inst -> inst.group(AMUtil.ENTITY_PREDICATE.fieldOf("entity").forGetter(EntitySummonTrigger::predicate)).apply(inst, EntitySummonTrigger::new));

    public EntitySummonTrigger(EntityType<?> entityType) {
        this(EntityPredicate.Builder.entity().of(entityType).build());
    }

    public EntitySummonTrigger(TagKey<EntityType<?>> entityTypeTag) {
        this(EntityPredicate.Builder.entity().of(entityTypeTag).build());
    }

    @Override
    public void register(final Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((LivingSpawnEvent.SpecialSpawn event) -> {
            if (!(event.getEntity().getLevel() instanceof ServerLevel serverLevel)) return;
            Player player = serverLevel.getNearestPlayer(event.getEntityLiving(), 5);
            if (player != null) {
                ritual.perform(player, serverLevel, event.getEntity().blockPosition(), new Context.MapContext(Map.of("player", player, "entity", event.getEntityLiving())));
            }
        });
    }

    @Override
    public boolean trigger(final ServerLevel level, final BlockPos pos, Context ctx) {
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
