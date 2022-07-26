package com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft;

import com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft.ScaledResolutionMixin;
import net.minecraft.client.gui.ScaledResolution;

/**
 * An interface applied to {@link ScaledResolution} by {@link ScaledResolutionMixin} to provide more expanded access.
 */
public interface IScaledResolutionMixin {
    /**
     * Provides the scaled down width as an int.
     * <p>
     * Equivalent to using: {@link ScaledResolution#getScaledWidth()}.
     *
     * @return scaled width as an int
     */
    int scaledWidth();

    /**
     * Provides the scaled down height as an int.
     * <p>
     * Equivalent to using: {@link ScaledResolution#getScaledHeight()}.
     *
     * @return scaled height as an int
     */
    int scaledHeight();

    /**
     * Provides the scaled down width as a double.
     * <p>
     * Equivalent to using: {@link ScaledResolution#getScaledWidth_double()}.
     *
     * @return scaled width as a double
     */
    double scaledWidthD();

    /**
     * Provides the scaled down height as a double.
     * <p>
     * Equivalent to using: {@link ScaledResolution#getScaledHeight_double()}.
     *
     * @return scaled height as a double
     */
    double scaledHeightD();

    /**
     * Provides the scale factor as an int.
     * <p>
     * Equivalent to using: {@link ScaledResolution#getScaleFactor()}.
     *
     * @return scale factor as an int
     */
    int scaleFactor();

    /**
     * Provides the scale factor.
     *
     * @return scale factor as float
     */
    float scaleFactorF();
}
