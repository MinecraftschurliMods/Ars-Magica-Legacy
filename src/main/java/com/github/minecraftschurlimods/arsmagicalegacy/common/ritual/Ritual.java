package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.EntitySpawnRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.BiomeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.DimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.DimensionTypeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.EnderDragonDimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.HeightRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.MagicLevelRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.MoonPhaseRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.UltrawarmDimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntityDeathTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntitySummonTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.GameEventRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.ItemDropRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.TriPredicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 *
 */
public record Ritual(RitualStructure structure, RitualTrigger trigger, List<RitualRequirement> requirements, RitualEffect effect) {
    public static final Codec<Ritual> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RitualStructure.CODEC.fieldOf("structure").forGetter(Ritual::structure),
            RitualTrigger.CODEC.fieldOf("trigger").forGetter(Ritual::trigger),
            RitualRequirement.CODEC.listOf().fieldOf("requirements").forGetter(Ritual::requirements),
            RitualEffect.CODEC.fieldOf("effect").forGetter(Ritual::effect)
    ).apply(inst, Ritual::new));

    public Ritual {
        trigger.register(this);
    }

    private static final BiMap<ResourceLocation, Codec<? extends RitualTrigger>> ritualTriggerCodecs = HashBiMap.create();
    private static final BiMap<ResourceLocation, Codec<? extends RitualEffect>>  ritualEffectCodecs = HashBiMap.create();
    private static final BiMap<ResourceLocation, Codec<? extends RitualRequirement>> ritualRequirementCodecs = HashBiMap.create();

    public boolean perform(Player player, ServerLevel level, BlockPos pos, final Context ctx) {
        return this.structure.test(level, pos) && requirements().stream().allMatch(ritualRequirement -> ritualRequirement.test(player, level, pos)) && this.trigger.trigger(level, pos, ctx) && this.effect.performEffect(level, pos);
    }

    public record RitualStructure(ResourceLocation type) implements BiPredicate<Level, BlockPos> {
        private static final Codec<RitualStructure> CODEC = ResourceLocation.CODEC.xmap(RitualStructure::new, RitualStructure::type);

        @Override
        public boolean test(final Level level, final BlockPos pos) {
            return PatchouliCompat.getMultiblockMatcher(type).test(level, pos);
        }
    }

    public interface RitualRequirement extends TriPredicate<Player, ServerLevel, BlockPos> {
        Codec<RitualRequirement> CODEC = ResourceLocation.CODEC.dispatch("type", o -> ritualRequirementCodecs.inverse().get(o.codec()), ritualRequirementCodecs::get);

        Codec<? extends RitualRequirement> codec();
    }

    public interface RitualTrigger {
        Codec<RitualTrigger> CODEC = ResourceLocation.CODEC.dispatch("type", o -> ritualTriggerCodecs.inverse().get(o.codec()), ritualTriggerCodecs::get);

        void register(Ritual ritual);

        boolean trigger(ServerLevel level, BlockPos pos, Context ctx);

        Codec<? extends RitualTrigger> codec();
    }

    public interface RitualEffect {
        Codec<RitualEffect> CODEC = ResourceLocation.CODEC.dispatch("type", o -> ritualEffectCodecs.inverse().get(o.codec()), ritualEffectCodecs::get);

        boolean performEffect(ServerLevel level, BlockPos pos);

        Codec<? extends RitualEffect> codec();
    }

    public interface Context {
        Context EMPTY = new Context() {
            @Nullable
            @Contract("_, _ -> null")
            @Override
            public <T> T get(final String name, final Class<T> clazz) {
                return null;
            }
        };

        @Nullable
        <T> T get(String name, Class<T> clazz);
    }

    public record MapContext(Map<String, Object> data) implements Context {
        @Override
        public <T> T get(String name, Class<T> clazz) {
            if (data.containsKey(name)) {
                Object o = data.get(name);
                if (clazz.isInstance(o)) {
                    return clazz.cast(o);
                }
            }
            return null;
        }
    }

    static {
        ritualTriggerCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "death"), EntityDeathTrigger.CODEC);
        ritualTriggerCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "summon"), EntitySummonTrigger.CODEC);
        ritualTriggerCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "item_drop"), ItemDropRitualTrigger.CODEC);
        ritualTriggerCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "game_event"), GameEventRitualTrigger.CODEC);

        ritualEffectCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "entity_spawn"), EntitySpawnRitualEffect.CODEC);

        ritualRequirementCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "biome"), BiomeRequirement.CODEC);
        ritualRequirementCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dimension"), DimensionRequirement.CODEC);
        ritualRequirementCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dimension_type"), DimensionTypeRequirement.CODEC);
        ritualRequirementCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_dragon_dimension"), EnderDragonDimensionRequirement.CODEC);
        ritualRequirementCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "height"), HeightRequirement.CODEC);
        ritualRequirementCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "magic_level"), MagicLevelRequirement.CODEC);
        ritualRequirementCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "moon_phase"), MoonPhaseRequirement.CODEC);
        ritualRequirementCodecs.put(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ultrawarm_dimension"), UltrawarmDimensionRequirement.CODEC);
    }
}
