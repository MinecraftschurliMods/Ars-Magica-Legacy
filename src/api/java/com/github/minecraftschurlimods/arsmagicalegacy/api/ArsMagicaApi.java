package com.github.minecraftschurlimods.arsmagicalegacy.api;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.ServiceLoader;

/// The main entrypoint for the Ars Magica: Legacy API.
@NonExtendable
public abstract class ArsMagicaApi {
    /// A [Lazy] that holds the [ArsMagicaApi] instance retrieved from the [ServiceLoader]. DO NOT ACCESS YOURSELF!
    private static final Lazy<ArsMagicaApi> INSTANCE = Lazy.of(() -> ServiceLoader.load(FMLLoader.getCurrent().getGameLayer(), ArsMagicaApi.class).findFirst().orElseThrow());

    /// The id of the Ars Magica: Legacy mod.
    public static final String MOD_ID = "arsmagicalegacy";

    /// Creates a new [Identifier] with the mod's namespace.
    ///
    /// @param path The path of the [Identifier].
    /// @return A new [Identifier].
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(ArsMagicaApi.MOD_ID, path);
    }

    /// @return An Arcane Compendium [ItemStack].
    public static ItemStackTemplate book() {
        return INSTANCE.get().getBook();
    }

    /// @return The [ManaHelper] instance.
    public static AbilityHelper abilityHelper() {
        return INSTANCE.get().getAbilityHelper();
    }

    /// @return The [ManaHelper] instance.
    public static BurnoutHelper burnoutHelper() {
        return INSTANCE.get().getBurnoutHelper();
    }

    /// @return The [ManaHelper] instance.
    public static MagicHelper magicHelper() {
        return INSTANCE.get().getMagicHelper();
    }

    /// @return The [ManaHelper] instance.
    public static ManaHelper manaHelper() {
        return INSTANCE.get().getManaHelper();
    }

    /// @return The [SpellHelper] instance.
    public static SpellHelper spellHelper() {
        return INSTANCE.get().getSpellHelper();
    }

    @Internal
    protected abstract ItemStackTemplate getBook();

    @Internal
    protected abstract AbilityHelper getAbilityHelper();

    @Internal
    protected abstract BurnoutHelper getBurnoutHelper();

    @Internal
    protected abstract MagicHelper getMagicHelper();

    @Internal
    protected abstract ManaHelper getManaHelper();

    @Internal
    protected abstract SpellHelper getSpellHelper();
}
