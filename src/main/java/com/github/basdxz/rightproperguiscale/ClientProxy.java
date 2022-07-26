package com.github.basdxz.rightproperguiscale;

import com.github.basdxz.rightproperguiscale.command.GUIScaleCommand;
import com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig;
import com.github.basdxz.rightproperguiscale.reflection.GameSettingReflections;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        RightProperGUIScaleConfig.register();
        GameSettingReflections.apply();
        GUIScaleCommand.register();
    }
}