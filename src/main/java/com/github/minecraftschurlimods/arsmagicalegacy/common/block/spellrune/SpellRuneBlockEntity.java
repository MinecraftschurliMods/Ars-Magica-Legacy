package com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class SpellRuneBlockEntity extends BlockEntity {
    public static final String SPELL_KEY = ArsMagicaAPI.MOD_ID + ":spell";
    public static final String INDEX_KEY = ArsMagicaAPI.MOD_ID + ":index";
    public static final String CASTER_KEY = ArsMagicaAPI.MOD_ID + ":caster";
    public static final String AWARD_XP_KEY = ArsMagicaAPI.MOD_ID + ":award_xp";
    private ISpell spell;
    private Integer index;
    private Integer casterId;
    private LivingEntity caster;
    private Boolean awardXp;

    public SpellRuneBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.SPELL_RUNE.get(), pWorldPosition, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put(SPELL_KEY, ISpell.CODEC.encodeStart(NbtOps.INSTANCE, spell).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        if (index != null) {
            pTag.putInt(INDEX_KEY, index);
        }
        if (casterId != null) {
            pTag.putInt(CASTER_KEY, casterId);
        }
        if (awardXp != null) {
            pTag.putBoolean(AWARD_XP_KEY, awardXp);
        }
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag.contains(SPELL_KEY)) {
            spell = ISpell.CODEC.decode(NbtOps.INSTANCE, pTag.get(SPELL_KEY)).map(Pair::getFirst).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn);
        }
        if (pTag.contains(INDEX_KEY)) {
            index = pTag.getInt(INDEX_KEY);
        }
        if (pTag.contains(CASTER_KEY)) {
            casterId = pTag.getInt(CASTER_KEY);
        }
        if (pTag.contains(AWARD_XP_KEY)) {
            awardXp = pTag.getBoolean(AWARD_XP_KEY);
        }
        super.load(pTag);
    }

    /**
     * Called when an entity collides with this block.
     *
     * @param level     The level of this block.
     * @param pos       The position of this block.
     * @param entity    The entity that cóllided with this block.
     * @param direction The direction the collision occured on.
     */
    public void collide(Level level, BlockPos pos, Entity entity, Direction direction) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        if (caster == null && casterId != null && level.getEntity(casterId) instanceof LivingEntity living) {
            caster = living;
        }
        if (spell == null || caster == null) return;
        SpellCastResult r1 = helper.invoke(spell, caster, level, new EntityHitResult(entity), 0, index, awardXp);
        SpellCastResult r2 = helper.invoke(spell, caster, level, new BlockHitResult(entity.position(), direction, pos, false), 0, index, awardXp);
        if (r1.isSuccess() || r2.isSuccess()) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }
    }

    /**
     * Sets this block's spell.
     *
     * @param spell   The spell.
     * @param caster  The original caster.
     * @param index   The shape group index to use.
     * @param awardXp Whether to grant xp to the original caster or not.
     */
    public void setSpell(ISpell spell, LivingEntity caster, int index, boolean awardXp) {
        this.spell = spell;
        this.index = index;
        this.casterId = caster.getId();
        this.caster = caster;
        this.awardXp = awardXp;
    }
}
