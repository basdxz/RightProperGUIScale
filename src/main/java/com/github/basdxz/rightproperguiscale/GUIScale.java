package com.github.basdxz.rightproperguiscale;

import com.github.basdxz.rightproperguiscale.command.GUIScaleCommand;
import com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig;
import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import com.github.basdxz.rightproperguiscale.mixin.plugin.Mixin;
import com.github.basdxz.rightproperguiscale.reflection.GameSettingReflections;
import com.github.basdxz.rightproperguiscale.util.Util;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

import java.text.DecimalFormat;

import static com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig.*;
import static net.minecraft.client.Minecraft.getMinecraft;

/**
 * The main class of the Right Proper GUI Scale mod, the Forge and Mixin entry points are listed below.
 *
 * @see RightProperGUIScale
 * @see Mixin
 */
@UtilityClass
public final class GUIScale {
    /**
     * The vanilla Minecraft Small GUI Scale setting.
     */
    private static final int VANILLA_SMALL_GUI_SCALE = 1;
    /**
     * The vanilla Minecraft Normal GUI Scale setting.
     */
    private static final int VANILLA_NORMAL_GUI_SCALE = 2;
    /**
     * The vanilla Minecraft Large GUI Scale setting.
     */
    private static final int VANILLA_LARGE_GUI_SCALE = 3;
    /**
     * The vanilla Minecraft Auto GUI Scale setting.
     */
    private static final int VANILLA_AUTO_GUI_SCALE = 0;
    /**
     * Float format to display the Video Setting label for {@link GameSettings.Options#GUI_SCALE}.
     */
    private static final DecimalFormat FLOAT_FORMAT = new DecimalFormat();
    /**
     * Flag for checking if the GUIScale has been initialized.
     */
    private static boolean IS_INITIALIZED = false;
    /**
     * Actual value of the current GUI Scale.
     */
    private static float VALUE = (float) GUI_SCALE_DEFAULT;
    /**
     * Integer value of the current GUI Scale, clamped to expected Minecraft limits.
     *
     * @see GUIScale#vanillaGuiScaleSetting
     */
    private static int VALUE_INT = defaultIntValue();
    /**
     * Temporary gui scale value used for giving quick feedback on the slider.
     */
    private static float TEMP = VALUE;
    /**
     * Last created resolution scale.
     */
    private static IScaledResolutionMixin LAST_SCALED_RESOLUTION;

    /**
     * Provides default int value.
     *
     * @return default int value
     */
    private static int defaultIntValue() {
        return vanillaGuiScaleSetting((float) GUI_SCALE_DEFAULT);
    }

    /**
     * Initializes the GUI Scale the first invoke, doing nothing in subsequent invocations.
     */
    public static void init() {
        if (IS_INITIALIZED)
            return;
        GameSettingReflections.apply();
        GUIScaleCommand.register();
        IS_INITIALIZED = true;
    }

    /**
     * Provides GUI Scale as float.
     *
     * @return GUI Scale as float
     */
    public static float value() {
        return VALUE;
    }

    /**
     * Provides GUI Scale as int, clamped to expected Minecraft limits.
     *
     * @return GUI Scale as int
     * @see GUIScale#vanillaGuiScaleSetting
     */
    public static int vanillaValue() {
        return VALUE_INT;
    }

    /**
     * Applies the current configuration defined in {@link RightProperGUIScaleConfig}.
     */
    public static void configure() {
        GameSettingReflections.apply();
        reapplyLimits();
    }

    /**
     * Reapplies the limits defined in {@link RightProperGUIScaleConfig}.
     */
    private static void reapplyLimits() {
        set(VALUE);
    }

    /**
     * Provides the slider label for displaying {@link GameSettings.Options#GUI_SCALE}.
     *
     * @return slider label
     */
    public static String sliderLabel() {
        return I18n.format(GameSettings.Options.GUI_SCALE.getEnumString()) + ": " + formattedSliderValues();
    }

