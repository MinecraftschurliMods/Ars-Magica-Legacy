package at.minecraftschurli.mods.arsmagicalegacy.attachment;

import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.compat.jei.HiddenSkills;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
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
