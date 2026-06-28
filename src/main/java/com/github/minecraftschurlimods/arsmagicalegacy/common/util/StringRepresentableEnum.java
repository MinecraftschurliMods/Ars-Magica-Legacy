package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

/// Helper interface that automatically implements [StringRepresentable#getSerializedName()] using [Enum#name()].
public interface StringRepresentableEnum extends StringRepresentable {
    String name();

    @Override
    default String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
