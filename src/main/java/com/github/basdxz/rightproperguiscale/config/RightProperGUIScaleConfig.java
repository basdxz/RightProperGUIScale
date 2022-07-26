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
    @Config.RangeFloat(min = 0F)
    @Config.LangKey("config.rightproperguiscale.min")
    @Config.DefaultFloat(256)
    public static float GUI_SCALE_MIN;

    private static final String CONFIG_REGISTRATION_ERROR = "Failed to register config";

    public static void register() {
        try {
            ConfigurationManager.registerConfig(RightProperGUIScale.class);
        } catch (ConfigException e) {
            RightProperGUIScale.logger.error(CONFIG_REGISTRATION_ERROR);
        }
    }
}
