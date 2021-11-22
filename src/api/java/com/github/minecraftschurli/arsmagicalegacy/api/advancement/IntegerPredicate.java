package com.github.minecraftschurli.arsmagicalegacy.api.advancement;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;

public record IntegerPredicate(MinMaxBounds.Ints composite) {
    public static final IntegerPredicate ANY = new IntegerPredicate(MinMaxBounds.Ints.ANY);

    public boolean matches(int level) {
        if (this == ANY) {
            return true;
        } else {
            return this.composite.matches(level);
        }
    }

    public JsonElement serializeToJson() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("level", this.composite.serializeToJson());
            return jsonobject;
        }
    }

    public static IntegerPredicate fromJson(@Nullable JsonElement pJson) {
        if (pJson != null && !pJson.isJsonNull()) {
            JsonObject jsonobject = GsonHelper.convertToJsonObject(pJson, "level");
            MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.fromJson(jsonobject.get("level"));
            return new IntegerPredicate(minmaxbounds$ints);
        } else {
            return ANY;
        }
    }

    public static class Builder {
        private MinMaxBounds.Ints composite = MinMaxBounds.Ints.ANY;

        public static IntegerPredicate.Builder level() {
            return new IntegerPredicate.Builder();
        }

        public IntegerPredicate.Builder setComposite(MinMaxBounds.Ints pComposite) {
            this.composite = pComposite;
            return this;
        }

        public IntegerPredicate build() {
            return new IntegerPredicate(this.composite);
        }
    }
}
