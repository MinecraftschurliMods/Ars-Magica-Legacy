package at.minecraftschurli.mods.arsmagicalegacy.apiimpl;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.event.ManaBurnoutCostEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.event.SpellCastEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.event.SpellPartCastEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellStatModifier;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.ContingencyAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.mods.arsmagicalegacy.spell.ItemSpellIngredient;
import at.minecraftschurli.mods.arsmagicalegacy.spell.SpellDamage;
import at.minecraftschurli.mods.arsmagicalegacy.spell.ToolTiers;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.NeoForge;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

final class SpellHelperImpl implements SpellHelper {
    @Override
    public SpellCastResult cast(Spell spell, Level level, @Nullable LivingEntity caster, boolean consume, boolean awardXp) {
        if (spell.isMalformed()) return new SpellCastResult(spell).setMessage(AMTranslations.SPELL_FAIL_MALFORMED);
        if (caster != null && caster.hasEffect(AMMobEffects.SILENCE)) return new SpellCastResult(spell).setMessage(AMTranslations.SPELL_FAIL_SILENCED);
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        RegistryAccess registryAccess = level.registryAccess();
        double manaCost = 0;
        double burnoutCost = 0;
        if (caster != null) {
            if (caster.hasEffect(AMMobEffects.CLARITY)) {
                caster.removeEffect(AMMobEffects.CLARITY);
            } else {
                double mana = spell.getManaCost(registryAccess);
                double burnout = spell.grammar().getBurnoutCost(registryAccess);
                ManaBurnoutCostEvent event = NeoForge.EVENT_BUS.post(new ManaBurnoutCostEvent(caster, spell, mana, burnout));
                manaCost = event.getMana();
                burnoutCost = event.getBurnout();
            }
            SpellCastEvent.Pre event = new SpellCastEvent.Pre(caster, spell, manaCost, burnoutCost, consume, awardXp);
            if (event.isCanceled()) return new SpellCastResult(spell).setMessage(event.getCancellationMessage());
            consume = event.isConsume();
            awardXp = event.isAwardXp();
            if (consume && !(caster instanceof Player player && player.isCreative())) {
                double mana = manaHelper.getMana(caster);
                if (mana < manaCost) return new SpellCastResult(spell).setMessage(AMTranslations.SPELL_FAIL_NOT_ENOUGH_MANA);
                if (mana < manaCost + burnoutHelper.getBurnout(caster)) return new SpellCastResult(spell).setMessage(AMTranslations.SPELL_FAIL_BURNED_OUT);
            }
        }
        SpellCastResult result = castPrimary(new SpellCastContext(spell, level, caster, consume, awardXp));
        if (result.isSuccess()) {
            if (consume && caster != null && !(caster instanceof Player player && player.isCreative())) {
                manaHelper.decreaseMana(caster, manaCost + burnoutCost);
                burnoutHelper.increaseBurnout(caster, burnoutCost);
            }
            if (awardXp && caster instanceof Player player) {
                MagicHelper helper = ArsMagicaApi.magicHelper();
                Registry<Skill> registry = AMRegistries.skills(player.registryAccess());
                boolean affinityGains = registry.containsKey(AMMagic.AFFINITY_GAINS_BOOST) && helper.knows(player, registry.getOrThrow(AMMagic.AFFINITY_GAINS_BOOST));
                boolean continuous = spell.isContinuous();
                Map<Holder<Affinity>, Double> affinityShifts = spell.grammar().affinityShifts(registryAccess);
                if (continuous) {
                    affinityShifts.replaceAll((_, v) -> v * AMServerConfig.CONTINUOUS_MODIFIER.get());
                }
                if (affinityGains) {
                    affinityShifts.replaceAll((_, v) -> v * AMServerConfig.AFFINITY_GAINS_MODIFIER.get());
                }
                helper.applyAffinityShift(player, affinityShifts);
                double xp = AMServerConfig.AFFINITY_TO_XP_RATIO.get() * affinityShifts.size();
                if (continuous) {
                    xp *= AMServerConfig.CONTINUOUS_MODIFIER.get();
                }
                if (affinityGains) {
                    xp *= AMServerConfig.AFFINITY_GAINS_XP_MODIFIER.get();
                }
                helper.addXp(player, xp);
            }
        }
        if (caster != null) {
            NeoForge.EVENT_BUS.post(new SpellCastEvent.Post(caster, spell, manaCost, burnoutCost, consume, awardXp));
        }
        return result;
    }

