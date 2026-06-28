package at.minecraftschurli.mods.arsmagicalegacy.api.magic;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.Util;

import java.util.List;
import java.util.Optional;

/// Represents a skill.
///
/// @param parents A [List] of parent [Skill] [Holder]s.
/// @param cost    The cost of the skill. If absent, the skill has no cost.
/// @param tab     The [OcculusTab] the skill resides in.
/// @param x       The x position of the skill.
/// @param y       The y position of the skill.
/// @param hidden  Whether the skill is hidden. Hidden skills will only show when learned through means other than within the occulus, e.g. via command.
@SuppressWarnings("DataFlowIssue")
public record Skill(List<Holder<Skill>> parents, Optional<Holder<SkillPoint>> cost, Holder<OcculusTab> tab, int x, int y, boolean hidden) {
    public static final Codec<Skill> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.lazyInitialized(() -> Skill.CODEC).listOf().fieldOf("parents").forGetter(Skill::parents),
        SkillPoint.CODEC.optionalFieldOf("cost").forGetter(Skill::cost),
        OcculusTab.CODEC.fieldOf("tab").forGetter(Skill::tab),
        Codec.INT.fieldOf("x").forGetter(Skill::x),
        Codec.INT.fieldOf("y").forGetter(Skill::y),
        Codec.BOOL.optionalFieldOf("hidden", false).forGetter(Skill::hidden)
    ).apply(inst, Skill::new));
    public static final Codec<Holder<Skill>> CODEC = RegistryFileCodec.create(AMRegistries.Keys.SKILL, DIRECT_CODEC);

    /// @param holder The skill [Holder] to query.
    /// @return The display name of the given skill.
    public static MutableComponent getName(Holder<Skill> holder) {
        return Component.translatable(Util.makeDescriptionId("skill", holder.getKey().identifier()) + ".name");
    }

    /// @param holder The skill [Holder] to query.
    /// @return The description of the given skill.
    public static MutableComponent getDescription(Holder<Skill> holder) {
        return Component.translatable(Util.makeDescriptionId("skill", holder.getKey().identifier()) + ".description");
    }
}
