package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIJiggler;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(GuiNewChat.class)
public abstract class GuiNewChatMixin {
    private static final int X_OFFSET = -3;
    private static final int Y_OFFSET = -27;

    private int lastMousePosX;
    private int lastMousePosY;
    private float lastScaleFactorFloat;

    @Inject(method = "func_146236_a(II)Lnet/minecraft/util/IChatComponent;",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/ScaledResolution;getScaleFactor()I"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private void storeVariablesForScaleFix(int mousePosX,
                                           int mousePosY,
                                           CallbackInfoReturnable<IChatComponent> cir,
                                           ScaledResolution scaledResolution) {
        lastMousePosX = mousePosX;
        lastMousePosY = mousePosY;
        lastScaleFactorFloat = GUIJiggler.toIScaledResolutionMixin(scaledResolution).getScaleFactorFloat();
    }

    // Minecraft chat scale
    @Shadow
    public abstract float func_146244_h();

    @Redirect(method = "func_146236_a(II)Lnet/minecraft/util/IChatComponent;",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/util/MathHelper;floor_float(F)I",
                       ordinal = 0),
              require = 1)
    private int fixChatComponentXPos(float ignored) {
        return MathHelper.floor_float(
                (lastMousePosX / lastScaleFactorFloat + X_OFFSET) / this.func_146244_h());
    }

    @Redirect(method = "func_146236_a(II)Lnet/minecraft/util/IChatComponent;",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/util/MathHelper;floor_float(F)I",
                       ordinal = 1),
              require = 1)
    private int fixChatComponentYPos(float ignored) {
        return MathHelper.floor_float(
                (lastMousePosY / lastScaleFactorFloat + Y_OFFSET) / this.func_146244_h());
    }
}
