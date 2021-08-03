package com.github.minecraftschurli.arsmagicalegacy.compat.jei;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;


/**
 * @author Minecraftschurli
 * @version 2021-06-15
 */
@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class JEICompat implements IModPlugin {
    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ArsMagicaAPI.MOD_ID, ArsMagicaAPI.MOD_ID);
    }
}
