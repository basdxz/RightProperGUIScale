package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.github.basdxz.rightproperguiscale.util.Util.toIScaledResolutionMixin;

/**
 * Fixes {@link GuiNewChat} having incorrect mouse position calculation, making it annoying to click on links in chat.
 */
@Unique
@Mixin(GuiNewChat.class)
public abstract class GuiNewChatMixin {
    @Shadow
    @Final
    private Minecraft mc;
    /**
     * X position mouse offset constant from: {@link GuiNewChat#func_146236_a(int, int)}
     */
    private static final int X_OFFSET = -3;
    /**
     * X position mouse offset constant from: {@link GuiNewChat#func_146236_a(int, int)}
     */
    private static final int Y_OFFSET = -27;
    /**
     * Last captured mouse X position.
     */
    private int lastMouseXPos;
    /**
     * Last captured mouse Y position.
     */
    private int lastMouseYPos;
    /**
     * Scale factor from the last captured scaled resolution.
     */
    private float lastScaleFactor;

    /**
     * Injects right after the Resolution Scale has been creates, captures it and all the other variables in scope.
     *
     * @param mouseXPos        mouse X position
     * @param mouseYPos        mouse Y position
     * @param cir              mixin callback info returnable
     * @param scaledResolution scaled resolution
     */
    @Inject(method = "func_146236_a(II)Lnet/minecraft/util/IChatComponent;",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/ScaledResolution;getScaleFactor()I"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private void captureVariables(int mouseXPos,
                                  int mouseYPos,
                                  CallbackInfoReturnable<IChatComponent> cir,
                                  ScaledResolution scaledResolution) {
        storeVariables(mouseXPos, mouseYPos, toIScaledResolutionMixin(scaledResolution).scaleFactorF());
    }

    private void storeVariables(int mousePosX, int mousePosY, float lastScaleFactorFloat) {
        this.lastMouseXPos = mousePosX;
        this.lastMouseYPos = mousePosY;
        this.lastScaleFactor = lastScaleFactorFloat;
    }

    /**
     * Injects where the relative mouse X position is set and replaces it with a proper value.
     *
     * @param relativeMouseXPos relative mouse X position.
     * @return relative mouse X position
     */
    @Redirect(method = "func_146236_a(II)Lnet/minecraft/util/IChatComponent;",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/util/MathHelper;floor_float(F)I",
                       ordinal = 0),
              require = 1)
    private int fixMouseXPos(float relativeMouseXPos) {
        return relativeMouseXPos();
    }

    /**
     * Provides relative mouse X position.
     *
     * @return relative mouse X position
     */
    private int relativeMouseXPos() {
        return MathHelper.floor_float((lastMouseXPos / lastScaleFactor + X_OFFSET) / chatScale());
    }

    /**
     * Injects where the relative mouse Y position is set and replaces it with a proper value.
     *
     * @param relativeMouseYPos relative mouse Y position.
     * @return relative mouse Y position
     */
    @Redirect(method = "func_146236_a(II)Lnet/minecraft/util/IChatComponent;",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/util/MathHelper;floor_float(F)I",
                       ordinal = 1),
              require = 1)
    private int fixMouseYPos(float relativeMouseYPos) {
        return relativeMouseYPos();
    }

    /**
     * Provides relative mouse Y position.
     *
     * @return relative mouse Y position
     */
    private int relativeMouseYPos() {
        return MathHelper.floor_float((lastMouseYPos / lastScaleFactor + Y_OFFSET) / chatScale());
    }

    /**
     * Provides the Minecraft chat scale.
     *
     * @return Minecraft chat scale
     */
    private float chatScale() {
        return mc.gameSettings.chatScale;
    }
}
