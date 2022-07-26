package com.github.basdxz.rightproperguiscale;

import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

import static com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig.*;
import static net.minecraft.client.Minecraft.getMinecraft;

@UtilityClass
public final class GUIJiggler {
    private static final int VANILLA_MAX_GUI_SCALE = 3;
    private static final int VANILLA_AUTO_GUI_SCALE = 0;

    private static final int MOUSE_BUTTON_MOVED = -1;

    private static float GUI_SCALE = GUI_SCALE_DEFAULT;
    private static float TEMP_GUI_SCALE = GUI_SCALE;

    public static String guiScaleSliderLabel() {
        return I18n.format(GameSettings.Options.GUI_SCALE.getEnumString()) + ": " +
               String.format("%.1f", TEMP_GUI_SCALE);
    }

    public static void setGUIScale(float guiScale) {
        setTempGUIScale(guiScale);
        updateGuiScale();
    }

    public static void setTempGUIScale(float guiScale) {
        TEMP_GUI_SCALE = clampGUIScale(guiScale);
    }

    private static float clampGUIScale(float guiScale) {
        return Math.max(Math.min(guiScale, GUI_SCALE_MAX), GUI_SCALE_MIN);
    }

    public static void updateGuiScale() {
        pushTempGuiScale();
        updateMinecraftGUIScale();
        updateCurrentScreen();
    }

    private static void pushTempGuiScale() {
        GUI_SCALE = TEMP_GUI_SCALE;
    }

    private static void updateMinecraftGUIScale() {
        getMinecraft().gameSettings.guiScale = guiScaleAsInt();
    }

    private static void updateCurrentScreen() {
        if (getMinecraft().currentScreen == null)
            return;
        val scaledResolution = newScaledResolution();
        getMinecraft().currentScreen.setWorldAndResolution(getMinecraft(),
                                                           scaledResolution.getScaledWidth(),
                                                           scaledResolution.getScaledHeight());
    }

    public static void saveGUIScale() {
        Minecraft.getMinecraft().gameSettings.saveOptions();
    }

    public static int guiScaleAsInt() {
        val intGuiScale = MathHelper.ceiling_float_int(GUI_SCALE);
        return intGuiScale > VANILLA_MAX_GUI_SCALE ? VANILLA_AUTO_GUI_SCALE : intGuiScale;
    }

    public static IScaledResolutionMixin newIScaledResolutionMixin() {
        return toIScaledResolutionMixin(newScaledResolution());
    }

    public static ScaledResolution newScaledResolution() {
        return new ScaledResolution(getMinecraft(), getMinecraft().displayWidth, getMinecraft().displayHeight);
    }

    public static IScaledResolutionMixin toIScaledResolutionMixin(@NonNull ScaledResolution scaledResolution) {
        if (!RightProperGUIScale.isEnabled())
            throw new IllegalStateException("Mod is disabled!");
        return (IScaledResolutionMixin) scaledResolution;
    }

    public static float guiScaleAsFloat() {
        return GUI_SCALE;
    }

    public static boolean mouseReleased(int mouseButton) {
        return MOUSE_BUTTON_MOVED != mouseButton;
    }

    public static boolean isScaleOption(GameSettings.Options option) {
        return GameSettings.Options.GUI_SCALE == option;
    }
}
