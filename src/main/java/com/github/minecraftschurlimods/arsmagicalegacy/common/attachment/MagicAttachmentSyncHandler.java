package com.github.minecraftschurlimods.arsmagicalegacy.common.attachment;

import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.MagicAttachment;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.jei.HiddenSkills;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jspecify.annotations.Nullable;

public class MagicAttachmentSyncHandler implements AttachmentSyncHandler<MagicAttachment> {
    @Override
    public void write(RegistryFriendlyByteBuf buf, MagicAttachment attachment, boolean initialSync) {
        MagicAttachment.STREAM_CODEC.encode(buf, attachment);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public MagicAttachment read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable MagicAttachment previousValue) {
        MagicAttachment value = MagicAttachment.STREAM_CODEC.decode(buf);
        if (FMLEnvironment.getDist().isClient() && ModList.get().isLoaded("jei") && (previousValue == null || !value.affinityShifts().equals(previousValue.affinityShifts()))) {
            AMClientUtil.mc().submit(HiddenSkills::update);
        }
        return value;
    }
}
