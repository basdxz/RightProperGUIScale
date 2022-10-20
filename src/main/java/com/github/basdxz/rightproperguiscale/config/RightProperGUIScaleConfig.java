package com.github.basdxz.rightproperguiscale.config;

import com.falsepattern.lib.config.Config;
import com.falsepattern.lib.config.ConfigurationManager;
import com.github.basdxz.rightproperguiscale.GUIScale;
import com.github.basdxz.rightproperguiscale.RightProperGUIScale;
import com.github.basdxz.rightproperguiscale.Tags;
import lombok.*;
import lombok.experimental.*;

/**
 * The configuration for {@link GUIScale}.
 */
@UtilityClass
@Config(modid = Tags.MODID)
public final class RightProperGUIScaleConfig {
    @Config.Comment("Minimum setting of the GUI Scale.")
    @Config.LangKey("config.rightproperguiscale.min")
    @Config.DefaultDouble(1D)
    @Config.RangeDouble(min = 0.1D, max = 1000D)
    public static double GUI_SCALE_MIN;
    @Config.Comment("Maxiumum setting of the GUI Scale.")
    @Config.LangKey("config.rightproperguiscale.max")
    @Config.DefaultDouble(10D)
    @Config.RangeDouble(min = 1D, max = 1000D)
    public static double GUI_SCALE_MAX = 10D;
    @Config.Comment("The step size of the GUI Scale slider.")
    @Config.LangKey("config.rightproperguiscale.step")
    @Config.DefaultDouble(0.1D)
    @Config.RangeDouble(min = 0.01D, max = 1000D)
    public static double GUI_SCALE_STEP = 0.1D;
    @Config.Comment("The Default GUI Scale.")
    @Config.LangKey("config.rightproperguiscale.default")
    @Config.DefaultDouble(4D)
    @Config.RangeDouble(min = 0.01D, max = 1000D)
    public static double GUI_SCALE_DEFAULT;
    @Config.Comment("The minimum width the GUI will scale to.")
    @Config.LangKey("config.rightproperguiscale.min_scaled_width")
    @Config.DefaultInt(320)
    @Config.RangeInt(min = 80, max = 15360)
    public static int MIN_SCALED_WIDTH;
    @Config.Comment("The minimum height the GUI will scale to.")
    @Config.LangKey("config.rightproperguiscale.min_scaled_height")
    @Config.DefaultInt(240)
    @Config.RangeInt(min = 60, max = 8640)
    public static int MIN_SCALED_HEIGHT;

    @Config.Ignore
    private static final String CONFIG_REGISTRATION_FAILED = "Failed to register config!";

    /**
     * Registers the config.
     * <p>
     * TODO: Update to register and load once FalsePatternLib updates.
     */
    public static void register() {
        try {
            ConfigurationManager.selfInit();
        } catch (Exception e) {
            logRegistrationFailure(e);
        }
    }

    /**
     * Logs registration failure.
     *
     * @param e config exception
     */
    private static void logRegistrationFailure(@NonNull Exception e) {
        RightProperGUIScale.logger.error(CONFIG_REGISTRATION_FAILED, e);
    }
}
