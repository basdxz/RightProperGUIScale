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
 * Mixin for {@link ScaledResolution} to replace how it's calculated. This implementation modifies it to determine it
 * arithmetically rather than iteratively like in the standard implementation. Providing the benefit of fractional
 * scales but with the downside of causing non-exact integer scaling.
 */
@Getter
@Mixin(ScaledResolution.class)
@Accessors(fluent = true, chain = true)
public abstract class ScaledResolutionPatchMixin implements IScaledResolutionMixin {
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
    /**
     * The new scale factor float, more precise than the old {@link ScaledResolution#scaleFactor}.
     */
    @Unique
    private float scaleFactorF;

    /**
     * Injects at the return of {@link ScaledResolution#ScaledResolution}, differing the actual logic to this point.
     *
     * @param minecraft minecraft
     * @param width     screen width
     * @param height    screen height
     * @param ci        mixin callback info
     */
    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;II)V",
            at = @At(value = "RETURN"),
            require = 1)
    private void deferredConstructor(Minecraft minecraft, int width, int height, CallbackInfo ci) {
        initScaleFactorF(width, height);
        initScaleFactor();
        initScaledWidthD(width);
        initScaledWidth();
        initScaledHeightD(height);
        initScaledHeight();
        updateLastScaledResolution();
    }

    /**
     * Initializes {@link ScaledResolutionPatchMixin#scaleFactorF}.
     *
     * @param width  screen width
     * @param height screen height
     */
    @Unique
    private void initScaleFactorF(int width, int height) {
        scaleFactorF = maxScaleFactor(width, height);
    }

    /**
     * Provides the maximum scale factor.
     *
     * @param width  screen width
     * @param height screen height
     * @return maximum scale factor
     */
    @Unique
    private float maxScaleFactor(int width, int height) {
        return Math.min(GUIScale.value(), maxScaleFactorFromResolution(width, height));
    }

    /**
     * Provides the maximum scale factor with respect to resolution.
     *
     * @param width  screen width
     * @param height screen height
     * @return maximum scale factor
     */
    @Unique
    private float maxScaleFactorFromResolution(int width, int height) {
        return Math.min(maxScaleFactorFromWidth(width), maxScaleFactorFromHeight(height));
    }

    /**
     * Provides the maximum scale factor with respect to horizontal resolution.
     *
     * @param width screen width
     * @return maximum scale factor
     */
    @Unique
    private float maxScaleFactorFromWidth(int width) {
        return Math.max((float) width / MIN_SCALED_WIDTH, 1F);
    }

    /**
     * Provides the maximum scale factor with respect to vertical resolution.
     *
     * @param height screen height
     * @return maximum scale factor
     */
    @Unique
    private float maxScaleFactorFromHeight(int height) {
        return Math.max((float) height / MIN_SCALED_HEIGHT, 1F);
    }

    /**
     * Initializes {@link ScaledResolution#scaleFactor}.
     */
    @Unique
    private void initScaleFactor() {
        scaleFactor = Math.max(Math.round(scaleFactorF), 1);
    }

    /**
     * Initializes {@link ScaledResolution#scaledWidthD}.
     *
     * @param width screen width
     */
    @Unique
    private void initScaledWidthD(int width) {
        scaledWidthD = width / scaleFactorF;
    }

    /**
     * Initializes {@link ScaledResolution#scaledWidth}.
     */
    @Unique
    private void initScaledWidth() {
        scaledWidth = MathHelper.ceiling_double_int(scaledWidthD);
    }

    /**
     * Initializes {@link ScaledResolution#scaledHeightD}.
     */
    @Unique
    private void initScaledHeightD(int height) {
        scaledHeightD = height / scaleFactorF;
    }

    /**
     * Initializes {@link ScaledResolution#scaledHeight}.
     */
    @Unique
    private void initScaledHeight() {
        scaledHeight = MathHelper.ceiling_double_int(scaledHeightD);
    }

    /**
     * Updates last scaled resolution in {@link GUIScale}.
     */
    @Unique
    private void updateLastScaledResolution() {
        GUIScale.lastScaledResolution(this);
    }
}
