package at.minecraftschurli.mods.arsmagicalegacy.spell.shape;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Chain extends PrimarySpellShape {
    public Chain() {
        super(AMSpells.RANGE_STAT, AMSpells.TARGET_NON_SOLID_STAT);
    }

    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        LivingEntity caster = context.caster();
        if (caster == null) return new SpellCastResult(context.spell()).setMessage(AMTranslations.SPELL_FAIL_NO_CASTER);
        context = context.setDirectEntityAndHitResult(caster, AMUtil.getHitResult(caster, modifiers, context, AMServerConfig.CHAIN_RANGE.get(), 0));
        HitResult hitResult = context.hitResult();
        SpellHelper helper = ArsMagicaApi.spellHelper();
        if (hitResult instanceof BlockHitResult) return helper.castSecondaryOrGrammar(context);
        if (!(hitResult instanceof EntityHitResult ehr)) return new SpellCastResult(context.spell());
        SpellCastResult result = helper.castSecondaryOrGrammar(context);
        context.setSpell(result.getSpell());
        for (Entity entity : getEntities(ehr.getEntity(), modifiers, context, caster)) {
            SpellCastResult newResult = helper.castSecondaryOrGrammar(context.setHitResult(new EntityHitResult(entity)));
            if (newResult.isSuccess()) {
                result.setSuccess();
            }
            Component message = newResult.getMessage();
            if (message != null) {
                result.setMessage(message);
            }
        }
        return result;
    }

    @Override
    public boolean isContinuous() {
        return true;
    }

    public static List<Entity> getEntities(Entity initial, List<SpellModifier> modifiers, SpellCastContext context, LivingEntity caster) {
        double range = ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.CHAIN_EXTRA_TARGETS_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context);
        List<Entity> list = new ArrayList<>();
        Entity current = initial;
        Entity next = null;
        Entity otherNext = null;
        Predicate<Entity> predicate = e -> !list.contains(e) && !caster.getUUID().equals(e.getUUID()) && !(e instanceof LivingEntity living && living.isDeadOrDying());
        for (int i = 0; i < AMServerConfig.CHAIN_EXTRA_TARGETS.get(); i++) {
            EntityType<?> currentType = current.getType();
            for (Entity e : initial.level().getEntities(current, new AABB(current.position().subtract(range), current.position().add(range)), predicate)) {
                double distance = e.distanceTo(current);
                if (e.getType() == currentType) {
                    if (next == null || next.distanceTo(current) > distance) {
                        next = e;
                    }
                } else if (next == null && (otherNext == null || otherNext.distanceTo(current) > distance)) {
                    otherNext = e;
                }
            }
            if (next == null) {
                next = otherNext;
            }
            if (next == null) break;
            list.add(next);
            current = next;
            next = null;
        }
        return list;
    }
}
