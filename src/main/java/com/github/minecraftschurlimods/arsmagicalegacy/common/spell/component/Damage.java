package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEnchantments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDamage;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
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
