package com.myname.mymodid;

import cpw.mods.fml.relauncher.ReflectionHelper;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.reflect.FieldUtils;

//TODO: Applying scale changes
//TODO: Saving scale
//TODO: Loading scale
//TODO: Proper incremental scaling
@UtilityClass
public final class GUIJiggler {
    public static final float GUI_SCALE_MIN = 0.20F;
    public static final float GUI_SCALE_MAX = 10F;
    public static final float GUI_SCALE_STEP = 0.1F;

    //TODO: better naming, this is not readable
    private static final String[] OPTIONS_ENUM_FLOAT_FIELD_NAMES = new String[]{"enumFloat", "field_74385_A"};
    private static final boolean OPTIONS_ENUM_FLOAT_FIELD_GUI_SCALE_DEFAULT = false;
    private static final String[] OPTIONS_VALUE_MIN_FIELD_NAMES = new String[]{"valueMin", "field_148271_N"};
    private static final float OPTIONS_ENUM_FLOAT_VALUE_STEP_GUI_SCALE_DEFAULT = 0F;
    private static final String[] OPTIONS_VALUE_MAX_FIELD_NAMES = new String[]{"valueMax", "field_148272_O"};
    private static final float OPTIONS_ENUM_FLOAT_VALUE_MIN_GUI_SCALE_DEFAULT = 0F;
    private static final String[] OPTIONS_VALUE_STEP_FIELD_NAMES = new String[]{"valueStep", "field_148270_M"};
    private static final float OPTIONS_ENUM_FLOAT_VALUE_MAX_GUI_SCALE_DEFAULT = 0F;

    private static float GUI_SCALE = GUI_SCALE_MAX;

    public static void init() {
        guiScaleButtonToSlider();
    }

    private static void guiScaleButtonToSlider() {
        setField(GameSettings.Options.class,
                 GameSettings.Options.GUI_SCALE,
                 true,
                 OPTIONS_ENUM_FLOAT_FIELD_NAMES);
        setField(GameSettings.Options.class,
                 GameSettings.Options.GUI_SCALE,
                 GUI_SCALE_MIN,
                 OPTIONS_VALUE_MIN_FIELD_NAMES);
        setField(GameSettings.Options.class,
                 GameSettings.Options.GUI_SCALE,
                 GUI_SCALE_MAX,
                 OPTIONS_VALUE_MAX_FIELD_NAMES);
        setField(GameSettings.Options.class,
                 GameSettings.Options.GUI_SCALE,
                 GUI_SCALE_STEP,
                 OPTIONS_VALUE_STEP_FIELD_NAMES);
    }

    @SneakyThrows
    private static <T> void setField(@NonNull Class<T> clazz,
                                     @NonNull T target,
                                     @NonNull Object value,
                                     @NonNull String... fieldNameAliases) {
        val field = ReflectionHelper.findField(clazz, fieldNameAliases);
        FieldUtils.removeFinalModifier(field);
        FieldUtils.writeField(field, target, value);
    }

    public static String guiScaleSliderLabel() {
        return I18n.format(GameSettings.Options.GUI_SCALE.getEnumString()) + ": " + GUI_SCALE;
    }

    public static void setGuiScale(float scale) {
        GUI_SCALE = scale;
    }

    public static void updateMinecraftResolutionScale() {
        val minecraft = Minecraft.getMinecraft();
        minecraft.gameSettings.guiScale = MathHelper.ceiling_float_int(GUI_SCALE);
        val scaledResolution = newScaledResolution(minecraft);
        minecraft.currentScreen.setWorldAndResolution(minecraft,
                                                      scaledResolution.getScaledWidth(),
                                                      scaledResolution.getScaledHeight());
    }

    private static ScaledResolution newScaledResolution(@NonNull Minecraft minecraft) {
        return new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
    }

    public static float getGuiScale() {
        return GUI_SCALE;
    }
}
