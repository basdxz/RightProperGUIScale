package com.github.basdxz.rightproperguiscale.config;

import com.falsepattern.lib.config.SimpleGuiFactory;
import net.minecraft.client.gui.GuiScreen;

public class RightProperGUIScaleGuiFactory implements SimpleGuiFactory {
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return RightProperGUIScaleGuiConfig.class;
    }
}