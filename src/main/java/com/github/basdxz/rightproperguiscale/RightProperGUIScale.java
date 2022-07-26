package com.github.basdxz.rightproperguiscale;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.github.basdxz.rightproperguiscale.Tags.*;

@Mod(modid = MODID,
     version = VERSION,
     name = MODNAME,
     acceptedMinecraftVersions = "[1.7.10]",
     dependencies = "required-after:spongemixins@[1.1.0,);" + "required-after:falsepatternlib@[0.9.0,);")
public class RightProperGUIScale {
    @SidedProxy(clientSide = GROUPNAME + ".ClientProxy", serverSide = GROUPNAME + ".CommonProxy")
    public static CommonProxy proxy;
    public static final Logger logger = LogManager.getLogger(MODNAME);

    public static Boolean isEnabled() {
        return true;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
}