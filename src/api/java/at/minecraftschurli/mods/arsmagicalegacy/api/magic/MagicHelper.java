package at.minecraftschurli.mods.arsmagicalegacy.api.magic;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;

/// Helper for operations related to a [Player]'s [MagicAttachment].
public interface MagicHelper {
    /// @param player The [Player] to get the level for.
    /// @return The given [Player]'s level.
    int getLevel(Player player);

    /// @param player The [Player] to get the xp for.
    /// @return The given [Player]'s xp.
    double getXp(Player player);

    /// If a [Player] is at the given level, calculate how much xp is required for the next level.
    ///
    /// @param level The current level.
    /// @return The xp required for the next level.
    double getXpForNextLevel(int level);

    /// @param player The [Player] to add the levels for.
    /// @param level  The levels to add.
    void addLevel(Player player, int level);

    /// @param player The [Player] to set the level for.
    /// @param level  The level to set.
    void setLevel(Player player, int level);

    /// @param player The [Player] to add the xp for.
    /// @param xp     The xp to add.
    void addXp(Player player, double xp);

    /// @param player The [Player] to set the xp for.
    /// @param xp     The xp to set.
    void setXp(Player player, double xp);

    /// @param player The [Player] to query.
    /// @return Whether the given [Player] knows magic and can interact with various systems added by the mod. This is always true if the player is in creative mode.
    boolean knowsMagic(Player player);

    /// Initiates magic on the given [Player], setting them to level 1 and granting some extra skill points.
    /// By default, this happens when they take an Arcane Compendium in the inventory for the first time.
    /// @param player The [Player] to initiate magic on.
    void initiateMagic(Player player);

    /// @param player The [Player] to query.
    /// @param skill  The [Skill] to check for.
    /// @return Whether the [Player] knows the given [Skill].
    boolean knows(Player player, Holder<Skill> skill);

    /// @param player The [Player] to query.
    /// @param skill  The [Skill] to check for.
    /// @return Whether the [Player] does not know, but meets the requirements to learn the given [Skill].
    boolean canLearn(Player player, Holder<Skill> skill);

    /// @param player The [Player] to query.
    /// @return A [List] of all [Skill]s the [Player] currently knows.
    List<? extends Holder<Skill>> getKnown(Player player);

    /// @param player The [Player] to query.
    /// @return A [List] of all [Skill]s the [Player] does not currently know.
    List<? extends Holder<Skill>> getUnknown(Player player);

    /// Adds the given [Skill] to the [Player]'s known [Skill]s.
    ///
    /// @param player The [Player] to add the [Skill] to.
    /// @param skill  The [Skill] to add.
    void learn(Player player, Holder<Skill> skill);

    /// Removes the given [Skill] from the [Player]'s known [Skill]s.
    ///
    /// @param player The [Player] to remove the [Skill] from.
    /// @param skill  The [Skill] to remove.
    void forget(Player player, Holder<Skill> skill);

    /// Adds all [Skill]s to the [Player]'s known [Skill]s.
    ///
    /// @param player The [Player] to add the [Skill]s to.
    void learnAll(Player player);

    /// Removes all [Skill]s from the [Player]'s known [Skill]s.
    ///
    /// @param player The [Player] to remove the [Skill]s from.
    void forgetAll(Player player);

    /// @param player     The [Player] to query.
    /// @param skillPoint The [SkillPoint] to check.
    /// @return The amount of [SkillPoint]s the [Player] has.
    int getSkillPoint(Player player, Holder<SkillPoint> skillPoint);

