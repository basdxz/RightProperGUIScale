package com.github.basdxz.rightproperguiscale.mixin.mixins.client.optifine;

import com.github.basdxz.rightproperguiscale.GUIScale;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.github.basdxz.rightproperguiscale.util.Util.mouseReleased;

@Unique
@Mixin(GuiScreen.class)
public abstract class GuiScreenMixin {
    @Inject(method = "mouseMovedOrUp(III)V",
            at = @At(value = "RETURN"),
            require = 1)
    private void updateResolutionScale(int mousePosX, int mousePosY, int mouseButton, CallbackInfo ci) {
        if (isGuiVideoSettings() && mouseReleased(mouseButton))
            GUIScale.update();
    }

    @SuppressWarnings("ConstantConditions")
    private boolean isGuiVideoSettings() {
        return ((Object) this) instanceof GuiVideoSettings;
    }
}
