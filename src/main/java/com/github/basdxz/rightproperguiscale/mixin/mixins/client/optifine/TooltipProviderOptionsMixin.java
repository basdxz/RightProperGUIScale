package com.github.basdxz.rightproperguiscale.mixin.mixins.client.optifine;


import com.github.basdxz.rightproperguiscale.util.Util;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Pseudo
@SuppressWarnings("UnresolvedMixinReference")
@Mixin(targets = "TooltipProviderOptions", remap = false)
public abstract class TooltipProviderOptionsMixin {
    private static final String GUI_SCALE_UNLOCALIZED_TAG = "options.rightProperGuiScale";

    @Redirect(method = "getTooltipLines(Lnet/minecraft/client/gui/GuiButton;I)[Ljava/lang/String;",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/client/settings/GameSettings$Options;func_74378_d()" +
                                "Ljava/lang/String;"),
              require = 1)
    private String injected(GameSettings.Options option) {
        if (Util.isScaleOption(option))
            return GUI_SCALE_UNLOCALIZED_TAG;
        return option.getEnumString();
    }
}
