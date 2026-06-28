package at.minecraftschurli.mods.arsmagicalegacy.apiimpl;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMagic;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@SuppressWarnings("DataFlowIssue")
final class ManaHelperImpl implements ManaHelper {
    @Override
    public double getManaBase() {
        return AMServerConfig.MANA_BASE.get();
    }

    @Override
    public double getManaMultiplier() {
        return AMServerConfig.MANA_MULTIPLIER.get();
    }

    @Override
    public double getManaRegenerationMultiplier() {
        return AMServerConfig.MANA_REGENERATION.get();
    }

    @Override
    public double getMana(LivingEntity entity) {
        return entity.isDeadOrDying() ? 0 : entity.getData(AMAttachments.MANA);
    }

    @Override
    public double getMaxMana(LivingEntity entity) {
        return entity.isDeadOrDying() || !entity.getAttributes().hasAttribute(AMAttributes.MAX_MANA) ? 0 : entity.getAttributeValue(AMAttributes.MAX_MANA);
    }

    @Override
    public double getManaRegeneration(LivingEntity entity) {
        if (entity.isDeadOrDying() || !entity.getAttributes().hasAttribute(AMAttributes.MANA_REGENERATION)) return 0;
        double attributeValue = entity.getAttributeValue(AMAttributes.MANA_REGENERATION);
        if (!(entity instanceof Player player)) return attributeValue;
        MagicHelper helper = ArsMagicaApi.magicHelper();
        Registry<Skill> skills = entity.registryAccess().lookupOrThrow(AMRegistries.Keys.SKILL);
        if (helper.knows(player, skills.getOrThrow(AMMagic.MANA_REGENERATION_BOOST_3))) return attributeValue * AMServerConfig.MANA_REGENERATION_3_MULTIPLIER.get();
        if (helper.knows(player, skills.getOrThrow(AMMagic.MANA_REGENERATION_BOOST_2))) return attributeValue * AMServerConfig.MANA_REGENERATION_2_MULTIPLIER.get();
        if (helper.knows(player, skills.getOrThrow(AMMagic.MANA_REGENERATION_BOOST_1))) return attributeValue * AMServerConfig.MANA_REGENERATION_1_MULTIPLIER.get();
        return attributeValue;
    }

    @Override
    public boolean setMana(LivingEntity entity, double amount) {
        if (amount < 0) return false;
        double max = getMaxMana(entity);
        if (max <= 0) return false;
        entity.setData(AMAttachments.MANA, Math.min(amount, max));
        return true;
    }

    @Override
    public boolean increaseMana(LivingEntity entity, double amount) {
        if (amount < 0) return false;
        double max = getMaxMana(entity);
        if (max <= 0) return false;
        entity.setData(AMAttachments.MANA, Math.min(entity.getData(AMAttachments.MANA) + amount, max));
        return true;
    }

    @Override
    public boolean decreaseMana(LivingEntity entity, double amount) {
        if (amount < 0) return false;
        double max = getMaxMana(entity);
        if (max <= 0) return false;
        entity.setData(AMAttachments.MANA, Math.max(entity.getData(AMAttachments.MANA) - amount, 0));
        return true;
    }

    @Override
    public boolean setMaxMana(LivingEntity entity, double amount) {
        if (!entity.getAttributes().hasAttribute(AMAttributes.MAX_MANA)) return false;
        entity.getAttribute(AMAttributes.MAX_MANA).setBaseValue(amount);
        return true;
    }

    @Override
    public boolean setManaRegeneration(LivingEntity entity, double amount) {
        if (!entity.getAttributes().hasAttribute(AMAttributes.MANA_REGENERATION)) return false;
        entity.getAttribute(AMAttributes.MANA_REGENERATION).setBaseValue(amount);
        return true;
    }
}
