package com.github.basdxz.rightproperguiscale.proxy;

import com.github.basdxz.rightproperguiscale.GUIScale;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Client forge proxy.
 */
public class ClientProxy extends CommonProxy {
    /**
     * Initializes the GUIScale singleton.
     *
     * @param event Forge pre init event
     */
    public void preInit(FMLPreInitializationEvent event) {
        GUIScale.init();
    }
}