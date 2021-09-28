package com.github.minecraftschurli.arsmagicalegacy;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.easyimclib.IMCHandler;
import com.github.minecraftschurli.simplenetlib.NetworkHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Attr;

@Mod(ArsMagicaAPI.MOD_ID)
public final class ArsMagicaLegacy {
    private static final Logger LOGGER = LogManager.getLogger(ArsMagicaAPI.MOD_ID);
    private static ArsMagicaLegacy INSTANCE;

    public static final IMCHandler IMC_HANDLER = IMCHandler.create(ArsMagicaAPI.MOD_ID);
    public static final NetworkHandler NETWORK_HANDLER = NetworkHandler.create(ArsMagicaAPI.MOD_ID, "main", 0);

    private final IModInfo modInfo;

    /**
     * The Mod Constructor
     */
    public ArsMagicaLegacy() {
        INSTANCE = this;
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::createEntityAttributes);
        IMC_HANDLER.init(bus);
        AMRegistries.init(bus);
        final ModLoadingContext context = ModLoadingContext.get();
        Config.init(context);
        modInfo = context.getActiveContainer().getModInfo();
    }

    /**
     * @return the mod name
     */
    public static String getModName() {
        return INSTANCE.modInfo.getDisplayName();
    }

    /**
     * @return the mod version as a string
     */
    public static String getVersion() {
        return INSTANCE.modInfo.getVersion().toString();
    }

    private void setup(final FMLCommonSetupEvent event) {
        AMItems.initSpawnEggs();
    }

    private void createEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(AMEntities.WATER_GUARDIAN.get(), LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 75).add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).build());
    }
}
