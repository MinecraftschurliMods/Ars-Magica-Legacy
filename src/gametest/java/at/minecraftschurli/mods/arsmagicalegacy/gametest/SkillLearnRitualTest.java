package at.minecraftschurli.mods.arsmagicalegacy.gametest;


import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.gametest.util.CustomGameTestHelper;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.EmptyTemplate;
import net.neoforged.testframework.gametest.GameTest;

import java.util.List;

@ForEachTest(groups = ArsMagicaApi.MOD_ID + ".unlock_skill")
public class SkillLearnRitualTest {
    private static void test(CustomGameTestHelper helper, Identifier skill, Spell spell) {
        helper.startSequenceWithPlayer(new BlockPos(1, 1, 1))
              .thenCastSpellAfter(2, spell)
              .thenExecuteAfter(15, player -> {
                  if (!ArsMagicaApi.magicHelper().knows(player, helper.getHolder(ResourceKey.create(AMRegistries.Keys.SKILL, skill)))) {
                      helper.fail("Player does not know " + skill);
                  }
              })
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that blizzard unlocks when casting the required unlock spell")
    public static void testUnlockBlizzard(CustomGameTestHelper helper) {
        test(helper, AMSpells.BLIZZARD.getId(), helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.FROST_DAMAGE.get(), AMSpells.FROST.get(), AMSpells.STORM.get())));
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that daylight unlocks when casting the required unlock spell")
    public static void testUnlockDaylight(CustomGameTestHelper helper) {
        test(helper, AMSpells.DAYLIGHT.getId(), helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.DIVINE_INTERVENTION.get(), AMSpells.SOLAR.get(), AMSpells.TRUE_SIGHT.get())));
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that dismembering unlocks when casting the required unlock spell")
    public static void testUnlockDismembering(CustomGameTestHelper helper) {
        test(helper, AMSpells.DISMEMBERING.getId(), helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.PHYSICAL_DAMAGE.get(), AMSpells.DAMAGE.get(), AMSpells.HEALING.get(), AMSpells.PIERCING.get())));
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that effect power unlocks when casting the required unlock spell")
    public static void testUnlockEffectPower(CustomGameTestHelper helper) {
        test(helper, AMSpells.EFFECT_POWER.getId(), helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.FLIGHT.get(), AMSpells.FURY.get(), AMSpells.REFLECT.get(), AMSpells.SHRINK.get(), AMSpells.SWIFT_SWIM.get(), AMSpells.TEMPORAL_ANCHOR.get())));
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that falling star unlocks when casting the required unlock spell")
    public static void testUnlockFallingStar(CustomGameTestHelper helper) {
        test(helper, AMSpells.FALLING_STAR.getId(), helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.ASTRAL_DISTORTION.get(), AMSpells.MAGIC_DAMAGE.get(), AMSpells.GRAVITY.get())));
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that fire rain unlocks when casting the required unlock spell")
    public static void testUnlockFireRain(CustomGameTestHelper helper) {
        test(helper, AMSpells.FIRE_RAIN.getId(), helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.FIRE_DAMAGE.get(), AMSpells.IGNITION.get(), AMSpells.STORM.get())));
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that mana blast unlocks when casting the required unlock spell")
    public static void testUnlockManaBlast(CustomGameTestHelper helper) {
        test(helper, AMSpells.MANA_BLAST.getId(), helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.EXPLOSION.get(), AMSpells.MANA_DRAIN.get())));
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that health boost unlocks when casting the required unlock spell")
    public static void testUnlockManaShield(CustomGameTestHelper helper) {
        test(helper, AMSpells.HEALTH_BOOST.getId(), helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.LIFE_TAP.get(), AMSpells.RESISTANCE.get())));
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that moonrise unlocks when casting the required unlock spell")
    public static void testUnlockMoonrise(CustomGameTestHelper helper) {
        test(helper, AMSpells.MOONRISE.getId(), helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.ENDER_INTERVENTION.get(), AMSpells.LUNAR.get(), AMSpells.NIGHT_VISION.get())));
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that prosperity unlocks when casting the required unlock spell")
    public static void testUnlockProsperity(CustomGameTestHelper helper) {
        test(helper, AMSpells.PROSPERITY.getId(), helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.DIG.get(), AMSpells.MINING_POWER.get(), AMSpells.SILK_TOUCH.get(), AMSpells.PHYSICAL_DAMAGE.get())));
    }
}
