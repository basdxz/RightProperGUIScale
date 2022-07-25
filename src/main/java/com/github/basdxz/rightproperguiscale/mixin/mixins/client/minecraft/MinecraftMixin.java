package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public int displayWidth;
    @Shadow
    public int displayHeight;

    @Redirect(method = "loadScreen()V",
              at = @At(value = "INVOKE",
                       target = "net/minecraft/client/shader/Framebuffer.bindFramebuffer (Z)V",
                       ordinal = 0),
              require = 1)
    private void fixNewFrameBuffer(Framebuffer instance, boolean setViewPort) {
        instance.createBindFramebuffer(displayWidth, displayHeight);
        instance.bindFramebuffer(setViewPort);
    }

    @Redirect(method = "loadScreen()V",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/client/shader/Framebuffer;framebufferRender(II)V",
                       ordinal = 0),
              require = 1)
    private void fixFrameBufferRender(Framebuffer instance, int f1, int f2) {
        instance.framebufferRender(displayWidth, displayHeight);
    }
}
