package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

import java.util.List;
import java.util.UUID;

public class PlaceBlock extends SpellComponent.CastBlock {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(), ArsMagicaApi.MOD_ID + "_place_block");

    @Override
    public SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult) {
        Spell spell = context.spell();
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        Block block = spell.dataComponents().grammar().get(AMDataComponents.SPELL_BLOCK.get());
        if (block == null || block.defaultBlockState().isAir()) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_PLACE_BLOCK_NO_SELECTION);
        ServerPlayer player = context.caster() instanceof ServerPlayer p ? p : FakePlayerFactory.get(level, GAME_PROFILE);
        ItemStack stack = new ItemStack(block.asItem());
        Inventory inventory = player.getInventory();
        if (!player.isCreative() && !inventory.contains(stack)) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_PLACE_BLOCK_NO_BLOCK);
        BlockPos pos = hitResult.getBlockPos();
        BlockPlaceContext placeContext = new BlockPlaceContext(level, player, InteractionHand.MAIN_HAND, stack, hitResult);
        if (!level.getBlockState(pos).canBeReplaced(placeContext)) {
            pos = pos.offset(hitResult.getDirection().getUnitVec3i());
        }
        BlockState state = block.getStateForPlacement(placeContext);
        if (state == null || !state.canSurvive(level, pos)) return SpellComponentCastResult.success(spell);
        level.setBlockAndUpdate(pos, state);
        block.setPlacedBy(level, pos, state, player, stack);
        if (!player.isCreative()) {
            inventory.getItem(inventory.findSlotMatchingItem(stack)).shrink(1);
        }
        return SpellComponentCastResult.success(spell);
    }

    @Override
    public DataComponentType<?> getDataComponentType() {
        return AMDataComponents.SPELL_BLOCK.get();
    }
}
