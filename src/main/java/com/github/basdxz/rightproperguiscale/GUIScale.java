package com.github.basdxz.rightproperguiscale;

import com.github.basdxz.rightproperguiscale.command.GUIScaleCommand;
import com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig;
import com.github.basdxz.rightproperguiscale.reflection.GameSettingReflections;
import com.github.basdxz.rightproperguiscale.util.Util;
import lombok.experimental.*;
import lombok.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

import static com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig.*;
import static net.minecraft.client.Minecraft.getMinecraft;

@UtilityClass
public final class GUIScale {
    private static final int VANILLA_MAX_GUI_SCALE = 3;
    private static final int VANILLA_AUTO_GUI_SCALE = 0;

    private static boolean INITIALIZED = false;
    private static float VALUE = GUI_SCALE_DEFAULT;
    private static float TEMP = VALUE;

    public static void init() {
        if (INITIALIZED)
            return;
        RightProperGUIScaleConfig.register();
        GameSettingReflections.apply();
        GUIScaleCommand.register();
        INITIALIZED = true;
    }

    public static void configure() {
        GameSettingReflections.apply();
        reapplyLimits();
    }

    private static void reapplyLimits() {
        set(VALUE);
    }

    public static String sliderLabel() {
        return I18n.format(GameSettings.Options.GUI_SCALE.getEnumString()) + ": " +
               String.format("%.1f", TEMP);
    }

    public static void set(float guiScale) {
        setTemp(guiScale);
        update();
    }

    public static void setTemp(float guiScale) {
        TEMP = clampGUIScale(guiScale);
    }

    private static float clampGUIScale(float guiScale) {
        return Math.max(Math.min(guiScale, GUI_SCALE_MAX), GUI_SCALE_MIN);
    }

    public static void update() {
        pushTemp();
        updateMinecraft();
        updateCurrentScreen();
    }

    private static void pushTemp() {
        VALUE = TEMP;
    }

    private static void updateMinecraft() {
        getMinecraft().gameSettings.guiScale = asInt();
    }

    private static void updateCurrentScreen() {
        if (getMinecraft().currentScreen == null)
            return;
        val scaledResolution = Util.newScaledResolution();
        getMinecraft().currentScreen.setWorldAndResolution(getMinecraft(),
                                                           scaledResolution.getScaledWidth(),
                                                           scaledResolution.getScaledHeight());
    }

    public static void save() {
        Minecraft.getMinecraft().gameSettings.saveOptions();
    }

    public static int asInt() {
        val intGuiScale = MathHelper.ceiling_float_int(VALUE);
        return intGuiScale > VANILLA_MAX_GUI_SCALE ? VANILLA_AUTO_GUI_SCALE : intGuiScale;
    }

    public static float asFloat() {
        return VALUE;
    }
}
