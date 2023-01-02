package com.github.basdxz.rightproperguiscale;

import lombok.experimental.*;

/**
 * String constants, mostly for the Forge injection point: {@link RightProperGUIScale}.
 */
@UtilityClass
public final class Tags {
    // GRADLETOKEN_* will be replaced by your configuration values at build time
    public static final String MODID = "GRADLETOKEN_MODID";
    public static final String MODNAME = "GRADLETOKEN_MODNAME";
    public static final String VERSION = "GRADLETOKEN_VERSION";
    public static final String GROUPNAME = "GRADLETOKEN_GROUPNAME";

    public static final String MINECRAFT_VERSION = "[1.7.10]";
    public static final String DEPENDENCIES = "required-after:gasstation@[0.5.1,);" +
                                              "required-after:falsepatternlib@[0.10.13,);";
    public static final String GUI_FACTORY_PATH = GROUPNAME + ".config.RightProperGUIScaleGuiFactory";
    public static final String PROXY_PATH = GROUPNAME + ".proxy";
    public static final String CLIENT_PROXY_PATH = PROXY_PATH + ".ClientProxy";
    public static final String SERVER_PROXY_PATH = PROXY_PATH + ".ServerProxy";
}