package com.github.minecraftschurli.arsmagicalegacy.common.util;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

import java.util.stream.Collector;

public class ComponentUtil {

    public static Collector<MutableComponent, MutableComponent, MutableComponent> joiningComponents(String del) {
        TextComponent cdlComp = new TextComponent(del);
        return Collector.of(TextComponent.EMPTY::copy,
                            (component, component2) -> component.append(cdlComp).append(component2),
                            (component, component2) -> component.append(cdlComp).append(component2));
    }
}
