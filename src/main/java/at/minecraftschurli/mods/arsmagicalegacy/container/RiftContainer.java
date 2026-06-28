package at.minecraftschurli.mods.arsmagicalegacy.container;

import at.minecraftschurli.mods.arsmagicalegacy.attachment.RiftAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RiftContainer extends SimpleContainer {
    private final LivingEntity entity;
    private final int size;

    public RiftContainer(LivingEntity entity, int size) {
        super(size);
        this.entity = entity;
        this.size = size;
        List<ItemStack> contents = entity.getData(AMAttachments.RIFT).contents();
        for (int i = 0; i < size; i++) {
            getItems().set(i, i < contents.size() ? contents.get(i) : ItemStack.EMPTY);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        List<ItemStack> contents = new ArrayList<>(entity.getData(AMAttachments.RIFT).contents());
        for (int i = 0; i < size; i++) {
            while (i >= contents.size()) {
                contents.add(ItemStack.EMPTY);
            }
            contents.set(i, getItem(i));
        }
        entity.setData(AMAttachments.RIFT, new RiftAttachment(contents));
    }
}
