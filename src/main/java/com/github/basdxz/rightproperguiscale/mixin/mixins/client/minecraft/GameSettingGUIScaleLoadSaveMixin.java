package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIScale;
import lombok.*;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

/**
 * A Mixin for {@link GameSettings} to handle loading and saving the {@link GUIScale} value.
 */
@Mixin(GameSettings.class)
public abstract class GameSettingGUIScaleLoadSaveMixin {
    /**
     * Injects when the GUI Scale is about to be parsed, parsing it as a float instead of an int and loads it with
     * {@link GUIScale#set(float)}.
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
