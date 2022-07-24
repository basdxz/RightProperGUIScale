package com.github.basdxz.rightproperguiscale;

import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

@UtilityClass
public final class GUIJiggler {
    public static final int MIN_SCALED_WIDTH = 320;
    public static final int MIN_SCALED_HEIGHT = 240;

    public static final float GUI_SCALE_MIN = 1F;
    public static final float GUI_SCALE_MAX = 10F;
    public static final float GUI_SCALE_STEP = 0.1F;
    public static final float GUI_SCALE_DEFAULT = 4.0F;

    private static final int VANILLA_MAX_GUI_SCALE = 3;
    private static final int VANILLA_AUTO_GUI_SCALE = 0;

    private static final int MOUSE_BUTTON_MOVED = -1;

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
        val minecraft = Minecraft.getMinecraft();
        updateMinecraftResolutionScale(minecraft);
        updateCurrentGUI(minecraft);
    }

    private static void pushTempGuiScale() {
        GUI_SCALE = TEMP_GUI_SCALE;
    }

    private static void updateMinecraftResolutionScale(@NonNull Minecraft minecraft) {
        minecraft.gameSettings.guiScale = guiScaleAsInt();
    }

    private static void updateCurrentGUI(@NonNull Minecraft minecraft) {
        val currentGUI = minecraft.currentScreen;
        if (currentGUI == null)
            return;
        val scaledResolution = newScaledResolution(minecraft);
        currentGUI.setWorldAndResolution(minecraft,
                                         scaledResolution.getScaledWidth(),
                                         scaledResolution.getScaledHeight());
    }

    public static int guiScaleAsInt() {
        val intGuiScale = MathHelper.ceiling_float_int(GUI_SCALE);
        return intGuiScale > VANILLA_MAX_GUI_SCALE ? VANILLA_AUTO_GUI_SCALE : intGuiScale;
    }

    private static ScaledResolution newScaledResolution(@NonNull Minecraft minecraft) {
        return new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
    }

    public static float guiScaleAsFloat() {
        return GUI_SCALE;
    }

    public static boolean mouseReleased(int mouseButton) {
        return MOUSE_BUTTON_MOVED != mouseButton;
    }
}
