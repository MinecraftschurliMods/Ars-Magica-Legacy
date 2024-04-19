package com.github.minecraftschurlimods.arsmagicalegacy.test;


import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.EmptyTemplate;
import net.neoforged.testframework.gametest.ExtendedGameTestHelper;

@ForEachTest(groups = ArsMagicaAPI.MOD_ID + ".unlock_skill")
public class SkillLearnRitualTest {
    private static void test(ExtendedGameTestHelper helper, ResourceLocation skill, ISpellPart... parts) {
        helper.startSequence(() -> helper.makeTickingMockServerPlayerInLevel(GameType.SURVIVAL))
              .thenExecuteAfter(2, player -> ArsMagicaAPI.get().makeSpell(SpellStack.of(parts)).cast(player, player.level(), 0, false, false))
              .thenExecuteAfter(8, player -> {
                  if (!ArsMagicaAPI.get().getSkillHelper().knows(player, skill)) {
                      helper.fail("Player does not know " + skill);
                  }
              }).thenExecute(Entity::discard).thenSucceed();
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that blizzard unlocks when casting the required unlock spell")
    public static void testUnlockBlizzard(ExtendedGameTestHelper helper) {
        test(helper, AMSpellParts.BLIZZARD.getId(), AMSpellParts.SELF.get(), AMSpellParts.FROST_DAMAGE.get(), AMSpellParts.DAMAGE.get(), AMSpellParts.FROST.get(), AMSpellParts.STORM.get());
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that daylight unlocks when casting the required unlock spell")
    public static void testUnlockDaylight(ExtendedGameTestHelper helper) {
        test(helper, AMSpellParts.DAYLIGHT.getId(), AMSpellParts.SELF.get(), AMSpellParts.DIVINE_INTERVENTION.get(), AMSpellParts.SOLAR.get(), AMSpellParts.TRUE_SIGHT.get());
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that dismembering unlocks when casting the required unlock spell")
    public static void testUnlockDismembering(ExtendedGameTestHelper helper) {
        test(helper, AMSpellParts.DISMEMBERING.getId(), AMSpellParts.SELF.get(), AMSpellParts.PHYSICAL_DAMAGE.get(), AMSpellParts.DAMAGE.get(), AMSpellParts.PIERCING.get());
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that effect power unlocks when casting the required unlock spell")
    public static void testUnlockEffectPower(ExtendedGameTestHelper helper) {
        test(helper, AMSpellParts.EFFECT_POWER.getId(), AMSpellParts.SELF.get(), AMSpellParts.AGILITY.get(), AMSpellParts.FLIGHT.get(), AMSpellParts.REFLECT.get(), AMSpellParts.SHRINK.get(), AMSpellParts.SWIFT_SWIM.get(), AMSpellParts.TEMPORAL_ANCHOR.get());
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that falling star unlocks when casting the required unlock spell")
    public static void testUnlockFallingStar(ExtendedGameTestHelper helper) {
        test(helper, AMSpellParts.FALLING_STAR.getId(), AMSpellParts.SELF.get(), AMSpellParts.ASTRAL_DISTORTION.get(), AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.SOLAR.get());
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that fire rain unlocks when casting the required unlock spell")
    public static void testUnlockFireRain(ExtendedGameTestHelper helper) {
        test(helper, AMSpellParts.FIRE_RAIN.getId(), AMSpellParts.SELF.get(), AMSpellParts.FIRE_DAMAGE.get(), AMSpellParts.IGNITION.get(), AMSpellParts.STORM.get());
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that mana blast unlocks when casting the required unlock spell")
    public static void testUnlockManaBlast(ExtendedGameTestHelper helper) {
        test(helper, AMSpellParts.MANA_BLAST.getId(), AMSpellParts.SELF.get(), AMSpellParts.EXPLOSION.get(), AMSpellParts.MANA_DRAIN.get());
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that health boost unlocks when casting the required unlock spell")
    public static void testUnlockManaShield(ExtendedGameTestHelper helper) {
        test(helper, AMSpellParts.HEALTH_BOOST.getId(), AMSpellParts.SELF.get(), AMSpellParts.SHIELD.get(), AMSpellParts.LIFE_TAP.get());
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that moonrise unlocks when casting the required unlock spell")
    public static void testUnlockMoonrise(ExtendedGameTestHelper helper) {
        test(helper, AMSpellParts.MOONRISE.getId(), AMSpellParts.SELF.get(), AMSpellParts.ENDER_INTERVENTION.get(), AMSpellParts.LUNAR.get(), AMSpellParts.NIGHT_VISION.get());
    }

    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that prosperity unlocks when casting the required unlock spell")
    public static void testUnlockProsperity(ExtendedGameTestHelper helper) {
        test(helper, AMSpellParts.PROSPERITY.getId(), AMSpellParts.SELF.get(), AMSpellParts.DIG.get(), AMSpellParts.MINING_POWER.get(), AMSpellParts.SILK_TOUCH.get(), AMSpellParts.PHYSICAL_DAMAGE.get());
    }
}
