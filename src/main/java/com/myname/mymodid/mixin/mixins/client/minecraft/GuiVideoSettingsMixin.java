package com.myname.mymodid.mixin.mixins.client.minecraft;

import com.myname.mymodid.GUIJiggler;
import net.minecraft.client.gui.GuiVideoSettings;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(GuiVideoSettings.class)
public abstract class GuiVideoSettingsMixin {
    private static final int MOUSE_BUTTON_MOVED = -1;

    @Inject(method = "mouseClicked(III)V",
            at = @At(value = "FIELD",
                     target = "net/minecraft/client/gui/GuiVideoSettings.guiGameSettings:" +
                              "Lnet/minecraft/client/settings/GameSettings;",
                     opcode = Opcodes.GETFIELD,
                     ordinal = 1,
                     by = -2),
            cancellable = true,
            require = 1)
    private void cancelScaledResolutionUpdate(int mousePosX, int mousePosY, int mouseButton, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "mouseMovedOrUp(III)V",
            at = @At(value = "FIELD",
                     target = "net/minecraft/client/gui/GuiVideoSettings.guiGameSettings:" +
                              "Lnet/minecraft/client/settings/GameSettings;",
                     opcode = Opcodes.GETFIELD,
                     ordinal = 1,
                     by = -2),
            cancellable = true,
            require = 1)
    private void redirectScaledResolutionUpdate(int mousePosX, int mousePosY, int mouseButton, CallbackInfo ci) {
        if (mouseReleased(mouseButton))
            GUIJiggler.updateGuiScale();
        ci.cancel();
    }

    private static boolean mouseReleased(int mouseButton) {
        return MOUSE_BUTTON_MOVED != mouseButton;
    }
}
