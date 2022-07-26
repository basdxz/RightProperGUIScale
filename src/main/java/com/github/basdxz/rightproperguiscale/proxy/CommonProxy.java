package com.github.basdxz.rightproperguiscale.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Forge common proxy.
 */
public abstract class CommonProxy {
    /**
     * Called by the Forge entry point soon after the mod is loaded.
     *
     * @param event Forge pre init event
     */
    public abstract void preInit(FMLPreInitializationEvent event);
}
