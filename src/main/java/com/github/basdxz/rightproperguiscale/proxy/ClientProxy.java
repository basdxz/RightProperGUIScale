package com.github.basdxz.rightproperguiscale.proxy;

import com.github.basdxz.rightproperguiscale.GUIScale;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        GUIScale.init();
    }
}