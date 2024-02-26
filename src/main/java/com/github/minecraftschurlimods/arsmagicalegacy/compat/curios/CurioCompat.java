package com.github.minecraftschurlimods.arsmagicalegacy.compat.curios;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.ICompatHandler;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.curios.client.MagitechGogglesCurioRenderer;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

@CompatManager.ModCompat("curios")
public class CurioCompat implements ICompatHandler {
    @Override
    public void clientInit(FMLClientSetupEvent event) {
        MagitechGogglesCurioRenderer.register();
    }

    @Override
    public void imcEnqueue(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("head").build());
    }

    @Override
    public void preInit(IEventBus modBus) {
        modBus.addListener((RegisterCapabilitiesEvent event) -> event.registerItem(CuriosCapability.ITEM, (stack, $) -> new ICurio() {
            @Override
            public ItemStack getStack() {
                return stack;
            }

            @Override
            public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
                return stack.getAttributeModifiers(EquipmentSlot.HEAD);
            }
        }, AMItems.MAGITECH_GOGGLES));
    }
}
