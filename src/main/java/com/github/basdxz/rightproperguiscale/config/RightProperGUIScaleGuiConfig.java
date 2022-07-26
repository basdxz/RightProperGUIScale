package com.github.basdxz.rightproperguiscale.config;

import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.SimpleGuiConfig;
import com.github.basdxz.rightproperguiscale.GUIScale;
import com.github.basdxz.rightproperguiscale.Tags;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;

/**
 * The {@link GuiConfig} implementation, delegating most of the logic to FalsePatternLib.
 */
public class RightProperGUIScaleGuiConfig extends SimpleGuiConfig {
    /**
     * Creates a new instance of the configuration GUI.
     *
     * @param parent parent gui
     * @throws ConfigException config exception
     */
    public RightProperGUIScaleGuiConfig(GuiScreen parent) throws ConfigException {
        super(parent, RightProperGUIScaleConfig.class, Tags.MODID, Tags.MODNAME);
    }

    /**
     * Invoked on the GUI being closed.
     */
    @Override
    public void onGuiClosed() {
        GUIScale.configure();
    }
}