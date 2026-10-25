package at.minecraftschurli.mods.arsmagicalegacy.test;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.apiimpl.AbilityHelperImpl;
import at.minecraftschurli.mods.arsmagicalegacy.apiimpl.BurnoutHelperImpl;
import at.minecraftschurli.mods.arsmagicalegacy.apiimpl.MagicHelperImpl;
import at.minecraftschurli.mods.arsmagicalegacy.apiimpl.ManaHelperImpl;
import at.minecraftschurli.mods.arsmagicalegacy.apiimpl.SpellHelperImpl;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.testframework.junit.EphemeralTestServerProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(EphemeralTestServerProvider.class)
public class ArsMagicaApiTest {
    /// Test that the book stack is present and has the correct item key.
    @Test
    public void testBookStackIsPresent(MinecraftServer server) {
        ItemStackTemplate book = ArsMagicaApi.book();
        assertNotNull(book, "Book stack is not present");
        ResourceKey<Item> bookItemKey = book.item().getKey();
        assertNotNull(bookItemKey, "Book item does not have a key");
        assertEquals("patchouli:guide_book", bookItemKey.identifier().toString(), "Book item is not patchouli:guide_book");
        Object bookData = book.components()
            .entrySet()
            .stream()
            .filter(e -> Objects.equals(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(e.getKey()), Identifier.fromNamespaceAndPath(PatchouliAPI.MOD_ID, "book")))
            .flatMap(e -> e.getValue().stream())
            .findFirst()
            .orElse(null);
        assertNotNull(bookData, "Book stack does not have book data");
    }

    /// Test that the implementation of [AbilityHelper] is present and is an instance of [AbilityHelperImpl].
    @Test
    public void testAbilityHelperImplementationIsPresent() {
        assertNotNull(ArsMagicaApi.abilityHelper(), "AbilityHelper implementation is not present");
        assertInstanceOf(AbilityHelperImpl.class, ArsMagicaApi.abilityHelper(), "AbilityHelper implementation is not an instance of AbilityHelperImpl");
    }

    /// Test that the implementation of [BurnoutHelper] is present and is an instance of [BurnoutHelperImpl].
    @Test
    public void testBurnoutHelperImplementationIsPresent() {
        assertNotNull(ArsMagicaApi.burnoutHelper(), "BurnoutHelper implementation is not present");
        assertInstanceOf(BurnoutHelperImpl.class, ArsMagicaApi.burnoutHelper(), "BurnoutHelper implementation is not an instance of BurnoutHelperImpl");
    }

    /// Test that the implementation of [MagicHelper] is present and is an instance of [MagicHelperImpl].
    @Test
    public void testMagicHelperImplementationIsPresent() {
        assertNotNull(ArsMagicaApi.magicHelper(), "MagicHelper implementation is not present");
        assertInstanceOf(MagicHelperImpl.class, ArsMagicaApi.magicHelper(), "MagicHelper implementation is not an instance of MagicHelperImpl");
    }

    /// Test that the implementation of [ManaHelper] is present and is an instance of [ManaHelperImpl].
    @Test
    public void testManaHelperImplementationIsPresent() {
        assertNotNull(ArsMagicaApi.manaHelper(), "ManaHelper implementation is not present");
        assertInstanceOf(ManaHelperImpl.class, ArsMagicaApi.manaHelper(), "ManaHelper implementation is not an instance of ManaHelperImpl");
    }

    /// Test that the implementation of [SpellHelper] is present and is an instance of [SpellHelperImpl].
    @Test
    public void testSpellHelperImplementationIsPresent() {
        assertNotNull(ArsMagicaApi.spellHelper(), "SpellHelper implementation is not present");
        assertInstanceOf(SpellHelperImpl.class, ArsMagicaApi.spellHelper(), "SpellHelper implementation is not an instance of SpellHelperImpl");
    }
}
