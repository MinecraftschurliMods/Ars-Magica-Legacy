package com.github.minecraftschurlimods.arsmagicalegacy.common.apiimpl;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import vazkii.patchouli.api.PatchouliAPI;

public final class ArsMagicaApiImpl extends ArsMagicaApi {
    private static final Identifier ARCANE_COMPENDIUM = id("arcane_compendium");
    private static final AbilityHelper ABILITY_HELPER = new AbilityHelperImpl();
    private static final BurnoutHelper BURNOUT_HELPER = new BurnoutHelperImpl();
    private static final MagicHelper MAGIC_HELPER = new MagicHelperImpl();
    private static final ManaHelper MANA_HELPER = new ManaHelperImpl();
    private static final SpellHelper SPELL_HELPER = new SpellHelperImpl();

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected ItemStackTemplate getBook() {
        return PatchouliAPI.get().getBookStackTemplate(ARCANE_COMPENDIUM);
    }

    @Override
    protected AbilityHelper getAbilityHelper() {
        return ABILITY_HELPER;
    }

    @Override
    protected BurnoutHelper getBurnoutHelper() {
        return BURNOUT_HELPER;
    }

    @Override
    protected MagicHelper getMagicHelper() {
        return MAGIC_HELPER;
    }

    @Override
    protected ManaHelper getManaHelper() {
        return MANA_HELPER;
    }

    @Override
    protected SpellHelper getSpellHelper() {
        return SPELL_HELPER;
    }
}
