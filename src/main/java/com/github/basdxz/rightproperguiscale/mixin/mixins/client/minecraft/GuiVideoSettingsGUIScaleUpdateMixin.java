package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIScale;
import lombok.*;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.github.basdxz.rightproperguiscale.util.Util.mouseReleased;

/**
 * A Mixin into {@link GuiVideoSettings} that redirects updates of GUI Scale and prevents updating
 * {@link ScaledResolution} too early, which would otherwise cause the user to lose focus on the slider.
 */
@Unique
@Mixin(GuiVideoSettings.class)
public abstract class GuiVideoSettingsGUIScaleUpdateMixin {
    /**
     * Injects at the end of where the mouse click is handled and
     * returns before {@link ScaledResolution} would be updated.
     *
     * @param mouseXPos   mouse X position
     * @param mouseYPos   mouse Y position
     * @param mouseButton mouse button state
     * @param ci          mixin callback info
     */
    @Inject(method = "mouseClicked(III)V",
            at = @At(value = "FIELD",
                     target = "net/minecraft/client/gui/GuiVideoSettings.guiGameSettings:" +
                              "Lnet/minecraft/client/settings/GameSettings;",
                     opcode = Opcodes.GETFIELD,
                     ordinal = 1,
                     by = -2),
            cancellable = true,
            require = 1)
    private void cancelScaledResolutionUpdate(int mouseXPos, int mouseYPos, int mouseButton, CallbackInfo ci) {
        earlyReturn(ci);
    }

    /**
     * Injects at the end of where the mouse move or release is handled,
     * updating {@link GUIScale} if the mouse was released.
     *
     * @param mouseXPos   mouse X position
     * @param mouseYPos   mouse Y position
     * @param mouseButton mouse button state
     * @param ci          mixin callback info
     */
    @Inject(method = "mouseMovedOrUp(III)V",
            at = @At(value = "FIELD",
                     target = "net/minecraft/client/gui/GuiVideoSettings.guiGameSettings:" +
                              "Lnet/minecraft/client/settings/GameSettings;",
                     opcode = Opcodes.GETFIELD,
                     ordinal = 1,
                     by = -2),
            cancellable = true,
            require = 1)
    private void redirectScaledResolutionUpdate(int mouseXPos, int mouseYPos, int mouseButton, CallbackInfo ci) {
        if (mouseReleased(mouseButton))
            GUIScale.update();
        earlyReturn(ci);
    }

    /**
     * Returns from the currently injected method.
     *
     * @param ci mixin callback info
     */
    private void earlyReturn(@NonNull CallbackInfo ci) {
        ci.cancel();
    }
}
