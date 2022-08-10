package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIScale;
import com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

/**
 * Mixin to load the {@link RightProperGUIScaleConfig} before {@link GUIScale} to correctly set the default values.
 */
@Mixin(Minecraft.class)
public abstract class MinecraftGUIScaleConfigLoading {
    /**
     * Injects right before the game starts loading and loads {@link RightProperGUIScaleConfig} before anything else.
     *
     * @param ci mixin callback info
     */
    @Inject(method = "startGame()V",
            at = @At(value = "HEAD"),
            require = 1)
    private void loadGUIScaleConfig(CallbackInfo ci) {
        RightProperGUIScaleConfig.register();
    }
}
