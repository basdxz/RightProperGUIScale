package com.github.basdxz.rightproperguiscale.util;

import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;

import static net.minecraft.client.Minecraft.getMinecraft;

@UtilityClass
public final class Util {
    private static final int MOUSE_BUTTON_MOVED = -1;

    public static IScaledResolutionMixin newIScaledResolutionMixin() {
        return toIScaledResolutionMixin(newScaledResolution());
    }

    public static ScaledResolution newScaledResolution() {
        return new ScaledResolution(getMinecraft(), getMinecraft().displayWidth, getMinecraft().displayHeight);
    }

    public static IScaledResolutionMixin toIScaledResolutionMixin(@NonNull ScaledResolution scaledResolution) {
        return (IScaledResolutionMixin) scaledResolution;
    }

    public static boolean mouseReleased(int mouseButton) {
        return MOUSE_BUTTON_MOVED != mouseButton;
    }

    public static boolean isGUIScaleOption(GameSettings.Options option) {
        return GameSettings.Options.GUI_SCALE == option;
    }
}
