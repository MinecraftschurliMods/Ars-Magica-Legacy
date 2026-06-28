package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.GrowthContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.Plant;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

import java.util.List;
import java.util.UUID;

public class Grow extends SpellComponent.CastBlock {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(), ArsMagicaApi.MOD_ID + "_grow");

    @Override
    public SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult) {
        Spell spell = context.spell();
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        ServerPlayer player = context.caster() instanceof ServerPlayer p ? p : FakePlayerFactory.get(level, GAME_PROFILE);
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        for (Plant plant : AMUtil.getPlants(state, level.registryAccess())) {
            GrowthContext growthContext = plant.createContext(player, level, pos, state, ItemStack.EMPTY);
            if (plant.growthType().canGrow(growthContext)) {
                plant.growthType().grow(growthContext);
                return SpellComponentCastResult.success(spell);
            }
        }
        if (state.getBlock() instanceof BonemealableBlock block && block.isValidBonemealTarget(level, pos, state) && block.isBonemealSuccess(level, level.getRandom(), pos, state)) {
            block.performBonemeal(level, level.getRandom(), pos, state);
        }
        return SpellComponentCastResult.success(spell);
    }
}
