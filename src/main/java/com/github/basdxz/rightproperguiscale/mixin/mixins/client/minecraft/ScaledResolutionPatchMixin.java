package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIScale;
import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig.MIN_SCALED_HEIGHT;
import static com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig.MIN_SCALED_WIDTH;

/**
 * Mixin for {@link ScaledResolution} to defer its creation
 *
 * @see IScaledResolutionMixin#scaleFactorF()
 * @see IScaledResolutionMixin#scaledWidth()
 * @see IScaledResolutionMixin#scaledHeight()
 */
@Getter
@Mixin(ScaledResolution.class)
@Accessors(fluent = true, chain = true)
public abstract class ScaledResolutionPatchMixin implements IScaledResolutionMixin {
    @Unique
    private int screenWidth;
    @Unique
    private int screenHeight;
    @Shadow
    private int scaleFactor;
    @Unique
    private float scaleFactorF;
    @Shadow
    private int scaledWidth;
    @Shadow
    private double scaledWidthD;
    @Shadow
    private int scaledHeight;
    @Shadow
    private double scaledHeightD;

    /**
     * Injects at the return of {@link ScaledResolution#ScaledResolution}, differing the actual initialization to this point.
     *
     * @param minecraft    minecraft
     * @param screenWidth  screen width
     * @param screenHeight screen height
     * @param ci           mixin callback info
     */
    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;II)V",
            at = @At(value = "RETURN"),
            require = 1)
    private void deferredConstructor(Minecraft minecraft, int screenWidth, int screenHeight, CallbackInfo ci) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        updateScaleFactor();
        updateScaledWidth();
        updateScaledHeight();
        updateLastScaledResolution();
    }

    /**
     * Updates the fields {@link ScaledResolutionPatchMixin#scaleFactorF} and {@link ScaledResolution#scaleFactor}.
     *
     * @see IScaledResolutionMixin#scaleFactor
     * @see IScaledResolutionMixin#scaleFactorF
     */
    @Unique
    private void updateScaleFactor() {
        val minWidthScale = Math.max((float) screenWidth / (float) MIN_SCALED_WIDTH, 1F);
        val minHeightScale = Math.max((float) screenHeight / (float) MIN_SCALED_HEIGHT, 1F);
        val minDimensionScale = Math.min(minWidthScale, minHeightScale);

        scaleFactorF = Math.min(GUIScale.value(), minDimensionScale);
        scaleFactor = Math.max(Math.round(scaleFactorF), 1);
    }

    /**
     * Updates the fields {@link ScaledResolution#scaledWidth} and {@link ScaledResolution#scaledWidthD}.
     *
     * @see IScaledResolutionMixin#scaledWidth()
     * @see IScaledResolutionMixin#scaledWidthD()
     */
    @Unique
    private void updateScaledWidth() {
        scaledWidth = MathHelper.ceiling_double_int(screenWidth / scaleFactorF);
        scaledWidthD = scaledWidth;
    }

    /**
     * Updates the fields {@link ScaledResolution#scaledHeight} and {@link ScaledResolution#scaledHeightD}.
     *
     * @see IScaledResolutionMixin#scaledHeight()
     * @see IScaledResolutionMixin#scaledHeightD()
     */
    @Unique
    private void updateScaledHeight() {
        scaledHeight = MathHelper.ceiling_double_int(screenHeight / scaleFactorF);
        scaledHeightD = scaledHeight;
    }

    /**
     * Updates last scaled resolution in {@link GUIScale}.
     * <p>
     * Required to update the display the scale factor in the settings GUI.
     */
    @Unique
    private void updateLastScaledResolution() {
        GUIScale.lastScaledResolution(this);
    }
}
