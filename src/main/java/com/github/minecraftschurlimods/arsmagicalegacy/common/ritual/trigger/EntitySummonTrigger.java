package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Context;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
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

public record EntitySummonTrigger(EntityPredicate predicate) implements RitualTrigger {
    public static final Codec<EntitySummonTrigger> CODEC = RecordCodecBuilder.create(inst -> inst.group(CodecHelper.ENTITY_PREDICATE.fieldOf("entity").forGetter(EntitySummonTrigger::predicate)).apply(inst, EntitySummonTrigger::new));

    public static EntitySummonTrigger simple(EntityType<?> entityType) {
        return new EntitySummonTrigger(EntityPredicate.Builder.entity().of(entityType).build());
    }

    public static EntitySummonTrigger tag(TagKey<EntityType<?>> entityTypeTag) {
        return new EntitySummonTrigger(EntityPredicate.Builder.entity().of(entityTypeTag).build());
    }

    @Override
    public void register(Ritual ritual) {}

    @Override
    public boolean trigger(Player player, ServerLevel level, BlockPos pos, Context ctx) {
        LivingEntity entity = ctx.get("entity", LivingEntity.class);
        if (entity == null) return false;
        entity.kill();
        return true;
    }

    @Override
    public Codec<? extends RitualTrigger> codec() {
        return CODEC;
    }
}
