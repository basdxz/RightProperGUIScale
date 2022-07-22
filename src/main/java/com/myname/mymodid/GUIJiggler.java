package com.myname.mymodid;

import cpw.mods.fml.relauncher.ReflectionHelper;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.settings.GameSettings;
import org.apache.commons.lang3.reflect.FieldUtils;

@UtilityClass
public final class GUIJiggler {
    //TODO: better naming, this is not readable
    private static final String[] OPTIONS_ENUM_FLOAT_FIELD_NAMES = new String[]{"enumFloat", "field_74385_A"};
    private static final boolean OPTIONS_ENUM_FLOAT_FIELD_GUI_SCALE_DEFAULT = false;
    private static final String[] OPTIONS_VALUE_MIN_FIELD_NAMES = new String[]{"valueMin", "field_148271_N"};
    private static final float OPTIONS_ENUM_FLOAT_VALUE_STEP_GUI_SCALE_DEFAULT = 0F;
    private static final String[] OPTIONS_VALUE_MAX_FIELD_NAMES = new String[]{"valueMax", "field_148272_O"};
    private static final float OPTIONS_ENUM_FLOAT_VALUE_MIN_GUI_SCALE_DEFAULT = 0F;
    private static final String[] OPTIONS_VALUE_STEP_FIELD_NAMES = new String[]{"valueStep", "field_148270_M"};
    private static final float OPTIONS_ENUM_FLOAT_VALUE_MAX_GUI_SCALE_DEFAULT = 0F;


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
                 0F,
                 OPTIONS_VALUE_MIN_FIELD_NAMES);
        setField(GameSettings.Options.class,
                 GameSettings.Options.GUI_SCALE,
                 10F,
                 OPTIONS_VALUE_MAX_FIELD_NAMES);
        setField(GameSettings.Options.class,
                 GameSettings.Options.GUI_SCALE,
                 1F,
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
}
