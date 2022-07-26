package com.github.basdxz.rightproperguiscale.config;

import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.SimpleGuiConfig;
import com.github.basdxz.rightproperguiscale.Tags;
import net.minecraft.client.gui.GuiScreen;

public class RightProperGUIScaleGuiConfig extends SimpleGuiConfig {
    public RightProperGUIScaleGuiConfig(GuiScreen parent) throws ConfigException {
        super(parent, RightProperGUIScaleConfig.class, Tags.MODID, Tags.MODNAME);
    }
}