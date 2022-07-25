package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.github.basdxz.rightproperguiscale.GUIJiggler.*;

@Getter
@Mixin(ScaledResolution.class)
@Accessors(fluent = true, chain = true)
public abstract class ScaledResolutionMixin implements IScaledResolutionMixin {
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

    @Unique
    private void initScaleFactorFloat(int width, int height) {
        scaleFactorFloat = maxScaleFactor(width, height);
    }

    @Unique
    private float maxScaleFactor(int width, int height) {
        return Math.min(guiScaleAsFloat(), maxScaleFactorFromResolution(width, height));
    }

    @Unique
    private float maxScaleFactorFromResolution(int width, int height) {
        return Math.min(maxScaleFactorFromWidth(width), maxScaleFactorFromHeight(height));
    }

    @Unique
    private float maxScaleFactorFromWidth(int width) {
        return Math.max((float) width / MIN_SCALED_WIDTH, 1F);
    }

    @Unique
    private float maxScaleFactorFromHeight(int height) {
        return Math.max((float) height / MIN_SCALED_HEIGHT, 1F);
    }

    @Unique
    private void initScaledWidth(int width) {
        scaledWidthD = width / scaleFactorFloat;
        scaledWidth = MathHelper.ceiling_double_int(scaledWidthD);
    }

    @Unique
    private void initScaledHeight(int height) {
        scaledHeightD = height / scaleFactorFloat;
        scaledHeight = MathHelper.ceiling_double_int(scaledHeightD);
    }

    @Unique
    private void initScaleFactor() {
        scaleFactor = Math.max(Math.round(scaleFactorFloat), 1);
    }
}
