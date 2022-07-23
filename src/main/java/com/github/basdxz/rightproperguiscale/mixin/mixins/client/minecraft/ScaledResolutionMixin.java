package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.github.basdxz.rightproperguiscale.GUIJiggler.*;

@Mixin(ScaledResolution.class)
public abstract class ScaledResolutionMixin {
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;
    @Shadow
    private double scaledWidthD;
    @Shadow
    private double scaledHeightD;
    @Shadow
    private int scaleFactor;
    private float scaleFactorFloat;

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;II)V",
            at = @At(value = "RETURN"),
            require = 1)
    private void deferredConstructor(Minecraft minecraft, int width, int height, CallbackInfo ci) {
        initScaleFactorFloat(width, height);
        initScaledWidth(width);
        initScaledHeight(height);
        initScaleFactor();
    }

    private void initScaleFactorFloat(int width, int height) {
        scaleFactorFloat = maxScaleFactor(width, height);
    }

    private float maxScaleFactor(int width, int height) {
        return Math.min(guiScaleAsFloat(), maxScaleFactorFromResolution(width, height));
    }

    private float maxScaleFactorFromResolution(int width, int height) {
        return Math.min(maxScaleFactorFromWidth(width), maxScaleFactorFromHeight(height));
    }

    private float maxScaleFactorFromWidth(int width) {
        return Math.max((float) width / MIN_SCALED_WIDTH, 1F);
    }

    private float maxScaleFactorFromHeight(int height) {
        return Math.max((float) height / MIN_SCALED_HEIGHT, 1F);
    }

    private void initScaledWidth(int width) {
        scaledWidthD = width / scaleFactorFloat;
        scaledWidth = MathHelper.ceiling_double_int(scaledWidthD);
    }

    private void initScaledHeight(int height) {
        scaledHeightD = height / scaleFactorFloat;
        scaledHeight = MathHelper.ceiling_double_int(scaledHeightD);
    }

    private void initScaleFactor() {
        scaleFactor = Math.round(scaleFactorFloat);
    }
}
