package com.github.minecraftschurli.arsmagicalegacy.common.affinity;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.github.minecraftschurli.codeclib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class AffinityHelper implements IAffinityHelper {
    private static final Lazy<AffinityHelper> INSTANCE = Lazy.concurrentOf(AffinityHelper::new);
    private static final CodecPacket.CodecPacketFactory<AffinityHolder> SYNC_PACKET = CodecPacket.create(AffinityHolder.CODEC, AffinityHelper::handleSync);

    public static AffinityHelper instance() {
        return INSTANCE.get();
    }

    private static final Capability<AffinityHolder> AFFINITY = CapabilityManager.get(new CapabilityToken<>() {});

    public AffinityHolder getAffinityHolder(Player player) {
        return player.getCapability(AFFINITY).orElseThrow(() -> new RuntimeException("Could not retrieve affinity capability for player %s".formatted(player.getUUID())));
    }

    @Override
    public ItemStack getEssenceForAffinity(ResourceLocation affinity) {
        return getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), affinity);
    }

    @Override
    public ItemStack getEssenceForAffinity(IAffinity affinity) {
        return getEssenceForAffinity(affinity.getId());
    }

    @Override
    public ItemStack getTomeForAffinity(ResourceLocation affinity) {
        return getStackForAffinity(AMItems.AFFINITY_TOME.get(), affinity);
    }

    @Override
    public ItemStack getTomeForAffinity(IAffinity affinity) {
        return getTomeForAffinity(affinity.getId());
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceLocation aff) {
        ItemStack stack = new ItemStack(item);
        Optional.ofNullable(ArsMagicaAPI.get().getAffinityRegistry().getValue(aff)).ifPresent(affinity -> item.setAffinity(stack, affinity));
        return stack;
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, IAffinity affinity) {
        return getStackForAffinity(item, affinity.getId());
    }

    @Override
    public IAffinity getAffinityForStack(ItemStack stack) {
        if (stack.getItem() instanceof IAffinityItem item) {
            return item.getAffinity(stack);
        }
        return Objects.requireNonNull(ArsMagicaAPI.get().getAffinityRegistry().getValue(IAffinity.NONE));
    }

    @Override
    public double getAffinityDepth(Player player, ResourceLocation affinity) {
        return getAffinityHolder(player).getAffinityDepth(affinity);
    }

    @Override
    public double getAffinityDepth(Player player, IAffinity affinity) {
        return getAffinityDepth(player, affinity.getId());
    }

    public static Capability<AffinityHolder> getCapability() {
        return AFFINITY;
    }

    public void syncToPlayer(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(SYNC_PACKET.create(getAffinityHolder(player)), player);
    }

    private static void handleSync(AffinityHolder affinityHolder, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(AFFINITY).ifPresent(holder -> holder.onSync(affinityHolder)));
    }

    public record AffinityHolder(Map<ResourceLocation, Double> depths) {
        public static final Codec<AffinityHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                CodecHelper.mapOf(ResourceLocation.CODEC, Codec.DOUBLE).fieldOf("depths").forGetter(AffinityHolder::depths)
        ).apply(inst, AffinityHolder::new));

        public static AffinityHolder empty() {
            return new AffinityHolder(new HashMap<>());
        }

        /**
             * Get the depths map for this affinity holder.
             *
             * @return the unmodifiable view of the affinity depths map
             */
        public Map<ResourceLocation, Double> depths() {
            return Collections.unmodifiableMap(depths);
        }

        public void onSync(AffinityHolder affinityHolder) {
            depths.clear();
            depths.putAll(affinityHolder.depths());
        }

        /**
         * Get the depth for a given affinity.
         *
         * @param affinity the affinity to get the depth for
         * @return the depth of the requested affinity or 0 if not available
         */
        public double getAffinityDepth(ResourceLocation affinity) {
            return depths().getOrDefault(affinity, 0d);
        }

        /**
         * Get the depth for a given affinity.
         *
         * @param affinity the affinity to get the depth for
         * @return the depth of the requested affinity or 0 if not available
         */
        public double getAffinityDepth(IAffinity affinity) {
            return getAffinityDepth(affinity.getId());
        }
    }
}
