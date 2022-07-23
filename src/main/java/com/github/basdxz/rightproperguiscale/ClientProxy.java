package com.github.basdxz.rightproperguiscale;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        if (RightProperGUIScale.isEnabled())
            GameSettingReflections.apply();
    }
}