    /**
     * Provides the formatted slider values.
     *
     * @return formatted slider values
     */
    private static String formattedSliderValues() {
        updateFloatFormat();
        return String.format("%sx (%sx)",
                             FLOAT_FORMAT.format(TEMP),
                             FLOAT_FORMAT.format(LAST_SCALED_RESOLUTION.scaleFactorF()));
    }

    /**
     * Updates {@link GUIScale#FLOAT_FORMAT}.
     */
    private static void updateFloatFormat() {
        val fractionDigits = fractionDigits();
        FLOAT_FORMAT.setMaximumFractionDigits(fractionDigits);
        FLOAT_FORMAT.setMinimumFractionDigits(fractionDigits);
    }

    /**
     * Provides the number of fractional digits to display depending on
     * {@link RightProperGUIScaleConfig#GUI_SCALE_STEP}.
     *
     * @return number of fractional digits
     */
    private static int fractionDigits() {
        return Math.round((float) -Math.log10(GUI_SCALE_STEP));
    }

    /**
     * Sets the {@link GUIScale#VALUE} and {@link GUIScale#VALUE_INT} to provided value and updates the GUI Scale.
     *
     * @param guiScale gui scale
     */
    public static void set(float guiScale) {
        setTemp(guiScale);
        update();
    }

    /**
     * Sets {@link GUIScale#TEMP} to provided value.
     *
     * @param guiScale temp gui scale
     */
    public static void setTemp(float guiScale) {
        TEMP = clampGUIScale(guiScale);
    }

    /**
     * Clamps the GUI scale within the limits currently defined in {@link RightProperGUIScaleConfig}.
     *
     * @param guiScale unclamped GUI scale.
     * @return the clamped GUI scale
     */
    private static float clampGUIScale(float guiScale) {
        return (float) Math.max(Math.min(guiScale, GUI_SCALE_MAX), GUI_SCALE_MIN);
    }

    /**
     * Updates the current GUI Scale.
     */
    public static void update() {
        pushTemp();
        updateMinecraft();
        updateCurrentGUI();
    }

    /**
     * Pushes {@link GUIScale#TEMP} to {@link GUIScale#VALUE} and {@link GUIScale#VALUE_INT}.
     */
    private static void pushTemp() {
        VALUE = TEMP;
        VALUE_INT = vanillaGuiScaleSetting(TEMP);
    }

    /**
     * Closest equivalent Vanilla GUI Scale setting.
     * <p>
     * A lot of other mods rely on the integer GUI scale {@value VANILLA_AUTO_GUI_SCALE},
     * being the only way the GUI size can be more than {@value VANILLA_LARGE_GUI_SCALE}.
     *
     * @param guiScale gui scale as float
     * @return vanilla-compatible gui scale setting
     */
    private static int vanillaGuiScaleSetting(float guiScale) {
        if (guiScale < VANILLA_SMALL_GUI_SCALE)
            return VANILLA_SMALL_GUI_SCALE;
        if (guiScale <= VANILLA_LARGE_GUI_SCALE)
            return MathHelper.ceiling_float_int(VALUE);
        return VANILLA_AUTO_GUI_SCALE;
    }

    /**
     * Updates Minecraft's GUI Scale.
     */
    private static void updateMinecraft() {
        val settings = getMinecraft().gameSettings;
        if (settings != null)
            settings.guiScale = VALUE_INT;
    }

    /**
     * Updates the currently open GUI size.
     */
    private static void updateCurrentGUI() {
        val screen = getMinecraft().currentScreen;
        if (screen == null)
            return;
        val scaledResolution = Util.newScaledResolution();
        screen.setWorldAndResolution(getMinecraft(),
                                     scaledResolution.getScaledWidth(),
                                     scaledResolution.getScaledHeight());
    }

    /**
     * Sets the last scaled resolution.
     * <p>
     * TODO: Optimisation to reduce internal Minecraft object spam.
     *
     * @param scaledResolution scaled resolution
     */
    public static void lastScaledResolution(@NonNull IScaledResolutionMixin scaledResolution) {
        LAST_SCALED_RESOLUTION = scaledResolution;
    }

    /**
     * Saves the GUI Scale to disk.
     */
    public static void save() {
        Minecraft.getMinecraft().gameSettings.saveOptions();
    }
}
