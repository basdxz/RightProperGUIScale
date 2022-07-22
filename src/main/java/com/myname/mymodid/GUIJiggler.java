package com.myname.mymodid;

import cpw.mods.fml.relauncher.ReflectionHelper;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.settings.GameSettings;
import org.apache.commons.lang3.reflect.FieldUtils;

@UtilityClass
public final class GUIJiggler {
    private static final String[] OPTIONS_ENUM_FLOAT_BOOLEAN_FIELD_NAMES = new String[]{"enumFloat", "bbm"};

    public static void init() {
        guiScaleButtonToSlider();
    }

    private static void guiScaleButtonToSlider() {
        setField(GameSettings.Options.class,
                 GameSettings.Options.GUI_SCALE,
                 true,
                 OPTIONS_ENUM_FLOAT_BOOLEAN_FIELD_NAMES);
    }

    @SneakyThrows
    private static <T> void setField(@NonNull Class<T> clazz,
                                     @NonNull T target,
                                     @NonNull Object value,
                                     @NonNull String... fieldNameAliases) {
        val field = ReflectionHelper.findField(clazz, fieldNameAliases);
        FieldUtils.removeFinalModifier(field);
        field.set(target, value);
    }
}
