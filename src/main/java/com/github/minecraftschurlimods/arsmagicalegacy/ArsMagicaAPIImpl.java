package com.github.minecraftschurlimods.arsmagicalegacy;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IBurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IContingencyHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IRiftHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredientType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellTransformation;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ContingencyHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.RiftHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.network.OpenOcculusGuiPacket;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Unmodifiable;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;
import java.util.Optional;

public final class ArsMagicaAPIImpl implements ArsMagicaAPI {

    @Override
    public ItemStack getBookStack() {
        if (ModList.get().isLoaded("patchouli")) {
            return PatchouliAPI.get().getBookStack(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_compendium"));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public IForgeRegistry<SkillPoint> getSkillPointRegistry() {
        return AMRegistries.SKILL_POINT_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<Affinity> getAffinityRegistry() {
        return AMRegistries.AFFINITY_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<ISpellPart> getSpellPartRegistry() {
        return AMRegistries.SPELL_PART_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<ContingencyType> getContingencyTypeRegistry() {
        return AMRegistries.CONTINGENCY_TYPE_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<Codec<? extends RitualTrigger>> getRitualTriggerTypeRegistry() {
        return AMRegistries.RITUAL_TRIGGER_TYPE_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<Codec<? extends RitualRequirement>> getRitualRequirementTypeRegistry() {
        return AMRegistries.RITUAL_REQUIREMENT_TYPE_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<Codec<? extends RitualEffect>> getRitualEffectTypeRegistry() {
        return AMRegistries.RITUAL_EFFECT_TYPE_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<SpellIngredientType<?>> getSpellIngredientTypeRegistry() {
        return AMRegistries.SPELL_INGREDIENT_TYPE_REGISTRY.get();
    }

    @Override
    public ISpellDataManager getSpellDataManager() {
        return SpellDataManager.instance();
    }

    @Unmodifiable
    @Override
    public ISkillHelper getSkillHelper() {
        return SkillHelper.instance();
    }

    @Unmodifiable
    @Override
    public IAffinityHelper getAffinityHelper() {
        return AffinityHelper.instance();
    }

    @Unmodifiable
    @Override
    public IMagicHelper getMagicHelper() {
        return MagicHelper.instance();
    }

    @Unmodifiable
    @Override
    public IManaHelper getManaHelper() {
        return ManaHelper.instance();
    }

    @Unmodifiable
    @Override
    public IBurnoutHelper getBurnoutHelper() {
        return BurnoutHelper.instance();
    }

    @Unmodifiable
    @Override
    public ISpellHelper getSpellHelper() {
        return SpellHelper.instance();
    }

    @Unmodifiable
    @Override
    public IRiftHelper getRiftHelper() {
        return RiftHelper.instance();
    }

    @Unmodifiable
    @Override
    public IEtheriumHelper getEtheriumHelper() {
        return EtheriumHelper.instance();
    }

    @Unmodifiable
    @Override
    public IContingencyHelper getContingencyHelper() {
        return ContingencyHelper.instance();
    }

    @Override
    public void openOcculusGui(Player player) {
        if (player instanceof ServerPlayer) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new OpenOcculusGuiPacket(), player);
        } else if (player.isLocalPlayer()) {
            ClientHelper.openOcculusGui();
        }
    }

    @Override
    public void openSpellCustomizationGui(Level level, Player player, ItemStack stack) {
        if (!level.isClientSide()) return;
        ClientHelper.openSpellCustomizationGui(stack);
    }

    @Override
    public void openSpellRecipeGui(Level level, Player player, ItemStack stack) {
        if (!level.isClientSide()) return;
        ClientHelper.openSpellRecipeGui(stack, true, 0, null);
    }

    @Override
    public ISpell makeSpell(List<ShapeGroup> shapeGroups, SpellStack spellStack, CompoundTag additionalData) {
        return new Spell(shapeGroups, spellStack, additionalData);
    }

    @Override
    public ISpell makeSpell(SpellStack spellStack, ShapeGroup... shapeGroups) {
        return Spell.of(spellStack, shapeGroups);
    }

    @Override
    public Optional<BlockState> getSpellTransformationFor(BlockState block, Level level, ResourceLocation spellPart) {
        if (level.isClientSide()) return Optional.empty();
        return level.registryAccess().registryOrThrow(SpellTransformation.REGISTRY_KEY).stream().filter(spellTransformation -> spellTransformation.spellPart().equals(spellPart) && spellTransformation.from().test(block, level.random)).findFirst().map(SpellTransformation::to);
    }
}
