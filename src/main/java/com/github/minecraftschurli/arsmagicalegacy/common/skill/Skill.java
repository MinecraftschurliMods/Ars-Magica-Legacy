package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.codeclib.CodecHelper;
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
            ResourceLocation.CODEC.fieldOf("icon").forGetter(ISkill::getIcon),
            Codec.INT.fieldOf("x").forGetter(ISkill::getX),
            Codec.INT.fieldOf("y").forGetter(ISkill::getY),
            Codec.BOOL.fieldOf("hidden").orElse(false).forGetter(ISkill::isHidden)
    ).apply(inst, Skill::new));
    @Internal
    public static final Codec<ISkill> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.listOf().<Set<ResourceLocation>>xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("parents").orElseGet(Sets::newHashSet).forGetter(ISkill::getParents),
            CodecHelper.mapOf(ResourceLocation.CODEC, Codec.INT).fieldOf("cost").orElseGet(Maps::newHashMap).forGetter(ISkill::getCost),
            ResourceLocation.CODEC.fieldOf("occulus_tab").forGetter(ISkill::getOcculusTab),
            ResourceLocation.CODEC.fieldOf("icon").forGetter(ISkill::getIcon),
            Codec.INT.fieldOf("x").forGetter(ISkill::getX),
            Codec.INT.fieldOf("y").forGetter(ISkill::getY),
            Codec.BOOL.fieldOf("hidden").orElse(false).forGetter(ISkill::isHidden)
    ).apply(inst, Skill::new));
    //@formatter:on
    private final int x;
    private final int y;
    private ResourceLocation id;
    private final ResourceLocation occulusTab;
    private final ResourceLocation icon;
    private final Set<ResourceLocation> parents;
    private final Map<ResourceLocation, Integer> cost;
    private final boolean hidden;

    /**
     * Creates a new skill.
     *
     * @param parents    The skill's parent set.
     * @param cost       The skill cost.
     * @param occulusTab The occulus tab to display the skill in.
     * @param icon       The skill icon.
     * @param x          The X coordinate to display the skill at.
     * @param y          The Y coordinate to display the skill at.
     * @param hidden     Whether the skill is hidden or not.
     */
    public Skill(Set<ResourceLocation> parents, Map<ResourceLocation, Integer> cost, ResourceLocation occulusTab, ResourceLocation icon, int x, int y, boolean hidden) {
        this.occulusTab = occulusTab;
        this.icon = icon;
        this.parents = parents;
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.hidden = hidden;
    }

    /**
     * Creates a new skill. Also sets the id.
     *
     * @param id         The skill's id.
     * @param parents    The skill's parent set.
     * @param cost       The skill cost.
     * @param occulusTab The occulus tab to display the skill in.
     * @param icon       The skill icon.
     * @param x          The X coordinate to display the skill at.
     * @param y          The Y coordinate to display the skill at.
     * @param hidden     Whether the skill is hidden or not.
     */
    public Skill(ResourceLocation id, Set<ResourceLocation> parents, Map<ResourceLocation, Integer> cost, ResourceLocation occulusTab, ResourceLocation icon, Integer x, Integer y, Boolean hidden) {
        this(parents, cost, occulusTab, icon, x, y, hidden);
        setId(id);
    }

    void setId(ResourceLocation id) {
        this.id = id;
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

    @Override
    public ResourceLocation getOcculusTab() {
        return occulusTab;
    }

    @Override
    public ResourceLocation getIcon() {
        return icon;
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
        var that = (Skill) obj;
        return Objects.equals(id, that.id) && Objects.equals(occulusTab, that.occulusTab) && Objects.equals(icon, that.icon) && Objects.equals(parents, that.parents) && x == that.x && y == that.y && Objects.equals(cost, that.cost) && hidden == that.hidden;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, occulusTab, icon, parents, x, y, cost, hidden);
    }

    @Override
    public String toString() {
        return "Skill[id=" + id + ", occulusTab=" + occulusTab + ", icon=" + icon + ", parents=" + parents + ", x=" + x + ", y=" + y + ", cost=" + cost + ", hidden=" + hidden + ']';
    }
}
