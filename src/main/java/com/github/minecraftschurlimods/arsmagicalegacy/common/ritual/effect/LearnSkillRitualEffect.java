package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualEffect;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public record LearnSkillRitualEffect(ISpellPart part) implements RitualEffect {
    public static final Codec<LearnSkillRitualEffect> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecHelper.forRegistry(AMRegistries.SPELL_PART_REGISTRY).fieldOf("spell_part").forGetter(LearnSkillRitualEffect::part)
    ).apply(inst, LearnSkillRitualEffect::new));

    @Override
    public boolean performEffect(Player player, ServerLevel level, BlockPos pos) {
        ArsMagicaAPI.get().getSkillHelper().learn(player, part().getRegistryName());
        return true;
    }

    @Override
    public Codec<? extends RitualEffect> codec() {
        return CODEC;
    }
}
