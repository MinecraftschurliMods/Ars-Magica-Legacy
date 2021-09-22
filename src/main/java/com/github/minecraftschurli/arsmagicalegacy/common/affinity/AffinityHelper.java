package com.github.minecraftschurli.arsmagicalegacy.common.affinity;

import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;

public final class AffinityHelper implements IAffinityHelper {
    private static final Lazy<AffinityHelper> INSTANCE = Lazy.concurrentOf(AffinityHelper::new);

    public static AffinityHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public ItemStack getItemStackForAffinity(final ResourceLocation aff) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getItemStackForAffinity(final IAffinity aff) {
        return ItemStack.EMPTY;
    }

    @Override
    public double getAffinityDepth(final Player player, final ResourceLocation affinity) {
        return 0;
    }

    @Override
    public double getAffinityDepth(final Player player, final IAffinity affinity) {
        return 0;
    }
}
