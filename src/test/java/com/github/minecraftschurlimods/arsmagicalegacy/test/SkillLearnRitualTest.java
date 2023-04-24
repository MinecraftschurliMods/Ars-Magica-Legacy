package com.github.minecraftschurlimods.arsmagicalegacy.test;


import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(false)
@GameTestHolder(ArsMagicaAPI.MOD_ID)
public class SkillLearnRitualTest {
    private static final String BATCH = "unlock_skill";

    private static void test(GameTestHelper helper, ResourceLocation skill, ISpellPart... parts) {
        Player player = helper.makeMockPlayer();
        helper.getLevel().addFreshEntity(player);
        ISpell spell = ArsMagicaAPI.get().makeSpell(SpellStack.of(parts));
        helper.runAfterDelay(2, () -> spell.cast(player, helper.getLevel(), 0, false, false));
        helper.runAfterDelay(10, () -> {
            if (ArsMagicaAPI.get().getSkillHelper().knows(player, skill)) {
                player.discard();
                helper.succeed();
            } else {
                player.discard();
                helper.fail("Player does not know " + skill);
            }
        });
    }

    @GameTest(template = "empty", batch = BATCH)
    public static void testUnlockBlizzard(GameTestHelper helper) {
        test(helper, AMSpellParts.BLIZZARD.getId(), AMSpellParts.SELF.get(), AMSpellParts.FROST_DAMAGE.get(), AMSpellParts.DAMAGE.get(), AMSpellParts.FROST.get(), AMSpellParts.STORM.get());
    }

    @GameTest(template = "empty", batch = BATCH)
    public static void testUnlockDaylight(GameTestHelper helper) {
        test(helper, AMSpellParts.DAYLIGHT.getId(), AMSpellParts.SELF.get(), AMSpellParts.DIVINE_INTERVENTION.get(), AMSpellParts.SOLAR.get(), AMSpellParts.TRUE_SIGHT.get());
    }

    @GameTest(template = "empty", batch = BATCH)
    public static void testUnlockDismembering(GameTestHelper helper) {
        test(helper, AMSpellParts.DISMEMBERING.getId(), AMSpellParts.SELF.get(), AMSpellParts.PHYSICAL_DAMAGE.get(), AMSpellParts.DAMAGE.get(), AMSpellParts.PIERCING.get());
    }

    @GameTest(template = "empty", batch = BATCH)
    public static void testUnlockEffectPower(GameTestHelper helper) {
        test(helper, AMSpellParts.EFFECT_POWER.getId(), AMSpellParts.SELF.get(), AMSpellParts.AGILITY.get(), AMSpellParts.FLIGHT.get(), AMSpellParts.REFLECT.get(), AMSpellParts.SHRINK.get(), AMSpellParts.SWIFT_SWIM.get(), AMSpellParts.TEMPORAL_ANCHOR.get());
    }

    @GameTest(template = "empty", batch = BATCH)
    public static void testUnlockFallingStar(GameTestHelper helper) {
        test(helper, AMSpellParts.FALLING_STAR.getId(), AMSpellParts.SELF.get(), AMSpellParts.ASTRAL_DISTORTION.get(), AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.SOLAR.get());
    }

    @GameTest(template = "empty", batch = BATCH)
    public static void testUnlockFireRain(GameTestHelper helper) {
        test(helper, AMSpellParts.FIRE_RAIN.getId(), AMSpellParts.SELF.get(), AMSpellParts.FIRE_DAMAGE.get(), AMSpellParts.IGNITION.get(), AMSpellParts.STORM.get());
    }

    @GameTest(template = "empty", batch = BATCH)
    public static void testUnlockManaBlast(GameTestHelper helper) {
        test(helper, AMSpellParts.MANA_BLAST.getId(), AMSpellParts.SELF.get(), AMSpellParts.EXPLOSION.get(), AMSpellParts.MANA_DRAIN.get());
    }

    @GameTest(template = "empty", batch = BATCH)
    public static void testUnlockManaShield(GameTestHelper helper) {
        test(helper, AMSpellParts.HEALTH_BOOST.getId(), AMSpellParts.SELF.get(), AMSpellParts.SHIELD.get(), AMSpellParts.LIFE_TAP.get());
    }

    @GameTest(template = "empty", batch = BATCH)
    public static void testUnlockMoonrise(GameTestHelper helper) {
        test(helper, AMSpellParts.MOONRISE.getId(), AMSpellParts.SELF.get(), AMSpellParts.ENDER_INTERVENTION.get(), AMSpellParts.LUNAR.get(), AMSpellParts.NIGHT_VISION.get());
    }

    @GameTest(template = "empty", batch = BATCH)
    public static void testUnlockProsperity(GameTestHelper helper) {
        test(helper, AMSpellParts.PROSPERITY.getId(), AMSpellParts.SELF.get(), AMSpellParts.DIG.get(), AMSpellParts.MINING_POWER.get(), AMSpellParts.SILK_TOUCH.get(), AMSpellParts.PHYSICAL_DAMAGE.get());
    }
}
