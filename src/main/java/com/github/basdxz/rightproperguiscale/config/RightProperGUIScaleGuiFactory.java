package com.github.basdxz.rightproperguiscale.config;

import com.falsepattern.lib.config.SimpleGuiFactory;
import cpw.mods.fml.client.IModGuiFactory;
import net.minecraft.client.gui.GuiScreen;

/**
 * The {@link IModGuiFactory} implementation, delegating most of the logic to FalsePatternLib.
 */
public class RightProperGUIScaleGuiFactory implements SimpleGuiFactory {
    /**
     * Provides the GUI config class.
     *
     * @return gui config class
     */
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return RightProperGUIScaleGuiConfig.class;
    }
}