package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Harvest extends SpellComponent.CastBlock {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(), ArsMagicaApi.MOD_ID + "_harvest");
    private final boolean replant;

    public Harvest(boolean replant) {
        super(AMSpells.FORTUNE_STAT, AMSpells.SILK_TOUCH_STAT);
        this.replant = replant;
    }

    @Override
    public SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult) {
        Spell spell = context.spell();
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        ServerPlayer player = context.caster() instanceof ServerPlayer p ? p : FakePlayerFactory.get(level, GAME_PROFILE);
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof GameMasterBlock && !player.canUseGameMasterBlocks() || player.blockActionRestricted(level, pos, player.gameMode.getGameModeForPlayer())) return SpellComponentCastResult.pass(spell);
        for (Plant plant : AMUtil.getPlants(state, level.registryAccess())) {
            Map<ResourceKey<Enchantment>, SpellStat> enchantments = Map.of(Enchantments.FORTUNE, AMSpells.FORTUNE_STAT, Enchantments.SILK_TOUCH, AMSpells.SILK_TOUCH_STAT);
            ItemStack tool = plant.tool()
                .map(itemStackTemplate -> AMUtil.getEnchanted(itemStackTemplate.create(), modifiers, context, enchantments))
                .orElseGet(() -> AMUtil.getEnchantedSpell(modifiers, context, enchantments));
            GrowthContext growthContext = plant.createContext(player, level, pos, state, tool);
            if (!plant.growthType().canHarvest(growthContext)) continue;
            plant.growthType().harvest(growthContext, replant).forEach(stack -> {
                if (player.isFakePlayer() || !player.getInventory().add(stack)) {
                    player.drop(stack, false);
                }
            });
            break;
        }
        return SpellComponentCastResult.success(spell);
    }
}
