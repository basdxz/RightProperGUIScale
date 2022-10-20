package com.github.basdxz.rightproperguiscale.config;

import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.SimpleGuiConfig;
import com.github.basdxz.rightproperguiscale.GUIScale;
import com.github.basdxz.rightproperguiscale.RightProperGUIScale;
import com.github.basdxz.rightproperguiscale.Tags;
import cpw.mods.fml.client.config.GuiConfig;
import lombok.*;
import net.minecraft.client.gui.GuiScreen;

import static com.falsepattern.lib.internal.impl.config.ConfigurationManagerImpl.save;


/**
 * The {@link GuiConfig} implementation, delegating most of the logic to FalsePatternLib.
 */
public class RightProperGUIScaleGuiConfig extends SimpleGuiConfig {
    protected static final String CONFIG_SAVING_FAILED = "Failed to save config!";

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
        try {
            save(RightProperGUIScaleConfig.class);
        } catch (ConfigException e) {
            logConfigSavingFailure(e);
        }
        GUIScale.configure();
    }


    /**
     * Logs registration failure.
     *
     * @param e config exception
     */
    protected void logConfigSavingFailure(@NonNull ConfigException e) {
        RightProperGUIScale.logger.error(CONFIG_SAVING_FAILED, e);
    }
}