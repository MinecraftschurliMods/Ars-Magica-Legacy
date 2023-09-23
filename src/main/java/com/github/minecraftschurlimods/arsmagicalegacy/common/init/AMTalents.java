package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public interface AMTalents {
    List<ResourceLocation> ALL = new ArrayList<>();
    ResourceLocation AFFINITY_GAINS_BOOST = registerTalent("affinity_gains_boost");
    ResourceLocation AUGMENTED_CASTING    = registerTalent("augmented_casting"); //TODO
    /*ResourceLocation EXTRA_SUMMONS        = registerTalent("extra_summons");
    ResourceLocation MAGE_BAND_1          = registerTalent("mage_band_1");
    ResourceLocation MAGE_BAND_2          = registerTalent("mage_band_2");*/
    ResourceLocation MANA_REGEN_BOOST_1   = registerTalent("mana_regen_boost_1");
    ResourceLocation MANA_REGEN_BOOST_2   = registerTalent("mana_regen_boost_2");
    ResourceLocation MANA_REGEN_BOOST_3   = registerTalent("mana_regen_boost_3");
    ResourceLocation SHIELD_OVERLOAD      = registerTalent("shield_overload");
    ResourceLocation SPELL_MOTION         = registerTalent("spell_motion");

    private static ResourceLocation registerTalent(String name) {
        ResourceLocation rl = new ResourceLocation(ArsMagicaAPI.MOD_ID, name);
        ALL.add(rl);
        return rl;
    }
}
