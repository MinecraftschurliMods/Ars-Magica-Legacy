package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.IPrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.IPrefabSpellManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class PrefabSpellManager extends CodecDataManager<IPrefabSpell> implements IPrefabSpellManager {
    public static final CreativeModeTab ITEM_CATEGORY = new CreativeModeTab(ArsMagicaAPI.MOD_ID + ".prefab_spells") {
        @Override
        public ItemStack makeIcon() {
            return AMItems.SPELL_PARCHMENT.map(ItemStack::new).orElse(ItemStack.EMPTY);
        }
    };
    private static final Lazy<PrefabSpellManager> INSTANCE = Lazy.concurrentOf(PrefabSpellManager::new);
    private Map<IPrefabSpell, ResourceLocation> keys;

    private PrefabSpellManager() {
        super("prefab_spells", PrefabSpell.CODEC, PrefabSpellManager::validate, LogManager.getLogger());
    }

    /**
     * @return The only instance of this class.
     */
    public static PrefabSpellManager instance() {
        return INSTANCE.get();
    }

    @Override
    public IPrefabSpell getOrDefault(@Nullable final ResourceLocation id, final Supplier<IPrefabSpell> defaultSupplier) {
        return getOrDefault((Object) id, defaultSupplier);
    }

    @Override
    public Optional<IPrefabSpell> getOptional(@Nullable final ResourceLocation id) {
        return getOptional((Object) id);
    }

    public ResourceLocation getKey(IPrefabSpell spell) {
        return keys.get(spell);
    }

    private static void validate(Map<ResourceLocation, IPrefabSpell> data, Logger l) {
        instance().keys = data.entrySet().stream().collect(Collectors.toUnmodifiableMap(Entry::getValue, Entry::getKey));
    }

    public record PrefabSpell(Component name, ISpell spell, ResourceLocation icon) implements IPrefabSpell {
        public static final Codec<IPrefabSpell> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.either(Codec.STRING, CodecHelper.COMPONENT).xmap(stringComponentEither -> stringComponentEither.mapLeft(TextComponent::new).map(Function.identity(), Function.identity()), Either::right).optionalFieldOf("name", new TranslatableComponent(TranslationConstants.SPELL_PREFAB_NAME)).forGetter(IPrefabSpell::name),
                ISpell.CODEC.fieldOf("spell").forGetter(IPrefabSpell::spell),
                ResourceLocation.CODEC.fieldOf("icon").forGetter(IPrefabSpell::icon)
        ).apply(inst, PrefabSpell::new));

        @Override
        public DataResult<JsonElement> getEncodedSpell() {
            return CODEC.encodeStart(JsonOps.INSTANCE, this);
        }

        @Override
        public ItemStack makeSpell() {
            var helper = ArsMagicaAPI.get().getSpellHelper();
            ItemStack stack = new ItemStack(AMItems.SPELL.get());
            helper.setSpell(stack, spell());
            helper.setSpellName(stack, name());
            helper.setSpellIcon(stack, icon());
            return stack;
        }

        @Override
        public int compareTo(IPrefabSpell o) {
            PrefabSpellManager manager = PrefabSpellManager.instance();
            return manager.getKey(this).compareTo(manager.getKey(o));
        }
    }
}
