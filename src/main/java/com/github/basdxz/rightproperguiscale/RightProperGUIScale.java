package com.github.basdxz.rightproperguiscale;

import com.github.basdxz.rightproperguiscale.mixin.plugin.Mixin;
import com.github.basdxz.rightproperguiscale.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.github.basdxz.rightproperguiscale.Tags.*;

/**
 * Forge entry point, loaded after {@link Mixin} has been processed.
 */
@Mod(modid = MODID,
     version = VERSION,
     name = MODNAME,
     acceptedMinecraftVersions = MINECRAFT_VERSION,
     guiFactory = GUI_FACTORY_PATH,
     dependencies = DEPENDENCIES)
public class RightProperGUIScale {
    /**
     * Logger provided by Forge.
     */
    public static final Logger logger = LogManager.getLogger(MODNAME);
    /**
     * Proxy injected by Forge.
     */
    @SidedProxy(clientSide = CLIENT_PROXY_PATH, serverSide = SERVER_PROXY_PATH)
    public static CommonProxy proxy;

    /**
     * Executed shortly after the mod is loaded.
     * <p>
     * Redirects to the forge provided proxy.
     *
     * @param event Forge pre init event
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
}