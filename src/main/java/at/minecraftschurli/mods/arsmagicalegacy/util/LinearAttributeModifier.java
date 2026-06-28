package at.minecraftschurli.mods.arsmagicalegacy.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record LinearAttributeModifier(Identifier id, double min, double max, AttributeModifier.Operation operation) {
    public static final Codec<LinearAttributeModifier> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Identifier.CODEC.fieldOf("id").forGetter(LinearAttributeModifier::id),
        Codec.DOUBLE.fieldOf("min").forGetter(LinearAttributeModifier::min),
        Codec.DOUBLE.fieldOf("max").forGetter(LinearAttributeModifier::max),
        AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(LinearAttributeModifier::operation)
    ).apply(inst, LinearAttributeModifier::new));
}
