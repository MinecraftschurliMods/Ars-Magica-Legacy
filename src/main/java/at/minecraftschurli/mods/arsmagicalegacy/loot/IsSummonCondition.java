package at.minecraftschurli.mods.arsmagicalegacy.loot;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.Util;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Set;

public record IsSummonCondition() implements LootItemCondition {
    public static final IsSummonCondition INSTANCE = new IsSummonCondition();
    public static final MapCodec<IsSummonCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (!lootContext.hasParameter(LootContextParams.THIS_ENTITY)) return false;
        Entity entity = lootContext.getParameter(LootContextParams.THIS_ENTITY);
        return entity.hasData(AMAttachments.SUMMON_OWNER) && !entity.getData(AMAttachments.SUMMON_OWNER).equals(Util.NIL_UUID);
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.THIS_ENTITY);
    }
}
