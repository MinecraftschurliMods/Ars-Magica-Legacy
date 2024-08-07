package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.ExtendedGameTestHelper;

import java.util.List;

@ForEachTest(groups = ArsMagicaAPI.MOD_ID + ".obelisk_conversion")
public class ObeliskConversionRitualTest {
    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "corruption_ritual")
    @TestHolder(description = "Test if the corruption ritual correctly converts the obelisk to a black aurem")
    public static void testCorruptionRitual(ExtendedGameTestHelper helper) {
        ArsMagicaAPI api = ArsMagicaAPI.get();
        ISpell corruptionSpell = api.makeSpell(List.of(), SpellStack.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.FIRE_DAMAGE.get()), new CompoundTag());
        BlockPos center = new BlockPos(3, 2, 2);
        BlockPos absoluteCenter = helper.absolutePos(center);
        // create player
        helper.startSequence(() -> {
            var player = helper.makeTickingMockServerPlayerInLevel(GameType.SURVIVAL).preventItemPickup();
            player.moveTo(helper.absolutePos(new BlockPos(1, 2, 1)), 0, 90);
            return player;
        // drop item
        }).thenExecuteAfter(2, player -> player.drop(new ItemStack(AMItems.SUNSTONE.get()), false))
        // cast spell
        .thenExecuteAfter(4, player -> {
            player.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(absoluteCenter));
            api.getSpellHelper().cast(corruptionSpell, player, player.level(), 0, false, false);
        // check for black aurem existence and item removal
        }).thenExecuteAfter(10, () -> {
            helper.assertBlock(center.above(), block -> block == AMBlocks.BLACK_AUREM.get(), "Black aurem not found");
            helper.assertItemEntityCountIs(AMItems.SUNSTONE.get(), center, 4, 0);
        }).thenExecute(Entity::discard).thenSucceed();
    }

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "purification_ritual")
    @TestHolder(description = "Test if the purification ritual correctly converts the obelisk to a celestial prism")
    public static void testPurificationRitual(ExtendedGameTestHelper helper) {
        ArsMagicaAPI api = ArsMagicaAPI.get();
        ISpell purificationSpell = api.makeSpell(List.of(), SpellStack.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.LIGHT.get()), new CompoundTag());
        BlockPos center = new BlockPos(3, 2, 3);
        BlockPos absoluteCenter = helper.absolutePos(center);
        // create player
        helper.startSequence(() -> {
            var player = helper.makeTickingMockServerPlayerInLevel(GameType.SURVIVAL).preventItemPickup();
            player.moveTo(helper.absolutePos(new BlockPos(1, 2, 1)), 0, 90);
            return player;
        // drop item
        }).thenExecuteAfter(2, player -> player.drop(new ItemStack(AMItems.MOONSTONE.get()), false))
        // cast spell
        .thenExecuteAfter(4, player -> {
            player.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(absoluteCenter));
            api.getSpellHelper().cast(purificationSpell, player, player.level(), 0, false, false);
        // check for celestial prism and item removal
        }).thenExecuteAfter(10, () -> {
            helper.assertBlock(center, block -> block == AMBlocks.CELESTIAL_PRISM.get(), "Celestial Prism not found");
            helper.assertItemEntityCountIs(AMItems.MOONSTONE.get(), center, 4, 0);
        }).thenExecute(Entity::discard).thenSucceed();
    }
}
