package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import lombok.*;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import static com.github.basdxz.rightproperguiscale.util.Util.newIScaledResolutionMixin;

/**
 * Fixes {@link GuiEnchantment} to render the book in the right place, by making it reference
 * {@link IScaledResolutionMixin#scaleFactorF()} instead of {@link ScaledResolution#getScaleFactor()}.
 */
@Mixin(GuiEnchantment.class)
public abstract class GuiEnchantmentBookAlignmentMixin {
    /**
     * Copied from {@link GuiEnchantment#drawGuiContainerBackgroundLayer(float, int, int)}.
     */
    private static final int BOOK_RENDER_WIDTH = 320;
    /**
     * Copied from {@link GuiEnchantment#drawGuiContainerBackgroundLayer(float, int, int)}.
     */
    private static final int BOOK_RENDER_HEIGHT = 240;

    /**
     * Injects right before the viewport for the book is set and replaces it with a correctly scaled one.
     *
     * @param xPos   viewport X position
     * @param yPos   viewport Y position
     * @param width  viewport width
     * @param height viewport height
     */
    @Redirect(method = "drawGuiContainerBackgroundLayer(FII)V",
              at = @At(value = "INVOKE",
                       target = "org/lwjgl/opengl/GL11.glViewport (IIII)V",
                       ordinal = 0),
              require = 1)
    private void fixBookViewport(int xPos, int yPos, int width, int height) {
        setBookViewport();
    }

    /**
     * Sets the viewport for rendering the book
     */
    private void setBookViewport() {
        val scaledResolution = newIScaledResolutionMixin();
        GL11.glViewport(bookViewportXPos(scaledResolution),
                        bookViewportYPos(scaledResolution),
                        bookViewportWidth(scaledResolution),
                        bookViewportHeight(scaledResolution));
    }

    /**
     * Provides the viewport X position.
     *
     * @param scaledResolution scaled resolution
     * @return viewport X position
     */
    private int bookViewportXPos(@NonNull IScaledResolutionMixin scaledResolution) {
        return MathHelper.ceiling_double_int((scaledResolution.scaledWidthD() - BOOK_RENDER_WIDTH) /
                                             2F * scaledResolution.scaleFactorF());
    }

    /**
     * Provides the viewport Y position.
     *
     * @param scaledResolution scaled resolution
     * @return viewport Y position
     */
    private int bookViewportYPos(@NonNull IScaledResolutionMixin scaledResolution) {
        return MathHelper.ceiling_double_int((scaledResolution.scaledHeightD() - BOOK_RENDER_HEIGHT) /
                                             2F * scaledResolution.scaleFactorF());
    }

    /**
     * Provides the viewport width.
     *
     * @param scaledResolution scaled resolution
     * @return viewport width
     */
    private int bookViewportWidth(@NonNull IScaledResolutionMixin scaledResolution) {
        return MathHelper.ceiling_float_int(BOOK_RENDER_WIDTH * scaledResolution.scaleFactorF());
    }

    /**
     * Provides the viewport height.
     *
     * @param scaledResolution scaled resolution
     * @return viewport height
     */
    private int bookViewportHeight(@NonNull IScaledResolutionMixin scaledResolution) {
        return MathHelper.ceiling_float_int(BOOK_RENDER_HEIGHT * scaledResolution.scaleFactorF());
    }
}
