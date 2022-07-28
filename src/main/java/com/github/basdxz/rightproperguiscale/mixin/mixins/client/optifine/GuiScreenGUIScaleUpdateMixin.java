package com.github.basdxz.rightproperguiscale.mixin.mixins.client.optifine;

import com.github.basdxz.rightproperguiscale.GUIScale;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.github.basdxz.rightproperguiscale.util.Util.mouseReleased;

/**
 * OptiFine specific Mixin to handle resolution scale updates.
 */
@Unique
@Mixin(GuiScreen.class)
public abstract class GuiScreenGUIScaleUpdateMixin {
    /**
     * Injects into the return of {@link GuiScreen#mouseMovedOrUp(int, int, int)} and updates {@link GUIScale}
     * if the current screen is {@link GuiVideoSettings} and the mouse has just been released.
     *
     * @param mousePosX   mouse X position
     * @param mousePosY   mouse Y position
     * @param mouseButton mouse button state
     * @param ci          mixin callback info
     */
    @Inject(method = "mouseMovedOrUp(III)V",
            at = @At(value = "RETURN"),
            require = 1)
    private void updateResolutionScale(int mousePosX, int mousePosY, int mouseButton, CallbackInfo ci) {
        if (isGUIVideoSettings() && mouseReleased(mouseButton))
            GUIScale.update();
    }

    /**
     * Checks if this GUI object is Video Settings
     *
     * @return is GUI Video Settings
     */
    @SuppressWarnings("ConstantConditions")
    private boolean isGUIVideoSettings() {
        return ((Object) this) instanceof GuiVideoSettings;
    }
}
