package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IContingencyHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;

public final class ContingencyHelper implements IContingencyHelper {
    private static final Lazy<ContingencyHelper> INSTANCE = Lazy.concurrentOf(ContingencyHelper::new);
    private static final Capability<Contingency> CONTINGENCY = CapabilityManager.get(new CapabilityToken<>() {});

    private ContingencyHelper() {
        MinecraftForge.EVENT_BUS.addListener((LivingDeathEvent event) -> triggerContingency(event.getEntity(), ContingencyType.DEATH));
        MinecraftForge.EVENT_BUS.addListener((LivingFallEvent event) -> triggerContingency(event.getEntity(), ContingencyType.FALL));
        MinecraftForge.EVENT_BUS.addListener((LivingDamageEvent event) -> triggerContingency(event.getEntity(), ContingencyType.DAMAGE));
    }

    public static ContingencyHelper instance() {
        return INSTANCE.get();
    }

    public static Capability<Contingency> getCapability() {
        return CONTINGENCY;
    }

    @Override
    public void setContingency(LivingEntity target, ResourceLocation type, ISpell spell) {
        IForgeRegistry<ContingencyType> registry = ArsMagicaAPI.get().getContingencyTypeRegistry();
        if (!registry.containsKey(type)) return;
        target.getCapability(CONTINGENCY).ifPresent(contingency -> {
            contingency.type = type;
            contingency.spell = spell;
            contingency.index = 1;
        });
    }

    @Override
    public void triggerContingency(LivingEntity entity, ResourceLocation type) {
        entity.getCapability(CONTINGENCY).ifPresent(contingency -> {
            IForgeRegistry<ContingencyType> registry = ArsMagicaAPI.get().getContingencyTypeRegistry();
            if (!registry.containsKey(type) || !Objects.equals(contingency.type, type)) return;
            contingency.execute(entity.level, entity);
            contingency.clear();
        });
    }

    @Override
    public void clearContingency(LivingEntity target) {
        target.getCapability(CONTINGENCY).ifPresent(Contingency::clear);
    }

    @Override
    public ResourceLocation getContingencyType(LivingEntity entity) {
        return entity.getCapability(CONTINGENCY)
                .map(contingency -> contingency.type)
                .orElse(ContingencyType.NONE);
    }

    public static class Contingency {
        public static final Codec<Contingency> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ContingencyType.CODEC.xmap(t -> ArsMagicaAPI.get().getContingencyTypeRegistry().getKey(t), rl -> ArsMagicaAPI.get().getContingencyTypeRegistry().getValue(rl)).fieldOf("type").forGetter(o -> o.type),
                ISpell.CODEC.fieldOf("spell").forGetter(o -> o.spell),
                Codec.INT.fieldOf("index").forGetter(o -> o.index)
        ).apply(inst, Contingency::new));
        private ResourceLocation type;
        private ISpell spell;
        private int index;

        private Contingency(ResourceLocation type, ISpell spell, int index) {
            this.type = type;
            this.spell = spell;
            this.index = index;
        }

        public Contingency() {
            this(ContingencyType.NONE, ISpell.EMPTY, 0);
        }

        private void clear() {
            this.type = ContingencyType.NONE;
            this.spell = ISpell.EMPTY;
            this.index = 0;
        }

        private void execute(Level level, LivingEntity target) {
            ArsMagicaAPI.get().getSpellHelper().invoke(spell, target, level, new EntityHitResult(target), 0, index, true);
        }
    }
}
