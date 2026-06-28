package com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellDataComponentMap;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellGrammar;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.menu.InscriptionTableMenu;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InscriptionTableBlockEntity extends AMBlockEntity<InscriptionTableBlockEntity.Data> implements Container, MenuProvider {
    private ItemStack stack = ItemStack.EMPTY;
    private MenuData menuData = MenuData.EMPTY;
    private boolean open;

    public InscriptionTableBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.INSCRIPTION_TABLE.get(), pos, state, Data.CODEC);
    }

    public MenuData getMenuData() {
        return menuData;
    }

    public void setMenuData(MenuData menuData) {
        this.menuData = menuData;
        setChanged();
    }

    public ItemStack setSpell(ItemStack stack) {
        stack.set(AMDataComponents.SPELL, getMenuData().toSpell());
        return stack;
    }

    @Override
    public void fromData(Data data) {
        stack = data.stack;
        menuData = data.menuData;
    }

    @Override
    public Data toData() {
        return new Data(stack, menuData);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? stack : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (slot != 0 || amount <= 0) return ItemStack.EMPTY;
        ItemStack result = stack;
        stack = ItemStack.EMPTY;
        setChanged();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return removeItem(slot, 1);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot != 0) return;
        this.stack = stack;
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        BlockPos pos = getBlockPos();
        return player.level().getBlockEntity(pos) == this && player.distanceToSqr(Vec3.atCenterOf(pos)) <= 64D;
    }

    @Override
    public void clearContent() {
        stack = ItemStack.EMPTY;
        setChanged();
    }

    @Override
    public Component getDisplayName() {
        return AMTranslations.INSCRIPTION_TABLE;
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return open ? null : new InscriptionTableMenu(containerId, playerInventory, this);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void startOpen(ContainerUser containerUser) {
        open = true;
    }

    @Override
    public void stopOpen(ContainerUser containerUser) {
        open = false;
    }

    public record MenuData(Optional<Component> name, List<Holder<Skill>> grammar, List<List<Holder<Skill>>> shapeGroups) {
        public static final Codec<MenuData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(MenuData::name),
            Skill.CODEC.listOf(0, SpellGrammar.MAX_PARTS).fieldOf("grammar").forGetter(MenuData::grammar),
            Skill.CODEC.listOf(0, SpellShapeGroup.MAX_PARTS).listOf(0, Spell.MAX_SHAPE_GROUPS).fieldOf("shape_groups").forGetter(MenuData::shapeGroups)
        ).apply(inst, MenuData::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, MenuData> STREAM_CODEC = StreamCodec.composite(
            ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs::optional), MenuData::name,
            ByteBufCodecs.holderRegistry(AMRegistries.Keys.SKILL).apply(ByteBufCodecs.list()), MenuData::grammar,
            ByteBufCodecs.holderRegistry(AMRegistries.Keys.SKILL).apply(ByteBufCodecs.list()).apply(ByteBufCodecs.list()), MenuData::shapeGroups,
            MenuData::new);
        public static final MenuData EMPTY = new MenuData(Optional.empty(), List.of(), List.of());

        public static MenuData fromSpell(Spell spell, RegistryAccess registryAccess) {
            List<List<Holder<Skill>>> groups = spell.shapeGroups()
                .stream()
                .map(e -> skills(e.parts(), registryAccess))
                .toList();
            return new MenuData(spell.name(), skills(spell.grammar().parts(), registryAccess), groups);
        }

        public Spell toSpell() {
            List<SpellShapeGroup> groups = shapeGroups.stream()
                .map(MenuData::spellParts)
                .map(SpellShapeGroup::of)
                .toList();
            return new Spell(name, Optional.empty(), groups, 0, SpellGrammar.of(spellParts(grammar)), SpellDataComponentMap.EMPTY);
        }

        private static List<SpellPart> spellParts(List<Holder<Skill>> skills) {
            return skills.stream()
                .map(Holder::getKey)
                .filter(Objects::nonNull)
                .map(ResourceKey::identifier)
                .map(AMRegistries.SPELL_PARTS::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Holder::value)
                .toList();
        }

        private static List<Holder<Skill>> skills(List<SpellPart> parts, RegistryAccess registryAccess) {
            return parts.stream()
                .map(AMRegistries.SPELL_PARTS::getKey)
                .filter(Objects::nonNull)
                .map(AMRegistries.skills(registryAccess)::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(e -> (Holder<Skill>) e)
                .toList();
        }
    }

    public record Data(ItemStack stack, MenuData menuData) {
        private static final Codec<Data> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ItemStack.CODEC.optionalFieldOf("stack", ItemStack.EMPTY).forGetter(Data::stack),
            MenuData.CODEC.fieldOf("menu_data").forGetter(Data::menuData)
        ).apply(inst, Data::new));
    }
}
