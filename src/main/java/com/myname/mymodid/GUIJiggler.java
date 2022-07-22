package com.myname.mymodid;

import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

//TODO: Proper incremental scaling
@UtilityClass
public final class GUIJiggler {
    public static final float GUI_SCALE_MIN = 0.20F;
    public static final float GUI_SCALE_MAX = 10F;
    public static final float GUI_SCALE_STEP = 0.1F;
    public static final float GUI_SCALE_DEFAULT = 4.0F;

    private static float GUI_SCALE = GUI_SCALE_DEFAULT;
    private static float TEMP_GUI_SCALE = GUI_SCALE;

    public static String guiScaleSliderLabel() {
        return I18n.format(GameSettings.Options.GUI_SCALE.getEnumString()) + ": " +
               String.format("%.1f", TEMP_GUI_SCALE);
    }

    public static void setTempGuiScale(float guiScale) {
        TEMP_GUI_SCALE = guiScale;
    }

    public static void updateGuiScale() {
        pushTempGuiScale();
        updateMinecraftResolutionScale();
    }

    private static void pushTempGuiScale() {
        GUI_SCALE = TEMP_GUI_SCALE;
    }

    private static void updateMinecraftResolutionScale() {
        val minecraft = Minecraft.getMinecraft();
        minecraft.gameSettings.guiScale = guiScaleAsInt();
        val scaledResolution = newScaledResolution(minecraft);
        minecraft.currentScreen.setWorldAndResolution(minecraft,
                                                      scaledResolution.getScaledWidth(),
                                                      scaledResolution.getScaledHeight());
    }

    public static int guiScaleAsInt() {
        return MathHelper.ceiling_float_int(GUI_SCALE);
    }

    private static ScaledResolution newScaledResolution(@NonNull Minecraft minecraft) {
        return new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
    }

    public static float guiScaleAsFloat() {
        return GUI_SCALE;
    }
}
