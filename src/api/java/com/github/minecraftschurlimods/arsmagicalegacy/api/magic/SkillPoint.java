package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.Util;

/// Represents a skill point.
///
/// @param color          The color (RGB) of the skill point.
/// @param minEarnLevel   The level from which on the skill point will be awarded.
/// @param levelsForPoint The amount of levels required to get the next skill point.
@SuppressWarnings("DataFlowIssue")
public record SkillPoint(int color, int minEarnLevel, int levelsForPoint) {
    public static final Codec<SkillPoint> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.INT.fieldOf("color").forGetter(SkillPoint::color),
        Codec.INT.fieldOf("min_earn_level").forGetter(SkillPoint::minEarnLevel),
        Codec.INT.fieldOf("levels_for_point").forGetter(SkillPoint::levelsForPoint)
    ).apply(inst, SkillPoint::new));
    public static final Codec<Holder<SkillPoint>> CODEC = RegistryFileCodec.create(AMRegistries.Keys.SKILL_POINT, DIRECT_CODEC);

    /// @param holder The skill point [Holder] to query.
    /// @return The display name of the given skill point.
    public static MutableComponent getName(Holder<SkillPoint> holder) {
        return Component.translatable(Util.makeDescriptionId("skill_point", holder.getKey().identifier()));
    }
}
