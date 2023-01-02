package com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft;

import com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft.ScaledResolutionPatchMixin;
import net.minecraft.client.gui.ScaledResolution;

/**
 * An interface applied to {@link ScaledResolution} by {@link ScaledResolutionPatchMixin} to expand its functionality.
 */
public interface IScaledResolutionMixin {
    /**
     * Bound to the {@link ScaledResolutionPatchMixin#screenWidth} field.
     *
     * @return the actual screen width
     */
    int screenWidth();

    /**
     * Bound to the {@link ScaledResolutionPatchMixin#screenHeight} field.
     *
     * @return the actual screen height
     */
    int screenHeight();

    /**
     * Bound to the {@link ScaledResolution#scaleFactor} field.
     * <p>
     * Always clamped to >= 1, as the original implementation does.
     *
     * @return the <b>OLD</b> resolution scale factor
     */
    int scaleFactor();

    /**
     * Bound to the {@link ScaledResolutionPatchMixin#scaleFactorF} field.
     *
     * @return the <b>NEW</b> resolution scale factor
     */
    float scaleFactorF();

    /**
     * Bound to the {@link ScaledResolution#scaledWidth} field.
     *
     * @return the scaled width
     */
    int scaledWidth();

    /**
     * Bound to the {@link ScaledResolution#scaledWidthD} field.
     * <p>
     * Equal to {@link ScaledResolution#scaledWidth} cast to a double, only provided because of compatibility.
     *
     * @return the scaled width as a double
     */
    double scaledWidthD();

    /**
     * Bound to the {@link ScaledResolution#scaledHeight} field.
     *
     * @return the scaled height
     */
    int scaledHeight();

    /**
     * Bound to the {@link ScaledResolution#scaledHeightD} field.
     * <p>
     * Equal to {@link ScaledResolution#scaledWidth} cast to a double, only provided because of compatibility.
     *
     * @return the scaled height as a double
     */
    double scaledHeightD();
}
