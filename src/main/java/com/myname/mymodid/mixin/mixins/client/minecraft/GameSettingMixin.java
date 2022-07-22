package com.myname.mymodid.mixin.mixins.client.minecraft;

import com.myname.mymodid.GUIJiggler;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.myname.mymodid.GUIJiggler.guiScaleSliderLabel;

@Mixin(GameSettings.class)
public abstract class GameSettingMixin {
    @Inject(method = "getKeyBinding(Lnet/minecraft/client/settings/GameSettings$Options;)Ljava/lang/String;",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void replaceGuiScaleLabel(GameSettings.Options option, CallbackInfoReturnable<String> cir) {
        if (isScaleOption(option)) {
            cir.setReturnValue(guiScaleSliderLabel());
            cir.cancel();
        }
    }

    @Inject(method = "setOptionFloatValue(Lnet/minecraft/client/settings/GameSettings$Options;F)V",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void replaceSetGuiScaleValue(GameSettings.Options option, float value, CallbackInfo ci) {
        if (isScaleOption(option)) {
            GUIJiggler.setGuiScale(value);
            ci.cancel();
        }
    }

    @Inject(method = "getOptionFloatValue(Lnet/minecraft/client/settings/GameSettings$Options;)F",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void replaceGetGuiScaleValue(GameSettings.Options option, CallbackInfoReturnable<Float> cir) {
        if (isScaleOption(option)) {
            cir.setReturnValue(GUIJiggler.getGuiScale());
            cir.cancel();
        }
    }

    private boolean isScaleOption(GameSettings.Options option) {
        return GameSettings.Options.GUI_SCALE == option;
    }
}
