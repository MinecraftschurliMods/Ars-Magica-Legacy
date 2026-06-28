package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.item.CrystalPhylacteryItem;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public interface AMCreativeTabs {
    DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArsMagicaApi.MOD_ID);
    DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup." + ArsMagicaApi.MOD_ID + ".main"))
        .icon(ArsMagicaApi.book()::create)
        .displayItems((display, output) -> {
            output.accept(AMItems.LIQUID_ETHERIUM_BUCKET);
            output.accept(AMItems.OCCULUS);
            output.accept(AMItems.INSCRIPTION_TABLE);
            output.accept(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1);
            output.accept(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2);
            output.accept(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3);
            output.accept(AMItems.ALTAR_CORE);
            output.accept(AMItems.MAGIC_WALL);
            output.accept(AMItems.OBELISK);
            output.accept(AMItems.CELESTIAL_PRISM);
            output.accept(AMItems.BLACK_AUREM);
            output.accept(AMItems.CRYSTAL_WRENCH);
            output.accept(AMItems.WIZARDS_CHALK);
            output.accept(AMItems.REDSTONE_INLAY);
            output.accept(AMItems.IRON_INLAY);
            output.accept(AMItems.GOLD_INLAY);
            output.accept(AMItems.VINTEUM_TORCH);
            output.accept(AMItems.SPELL_PARCHMENT);
            output.accept(AMItems.SPELL_BOOK);
            output.accept(AMItems.MAGITECH_GOGGLES);
            output.accept(AMItems.MAGE_HELMET);
            output.accept(AMItems.MAGE_CHESTPLATE);
            output.accept(AMItems.MAGE_LEGGINGS);
            output.accept(AMItems.MAGE_BOOTS);
            output.accept(AMItems.BATTLEMAGE_HELMET);
            output.accept(AMItems.BATTLEMAGE_CHESTPLATE);
            output.accept(AMItems.BATTLEMAGE_LEGGINGS);
            output.accept(AMItems.BATTLEMAGE_BOOTS);
            output.accept(AMItems.MANA_CAKE);
            output.accept(AMItems.MANA_MARTINI);
            acceptVariants(display, output, AMItems.INFINITY_ORB, AMRegistries.Keys.SKILL_POINT, (stack, holder) -> stack.set(AMDataComponents.SKILL_POINT, holder));
            acceptVariants(display, output, AMItems.AFFINITY_ESSENCE, AMRegistries.Keys.AFFINITY, (stack, holder) -> stack.set(AMDataComponents.AFFINITY, holder), Affinity.NONE);
            acceptVariants(display, output, AMItems.AFFINITY_TOME, AMRegistries.Keys.AFFINITY, (stack, holder) -> stack.set(AMDataComponents.AFFINITY, holder));
            output.accept(AMItems.BLANK_RUNE);
            output.accept(AMItems.BLACK_RUNE);
            output.accept(AMItems.LIGHT_GRAY_RUNE);
            output.accept(AMItems.GRAY_RUNE);
            output.accept(AMItems.WHITE_RUNE);
            output.accept(AMItems.BROWN_RUNE);
            output.accept(AMItems.RED_RUNE);
            output.accept(AMItems.ORANGE_RUNE);
            output.accept(AMItems.YELLOW_RUNE);
            output.accept(AMItems.LIME_RUNE);
            output.accept(AMItems.GREEN_RUNE);
            output.accept(AMItems.CYAN_RUNE);
            output.accept(AMItems.LIGHT_BLUE_RUNE);
            output.accept(AMItems.BLUE_RUNE);
            output.accept(AMItems.PURPLE_RUNE);
            output.accept(AMItems.MAGENTA_RUNE);
            output.accept(AMItems.PINK_RUNE);
            output.accept(AMItems.RUNE_BAG);
            output.accept(AMItems.CHIMERITE_ORE);
            output.accept(AMItems.DEEPSLATE_CHIMERITE_ORE);
            output.accept(AMItems.CHIMERITE);
            output.accept(AMItems.CHIMERITE_BLOCK);
            output.accept(AMItems.TOPAZ_ORE);
            output.accept(AMItems.DEEPSLATE_TOPAZ_ORE);
            output.accept(AMItems.TOPAZ);
            output.accept(AMItems.TOPAZ_BLOCK);
            output.accept(AMItems.VINTEUM_ORE);
            output.accept(AMItems.DEEPSLATE_VINTEUM_ORE);
            output.accept(AMItems.VINTEUM_DUST);
            output.accept(AMItems.VINTEUM_BLOCK);
            output.accept(AMItems.MOONSTONE_ORE);
            output.accept(AMItems.DEEPSLATE_MOONSTONE_ORE);
            output.accept(AMItems.MOONSTONE);
            output.accept(AMItems.MOONSTONE_BLOCK);
            output.accept(AMItems.SUNSTONE_ORE);
            output.accept(AMItems.SUNSTONE);
            output.accept(AMItems.SUNSTONE_BLOCK);
            output.accept(AMItems.ARCANE_COMPOUND);
            output.accept(AMItems.ARCANE_ASH);
            output.accept(AMItems.PURIFIED_VINTEUM_DUST);
            output.accept(AMItems.WITCHWOOD_LOG);
            output.accept(AMItems.WITCHWOOD);
            output.accept(AMItems.STRIPPED_WITCHWOOD_LOG);
            output.accept(AMItems.STRIPPED_WITCHWOOD);
            output.accept(AMItems.WITCHWOOD_LEAVES);
            output.accept(AMItems.WITCHWOOD_SAPLING);
            output.accept(AMItems.WITCHWOOD_PLANKS);
            output.accept(AMItems.WITCHWOOD_SLAB);
            output.accept(AMItems.WITCHWOOD_STAIRS);
            output.accept(AMItems.WITCHWOOD_FENCE);
            output.accept(AMItems.WITCHWOOD_FENCE_GATE);
            output.accept(AMItems.WITCHWOOD_DOOR);
            output.accept(AMItems.WITCHWOOD_TRAPDOOR);
            output.accept(AMItems.WITCHWOOD_BUTTON);
            output.accept(AMItems.WITCHWOOD_PRESSURE_PLATE);
            output.accept(AMItems.WITCHWOOD_SIGN);
            output.accept(AMItems.WITCHWOOD_HANGING_SIGN);
            output.accept(AMItems.WITCHWOOD_BOAT);
            output.accept(AMItems.WITCHWOOD_CHEST_BOAT);
            output.accept(AMItems.AUM);
            output.accept(AMItems.CERUBLOSSOM);
            output.accept(AMItems.DESERT_NOVA);
            output.accept(AMItems.TARMA_ROOT);
            output.accept(AMItems.WAKEBLOOM);
            output.accept(AMItems.DRYAD_SPAWN_EGG);
            output.accept(AMItems.MANA_CREEPER_SPAWN_EGG);
            output.accept(AMItems.WATER_GUARDIAN_SPAWN_EGG);
            output.accept(AMItems.FIRE_GUARDIAN_SPAWN_EGG);
            output.accept(AMItems.EARTH_GUARDIAN_SPAWN_EGG);
            output.accept(AMItems.AIR_GUARDIAN_SPAWN_EGG);
            output.accept(AMItems.ICE_GUARDIAN_SPAWN_EGG);
            output.accept(AMItems.LIGHTNING_GUARDIAN_SPAWN_EGG);
            output.accept(AMItems.NATURE_GUARDIAN_SPAWN_EGG);
            output.accept(AMItems.LIFE_GUARDIAN_SPAWN_EGG);
            output.accept(AMItems.ARCANE_GUARDIAN_SPAWN_EGG);
            output.accept(AMItems.ENDER_GUARDIAN_SPAWN_EGG);
            output.accept(AMItems.CRYSTAL_PHYLACTERY);
            CrystalPhylacteryItem.addToCreativeTab(output::accept);
        })
        .build());
    DeferredHolder<CreativeModeTab, CreativeModeTab> SPELL_PREFABS = CREATIVE_TABS.register("spell_prefabs", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup." + ArsMagicaApi.MOD_ID + ".spell_prefabs"))
        .icon(AMItems.SPELL_PARCHMENT::toStack)
        .displayItems((display, output) -> {
            HolderLookup.RegistryLookup<Spell> lookup = display.holders().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB);
            lookup.listElements()
                .sorted(Comparator.comparing(holder -> Objects.requireNonNull(holder.getKey())))
                .map(Holder::value)
                .forEach(spell -> {
                    ItemStack stack = AMItems.SPELL.toStack();
                    stack.set(AMDataComponents.SPELL, spell);
                    output.accept(stack);
                });
        })
        .build());

    @SafeVarargs
    private static <T> void acceptVariants(CreativeModeTab.ItemDisplayParameters display, CreativeModeTab.Output output, DeferredItem<?> item, ResourceKey<Registry<T>> registryKey, BiConsumer<ItemStack, Holder<T>> consumer, ResourceKey<T>... ignored) {
        Set<ResourceKey<T>> set = Set.of(ignored);
        display.holders().lookup(registryKey).ifPresent(registry -> registry.listElements().forEach(holder -> {
            if (set.contains(holder.key())) return;
            ItemStack stack = item.toStack();
            consumer.accept(stack, holder);
            output.accept(stack);
        }));
    }
}
