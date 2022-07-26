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

import static com.github.basdxz.rightproperguiscale.util.Util.toIScaledResolutionMixin;

@Unique
@Mixin(LoadingScreenRenderer.class)
public abstract class LoadingScreenRendererMixin {
    @Shadow
    private ScaledResolution field_146587_f;
    @Shadow
    private Minecraft mc;

    @Redirect(method = "func_73722_d(Ljava/lang/String;)V",
              at = @At(value = "INVOKE",
                       target = "Lorg/lwjgl/opengl/GL11;glOrtho(DDDDDD)V",
                       ordinal = 0),
              require = 1)
    private void fixCenteredText(double left, double right, double bottom, double top, double zNear, double zFar) {
        setOrtho(toIScaledResolutionMixin(field_146587_f), left, top, zNear, zFar);
    }

    private void setOrtho(@NonNull IScaledResolutionMixin scaledResolution,
                          double left,
                          double top,
                          double zNear,
                          double zFar) {
        GL11.glOrtho(left,
                     rightOrtho(scaledResolution),
                     bottomOrtho(scaledResolution),
                     top,
                     zNear,
                     zFar);
    }

    private float rightOrtho(@NonNull IScaledResolutionMixin scaledResolution) {
        return scaledResolution.scaledWidth() * scaledResolution.scaleFactorF();
    }

    private float bottomOrtho(@NonNull IScaledResolutionMixin scaledResolution) {
        return scaledResolution.scaledHeight() * scaledResolution.scaleFactorF();
    }

    @Redirect(method = "setLoadingProgress(I)V",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/client/shader/Framebuffer;framebufferRender(II)V",
                       ordinal = 0),
              require = 1)
    private void fixFrameBufferRender(Framebuffer instance, int f1, int f2) {
        instance.framebufferRender(this.mc.displayWidth, this.mc.displayHeight);
    }
}
