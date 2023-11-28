package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterShadersEvent;

import java.io.IOException;

public final class AMShaders {
    private AMShaders() {}

    private static ShaderInstance COLOR_WHEEL_SHADER;

    public static ShaderInstance getColorWheelShader() {
        return COLOR_WHEEL_SHADER;
    }

    public static void setUniform(String name, float value) {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        Uniform uniform = shader.getUniform(name);
        if (uniform == null) return;
        uniform.set(value);
    }

    public static void setUniform(String name, float value, float value2) {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        Uniform uniform = shader.getUniform(name);
        if (uniform == null) return;
        uniform.set(value, value2);
    }

    public static void setUniform(String name, float value, float value2, float value3) {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        Uniform uniform = shader.getUniform(name);
        if (uniform == null) return;
        uniform.set(value, value2, value3);
    }

    public static void setUniform(String name, float value, float value2, float value3, float value4) {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        Uniform uniform = shader.getUniform(name);
        if (uniform == null) return;
        uniform.set(value, value2, value3, value4);
    }

    public static void setUniform(String name, int value) {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        Uniform uniform = shader.getUniform(name);
        if (uniform == null) return;
        uniform.set(value);
    }

    public static void setUniform(String name, int value, int value2) {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        Uniform uniform = shader.getUniform(name);
        if (uniform == null) return;
        uniform.set(value, value2);
    }

    public static void setUniform(String name, int value, int value2, int value3) {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        Uniform uniform = shader.getUniform(name);
        if (uniform == null) return;
        uniform.set(value, value2, value3);
    }

    public static void setUniform(String name, int value, int value2, int value3, int value4) {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        Uniform uniform = shader.getUniform(name);
        if (uniform == null) return;
        uniform.set(value, value2, value3, value4);
    }

    static void init(RegisterShadersEvent evt) {
        try {
            evt.registerShader(new ShaderInstance(evt.getResourceManager(), new ResourceLocation(ArsMagicaAPI.MOD_ID, "color_wheel"), DefaultVertexFormat.POSITION), shaderInstance -> AMShaders.COLOR_WHEEL_SHADER = shaderInstance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
