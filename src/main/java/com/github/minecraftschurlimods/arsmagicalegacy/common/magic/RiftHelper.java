package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IRiftHelper;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ATTACHMENT_TYPES;

public final class RiftHelper implements IRiftHelper {
    private static final Lazy<RiftHelper> INSTANCE = Lazy.concurrentOf(RiftHelper::new);
    private static final Supplier<AttachmentType<RiftHolder>> RIFT_CAPABILITY = ATTACHMENT_TYPES.register("rift", () -> AttachmentType.serializable(RiftHolder::new).copyOnDeath().build());

    private RiftHelper() {}

    /**
     * @return The only instance of this class.
     */
    public static RiftHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public ItemStackHandler getRift(Player player) {
        return player.getData(RIFT_CAPABILITY);
    }

    public static final class RiftHolder extends ItemStackHandler {

        public RiftHolder() {
            super(54);
        }
    }
}
