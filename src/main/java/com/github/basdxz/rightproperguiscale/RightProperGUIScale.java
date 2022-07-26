package com.github.basdxz.rightproperguiscale;

import com.github.basdxz.rightproperguiscale.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.github.basdxz.rightproperguiscale.Tags.*;

@Mod(modid = MODID,
     version = VERSION,
     name = MODNAME,
     acceptedMinecraftVersions = MINECRAFT_VERSION,
     guiFactory = GUI_FACTORY_PATH,
     dependencies = DEPENDENCIES)
public class RightProperGUIScale {
    public static final Logger logger = LogManager.getLogger(MODNAME);

    @SidedProxy(clientSide = CLIENT_PROXY_PATH, serverSide = SERVER_PROXY_PATH)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
}