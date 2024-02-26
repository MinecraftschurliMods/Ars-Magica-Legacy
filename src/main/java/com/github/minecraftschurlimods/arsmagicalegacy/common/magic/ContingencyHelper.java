package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IContingencyHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

import java.util.Objects;
import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ATTACHMENT_TYPES;

public final class ContingencyHelper implements IContingencyHelper {
    private static final Lazy<ContingencyHelper> INSTANCE = Lazy.concurrentOf(ContingencyHelper::new);
    private static final Supplier<AttachmentType<Contingency>> CONTINGENCY = ATTACHMENT_TYPES.register("contingency", () -> AttachmentType.builder(Contingency::new).serialize(Contingency.CODEC).build());

    private ContingencyHelper() {
        NeoForge.EVENT_BUS.addListener((LivingDeathEvent event) -> triggerContingency(event.getEntity(), ContingencyType.DEATH));
        NeoForge.EVENT_BUS.addListener((LivingFallEvent event) -> triggerContingency(event.getEntity(), ContingencyType.FALL));
        NeoForge.EVENT_BUS.addListener((LivingDamageEvent event) -> triggerContingency(event.getEntity(), ContingencyType.DAMAGE));
    }

    public static ContingencyHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public void setContingency(LivingEntity target, ResourceLocation type, ISpell spell) {
        Registry<ContingencyType> registry = ArsMagicaAPI.get().getContingencyTypeRegistry();
        if (!registry.containsKey(type)) return;
        target.setData(CONTINGENCY, new Contingency(type, spell, 1));
    }

    @Override
    public void triggerContingency(LivingEntity entity, ResourceLocation type) {
        if (!entity.hasData(CONTINGENCY)) return;
        Contingency contingency = entity.getData(CONTINGENCY);
        if (contingency.type.equals(ContingencyType.NONE)) return;
        Registry<ContingencyType> registry = ArsMagicaAPI.get().getContingencyTypeRegistry();
        if (!registry.containsKey(type) || !Objects.equals(contingency.type, type)) return;
        contingency.execute(entity.level(), entity);
        clearContingency(entity);
    }

    @Override
    public void clearContingency(LivingEntity target) {
        target.setData(CONTINGENCY, new Contingency());
    }

    @Override
    public ResourceLocation getContingencyType(LivingEntity entity) {
        if (!entity.hasData(CONTINGENCY)) return ContingencyType.NONE;
        return entity.getData(CONTINGENCY).type;
    }

    public record Contingency(ResourceLocation type, ISpell spell, int index) {
        public static final Codec<Contingency> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ContingencyType.CODEC.xmap(t -> ArsMagicaAPI.get().getContingencyTypeRegistry().getKey(t), rl -> ArsMagicaAPI.get().getContingencyTypeRegistry().get(rl)).fieldOf("type").forGetter(Contingency::type),
                ISpell.CODEC.fieldOf("spell").forGetter(Contingency::spell),
                Codec.INT.fieldOf("index").forGetter(Contingency::index)
        ).apply(inst, Contingency::new));

        public Contingency() {
            this(ContingencyType.NONE, ISpell.EMPTY, 0);
        }

        private void execute(Level level, LivingEntity target) {
            ArsMagicaAPI.get().getSpellHelper().invoke(spell, target, level, new EntityHitResult(target), 0, index, true);
        }
    }
}
