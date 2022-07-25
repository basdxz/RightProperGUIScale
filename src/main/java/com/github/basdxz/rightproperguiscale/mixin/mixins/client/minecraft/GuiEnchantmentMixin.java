package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import lombok.*;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import static com.github.basdxz.rightproperguiscale.GUIJiggler.newIScaledResolutionMixin;

@Mixin(GuiEnchantment.class)
public abstract class GuiEnchantmentMixin {
    private static final int BOOK_RENDER_WIDTH = 320;
    private static final int BOOK_RENDER_HEIGHT = 240;

    @Redirect(method = "drawGuiContainerBackgroundLayer(FII)V",
              at = @At(value = "INVOKE",
                       target = "org/lwjgl/opengl/GL11.glViewport (IIII)V",
                       ordinal = 0),
              require = 1)
    private void fixBookRender(int x, int y, int width, int height) {
        setBookViewport();
    }

    private void setBookViewport() {
        val scaledResolution = newIScaledResolutionMixin();
        GL11.glViewport(bookViewportXPos(scaledResolution),
                        bookViewportYPos(scaledResolution),
                        bookViewportWidth(scaledResolution),
                        bookViewportHeight(scaledResolution));
    }

    private int bookViewportXPos(@NonNull IScaledResolutionMixin scaledResolution) {
        return MathHelper.ceiling_double_int((scaledResolution.getScaledWidth_double() - BOOK_RENDER_WIDTH) / 2F * scaledResolution.getScaleFactorFloat());
    }

    private int bookViewportYPos(@NonNull IScaledResolutionMixin scaledResolution) {
        return MathHelper.ceiling_double_int((scaledResolution.getScaledHeight_double() - BOOK_RENDER_HEIGHT) / 2F * scaledResolution.getScaleFactorFloat());
    }

    private int bookViewportWidth(@NonNull IScaledResolutionMixin scaledResolution) {
        return MathHelper.ceiling_float_int(BOOK_RENDER_WIDTH * scaledResolution.getScaleFactorFloat());
    }

    private int bookViewportHeight(@NonNull IScaledResolutionMixin scaledResolution) {
        return MathHelper.ceiling_float_int(BOOK_RENDER_HEIGHT * scaledResolution.getScaleFactorFloat());
    }
}