    /// Adds [SkillPoint]s to the [Player].
    ///
    /// @param player     The [Player] to add the [SkillPoint]s to.
    /// @param skillPoint The [SkillPoint] to add.
    /// @param amount     The amount of [SkillPoint]s to add.
    void addSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount);

    /// Adds one [SkillPoint] to the [Player].
    ///
    /// @param player     The [Player] to add the [SkillPoint] to.
    /// @param skillPoint The [SkillPoint] to add.
    void addSkillPoint(Player player, Holder<SkillPoint> skillPoint);

    /// Sets [SkillPoint]s on the [Player].
    ///
    /// @param player     The [Player] to set the [SkillPoint]s on.
    /// @param skillPoint The [SkillPoint] to set.
    /// @param amount     The amount of [SkillPoint]s to set.
    void setSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount);

    /// @param player   The [Player] to query.
    /// @param affinity The [Affinity] to query.
    /// @return The [Affinity] depth of the given [Player], in the range [0, 1].
    double getAffinityDepth(Player player, Holder<Affinity> affinity);

    /// Sets the given [Player]'s [Affinity] depth. Will not bypass locks.
    ///
    /// @param player   The [Player] to set the [Affinity] depth on.
    /// @param affinity The [Affinity] to set the depth for.
    /// @param depth    The depth to set.
    void setAffinityDepth(Player player, Holder<Affinity> affinity, double depth);

    /// Sets the given [Player]'s [Affinity] depths. Will not bypass locks.
    ///
    /// @param player     The [Player] to set the [Affinity] depth on.
    /// @param affinities The [Affinity] depths to set.
    void setAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities);

    /// Sets the given [Player]'s [Affinity] depth.
    ///
    /// @param player        The [Player] to set the [Affinity] depth on.
    /// @param affinity      The [Affinity] to set the depth for.
    /// @param depth         The depth to set.
    /// @param bypassLocks   If true, bypasses locks.
    /// @param commandSource Whether the method was called from a command.
    void setAffinityDepth(Player player, Holder<Affinity> affinity, double depth, boolean bypassLocks, boolean commandSource);

    /// Sets the given [Player]'s [Affinity] depths.
    ///
    /// @param player        The [Player] to set the [Affinity] depths on.
    /// @param affinities    The [Affinity] depths to set.
    /// @param bypassLocks   If true, bypasses locks.
    /// @param commandSource Whether the method was called from a command.
    void setAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities, boolean bypassLocks, boolean commandSource);

    /// Adds to the given [Player]'s [Affinity] depth. Will not bypass locks.
    ///
    /// @param player   The [Player] to add the [Affinity] depth to.
    /// @param affinity The [Affinity] to add the depth for.
    /// @param depth    The depth to add.
    void addAffinityDepth(Player player, Holder<Affinity> affinity, double depth);

    /// Adds to the given [Player]'s [Affinity] depth. Will not bypass locks.
    ///
    /// @param player     The [Player] to add the [Affinity] depths to.
    /// @param affinities The [Affinity] depths to add.
    void addAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities);

    /// Adds to the given [Player]'s [Affinity] depth.
    ///
    /// @param player        The [Player] to add the [Affinity] depth to.
    /// @param affinity      The [Affinity] to add the depth for.
    /// @param depth         The depth to add.
    /// @param bypassLocks   If true, bypasses locks.
    /// @param commandSource Whether the method was called from a command.
    void addAffinityDepth(Player player, Holder<Affinity> affinity, double depth, boolean bypassLocks, boolean commandSource);

    /// Adds to the given [Player]'s [Affinity] depth.
    ///
    /// @param player        The [Player] to add the [Affinity] depths to.
    /// @param affinities    The [Affinity] depths to add.
    /// @param bypassLocks   If true, bypasses locks.
    /// @param commandSource Whether the method was called from a command.
    void addAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities, boolean bypassLocks, boolean commandSource);

    /// Applies an [Affinity] shift to the given [Player]. It sets the [Affinity] change itself, as well as modifying the adjacent and opposite [Affinity]s. Will not bypass locks.
    ///
    /// @param player   The [Player] to apply the [Affinity] shift to.
    /// @param affinity The [Affinity] to apply the shift for.
    /// @param shift    The shift to apply.
    void applyAffinityShift(Player player, Holder<Affinity> affinity, double shift);

    /// Applies [Affinity] shifts to the given [Player]. It sets the [Affinity] changes itself, as well as modifying the adjacent and opposite [Affinity]s. Will not bypass locks.
    ///
    /// @param player         The [Player] to apply the [Affinity] shifts to.
    /// @param affinityShifts The [Affinity] shifts to apply.
    void applyAffinityShift(Player player, Map<Holder<Affinity>, Double> affinityShifts);

    /// Applies an [Affinity] shift to the given [Player]. It sets the [Affinity] change itself, as well as modifying the adjacent and opposite [Affinity]s.
    ///
    /// @param player        The [Player] to apply the [Affinity] shift to.
    /// @param affinity      The [Affinity] to apply the shift for.
    /// @param shift         The shift to apply.
    /// @param bypassLocks   If true, bypasses locks.
    /// @param commandSource Whether the method was called from a command.
    void applyAffinityShift(Player player, Holder<Affinity> affinity, double shift, boolean bypassLocks, boolean commandSource);

    /// Applies [Affinity] shifts to the given [Player]. It sets the [Affinity] changes itself, as well as modifying the adjacent and opposite [Affinity]s.
    ///
    /// @param player         The [Player] to apply the [Affinity] shifts to.
    /// @param affinityShifts The [Affinity] shifts to apply.
    /// @param bypassLocks    If true, bypasses locks.
    /// @param commandSource  Whether the method was called from a command.
    void applyAffinityShift(Player player, Map<Holder<Affinity>, Double> affinityShifts, boolean bypassLocks, boolean commandSource);

    /// Locks the [Player]'s [Affinity] depths.
    ///
    /// @param player The [Player] to lock the [Affinity] depths of.
    /// @see MagicHelper#unlockAffinities(Player)
    void lockAffinities(Player player);

    /// Unlocks the [Player]'s [Affinity] depths.
    ///
    /// @param player The [Player] to unlock the [Affinity] depths of.
    /// @see MagicHelper#lockAffinities(Player)
    void unlockAffinities(Player player);

    /// Sets the [Player]'s [Affinity] depth lock depending on whether there is an affinity at 100% or not.
    ///
    /// @param player The [Player] to set the [Affinity] depth lock for.
    void updateAffinityLock(Player player);
}
