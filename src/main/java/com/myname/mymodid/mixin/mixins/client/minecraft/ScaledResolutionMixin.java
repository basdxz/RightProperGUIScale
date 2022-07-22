package com.myname.mymodid.mixin.mixins.client.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(ScaledResolution.class)
public abstract class ScaledResolutionMixin {
    private static final int MIN_SCALED_WIDTH = 320;
    private static final int MIN_SCALED_HEIGHT = 240;

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

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;II)V",
            at = @At(value = "RETURN"),
            require = 1)
    private void deferredConstructor(Minecraft minecraft, int width, int height, CallbackInfo ci) {
        scaleFactor = 1;
        int guiScale = minecraft.gameSettings.guiScale;

        if (guiScale == 0)
            guiScale = 1000;

        initScaleFactor(guiScale, width, height);
        initScaledWidth(width);
        initScaledHeight(height);
    }

    private void initScaleFactor(int guiScale, int width, int height) {
        while (scaleFactor < guiScale &&
               nextScaledWidth(width) >= MIN_SCALED_WIDTH &&
               nextScaledHeight(height) >= MIN_SCALED_HEIGHT)
            ++scaleFactor;
    }

    private int nextScaledWidth(int width) {
        return width / (scaleFactor + 1);
    }

    private int nextScaledHeight(int height) {
        return height / (scaleFactor + 1);
    }

    private void initScaledWidth(int width) {
        scaledWidthD = (double) width / (double) scaleFactor;
        scaledWidth = MathHelper.ceiling_double_int(scaledWidthD);
    }

    private void initScaledHeight(int height) {
        scaledHeightD = (double) height / (double) scaleFactor;
        scaledHeight = MathHelper.ceiling_double_int(scaledHeightD);
    }
}
