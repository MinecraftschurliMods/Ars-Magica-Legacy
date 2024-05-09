package com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.UUID;

public class SpellRuneBlockEntity extends BlockEntity {
    public static final String SPELL_KEY = ArsMagicaAPI.MOD_ID + ":spell";
    public static final String INDEX_KEY = ArsMagicaAPI.MOD_ID + ":index";
    public static final String CASTER_KEY = ArsMagicaAPI.MOD_ID + ":caster";
    public static final String AWARD_XP_KEY = ArsMagicaAPI.MOD_ID + ":award_xp";
    private ISpell spell;
    private Integer index;
    private UUID casterId;
    private LivingEntity caster;
    private Boolean awardXp;

    public SpellRuneBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.SPELL_RUNE.get(), pWorldPosition, pBlockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains(SPELL_KEY)) {
            spell = ISpell.CODEC.decode(NbtOps.INSTANCE, tag.get(SPELL_KEY)).map(Pair::getFirst).getOrThrow();
        }
        if (tag.contains(INDEX_KEY)) {
            index = tag.getInt(INDEX_KEY);
        }
        if (tag.contains(CASTER_KEY)) {
            casterId = tag.getUUID(CASTER_KEY);
        }
        if (tag.contains(AWARD_XP_KEY)) {
            awardXp = tag.getBoolean(AWARD_XP_KEY);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.put(SPELL_KEY, ISpell.CODEC.encodeStart(NbtOps.INSTANCE, spell).getOrThrow());
        if (index != null) {
            tag.putInt(INDEX_KEY, index);
        }
        if (casterId != null) {
            tag.putUUID(CASTER_KEY, casterId);
        }
        if (awardXp != null) {
            tag.putBoolean(AWARD_XP_KEY, awardXp);
        }
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
        if (caster == null && casterId != null && level instanceof ServerLevel server && server.getEntity(casterId) instanceof LivingEntity living) {
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
        this.casterId = caster.getUUID();
        this.caster = caster;
        this.awardXp = awardXp;
    }
}
