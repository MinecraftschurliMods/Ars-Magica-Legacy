package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDataManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class NetworkInit {
    private static final String NETWORK_VERSION = "5";
    private NetworkInit() {}

    public static void init(IEventBus bus) {
        bus.addListener(NetworkInit::registerNetworkPackets);
    }

    private static void registerNetworkPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(NETWORK_VERSION);
        registrar
                .playToServer(InscriptionTableSyncPacket.TYPE,        InscriptionTableSyncPacket.STREAM_CODEC,        InscriptionTableSyncPacket::handle)
                .playToServer(LearnSkillPacket.TYPE,                  LearnSkillPacket.STREAM_CODEC,                  LearnSkillPacket::handle)
                .playToServer(NextShapeGroupPacket.TYPE,              NextShapeGroupPacket.STREAM_CODEC,              NextShapeGroupPacket::handle)
                .playToServer(SetLecternPagePacket.TYPE,              SetLecternPagePacket.STREAM_CODEC,              SetLecternPagePacket::handle)
                .playToServer(SpellBookNextSpellPacket.TYPE,          SpellBookNextSpellPacket.STREAM_CODEC,          SpellBookNextSpellPacket::handle)
                .playToServer(SpellIconSelectPacket.TYPE,             SpellIconSelectPacket.STREAM_CODEC,             SpellIconSelectPacket::handle)
                .playToServer(TakeSpellRecipeFromLecternPacket.TYPE,  TakeSpellRecipeFromLecternPacket.STREAM_CODEC,  TakeSpellRecipeFromLecternPacket::handle)
                .playToServer(InscriptionTableCreateSpellPacket.TYPE, InscriptionTableCreateSpellPacket.STREAM_CODEC, InscriptionTableCreateSpellPacket::handle)
                //.playToClient(BEClientSyncPacket.TYPE,                BEClientSyncPacket.STREAM_CODEC,                BEClientSyncPacket::handle)
                .playToClient(OpenOcculusGuiPacket.TYPE,              OpenOcculusGuiPacket.STREAM_CODEC,              OpenOcculusGuiPacket::handle)
                .playToClient(OpenSpellRecipeGuiInLecternPacket.TYPE, OpenSpellRecipeGuiInLecternPacket.STREAM_CODEC, OpenSpellRecipeGuiInLecternPacket::handle)
                .playToClient(SpawnAMParticlesPacket.TYPE,            SpawnAMParticlesPacket.STREAM_CODEC,            SpawnAMParticlesPacket::handle)
                .playToClient(SpawnComponentParticlesPacket.TYPE,     SpawnComponentParticlesPacket.STREAM_CODEC,     SpawnComponentParticlesPacket::handle)
        ;
        SkillHelper.registerSyncPacket(registrar);
        AffinityHelper.registerSyncPacket(registrar);
        BurnoutHelper.registerSyncPacket(registrar);
        ManaHelper.registerSyncPacket(registrar);
        MagicHelper.registerSyncPacket(registrar);
        SpellDataManager.instance().subscribeAsSyncable(registrar);
    }
}
