package com.github.minecraftschurlimods.arsmagicalegacy.compat.theoneprobe;

import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.ICompatHandler;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

import java.util.function.Function;
import java.util.function.Supplier;

@CompatManager.ModCompat("theoneprobe")
public class TOPCompat implements ICompatHandler {
    @Override
    public void imcEnqueue(final InterModEnqueueEvent event) {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", (Supplier<Function<ITheOneProbe, Void>>) (() -> api -> {initTOP(api);return null;}));
    }

    private void initTOP(final ITheOneProbe api) {
        api.registerProvider(new EtheriumProbeInfoProvider());
    }
}
