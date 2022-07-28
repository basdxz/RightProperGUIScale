package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIScale;
import com.github.basdxz.rightproperguiscale.util.Util;
import lombok.*;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

/**
 * A Mixin for {@link GameSettings} which tweaks the GUI Scale slider to show the correct label and properly set/get
 * its value.
 */
@Mixin(GameSettings.class)
public abstract class GameSettingGUIScaleSliderMixin {
    @Shadow
    public int guiScale;

    /**
     * Injects when a float {@link GameSettings.Options option} is about to be retrieved, replacing the return if it's
     * the {@link GameSettings.Options GUI Scale setting}.
     *
     * @param option game setting option
     * @param cir    mixin callback info returnable
     */
    @Inject(method = "getKeyBinding(Lnet/minecraft/client/settings/GameSettings$Options;)Ljava/lang/String;",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void guiScaleLabel(GameSettings.Options option, CallbackInfoReturnable<String> cir) {
        if (Util.isGUIScaleOption(option))
            returnGUIScaleSliderLabel(cir);
    }

    /**
     * Invokes the returnable callback with {@link GUIScale#sliderLabel()} as the return.
     *
     * @param cir mixin callback info returnable
     */
    private void returnGUIScaleSliderLabel(@NonNull CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(GUIScale.sliderLabel());
        cir.cancel();
    }

    /**
     * Injects when a float {@link GameSettings.Options option} is about to be set, redirecting the action if the
     * option is {@link GameSettings.Options GUI Scale setting}.
     *
     * @param option game setting option
     * @param ci     mixin callback info
     */
    @Inject(method = "setOptionFloatValue(Lnet/minecraft/client/settings/GameSettings$Options;F)V",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void setGuiScaleValue(GameSettings.Options option, float value, CallbackInfo ci) {
        if (Util.isGUIScaleOption(option))
            setTempGUIScale(guiScale, ci);
    }

    /**
     * Sets the provided GUI Scale into {@link GUIScale#setTemp(float)} and invokes the return callback.
     *
     * @param guiScale GUI Scale
     * @param ci       mixin callback info
     */
    private void setTempGUIScale(float guiScale, @NonNull CallbackInfo ci) {
        GUIScale.setTemp(guiScale);
        ci.cancel();
    }

    /**
     * Injects when a float {@link GameSettings.Options option} is about to be retrieved, replacing the return with
     * {@link GUIScale#value()} if it's the {@link GameSettings.Options GUI Scale setting}.
     *
     * @param option game setting option
     * @param cir    mixin callback info returnable
     */
    @Inject(method = "getOptionFloatValue(Lnet/minecraft/client/settings/GameSettings$Options;)F",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void getGuiScaleValue(GameSettings.Options option, CallbackInfoReturnable<Float> cir) {
        if (Util.isGUIScaleOption(option))
            returnGUIScale(cir);
    }

    /**
     * Invokes the returnable callback with {@link GUIScale#value()} as the return.
     *
     * @param cir mixin callback info returnable
     */
    private void returnGUIScale(@NonNull CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(GUIScale.value());
        cir.cancel();
    }
}
