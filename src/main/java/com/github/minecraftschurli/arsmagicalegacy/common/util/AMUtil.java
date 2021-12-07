package com.github.minecraftschurli.arsmagicalegacy.common.util;

import net.minecraft.world.phys.Vec3;

public final class AMUtil {
    public static Vec3 closestPointOnLine(Vec3 view, Vec3 a, Vec3 b) {
        Vec3 c = view.subtract(a);
        Vec3 d = b.subtract(a).normalize();
        double p = d.dot(c);
        return p <= 0 ? a : p >= a.distanceTo(b) ? b : a.add(d.scale(p));
    }
}
