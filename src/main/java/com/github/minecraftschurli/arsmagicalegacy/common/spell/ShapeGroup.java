package com.github.minecraftschurli.arsmagicalegacy.common.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.List;

public record ShapeGroup(List<ISpellPart> parts) {
    public static final Codec<ShapeGroup> CODEC = CodecHelper.forRegistry(ArsMagicaAPI.get().getSpellPartRegistry())
                                                             .listOf()
                                                             .xmap(ShapeGroup::of, ShapeGroup::parts);

    private static ShapeGroup of(List<ISpellPart> parts) {
        for (ISpellPart part : parts) {
            if (part instanceof ISpellPart.ISpellComponent) {
                throw new MalformedShapeGroupException("Shape groups can not contain components!", parts);
            }
        }
        return new ShapeGroup(parts);
    }

    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<ISpellPart> parts() {
        return Collections.unmodifiableList(this.parts);
    }

    public static class MalformedShapeGroupException extends RuntimeException {
        private final List<ISpellPart> parts;

        public MalformedShapeGroupException(String message, List<ISpellPart> parts) {
            super(message);
            this.parts = parts;
        }

        public List<ISpellPart> getParts() {
            return this.parts;
        }
    }
}
