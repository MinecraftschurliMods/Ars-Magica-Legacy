package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual;

import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import org.apache.logging.log4j.LogManager;

/**
 *
 */
public final class RitualManager extends CodecDataManager<Ritual> {
    public RitualManager() {
        super("am_rituals", Ritual.CODEC, LogManager.getLogger());
    }
}
