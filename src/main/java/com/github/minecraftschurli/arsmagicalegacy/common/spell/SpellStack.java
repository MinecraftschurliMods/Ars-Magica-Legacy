package com.github.minecraftschurli.arsmagicalegacy.common.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

public record SpellStack(List<ISpellPart> parts,
                         List<ISpellPart.ISpellShape> shapes,
                         Map<ISpellPart.ISpellComponent, List<ISpellPart.ISpellModifier>> componentsWithModifiers) {
    public static final Codec<SpellStack> CODEC = CodecHelper.forRegistry(ArsMagicaAPI.get().getSpellPartRegistry())
                                                             .listOf()
                                                             .xmap(SpellStack::of, SpellStack::parts);

    public static final SpellStack EMPTY = SpellStack.of(List.of());

    public static SpellStack of(List<ISpellPart> parts) {
        Map<ISpellPart.ISpellComponent, List<ISpellPart.ISpellModifier>> componentsWithModifiers = new HashMap<>();
        List<ISpellPart.ISpellShape> shapes = new ArrayList<>();
        ISpellPart.ISpellComponent lastComponent = null;
        for (ISpellPart part : parts) {
            if (part instanceof ISpellPart.ISpellComponent component) {
                lastComponent = component;
            }
            if (part instanceof ISpellPart.ISpellModifier modifier) {
                componentsWithModifiers.computeIfAbsent(lastComponent, c -> new ArrayList<>()).add(modifier);
            }
            if (part instanceof ISpellPart.ISpellShape shape) {
                if (lastComponent != null) {
                    throw new MalformedSpellStackException("A shape can not come after the first component!", parts);
                }
                shapes.add(shape);
            }
        }
        return new SpellStack(parts, shapes, componentsWithModifiers);
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
    public List<ISpellPart.ISpellShape> shapes() {
        return Collections.unmodifiableList(this.shapes);
    }

    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public Map<ISpellPart.ISpellComponent, List<ISpellPart.ISpellModifier>> componentsWithModifiers() {
        return Collections.unmodifiableMap(this.componentsWithModifiers);
    }

    public static class MalformedSpellStackException extends RuntimeException {
        private final List<ISpellPart> parts;

        public MalformedSpellStackException(String message, List<ISpellPart> parts) {
            super(message);
            this.parts = parts;
        }

        public List<ISpellPart> getParts() {
            return this.parts;
        }
    }
}
