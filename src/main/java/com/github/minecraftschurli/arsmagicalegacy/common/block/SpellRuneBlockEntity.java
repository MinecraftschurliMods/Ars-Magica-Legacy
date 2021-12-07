package com.github.minecraftschurli.arsmagicalegacy.common.block;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.Spell;
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
    public static final String AWARD_XP_KEY = ArsMagicaAPI.MOD_ID + ":award_xp";

    private ISpell spell;
    private Integer index;
    private LivingEntity caster;
    private Boolean awardXp;

    public SpellRuneBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.SPELL_RUNE.get(), pWorldPosition, pBlockState);
    }

    @Override
    public CompoundTag save(CompoundTag pTag) {
        if (this.spell instanceof Spell) {
            pTag.put(SPELL_KEY, Spell.CODEC.encodeStart(NbtOps.INSTANCE, (Spell) this.spell).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        }
        if (this.index != null) {
            pTag.putInt(INDEX_KEY, this.index);
        }
        if (this.awardXp != null) {
            pTag.putBoolean(AWARD_XP_KEY, this.awardXp);
        }
        return super.save(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag.contains(SPELL_KEY)) {
            this.spell = Spell.CODEC.decode(NbtOps.INSTANCE, pTag.get(SPELL_KEY)).map(Pair::getFirst).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn);
        }
        if (pTag.contains(INDEX_KEY)) {
            this.index = pTag.getInt(INDEX_KEY);
        }
        if (pTag.contains(AWARD_XP_KEY)) {
            this.awardXp = pTag.getBoolean(AWARD_XP_KEY);
        }
        super.load(pTag);
    }

    public void collide(Level level, BlockPos pos, Entity entity, Direction direction) {
        if (this.spell == null) return;
        SpellCastResult r1 = ArsMagicaAPI.get().getSpellHelper().invoke(this.spell,
                this.caster,
                level,
                new EntityHitResult(entity),
                0,
                this.index,
                this.awardXp);
        SpellCastResult r2 = ArsMagicaAPI.get().getSpellHelper().invoke(this.spell,
                this.caster,
                level,
                new BlockHitResult(entity.position(),
                        direction,
                        pos,
                        false),
                0,
                this.index,
                this.awardXp);
        if (r1.isSuccess() || r2.isSuccess()) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_CLIENTS);
        }
    }

    public void setSpell(ISpell spell, LivingEntity caster, int index, boolean awardXp) {
        this.spell = spell;
        this.index = index;
        this.caster = caster;
        this.awardXp = awardXp;
    }
}
