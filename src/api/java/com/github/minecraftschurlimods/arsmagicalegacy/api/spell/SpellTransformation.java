package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public record SpellTransformation(RuleTest from, BlockState to, ResourceLocation spellPart) {
    public static final ResourceKey<Registry<SpellTransformation>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_transformation"));
    public static final Codec<SpellTransformation> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RuleTest.CODEC.fieldOf("from").forGetter(SpellTransformation::from),
            BlockState.CODEC.fieldOf("to").forGetter(SpellTransformation::to),
            ResourceLocation.CODEC.fieldOf("spell_part").forGetter(SpellTransformation::spellPart)
    ).apply(instance, SpellTransformation::new));
    public static final Codec<Holder<SpellTransformation>> REFERENCE_CODEC = RegistryFileCodec.create(REGISTRY_KEY, DIRECT_CODEC);
    public static final Codec<HolderSet<SpellTransformation>> LIST_CODEC = RegistryCodecs.homogeneousList(REGISTRY_KEY, DIRECT_CODEC);
}
