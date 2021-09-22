package com.github.minecraftschurli.arsmagicalegacy.server;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ArsMagicaAPI.MOD_ID)
public final class ServerInit {
    @SubscribeEvent
    static void registerCommands(final RegisterCommandsEvent event) {
    }

    @SubscribeEvent
    static void registerResourceManagerListener(final AddReloadListenerEvent event) {
        event.addListener(SkillManager.instance());
    }
}
