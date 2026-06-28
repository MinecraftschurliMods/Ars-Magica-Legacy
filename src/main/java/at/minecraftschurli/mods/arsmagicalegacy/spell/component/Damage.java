package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEnchantments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.spell.SpellDamage;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Damage extends SpellComponent.CastEntity {
    private final Function<@Nullable LivingEntity, ResourceKey<DamageType>> damageType;

    public Damage(Function<@Nullable LivingEntity, ResourceKey<DamageType>> damageType) {
        super(AMSpells.DAMAGE_STAT, AMSpells.DISMEMBERING_STAT, AMSpells.FORTUNE_STAT, AMSpells.HEALING_STAT);
        this.damageType = damageType;
    }

    public Damage(ResourceKey<DamageType> damageType) {
        this(_ -> damageType);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        Entity target = hitResult.getEntity();
        double damage = AMServerConfig.DAMAGE_DAMAGE.get();
        SpellHelper helper = ArsMagicaApi.spellHelper();
        if (damage < 0) {
            if (target instanceof LivingEntity living) {
                living.heal((float) helper.getModifiedStat(-damage, AMSpells.HEALING_STAT, modifiers, context));
            }
            return SpellComponentCastResult.success(spell);
        }
        if (context.level() instanceof ServerLevel level && !Objects.requireNonNull(level.getServer()).getGameRules().get(GameRules.PVP) && target instanceof Player) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_DAMAGE_PVP);
        float finalDamage = (float) helper.getModifiedStat(damage, AMSpells.DAMAGE_STAT, modifiers, context);
        ItemStack stack = AMUtil.getEnchantedSpell(modifiers, context, Map.of(Enchantments.LOOTING, AMSpells.FORTUNE_STAT, AMEnchantments.DISMEMBERING, AMSpells.DISMEMBERING_STAT));
        spell = spell.updateDataComponents(map -> map.updateGrammar(grammar -> grammar.set(AMDataComponents.SPELL_DAMAGE.get(), grammar.getOrDefault(AMDataComponents.SPELL_DAMAGE.get(), SpellDamage.EMPTY).setDamage(target, damageType.apply(context.caster()), finalDamage, stack))));
        return SpellComponentCastResult.success(spell);
    }
}
