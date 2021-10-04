package com.github.minecraftschurli.arsmagicalegacy;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.EventHandler;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.ArcaneGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.Dryad;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.FireGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.LifeGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.Mage;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.ManaCreeper;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WaterGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WinterGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.OcculusTabManager;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillManager;
import com.github.minecraftschurli.arsmagicalegacy.network.LearnSkillPacket;
import com.github.minecraftschurli.arsmagicalegacy.network.OpenOcculusGuiPacket;
import com.github.minecraftschurli.codeclib.CodecPacket;
import com.github.minecraftschurli.easyimclib.IMCHandler;
import com.github.minecraftschurli.simplenetlib.NetworkHandler;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ArsMagicaAPI.MOD_ID)
public final class ArsMagicaLegacy {
    public static final Logger LOGGER = LogManager.getLogger(ArsMagicaAPI.MOD_ID);
    private static ArsMagicaLegacy INSTANCE;

    public static final IMCHandler IMC_HANDLER = IMCHandler.create(ArsMagicaAPI.MOD_ID);
    public static final NetworkHandler NETWORK_HANDLER = NetworkHandler.create(ArsMagicaAPI.MOD_ID, "main", 0);

    /**
     * The Mod Constructor
     */
    public ArsMagicaLegacy() {
        if (INSTANCE != null) throw new IllegalStateException("Tried to create mod %s more than once!".formatted(ArsMagicaAPI.MOD_ID));
        INSTANCE = this;
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::createEntityAttributes);
        IMC_HANDLER.init(bus);
        AMRegistries.init(bus);
        EventHandler.register(bus);
        Config.init();
        registerNetworkPackets();
        CodecPacket.register(NETWORK_HANDLER);
        OcculusTabManager.instance();
        SkillManager.instance();
    }

    /**
     * @return the mod name
     */
    public static String getModName() {
        return ModLoadingContext.get().getActiveContainer().getModInfo().getDisplayName();
    }

    private void createEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(AMEntities.WATER_GUARDIAN.get(), WaterGuardian.createAttributes().build());
        event.put(AMEntities.FIRE_GUARDIAN.get(), FireGuardian.createAttributes().build());
        event.put(AMEntities.EARTH_GUARDIAN.get(), EarthGuardian.createAttributes().build());
        event.put(AMEntities.AIR_GUARDIAN.get(), AirGuardian.createAttributes().build());
        event.put(AMEntities.WINTER_GUARDIAN.get(), WinterGuardian.createAttributes().build());
        event.put(AMEntities.LIGHTNING_GUARDIAN.get(), LightningGuardian.createAttributes().build());
        event.put(AMEntities.NATURE_GUARDIAN.get(), NatureGuardian.createAttributes().build());
        event.put(AMEntities.LIFE_GUARDIAN.get(), LifeGuardian.createAttributes().build());
        event.put(AMEntities.ARCANE_GUARDIAN.get(), ArcaneGuardian.createAttributes().build());
        event.put(AMEntities.ENDER_GUARDIAN.get(), EnderGuardian.createAttributes().build());
        event.put(AMEntities.DRYAD.get(), Dryad.createAttributes().build());
        event.put(AMEntities.MAGE.get(), Mage.createAttributes().build());
        event.put(AMEntities.MANA_CREEPER.get(), ManaCreeper.createAttributes().build());
    }

    private void registerNetworkPackets() {
        NETWORK_HANDLER.register(LearnSkillPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(OpenOcculusGuiPacket.class, NetworkDirection.PLAY_TO_CLIENT);
    }
}
