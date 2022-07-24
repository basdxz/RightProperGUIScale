package com.github.basdxz.rightproperguiscale.mixin.mixins.client.optifine;

import com.github.basdxz.rightproperguiscale.GUIJiggler;
import com.github.basdxz.rightproperguiscale.stubpackage.net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.github.basdxz.rightproperguiscale.GUIJiggler.mouseReleased;

@Mixin(GuiScreen.class)
public abstract class GuiVideoSettingsMixin {
    @Inject(method = "mouseMovedOrUp(III)V",
            at = @At(value = "RETURN"),
            require = 1)
    private void updateResolutionScale(int mousePosX, int mousePosY, int mouseButton, CallbackInfo ci) {
        if (isGuiVideoSettings() && mouseReleased(mouseButton))
            GUIJiggler.updateGuiScale();
    }

    @SuppressWarnings("ConstantConditions")
    private boolean isGuiVideoSettings() {
        return ((Object) this) instanceof GuiVideoSettings;
    }
}
