package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.IPrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.IPrefabSpellManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
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
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public final class PrefabSpellManager extends CodecDataManager<IPrefabSpell> implements IPrefabSpellManager {
    public static final CreativeModeTab ITEM_CATEGORY = new CreativeModeTab(ArsMagicaAPI.MOD_ID + ".prefab_spells") {
        @Override
        public ItemStack makeIcon() {
            return AMItems.SPELL_PARCHMENT.map(ItemStack::new).orElse(ItemStack.EMPTY);
        }
    };
    private static final Lazy<PrefabSpellManager> INSTANCE = Lazy.concurrentOf(PrefabSpellManager::new);

    private PrefabSpellManager() {
        super("prefab_spells", PrefabSpell.CODEC, LogManager.getLogger());
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
            ItemStack stack = new ItemStack(AMItems.SPELL.get());
            SpellItem.saveSpell(stack, spell());
            stack.setHoverName(name());
            SpellItem.setSpellIcon(stack, icon());
            return stack;
        }
    }
}
