package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIScale;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

/**
 * A Mixin for {@link GameSettings} that appends the GUI Scale with {@link GUIScale#vanillaValue()}.
 */
@Mixin(GameSettings.class)
public abstract class GameSettingsInitGUIScale {
    @Shadow
    public int guiScale;

    /**
     * Injects at the end of the constructor and initialised the GUI Scale value.
     *
     * @param ci mixin callback info
     */
    @Inject(method = {"<init>()V",
                      "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V"},
            at = @At(value = "RETURN"),
            require = 1)
    private void appendConstructor(CallbackInfo ci) {
        initGUIScale();
    }

    /**
     * Initializes the {@link GameSettings#guiScale} value to {@link GUIScale#vanillaValue()}.
     */
    private void initGUIScale() {
        guiScale = GUIScale.vanillaValue();
    }
}
