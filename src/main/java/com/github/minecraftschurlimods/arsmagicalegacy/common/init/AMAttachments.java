package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component.Summon;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@NonExtendable
public interface AMAttachments {
    Supplier<AttachmentType<Summon.Minions>> SUMMON_MINIONS = AMRegistries.ATTACHMENT_TYPES.register("summon_minions", () -> AttachmentType.builder(() -> new Summon.Minions(List.of())).serialize(Summon.Minions.CODEC.codec()).copyOnDeath().build());
    Supplier<AttachmentType<Summon.Owner>>   SUMMON_OWNER   = AMRegistries.ATTACHMENT_TYPES.register("summon_owner",   () -> AttachmentType.builder(() -> new Summon.Owner(Optional.empty())).serialize(Summon.Owner.CODEC.codec()).copyOnDeath().build());

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
