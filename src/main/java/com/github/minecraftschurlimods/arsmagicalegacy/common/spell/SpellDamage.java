package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMExtraCodecs;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.DamageSourceWithItemStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record SpellDamage(Map<Integer, Map<ResourceKey<DamageType>, Pair<Float, ItemStack>>> damage) {
    private static final Codec<Map<ResourceKey<DamageType>, Pair<Float, ItemStack>>> DAMAGE_CODEC =
        Codec.unboundedMap(ResourceKey.codec(Registries.DAMAGE_TYPE), Codec.pair(Codec.FLOAT, ItemStack.CODEC));
    private static final StreamCodec<RegistryFriendlyByteBuf, Map<ResourceKey<DamageType>, Pair<Float, ItemStack>>> DAMAGE_STREAM_CODEC =
        AMExtraCodecs.mapStreamCodec(ResourceKey.streamCodec(Registries.DAMAGE_TYPE), AMExtraCodecs.pairStreamCodec(ByteBufCodecs.FLOAT, ItemStack.STREAM_CODEC));
    public static final Codec<SpellDamage> CODEC =
        Codec.unboundedMap(AMExtraCodecs.STRING_ENCODED_INT_CODEC, DAMAGE_CODEC).xmap(SpellDamage::new, SpellDamage::damage);
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellDamage> STREAM_CODEC =
        AMExtraCodecs.mapStreamCodec(ByteBufCodecs.INT, DAMAGE_STREAM_CODEC).map(SpellDamage::new, SpellDamage::damage);
    public static final SpellDamage EMPTY = new SpellDamage(Map.of());

    public SpellDamage setDamage(Entity entity, ResourceKey<DamageType> type, float amount, ItemStack stack) {
        Map<Integer, Map<ResourceKey<DamageType>, Pair<Float, ItemStack>>> newDamage = new HashMap<>(damage);
        Map<ResourceKey<DamageType>, Pair<Float, ItemStack>> map = new HashMap<>(newDamage.getOrDefault(entity.getId(), new HashMap<>()));
        map.put(type, Pair.of(amount, stack));
        newDamage.put(entity.getId(), map);
        return new SpellDamage(newDamage);
    }

    public void apply(Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        Registry<DamageType> damageTypes = level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE);
        for (Map.Entry<Integer, Map<ResourceKey<DamageType>, Pair<Float, ItemStack>>> damageEntry : damage.entrySet()) {
            Entity entity = level.getEntity(damageEntry.getKey());
            if (entity == null) continue;
            int invulnerableTime = entity.invulnerableTime;
            boolean hurtMarked = entity.hurtMarked;
            for (Map.Entry<ResourceKey<DamageType>, Pair<Float, ItemStack>> entry : damageEntry.getValue().entrySet()) {
                Optional<? extends Holder<DamageType>> holder = damageTypes.get(entry.getKey());
                if (holder.isEmpty()) continue;
                Pair<Float, ItemStack> value = entry.getValue();
                if (caster != null) {
                    ItemStack oldStack = caster.getMainHandItem();
                    caster.setItemInHand(InteractionHand.MAIN_HAND, value.getSecond());
                    DamageSource source = new DamageSourceWithItemStack(holder.get(), directEntity, caster, value.getSecond());
                    if (entity instanceof LivingEntity living && living.isInvulnerableTo(serverLevel, source)) {
                        caster.setItemInHand(InteractionHand.MAIN_HAND, oldStack);
                        continue;
                    }
                    entity.hurtServer(serverLevel, source, value.getFirst());
                    caster.setItemInHand(InteractionHand.MAIN_HAND, oldStack);
                } else {
                    DamageSource source = new DamageSourceWithItemStack(holder.get(), directEntity, null, value.getSecond());
                    if (entity instanceof LivingEntity living && living.isInvulnerableTo(serverLevel, source)) continue;
                    entity.hurtServer(serverLevel, source, value.getFirst());
                }
                invulnerableTime = Math.max(invulnerableTime, entity.invulnerableTime);
                hurtMarked |= entity.hurtMarked;
                entity.invulnerableTime = 0;
                entity.hurtMarked = false;
            }
            entity.invulnerableTime = invulnerableTime;
            entity.hurtMarked = hurtMarked;
        }
    }
}
