package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.mixin.plugin.TargetedMod;
import lombok.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

/**
 * Mixin to fix the early {@link Minecraft} loading screen to use proper display width and height for it's
 * {@link Framebuffer}. Preventing wierd bars on the right/top, from the screen not being fully filled.
 * <p>
 * This patch is incompatible with {@link TargetedMod#BETTER_LOADING_SCREEN} since this mod removes the targeted
 * code. However, this mod also doesn't experience the bug this patch intended to fix.
 */
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public int displayWidth;
    @Shadow
    public int displayHeight;

    /**
     * Injects right after the frame buffer has been creates and resets it to be the right size.
     *
     * @param instance    frame buffer
     * @param setViewPort view port state
     */
    @Redirect(method = "loadScreen()V",
              at = @At(value = "INVOKE",
                       target = "net/minecraft/client/shader/Framebuffer.bindFramebuffer (Z)V",
                       ordinal = 0),
              require = 1)
    private void fixNewFramebuffer(Framebuffer instance, boolean setViewPort) {
        resetFramebuffer(instance, setViewPort);
    }

    /**
     * Resets the frame buffer to the current screen size.
     *
     * @param framebuffer frame buffer
     * @param setViewPort view port state
     */
    private void resetFramebuffer(@NonNull Framebuffer framebuffer, boolean setViewPort) {
        framebuffer.createBindFramebuffer(displayWidth, displayHeight);
        framebuffer.bindFramebuffer(setViewPort);
    }

    /**
     * Injects before the {@link Framebuffer} has been rendered out to screen and renders the right size.
     *
     * @param instance frame buffer
     * @param width    screen width
     * @param height   screen height
     */
    @Redirect(method = "loadScreen()V",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/client/shader/Framebuffer;framebufferRender(II)V",
                       ordinal = 0),
              require = 1)
    private void fixFramebufferRender(Framebuffer instance, int width, int height) {
        framebufferRender(instance);
    }

    /**
     * Renders out a frame buffer to the screen.
     *
     * @param framebuffer frame buffer
     */
    private void framebufferRender(@NonNull Framebuffer framebuffer) {
        framebuffer.framebufferRender(displayWidth, displayHeight);
    }
}
