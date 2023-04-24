package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.BeforeBatch;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@PrefixGameTestTemplate(false)
@GameTestHolder(ArsMagicaAPI.MOD_ID)
public class ObeliskConversionRitualTest {
    private static final String BATCH = "obelisk_conversion";
    private static ISpell PURIFICATION_SPELL;
    private static ISpell CORRUPTION_SPELL;

    @BeforeBatch(batch = BATCH)
    public static void setup(ServerLevel level) {
        PURIFICATION_SPELL = ArsMagicaAPI.get().makeSpell(List.of(), SpellStack.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.LIGHT.get()), new CompoundTag());
        CORRUPTION_SPELL = ArsMagicaAPI.get().makeSpell(List.of(), SpellStack.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.FIRE_DAMAGE.get()), new CompoundTag());
    }

    @GameTest(template = "corruption_ritual", batch = BATCH)
    public static void testCorruptionRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        BlockPos center = new BlockPos(3, 2, 2);
        BlockPos absoluteCenter = helper.absolutePos(center);
        // create player
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(1, 2, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        // drop item
        helper.runAfterDelay(2, () -> player.drop(new ItemStack(AMItems.SUNSTONE.get()), false));
        // cast spell
        helper.runAfterDelay(4, () -> {
            player.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(absoluteCenter));
            CORRUPTION_SPELL.cast(player, serverlevel, 0, false, false);
        });
        // check for black aurem and item removal
        helper.runAfterDelay(10, () -> {
            helper.assertBlock(center.above(), block -> block == AMBlocks.BLACK_AUREM.get(), "Black aurem not found");
            helper.assertItemEntityCountIs(AMItems.SUNSTONE.get(), center, 4, 0);
            helper.succeed();
            player.discard();
        });
    }

    @GameTest(template = "purification_ritual", batch = BATCH)
    public static void testPurificationRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        BlockPos center = new BlockPos(3, 2, 3);
        BlockPos absoluteCenter = helper.absolutePos(center);
        // create player
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(1, 2, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        // drop item
        helper.runAfterDelay(2, () -> player.drop(new ItemStack(AMItems.MOONSTONE.get()), false));
        // cast spell
        helper.runAfterDelay(4, () -> {
            player.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(absoluteCenter));
            PURIFICATION_SPELL.cast(player, serverlevel, 0, false, false);
        });
        // check for celestial prism and item removal
        helper.runAfterDelay(10, () -> {
            helper.assertBlock(center, block -> block == AMBlocks.CELESTIAL_PRISM.get(), "Celestial Prism not found");
            helper.assertItemEntityCountIs(AMItems.MOONSTONE.get(), center, 4, 0);
            helper.succeed();
        });
    }
}
