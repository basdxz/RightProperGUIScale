package com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft;

public interface IScaledResolutionMixin {
    int scaledWidth();

    int scaledHeight();

    double scaledWidthDouble();

    double getScaledHeightDouble();

    int scaleFactor();

    float scaleFactorFloat();
}
