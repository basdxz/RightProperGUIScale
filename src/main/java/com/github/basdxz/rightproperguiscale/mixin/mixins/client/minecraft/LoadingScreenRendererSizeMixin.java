package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import lombok.*;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import static com.github.basdxz.rightproperguiscale.util.Util.framebufferRender;
import static com.github.basdxz.rightproperguiscale.util.Util.toIScaledResolutionMixin;

/**
 * Mixin for {@link LoadingScreenRenderer} to correctly set its orthographic projection matrix in OpenGL to fix the
 * centering of the text. Also sets the proper display width and height for it's {@link Framebuffer},
 * preventing wierd bars on the right/top, from the screen not being fully filled.
 */
@Unique
@Mixin(LoadingScreenRenderer.class)
public abstract class LoadingScreenRendererSizeMixin {
    @Shadow
    private ScaledResolution field_146587_f;
    @Shadow
    private Minecraft mc;

    /**
     * Injects where the orthographic matrix is set, redirecting it to be set with the proper float scale.
     *
     * @param left   distance to the left horizontal clipping planes
     * @param right  distance to the right horizontal clipping planes
     * @param bottom distance to the bottom vertical clipping planes
     * @param top    distance to the top vertical clipping planes
     * @param zNear  distance to the near clipping plane
     * @param zFar   distance to the far clipping plane
     */
    @Redirect(method = "func_73722_d(Ljava/lang/String;)V",
              at = @At(value = "INVOKE",
                       target = "Lorg/lwjgl/opengl/GL11;glOrtho(DDDDDD)V",
                       ordinal = 0),
              require = 1)
    private void fixCenteredText(double left, double right, double bottom, double top, double zNear, double zFar) {
        setOrtho(toIScaledResolutionMixin(field_146587_f), left, top, zNear, zFar);
    }

    /**
     * Sets the current OpenGL orthographic matrix with respect to
     * the provided {@link IScaledResolutionMixin Scaled Resolution}.
     *
     * @param scaledResolution scaled resolution
     * @param left             distance to the left horizontal clipping planes
     * @param top              distance to the top vertical clipping planes
     * @param zNear            distance to the near clipping plane
     * @param zFar             distance to the far clipping plane
     */
    private void setOrtho(@NonNull IScaledResolutionMixin scaledResolution,
                          double left,
                          double top,
                          double zNear,
                          double zFar) {
        GL11.glOrtho(left, rightOrtho(scaledResolution), bottomOrtho(scaledResolution), top, zNear, zFar);
    }

    /**
     * Provides the right bound of the orthographic projection,
     * to be used as the second argument of: {@link GL11#glOrtho(double, double, double, double, double, double)}
     *
     * @param scaledResolution scaled resolution
     * @return distance to the right horizontal clipping planes
     */
    private float rightOrtho(@NonNull IScaledResolutionMixin scaledResolution) {
        return scaledResolution.scaledWidth() * scaledResolution.scaleFactor();
    }

    /**
     * Provides the bottom bound of the orthographic projection,
     * to be used as the third argument of: {@link GL11#glOrtho(double, double, double, double, double, double)}
     *
     * @param scaledResolution scaled resolution
     * @return distance to the bottom vertical clipping planes
     */
    private float bottomOrtho(@NonNull IScaledResolutionMixin scaledResolution) {
        return scaledResolution.scaledHeight() * scaledResolution.scaleFactor();
    }

    /**
     * Injects before the {@link Framebuffer} has been rendered out to screen and renders the right size.
     *
     * @param instance framebuffer
     * @param width    screen width
     * @param height   screen height
     */
    @Redirect(method = "setLoadingProgress(I)V",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/client/shader/Framebuffer;framebufferRender(II)V",
                       ordinal = 0),
              require = 1)
    private void fixFrameBufferRender(Framebuffer instance, int width, int height) {
        framebufferRender(instance);
    }
}