    @Override
    public SpellCastResult castPrimary(SpellCastContext context) {
        Spell spell = context.spell();
        SpellShapeGroup shapeGroup = spell.currentShapeGroup();
        PrimarySpellShape primary = shapeGroup.primaryShape();
        List<SpellModifier> modifiers = shapeGroup.primaryModifiers();
        SpellCastResult result;
        if (primary != null) {
            result = primary.cast(modifiers, context);
            NeoForge.EVENT_BUS.post(new SpellPartCastEvent.PrimaryShape(primary, modifiers, context));
        } else {
            result = new SpellCastResult(spell).setMessage(AMTranslations.SPELL_FAIL_MALFORMED);
        }
        return result;
    }

    @Override
    public SpellCastResult castSecondary(SpellCastContext context) {
        Spell spell = context.spell();
        SpellShapeGroup shapeGroup = spell.currentShapeGroup();
        SecondarySpellShape secondary = shapeGroup.secondaryShape();
        List<SpellModifier> modifiers = shapeGroup.secondaryModifiers();
        SpellCastResult result;
        if (secondary != null) {
            result = secondary.cast(modifiers, context);
            NeoForge.EVENT_BUS.post(new SpellPartCastEvent.SecondaryShape(secondary, modifiers, context));
        } else {
            result = new SpellCastResult(spell).setMessage(AMTranslations.SPELL_FAIL_MALFORMED);
        }
        return result;
    }

    @Override
    public SpellCastResult castGrammar(SpellCastContext context) {
        Spell spell = context.spell();
        Level level = context.level();
        SpellCastResult result = new SpellCastResult(spell);
        for (Pair<SpellComponent, List<SpellModifier>> pair : spell.grammar().components()) {
            SpellComponent component = pair.getFirst();
            List<SpellModifier> modifiers = pair.getSecond();
            SpellComponentCastResult componentResult = component.cast(modifiers, context);
            if (componentResult.isSuccess()) {
                result.setSuccess();
            } else if (componentResult.isFailure()) {
                Component message = componentResult.getMessage();
                if (message != null) {
                    result.setMessage(message);
                }
            }
            context = context.setSpell(componentResult.getSpell());
            if (level.isClientSide()) {
                component.spawnParticles(modifiers, context);
            }
            NeoForge.EVENT_BUS.post(new SpellPartCastEvent.Component(component, modifiers, context));
        }
        spell = context.spell();
        SpellDamage damage = spell.dataComponents().grammar().get(AMDataComponents.SPELL_DAMAGE.get());
        if (damage != null) {
            damage.apply(level, context.caster(), context.directEntity());
            spell = spell.updateDataComponents(components -> components.updateGrammar(grammar -> grammar.remove(AMDataComponents.SPELL_DAMAGE.get())));
        }
        result.setSpell(spell);
        return result;
    }

    @Override
    public SpellCastResult castSecondaryOrGrammar(SpellCastContext context) {
        return context.spell().currentShapeGroup().secondaryShape() != null ? castSecondary(context) : castGrammar(context);
    }

    @Override
    public double getModifiedStat(double base, SpellStat stat, List<SpellModifier> modifiers, SpellCastContext context) {
        double modified = base;
        for (SpellModifier modifier : modifiers) {
            if (modifier.getStats().contains(stat)) {
                modified = modifier.getModifier(stat).modify(base, modified, context);
            }
        }
        if (context.caster() instanceof Player player && ArsMagicaApi.magicHelper().knows(player, player.registryAccess().getOrThrow(AMMagic.AUGMENTED_CASTING))) {
            Map<SpellStat, SpellStatModifier> stats = SpellStat.genericModifiers(_ -> AMServerConfig.AUGMENTED_CASTING_MULTIPLIER.get());
            if (stats.containsKey(stat)) {
                modified = stats.get(stat).modify(base, modified, context);
            }
        }
        return modified;
    }

