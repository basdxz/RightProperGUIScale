package com.github.basdxz.rightproperguiscale;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        throw new RuntimeException("Laughing at this user! They got a client side mod in their server instance!");
    }
}
