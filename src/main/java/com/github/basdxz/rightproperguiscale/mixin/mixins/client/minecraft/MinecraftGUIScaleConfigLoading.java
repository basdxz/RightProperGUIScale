package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Minecraft.class)
public abstract class MinecraftGUIScaleConfigLoading {
    @Inject(method = "startGame()V",
            at = @At(value = "HEAD"),
            require = 1)
    private void loadGUIScaleConfig(CallbackInfo ci) {
        RightProperGUIScaleConfig.register();
    }
}
