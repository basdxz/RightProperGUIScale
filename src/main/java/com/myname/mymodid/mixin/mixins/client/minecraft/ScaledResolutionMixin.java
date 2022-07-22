package com.myname.mymodid.mixin.mixins.client.minecraft;

import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ScaledResolution.class)
public abstract class ScaledResolutionMixin {
    @Shadow
    private double scaledWidthD;
    @Shadow
    private double scaledHeightD;

    @Redirect(method = "<init>(Lnet/minecraft/client/Minecraft;II)V",
              at = @At(value = "FIELD",
                       target = "Lnet/minecraft/client/gui/ScaledResolution;scaledWidthD:D",
                       opcode = Opcodes.PUTFIELD),
              require = 1)
    private void rescaleWidth(ScaledResolution instance, double value) {
        scaledWidthD = value / 1F;
    }

    @Redirect(method = "<init>(Lnet/minecraft/client/Minecraft;II)V",
              at = @At(value = "FIELD",
                       target = "Lnet/minecraft/client/gui/ScaledResolution;scaledHeightD:D",
                       opcode = Opcodes.PUTFIELD),
              require = 1)
    private void rescaleHeight(ScaledResolution instance, double value) {
        scaledHeightD = value / 1F;
    }
}
