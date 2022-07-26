package com.github.basdxz.rightproperguiscale.config;

import com.falsepattern.lib.config.Config;
import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.ConfigurationManager;
import com.github.basdxz.rightproperguiscale.RightProperGUIScale;
import com.github.basdxz.rightproperguiscale.Tags;
import lombok.experimental.*;

@UtilityClass
@Config(modid = Tags.MODID)
public final class RightProperGUIScaleConfig {
    @Config.Comment("Foo")
    @Config.LangKey("config.rightproperguiscale.min")
    @Config.DefaultFloat(1F)
    @Config.RangeFloat(min = 0.1F, max = 1000F)
    public static float GUI_SCALE_MIN;
    @Config.Comment("Foo")
    @Config.LangKey("config.rightproperguiscale.max")
    @Config.DefaultFloat(10F)
    @Config.RangeFloat(min = 1F, max = 1000F)
    public static float GUI_SCALE_MAX = 10F;
    @Config.Comment("Foo")
    @Config.LangKey("config.rightproperguiscale.step")
    @Config.DefaultFloat(0.1F)
    @Config.RangeFloat(min = 0.01F, max = 1000F)
    public static float GUI_SCALE_STEP = 0.1F;
    @Config.Comment("Foo")
    @Config.LangKey("config.rightproperguiscale.default")
    @Config.DefaultFloat(4F)
    @Config.RangeFloat(min = 0.01F, max = 1000F)
    public static float GUI_SCALE_DEFAULT;
    @Config.Comment("Foo")
    @Config.LangKey("config.rightproperguiscale.min_scaled_width")
    @Config.DefaultInt(320)
    @Config.RangeInt(min = 80, max = 15360)
    public static int MIN_SCALED_WIDTH;
    @Config.Comment("Foo")
    @Config.LangKey("config.rightproperguiscale.min_scaled_height")
    @Config.DefaultInt(240)
    @Config.RangeInt(min = 60, max = 8640)
    public static int MIN_SCALED_HEIGHT;

    public static void register() {
        try {
            ConfigurationManager.registerConfig(RightProperGUIScaleConfig.class);
        } catch (ConfigException e) {
            RightProperGUIScale.logger.error("Failed to register config!");
        }
    }
}
