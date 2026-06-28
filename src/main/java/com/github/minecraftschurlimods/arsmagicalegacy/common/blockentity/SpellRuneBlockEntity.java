package com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SetSpellRuneOwnerPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class SpellRuneBlockEntity extends AMBlockEntity<SpellRuneBlockEntity.Data> {
    private Spell spell = Spell.EMPTY;
    @Nullable
    private LivingEntity owner;
    private boolean consume;
    private boolean awardXp;
    private int power;

    public SpellRuneBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.SPELL_RUNE.get(), pos, state, Data.CODEC);
    }

    @Override
    public void fromData(Data data) {
        spell = data.spell;
        if (data.owner.isPresent() && level instanceof ServerLevel serverLevel && serverLevel.getEntity(data.owner.get()) instanceof LivingEntity living) {
            owner = living;
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, ChunkPos.containing(getBlockPos()), new SetSpellRuneOwnerPacket(getBlockPos(), owner.getUUID()));
        }
        consume = data.consume;
        awardXp = data.awardXp;
        power = data.power;
    }

    @Override
    public Data toData() {
        return new Data(spell, owner == null ? Optional.empty() : Optional.of(owner.getUUID()), consume, awardXp, power);
    }

    public void setOwner(@Nullable UUID uuid) {
        if (level == null) return;
        owner = uuid != null && level.getEntity(uuid) instanceof LivingEntity living ? living : null;
    }

    public void setData(SpellCastContext context, int power) {
        this.spell = context.spell();
        this.owner = context.caster();
        this.consume = context.consume();
        this.awardXp = context.awardXp();
        this.power = power;
    }

    public void cast(Level level, BlockPos pos, Entity entity) {
        SpellCastResult result = ArsMagicaApi.spellHelper().castGrammar(new SpellCastContext(spell, level, owner, null, new EntityHitResult(entity), consume, awardXp));
        if (!result.isSuccess()) return;
        spell = result.getSpell();
        power--;
        if (power < 1) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        } else {
            setChanged();
        }
    }

    public record Data(Spell spell, Optional<UUID> owner, boolean consume, boolean awardXp, int power) {
        public static final Codec<Data> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Spell.CODEC.fieldOf("spell").forGetter(Data::spell),
            UUIDUtil.CODEC.optionalFieldOf("owner").forGetter(Data::owner),
            Codec.BOOL.fieldOf("consume").forGetter(Data::consume),
            Codec.BOOL.fieldOf("award_xp").forGetter(Data::awardXp),
            Codec.INT.fieldOf("power").forGetter(Data::power)
        ).apply(inst, Data::new));
    }
}
