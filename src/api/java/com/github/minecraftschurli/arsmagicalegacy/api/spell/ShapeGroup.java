package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

public record ShapeGroup(List<ISpellPart> parts, List<Pair<ISpellShape, List<ISpellModifier>>> shapesWithModifiers) {
    public static final Codec<ShapeGroup> CODEC = CodecHelper.forRegistry(ArsMagicaAPI.get()::getSpellPartRegistry)
                                                             .listOf()
                                                             .xmap(ShapeGroup::of, ShapeGroup::parts);

    public static ShapeGroup of(List<ISpellPart> parts) {
        List<Pair<ISpellShape, List<ISpellModifier>>> map = new ArrayList<>();
        List<ISpellModifier> currentMods = null;
        for (ISpellPart part : parts) {
            if (part instanceof ISpellComponent) {
                throw new MalformedShapeGroupException("Shape groups can not contain components!", parts);
            }
            if (part instanceof ISpellShape shape) {
                currentMods = new ArrayList<>();
                map.add(Pair.of(shape, Collections.unmodifiableList(currentMods)));
            }
            if (part instanceof ISpellModifier modifier) {
                if (currentMods == null) {
                    throw new MalformedShapeGroupException("A Modifier can not come before a shape in a shape group!", parts);
                }
                currentMods.add(modifier);
            }
        }
        return new ShapeGroup(parts, map);
    }

    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<ISpellPart> parts() {
        return Collections.unmodifiableList(this.parts);
    }

    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<Pair<ISpellShape, List<ISpellModifier>>> shapesWithModifiers() {
        return Collections.unmodifiableList(this.shapesWithModifiers);
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
