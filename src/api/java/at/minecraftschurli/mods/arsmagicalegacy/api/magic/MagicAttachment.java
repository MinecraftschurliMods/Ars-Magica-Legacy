package at.minecraftschurli.mods.arsmagicalegacy.api.magic;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/// Represents an attachment on a player, holding various data related to the mod.
///
/// Keep in mind that due to the immutability contract, any modification must use a new instance.
///
/// @param level          The magic level of the player.
/// @param xp             The magic xp of the player.
/// @param skills         The [Skill]s the player knows. Immutable by contract.
/// @param skillPoints    The [SkillPoint]s the player has. Immutable by contract.
/// @param affinityShifts The [Affinity] shifts the player has. Immutable by contract.
public record MagicAttachment(int level, double xp, Set<Holder<Skill>> skills, Map<Holder<SkillPoint>, Integer> skillPoints, Map<Holder<Affinity>, Double> affinityShifts, boolean affinityLocked) {
    public static final Codec<MagicAttachment> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.INT.fieldOf("level").forGetter(MagicAttachment::level),
        Codec.DOUBLE.fieldOf("xp").forGetter(MagicAttachment::xp),
        Skill.CODEC.listOf().xmap(Set::copyOf, List::copyOf).fieldOf("skills").forGetter(MagicAttachment::skills),
        Codec.unboundedMap(SkillPoint.CODEC, Codec.INT).fieldOf("skill_points").forGetter(MagicAttachment::skillPoints),
        Codec.unboundedMap(Affinity.CODEC, Codec.DOUBLE).fieldOf("affinity_shifts").forGetter(MagicAttachment::affinityShifts),
        Codec.BOOL.fieldOf("affinity_locked").forGetter(MagicAttachment::affinityLocked)
    ).apply(inst, MagicAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, MagicAttachment> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT, MagicAttachment::level,
        ByteBufCodecs.DOUBLE, MagicAttachment::xp,
        ByteBufCodecs.holderRegistry(AMRegistries.Keys.SKILL).apply(ByteBufCodecs.collection(HashSet::new)), MagicAttachment::skills,
        ByteBufCodecs.map(HashMap::new, ByteBufCodecs.holderRegistry(AMRegistries.Keys.SKILL_POINT), ByteBufCodecs.INT), MagicAttachment::skillPoints,
        ByteBufCodecs.map(HashMap::new, ByteBufCodecs.holderRegistry(AMRegistries.Keys.AFFINITY), ByteBufCodecs.DOUBLE), MagicAttachment::affinityShifts,
        ByteBufCodecs.BOOL, MagicAttachment::affinityLocked,
        MagicAttachment::new);
    public static final MagicAttachment DEFAULT = new MagicAttachment(0, 0, Set.of(), Map.of(), Map.of(), false);

    /// @param level The new level to set.
    /// @return A new instance with the new level set.
    public MagicAttachment setLevel(int level) {
        return new MagicAttachment(level, xp, skills, skillPoints, affinityShifts, affinityLocked);
    }

    /// @param xp The new xp to set.
    /// @return A new instance with the new xp set.
    public MagicAttachment setXp(double xp) {
        return new MagicAttachment(level, xp, skills, skillPoints, affinityShifts, affinityLocked);
    }

    /// @param consumer The operation to perform on the skills.
    /// @return A new instance with the updated skills set.
    public MagicAttachment updateSkills(Consumer<Set<Holder<Skill>>> consumer) {
        Set<Holder<Skill>> skills = new HashSet<>(this.skills);
        consumer.accept(skills);
        return new MagicAttachment(level, xp, skills, skillPoints, affinityShifts, affinityLocked);
    }

    /// @param consumer The operation to perform on the skill points.
    /// @return A new instance with the updated skill points set.
    public MagicAttachment updateSkillPoints(Consumer<Map<Holder<SkillPoint>, Integer>> consumer) {
        Map<Holder<SkillPoint>, Integer> skillPoints = new HashMap<>(this.skillPoints);
        consumer.accept(skillPoints);
        return new MagicAttachment(level, xp, skills, skillPoints, affinityShifts, affinityLocked);
    }

    /// @param consumer The operation to perform on the affinity shifts.
    /// @return A new instance with the updated affinity shifts set.
    public MagicAttachment updateAffinityShifts(Consumer<Map<Holder<Affinity>, Double>> consumer) {
        Map<Holder<Affinity>, Double> affinityShifts = new HashMap<>(this.affinityShifts);
        consumer.accept(affinityShifts);
        return new MagicAttachment(level, xp, skills, skillPoints, affinityShifts, affinityLocked);
    }

    /// @param affinityLocked The new affinity lock status to set.
    /// @return A new instance with the new affinity lock status set.
    public MagicAttachment setAffinityLocked(boolean affinityLocked) {
        return new MagicAttachment(level, xp, skills, skillPoints, affinityShifts, affinityLocked);
    }
}