    @Override
    public int getColor(List<SpellModifier> modifiers, Spell spell, int shapeGroupIndex) {
        return spell.dataComponents().get(shapeGroupIndex).getOrDefault(AMDataComponents.SPELL_COLOR.get(), -1);
    }

    @Override
    public List<SpellModifier> getModifiers(SpellPart part) {
        if (part.isModifier()) return List.of();
        Set<SpellStat> stats = part.getStats();
        return AMRegistries.SPELL_PARTS
            .stream()
            .filter(SpellPart::isModifier)
            .filter(p -> !Sets.intersection(stats, p.getStats()).isEmpty())
            .map(p -> (SpellModifier) p)
            .toList();
    }

    @Override
    public void setContingency(LivingEntity entity, Identifier contingency, Spell spell) {
        entity.setData(AMAttachments.CONTINGENCY, new ContingencyAttachment(contingency, spell));
    }

    @Override
    public void triggerContingency(LivingEntity entity, Identifier contingency) {
        ContingencyAttachment attachment = entity.getData(AMAttachments.CONTINGENCY);
        if (attachment.contingency().equals(contingency)) {
            castGrammar(new SpellCastContext(attachment.spell(), entity.level(), entity, entity, new EntityHitResult(entity), true, true));
        }
    }

    @Override
    public TagKey<Block> getIncorrectTagForToolTier(int toolTier) {
        return ToolTiers.INSTANCE.get(toolTier);
    }

    @Override
    public int getMaxSummons(LivingEntity entity) {
        return AMServerConfig.SUMMON_COUNT.get() + (entity instanceof Player player && ArsMagicaApi.magicHelper().knows(player, entity.registryAccess().holderOrThrow(AMMagic.EXTRA_SUMMONS)) ? AMServerConfig.EXTRA_SUMMONS_COUNT.get() : 0);
    }

    @Override
    public double getManaToBurnoutRatio() {
        return AMServerConfig.MANA_TO_BURNOUT_RATIO.get();
    }

    @Override
    public List<SpellIngredient> getRecipe(Spell spell, RegistryAccess registryAccess) {
        List<SpellIngredient> list = new ArrayList<>();
        list.add(new ItemSpellIngredient(Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(AMTags.Items.SPELLCRAFTING_START)), 1));
        spell.shapeGroups()
            .stream()
            .map(SpellShapeGroup::parts)
            .flatMap(List::stream)
            .map(part -> part.getData(registryAccess))
            .map(SpellPartData::recipe)
            .forEach(list::addAll);
        spell.grammar()
            .parts()
            .stream()
            .map(part -> part.getData(registryAccess))
            .map(SpellPartData::recipe)
            .forEach(list::addAll);
        list.add(new ItemSpellIngredient(Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(AMTags.Items.SPELLCRAFTING_END)), 1));
        return list;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public List<SpellIngredient> getFlatRecipe(Spell spell, RegistryAccess registryAccess) {
        List<SpellIngredient> result = new ArrayList<>();
        for (SpellIngredient ingredient : getRecipe(spell, registryAccess)) {
            Optional<SpellIngredient> optional = result.stream().filter(e -> e.canCombine(ingredient)).findAny();
            if (optional.isPresent()) {
                SpellIngredient previous = optional.get();
                int index = result.indexOf(previous);
                result.remove(previous);
                result.add(index, ingredient.combine(previous));
            } else {
                result.add(ingredient);
            }
        }
        return result;
    }

    @Override
    public void spawnParticles(Identifier part, List<SpellModifier> modifiers, SpellCastContext context) {
        if (!context.level().isClientSide()) return;
        HitResult hitResult = context.hitResult();
        if (hitResult == null) return;
        AMClientUtil.spawnParticles(part, switch (hitResult) {
            case BlockHitResult blockHitResult -> blockHitResult.getBlockPos().getBottomCenter();
            case EntityHitResult entityHitResult -> hitResult.getLocation().add(0, entityHitResult.getEntity().getEyeHeight(), 0);
            default -> hitResult.getLocation();
        }, getColor(modifiers, context.spell(), -1), context.caster(), context.directEntity(), hitResult);
    }
}
