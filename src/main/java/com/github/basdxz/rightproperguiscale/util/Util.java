package com.github.basdxz.rightproperguiscale.util;

import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;

import static net.minecraft.client.Minecraft.getMinecraft;

/**
 * Utilities that don't have a better place to be.
 */
@UtilityClass
public final class Util {
    private static final int MOUSE_BUTTON_MOVED = -1;

    /**
     * Creates a new {@link IScaledResolutionMixin}.
     *
     * @return new scaled resolution
     */
    public static IScaledResolutionMixin newIScaledResolutionMixin() {
        return toIScaledResolutionMixin(newScaledResolution());
    }

    /**
     * Creates a new {@link ScaledResolution}.
     *
     * @return new scaled resolution
     */
    public static ScaledResolution newScaledResolution() {
        return new ScaledResolution(getMinecraft(), getMinecraft().displayWidth, getMinecraft().displayHeight);
    }

    /**
     * Converts a {@link ScaledResolution} into {@link IScaledResolutionMixin}.
     *
     * @param scaledResolution converted scaled instance to convert
     * @return converted scaled resolution
     */
    public static IScaledResolutionMixin toIScaledResolutionMixin(@NonNull ScaledResolution scaledResolution) {
        return (IScaledResolutionMixin) scaledResolution;
    }

    /**
     * Used to check the third parameter from: {@link GuiScreen#mouseMovedOrUp(int, int, int)}
     * to check if a mouse button has just been released.
     *
     * @param mouseButton mouse button state
     * @return is the mouse button released
     */
    public static boolean mouseReleased(int mouseButton) {
        return MOUSE_BUTTON_MOVED != mouseButton;
    }

    /**
     * Checks if the given option is {@link GameSettings.Options#GUI_SCALE}.
     *
     * @param option option to compare
     * @return is the option GUI Scale
     */
    public static boolean isGUIScaleOption(GameSettings.Options option) {
        return GameSettings.Options.GUI_SCALE == option;
    }

    /**
     * Renders out a framebuffer to the entire screen.
     *
     * @param framebuffer framebuffer
     */
    public static void framebufferRender(@NonNull Framebuffer framebuffer) {
        framebuffer.framebufferRender(getMinecraft().displayWidth, getMinecraft().displayHeight);
    }
}
