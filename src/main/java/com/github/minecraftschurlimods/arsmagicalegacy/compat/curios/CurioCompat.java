package com.github.minecraftschurlimods.arsmagicalegacy.compat.curios;

import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.ICompatHandler;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.curios.client.MagitechGogglesCurioRenderer;
import com.google.common.collect.Multimap;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;
import java.util.function.BiFunction;

@CompatManager.ModCompat("curios")
public class CurioCompat implements ICompatHandler {
    @Override
    public void clientInit(FMLClientSetupEvent event) {
        MagitechGogglesCurioRenderer.register();
    }

    @Override
    public void imcEnqueue(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("head").build());
    }

    public ICapabilityProvider makeCurioCap(ItemStack stack, BiFunction<String, ItemStack, Multimap<Attribute, AttributeModifier>> proxy) {
        return new AttributeModCapabilityProvider(stack, proxy);
    }

    private static class AttributeModCapabilityProvider extends GenericCapabilityProvider<ICurio> {
        public AttributeModCapabilityProvider(ItemStack stack, BiFunction<String, ItemStack, Multimap<Attribute, AttributeModifier>> proxy) {
            super(CuriosCapability.ITEM, new ICurio() {
                @Override
                public ItemStack getStack() {
                    return stack;
                }

                @Override
                public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
                    return proxy.apply(slotContext.identifier(), getStack());
                }
            });
        }
    }

    private static class GenericCapabilityProvider<T> implements ICapabilityProvider {
        private final Capability<T> capability;
        private final LazyOptional<T> inst;

        private GenericCapabilityProvider(Capability<T> capability, T instance) {
            this.capability = capability;
            inst = LazyOptional.of(() -> instance);
        }

        @Override
        public <X> LazyOptional<X> getCapability(Capability<X> cap, @Nullable Direction side) {
            return capability.orEmpty(cap, inst);
        }
    }
}
