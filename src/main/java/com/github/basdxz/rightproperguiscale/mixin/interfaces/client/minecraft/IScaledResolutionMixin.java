package com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIScale;
import com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig;
import com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft.ScaledResolutionPatchMixin;
import net.minecraft.client.gui.ScaledResolution;

/**
 * An interface applied to {@link ScaledResolution} by {@link ScaledResolutionPatchMixin} to expand its functionality.
 */
public interface IScaledResolutionMixin {
    /**
     * Bound to the {@link ScaledResolutionPatchMixin#scaleFactorF} field.
     * <p>
     * The scale factor is constrained by the set {@link GUIScale#value()} as the maximum scale,
     * additionally constrained to the configured the minimum width and height.
     * {@link RightProperGUIScaleConfig#MIN_SCALED_WIDTH} and {@link RightProperGUIScaleConfig#MIN_SCALED_HEIGHT}.
     *
     * @return the scale factor
     */
    float scaleFactor();

    /**
     * Bound to the {@link ScaledResolution#scaledWidth} field.
     * <p>
     * The scaled width is equal to the screen width divided by the scale factor, rounded up to the nearest integer.
     *
     * @return the scaled width
     */
    int scaledWidth();

    /**
     * Bound to the {@link ScaledResolution#scaledHeight} field.
     * <p>
     * The scaled height is equal to the screen height divided by the scale factor, rounded up to the nearest integer.
     *
     * @return the scaled height
     */
    int scaledHeight();
}
