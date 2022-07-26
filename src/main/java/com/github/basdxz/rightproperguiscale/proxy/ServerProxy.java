package com.github.basdxz.rightproperguiscale.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import static com.github.basdxz.rightproperguiscale.Tags.MODNAME;

/**
 * Forge server proxy.
 */
public class ServerProxy extends CommonProxy {
    private final static String CLIENT_SIDE_ONLY_ERROR =
            "Laughing at this user! " + MODNAME + " is a CLIENT-SIDE MOD!!!";

    /**
     * Throws an error if the mod is loaded on the server.
     *
     * @param event Forge pre init event
     */
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        throw newClientSideOnlyError();
    }

    protected final Error newClientSideOnlyError() {
        return new Error(CLIENT_SIDE_ONLY_ERROR);
    }
}
