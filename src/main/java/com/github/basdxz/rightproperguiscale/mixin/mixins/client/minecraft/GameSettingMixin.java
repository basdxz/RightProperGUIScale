package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIScale;
import com.github.basdxz.rightproperguiscale.util.Util;
import lombok.*;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

/**
 * A Mixin for {@link GameSettings} which performs a number of patches.
 * <li>Appending the constructors to properly initialize the GUI Scale.</li>
 * <li>Replacing the text label on the GUI Scale slider.</li>
 * <li>Redirecting the setting and getting of the GUI Scale slider.</li>
 * <li>Redirecting the saving and loading of the GUI Scale setting.</li>
 */
@Mixin(GameSettings.class)
public abstract class GameSettingMixin {
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

    /**
     * Injects when the GUI Scale is about to be parsed, parsing it as a float instead of an int and loads it with
     * {@link GUIScale#set(float)}
     *
     * @param guiScale GUI Scale
     * @return GUI Scale
     */
    @Redirect(method = "loadOptions()V",
              slice = @Slice(from = @At(value = "FIELD",
                                        opcode = Opcodes.PUTFIELD,
                                        target = "Lnet/minecraft/client/settings/GameSettings;renderDistanceChunks:I"),
                             to = @At(value = "FIELD",
                                      opcode = Opcodes.PUTFIELD,
                                      target = "Lnet/minecraft/client/settings/GameSettings;guiScale:I")),
              at = @At(value = "INVOKE",
                       target = "Ljava/lang/Integer;parseInt(Ljava/lang/String;)I"),
              require = 1)
    private int loadGuiScale(String guiScale) {
        setGUIScaleFromOptionsFile(guiScale);
        return loadedGUIScale();
    }

    /**
     * Sets {@link GUIScale} to the provided GUI Scale.
     *
     * @param guiScale GUI Scale
     */
    private void setGUIScaleFromOptionsFile(@NonNull String guiScale) {
        GUIScale.set(parseGUIScaleFromOptionsFile(guiScale));
    }

    /**
     * Parses the provided GUI Scale string into it's float value.
     *
     * @param guiScale GUI Scale
     * @return GUI Scale
     */
    private float parseGUIScaleFromOptionsFile(@NonNull String guiScale) {
        return Float.parseFloat(guiScale);
    }

    /**
     * Provides the loaded GUI scale int.
     *
     * @return GUI Scale
     */
    private int loadedGUIScale() {
        return GUIScale.vanillaValue();
    }

    /**
     * Injects where the GUI Scale value would be saved and replaces it with {@link GUIScale#value()}.
     *
     * @param instance
     * @param minecraftGuiScale
     * @return
     */
    @Redirect(method = "saveOptions()V",
              slice = @Slice(from = @At(value = "FIELD",
                                        opcode = Opcodes.GETFIELD,
                                        target = "Lnet/minecraft/client/settings/GameSettings;guiScale:I"),
                             to = @At(value = "FIELD",
                                      opcode = Opcodes.GETFIELD,
                                      target = "Lnet/minecraft/client/settings/GameSettings;particleSetting:I")),
              at = @At(value = "INVOKE",
                       target = "Ljava/lang/StringBuilder;append(I)Ljava/lang/StringBuilder;"),
              require = 1)
    private StringBuilder saveGuiScale(StringBuilder instance, int minecraftGuiScale) {
        return appendGuiScaleToOptionsFile(instance);
    }

    /**
     * Appends the GUI Scale value to the options file string builder.
     *
     * @param optionsFileStringBuilder options file string builder
     * @return options file string builder
     */
    private StringBuilder appendGuiScaleToOptionsFile(@NonNull StringBuilder optionsFileStringBuilder) {
        return optionsFileStringBuilder.append(GUIScale.value());
    }
}
