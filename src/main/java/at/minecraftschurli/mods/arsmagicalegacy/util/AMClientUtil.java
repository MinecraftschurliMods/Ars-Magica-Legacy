package at.minecraftschurli.mods.arsmagicalegacy.util;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.SpellCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellrecipe.SpellRecipeScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.ParticleUtil;
import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.entity.FallingStar;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ManaVortex;
import at.minecraftschurli.mods.arsmagicalegacy.entity.SpellEntity;
import at.minecraftschurli.mods.arsmagicalegacy.entity.SpellShapeEntity;
import at.minecraftschurli.mods.arsmagicalegacy.entity.Whirlwind;
import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.google.common.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public final class AMClientUtil {
    public static final DataTicket<AbstractBoss.Action> ACTION_DATA_TICKET = DataTickets.create("action", new TypeToken<>() {});
    public static final DataTicket<List<String>> HIDDEN_BONES_DATA_TICKET = DataTickets.create("hidden_bones", new TypeToken<>() {});

    private AMClientUtil() {}

    public static Minecraft mc() {
        return Minecraft.getInstance();
    }

    @Nullable
    public static LocalPlayer player() {
        return mc().player;
    }

    @Nullable
    public static ClientLevel level() {
        return mc().level;
    }

    public static Font font() {
        return mc().font;
    }

    public static int getRedI(int color) {
        return 0xFF & color >> 16;
    }

    public static int getGreenI(int color) {
        return 0xFF & color >> 8;
    }

    public static int getBlueI(int color) {
        return 0xFF & color;
    }

    public static float getRedF(int color) {
        return getRedI(color) / 255f;
    }

    public static float getGreenF(int color) {
        return getGreenI(color) / 255f;
    }

    public static float getBlueF(int color) {
        return getBlueI(color) / 255f;
    }

    public static float[] rgbToHsb(int red, int green, int blue) {
        int max = Math.max(Math.max(red, green), blue);
        int min = Math.min(Math.min(red, green), blue);
        if (max == 0) return new float[]{0, 0, 0};
        float hue, saturation, brightness;
        brightness = max / 255f;
        saturation = (max - min) / (float) max;
        if (saturation == 0) {
            hue = 0;
        } else {
            float r = (float) (max - red) / (float) (max - min);
            float g = (float) (max - green) / (float) (max - min);
            float b = (float) (max - blue) / (float) (max - min);
            hue = red == max ? b - g : green == max ? 2f + r - b : 4f + g - r;
            hue /= 6f;
            if (hue < 0) {
                hue += 1f;
            }
        }
        return new float[]{hue, saturation, brightness};
    }

    public static int[] hsbToRgb(float hue, float saturation, float brightness) {
        if (saturation == 0) {
            int gray = (int) (brightness * 255f + 0.5f);
            return new int[]{gray, gray, gray};
        }
        float h = (hue - (float) Math.floor(hue)) * 6f;
        float f = h - (float) Math.floor(h);
        float p = brightness * (1f - saturation);
        float q = brightness * (1f - saturation * f);
        float t = brightness * (1f - (saturation * (1f - f)));
        return switch ((int) h) {
            case 0 -> new int[]{(int) (brightness * 255 + 0.5f), (int) (t * 255 + 0.5f), (int) (p * 255 + 0.5f)};
            case 1 -> new int[]{(int) (q * 255 + 0.5f), (int) (brightness * 255 + 0.5f), (int) (p * 255 + 0.5f)};
            case 2 -> new int[]{(int) (p * 255 + 0.5f), (int) (brightness * 255 + 0.5f), (int) (t * 255 + 0.5f)};
            case 3 -> new int[]{(int) (p * 255 + 0.5f), (int) (q * 255 + 0.5f), (int) (brightness * 255 + 0.5f)};
            case 4 -> new int[]{(int) (t * 255 + 0.5f), (int) (p * 255 + 0.5f), (int) (brightness * 255 + 0.5f)};
            case 5 -> new int[]{(int) (brightness * 255 + 0.5f), (int) (p * 255 + 0.5f), (int) (q * 255 + 0.5f)};
            default -> new int[]{0, 0, 0};
        };
    }

    public static int averageColors(int... colors) {
        int red = Arrays.stream(colors).map(AMClientUtil::getRedI).sum();
        int green = Arrays.stream(colors).map(AMClientUtil::getGreenI).sum();
        int blue = Arrays.stream(colors).map(AMClientUtil::getBlueI).sum();
        return (red / colors.length) << 16 | (green / colors.length) << 8 | (blue / colors.length);
    }

    public static void setOcculusScreen() {
        mc().setScreen(new OcculusScreen());
    }

    public static void setSpellCustomizationScreen(Spell spell, InteractionHand hand) {
        mc().setScreen(new SpellCustomizationScreen(spell, hand));
    }

    public static void setSpellRecipeScreen(ItemStack stack, boolean playTurnSound, int startPage, @Nullable BlockPos lecternPos) {
        mc().setScreen(new SpellRecipeScreen(stack, playTurnSound, startPage, lecternPos));
    }

    public static void renderItem(GuiGraphicsExtractor graphics, ItemStack stack, int x, int y) {
        renderItem(graphics, font(), stack, x, y);
    }

    public static void renderItem(GuiGraphicsExtractor graphics, Font font, ItemStack stack, int x, int y) {
        graphics.item(stack, x, y);
        graphics.itemDecorations(font, stack, x, y);
    }

    public static void spawnParticles(Identifier id, Vec3 position, int color, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        ParticleUtil.spawnParticles(id, position, color, caster, directEntity, hitResult);
    }

    public static void spawnArcaneCompendiumConversionParticles(List<BlockPos> from, Vec3 to) {
        ParticleUtil.spawnArcaneCompendiumConversionParticles(from, to);
    }

    public static void spawnArcaneCompendiumConversionFinishParticles(Vec3 position) {
        ParticleUtil.spawnArcaneCompendiumConversionFinishParticles(position);
    }

    public static void spawnFallingStarParticles(FallingStar entity, boolean ground) {
        ParticleUtil.spawnFallingStarParticles(entity, ground);
    }

    public static void spawnManaVortexParticles(ManaVortex entity) {
        ParticleUtil.spawnManaVortexParticles(entity);
    }

    public static void spawnWhirlwindParticles(Whirlwind entity) {
        ParticleUtil.spawnWhirlwindParticles(entity);
    }

    public static void spawnSpellEntityParticles(SpellEntity entity, double range, double verticalRange, int color, @Nullable LivingEntity caster) {
        ParticleUtil.spawnSpellEntityParticles(entity, range, verticalRange, color, caster);
    }

    public static void spawnSpellEntityParticles(SpellShapeEntity entity, Spell spell, Vec3 position, int color, @Nullable LivingEntity caster) {
        ParticleUtil.spawnSpellEntityParticles(entity, spell, position, color, caster);
    }

    public static void blit(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, int width, int height) {
        blit(graphics, texture, x, y, 0, 0, width, height);
    }

    public static void blit(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, int width, int height, int color) {
        blit(graphics, texture, x, y, 0, 0, width, height, color);
    }

    public static void blit(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, float u, float v, int width, int height) {
        blit(graphics, texture, x, y, u, v, width, height, width, height, -1);
    }

    public static void blit(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, float u, float v, int width, int height, int color) {
        blit(graphics, texture, x, y, u, v, width, height, width, height, color);
    }

    public static void blit(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        blit(graphics, texture, x, y, u, v, width, height, textureWidth, textureHeight, -1);
    }

    public static void blit(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, u, v, width, height, textureWidth, textureHeight, color);
    }

    public static void blitFull(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, int width, int height) {
        blitFull(graphics, texture, x, y, 0, 0, width, height);
    }

    public static void blitFull(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, int width, int height, int color) {
        blitFull(graphics, texture, x, y, 0, 0, width, height, color);
    }

    public static void blitFull(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, float u, float v, int width, int height) {
        blit(graphics, texture, x, y, u, v, width, height, 256, 256, -1);
    }

    public static void blitFull(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, float u, float v, int width, int height, int color) {
        blit(graphics, texture, x, y, u, v, width, height, 256, 256, color);
    }

    public static void blitSprite(GuiGraphicsExtractor graphics, TextureAtlasSprite sprite, int x, int y, int width, int height) {
        blitSprite(graphics, sprite, x, y, width, height, -1);
    }

    public static void blitSprite(GuiGraphicsExtractor graphics, TextureAtlasSprite sprite, int x, int y, int width, int height, int color) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x, y, width, height, color);
    }

    public static void blitSprite(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, int width, int height) {
        blitSprite(graphics, texture, x, y, width, height, -1);
    }

    public static void blitSprite(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, int width, int height, int color) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, texture, x, y, width, height, color);
    }

    public static void addCube(PartDefinition pd, String name, int texU, int texV, float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ, float offsetX, float offsetY, float offsetZ) {
        pd.addOrReplaceChild(name, CubeListBuilder.create().texOffs(texU, texV).addBox(originX, originY, originZ, sizeX, sizeY, sizeZ, CubeDeformation.NONE), PartPose.offset(offsetX, offsetY, offsetZ));
    }

    public static void addCube(PartDefinition pd, String name, int texU, int texV, float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ, float offsetX, float offsetY, float offsetZ, float rotationX, float rotationY, float rotationZ) {
        pd.addOrReplaceChild(name, CubeListBuilder.create().texOffs(texU, texV).addBox(originX, originY, originZ, sizeX, sizeY, sizeZ, CubeDeformation.NONE), PartPose.offsetAndRotation(offsetX, offsetY, offsetZ, AMUtil.wrapToRadians(rotationX), AMUtil.wrapToRadians(rotationY), AMUtil.wrapToRadians(rotationZ)));
    }
}
