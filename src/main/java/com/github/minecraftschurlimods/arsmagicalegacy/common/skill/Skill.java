package com.github.minecraftschurlimods.arsmagicalegacy.common.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Skill implements ISkill {
    //@formatter:off
    @Internal
    public static final Codec<ISkill> NETWORK_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(ISkill::getId),
            ResourceLocation.CODEC.listOf().<Set<ResourceLocation>>xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("parents").orElseGet(Sets::newHashSet).forGetter(ISkill::getParents),
            CodecHelper.mapOf(ResourceLocation.CODEC, Codec.INT).fieldOf("cost").orElseGet(Maps::newHashMap).forGetter(ISkill::getCost),
            ResourceLocation.CODEC.fieldOf("occulus_tab").forGetter(ISkill::getOcculusTab),
            Codec.INT.fieldOf("x").forGetter(ISkill::getX),
            Codec.INT.fieldOf("y").forGetter(ISkill::getY),
            Codec.BOOL.fieldOf("hidden").orElse(false).forGetter(ISkill::isHidden)
    ).apply(inst, Skill::new));
    @Internal
    public static final Codec<ISkill> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.listOf().<Set<ResourceLocation>>xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("parents").orElseGet(Sets::newHashSet).forGetter(ISkill::getParents),
            CodecHelper.mapOf(ResourceLocation.CODEC, Codec.INT).fieldOf("cost").orElseGet(Maps::newHashMap).forGetter(ISkill::getCost),
            ResourceLocation.CODEC.fieldOf("occulus_tab").forGetter(ISkill::getOcculusTab),
            Codec.INT.fieldOf("x").forGetter(ISkill::getX),
            Codec.INT.fieldOf("y").forGetter(ISkill::getY),
            Codec.BOOL.fieldOf("hidden").orElse(false).forGetter(ISkill::isHidden)
    ).apply(inst, Skill::new));
    //@formatter:on
    private final int x;
    private final int y;
    private final ResourceLocation occulusTab;
    private final Set<ResourceLocation> parents;
    private final Map<ResourceLocation, Integer> cost;
    private final boolean hidden;
    private ResourceLocation id;

    public Skill(Set<ResourceLocation> parents, Map<ResourceLocation, Integer> cost, ResourceLocation occulusTab, int x, int y, boolean hidden) {
        this.occulusTab = occulusTab;
        this.parents = parents;
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.hidden = hidden;
    }

    public Skill(ResourceLocation id, Set<ResourceLocation> parents, Map<ResourceLocation, Integer> cost, ResourceLocation occulusTab, Integer x, Integer y, Boolean hidden) {
        this(parents, cost, occulusTab, x, y, hidden);
        setId(id);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    void setId(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getOcculusTab() {
        return occulusTab;
    }

    @Override
    public Set<ResourceLocation> getParents() {
        return Collections.unmodifiableSet(parents);
    }

    @Override
    public Map<ResourceLocation, Integer> getCost() {
        return Collections.unmodifiableMap(cost);
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        Skill that = (Skill) obj;
        return Objects.equals(id, that.id) && Objects.equals(occulusTab, that.occulusTab) && Objects.equals(parents, that.parents) && x == that.x && y == that.y && Objects.equals(cost, that.cost) && hidden == that.hidden;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, occulusTab, parents, x, y, cost, hidden);
    }

    @Override
    public String toString() {
        return "Skill[id=" + id + ", occulusTab=" + occulusTab + ", parents=" + parents + ", x=" + x + ", y=" + y + ", cost=" + cost + ", hidden=" + hidden + ']';
    }
}
