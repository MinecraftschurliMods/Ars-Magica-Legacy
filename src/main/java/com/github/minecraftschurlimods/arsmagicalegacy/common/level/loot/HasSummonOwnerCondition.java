package com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttachments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMLootModifiers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Set;

public record HasSummonOwnerCondition() implements LootItemCondition {
    @Override
    public LootItemConditionType getType() {
        return AMLootModifiers.HAS_SUMMON_OWNER.get();
    }

    @Override
    public boolean test(LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (entity == null || !entity.hasData(AMAttachments.SUMMON_OWNER)) return false;
        return entity.getData(AMAttachments.SUMMON_OWNER).uuid().isPresent();
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.THIS_ENTITY);
    }
}
