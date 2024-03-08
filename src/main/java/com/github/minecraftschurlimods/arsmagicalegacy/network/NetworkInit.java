package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDataManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public final class NetworkInit {
    private static final String NETWORK_VERSION = "5";
    private NetworkInit() {}

    public static void init(IEventBus bus) {
        bus.addListener(NetworkInit::registerNetworkPackets);
    }

    private static void registerNetworkPackets(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(ArsMagicaAPI.MOD_ID).versioned(NETWORK_VERSION);
        registrar
                .play(InscriptionTableSyncPacket.ID,        InscriptionTableSyncPacket::new,        builder -> builder.server(InscriptionTableSyncPacket::handle))
                .play(LearnSkillPacket.ID,                  LearnSkillPacket::new,                  builder -> builder.server(LearnSkillPacket::handle))
                .play(NextShapeGroupPacket.ID,              NextShapeGroupPacket::new,              builder -> builder.server(NextShapeGroupPacket::handle))
                .play(SetLecternPagePacket.ID,              SetLecternPagePacket::new,              builder -> builder.server(SetLecternPagePacket::handle))
                .play(SpellBookNextSpellPacket.ID,          SpellBookNextSpellPacket::new,          builder -> builder.server(SpellBookNextSpellPacket::handle))
                .play(SpellIconSelectPacket.ID,             SpellIconSelectPacket::new,             builder -> builder.server(SpellIconSelectPacket::handle))
                .play(TakeSpellRecipeFromLecternPacket.ID,  TakeSpellRecipeFromLecternPacket::new,  builder -> builder.server(TakeSpellRecipeFromLecternPacket::handle))
                .play(InscriptionTableCreateSpellPacket.ID, InscriptionTableCreateSpellPacket::new, builder -> builder.server(InscriptionTableCreateSpellPacket::handle))
                .play(BEClientSyncPacket.ID,                BEClientSyncPacket::new,                builder -> builder.client(BEClientSyncPacket::handle))
                .play(OpenOcculusGuiPacket.ID,              OpenOcculusGuiPacket::new,              builder -> builder.client(OpenOcculusGuiPacket::handle))
                .play(OpenSpellRecipeGuiInLecternPacket.ID, OpenSpellRecipeGuiInLecternPacket::new, builder -> builder.client(OpenSpellRecipeGuiInLecternPacket::handle))
                .play(SpawnAMParticlesPacket.ID,            SpawnAMParticlesPacket::new,            builder -> builder.client(SpawnAMParticlesPacket::handle))
                .play(SpawnComponentParticlesPacket.ID,     SpawnComponentParticlesPacket::new,     builder -> builder.client(SpawnComponentParticlesPacket::handle))
        ;
        SkillHelper.registerSyncPacket(registrar);
        AffinityHelper.registerSyncPacket(registrar);
        BurnoutHelper.registerSyncPacket(registrar);
        ManaHelper.registerSyncPacket(registrar);
        MagicHelper.registerSyncPacket(registrar);
        SpellDataManager.instance().subscribeAsSyncable(registrar);
    }
}
