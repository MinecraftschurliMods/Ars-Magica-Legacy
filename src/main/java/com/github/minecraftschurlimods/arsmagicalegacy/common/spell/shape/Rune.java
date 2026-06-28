package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SecondarySpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.SpellRuneBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

import java.util.List;
import java.util.UUID;

public class Rune extends SecondarySpellShape {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(), ArsMagicaApi.MOD_ID + "_rune");

    public Rune() {
        super(AMSpells.RUNE_POWER_STAT);
    }

    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        Level level = context.level();
        if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) return new SpellCastResult(spell);
        if (!(context.hitResult() instanceof BlockHitResult blockHitResult)) return new SpellCastResult(spell).setMessage(AMTranslations.SPELL_FAIL_NO_BLOCK);
        LivingEntity caster = context.caster();
        ServerPlayer player = caster instanceof ServerPlayer p ? p : FakePlayerFactory.get(serverLevel, GAME_PROFILE);
        Direction direction = blockHitResult.getDirection();
        BlockPos pos = blockHitResult.getBlockPos().offset(direction.getUnitVec3i());
        BlockState state = AMBlocks.SPELL_RUNE.get().getStateForPlacement(new BlockPlaceContext(level, player, InteractionHand.MAIN_HAND, ItemStack.EMPTY, new BlockHitResult(blockHitResult.getLocation(), direction, pos, false)));
        level.setBlockAndUpdate(pos, state);
        if (level.getBlockEntity(pos) instanceof SpellRuneBlockEntity spellRune) {
            spellRune.setData(context, (int) ArsMagicaApi.spellHelper().getModifiedStat(1, AMSpells.RUNE_POWER_STAT, modifiers, context));
        }
        return new SpellCastResult(spell).setSuccess();
    }
}
