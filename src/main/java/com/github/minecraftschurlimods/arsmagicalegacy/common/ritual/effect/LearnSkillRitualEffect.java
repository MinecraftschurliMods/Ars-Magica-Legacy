package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public record LearnSkillRitualEffect(ResourceLocation id) implements RitualEffect {
    public static final Codec<LearnSkillRitualEffect> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("skill").forGetter(LearnSkillRitualEffect::id)
    ).apply(inst, LearnSkillRitualEffect::new));

    public LearnSkillRitualEffect(ISpellPart part) {
        this(part.getId());
    }

    @Override
    public boolean performEffect(Player player, ServerLevel level, BlockPos pos) {
        ArsMagicaAPI.get().getSkillHelper().learn(player, id);
        return true;
    }

    @Override
    public Codec<? extends RitualEffect> codec() {
        return CODEC;
    }
}
