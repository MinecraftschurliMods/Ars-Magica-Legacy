package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;

import java.util.function.Function;

public class PrefabSpellManager extends CodecDataManager<PrefabSpellManager.PrefabSpell> {
    public static final CreativeModeTab ITEM_CATEGORY = new CreativeModeTab(ArsMagicaAPI.MOD_ID+".prefab_spells") {
        @Override
        public ItemStack makeIcon() {
            return AMItems.SPELL_PARCHMENT.map(ItemStack::new).orElse(ItemStack.EMPTY);
        }
    };

    private static final Lazy<PrefabSpellManager> INSTANCE = Lazy.concurrentOf(PrefabSpellManager::new);

    private PrefabSpellManager() {
        super("prefab_spells", PrefabSpell.CODEC, LogManager.getLogger());
    }

    public static PrefabSpellManager instance() {
        return INSTANCE.get();
    }

    public record PrefabSpell(Component name, Spell spell, ResourceLocation icon) {
        public static final Codec<PrefabSpell> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.either(Codec.STRING, CodecHelper.COMPONENT).xmap(stringComponentEither -> stringComponentEither.mapLeft(TextComponent::new).map(Function.identity(), Function.identity()), Either::right).optionalFieldOf("name", new TranslatableComponent(TranslationConstants.DEFAULT_PREFAB_SPELL)).forGetter(PrefabSpell::name),
                Spell.CODEC.fieldOf("spell").forGetter(PrefabSpell::spell),
                ResourceLocation.CODEC.fieldOf("icon").forGetter(PrefabSpell::icon)
        ).apply(inst, PrefabSpell::new));

        public PrefabSpell(String name, Spell spell, ResourceLocation icon) {
            this(new TextComponent(name), spell, icon);
        }

        public ItemStack makeSpell() {
            ItemStack stack = new ItemStack(AMItems.SPELL.get());
            SpellItem.saveSpell(stack, spell());
            stack.setHoverName(name());
            SpellItem.setSpellIcon(stack, icon());
            return stack;
        }

        public ItemStack makeRecipe() {
            return InscriptionTableBlockEntity.makeRecipe(name().getString(), "The Mystic Forces", spell());
        }
    }
}
