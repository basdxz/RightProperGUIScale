package com.myname.mymodid;

import cpw.mods.fml.relauncher.ReflectionHelper;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.settings.GameSettings;
import org.apache.commons.lang3.reflect.FieldUtils;

import static com.myname.mymodid.GUIJiggler.*;

@UtilityClass
public final class GameSettingReflections {
    //TODO: better naming, this is not readable
    private static final String[] ENUM_FLOAT_FIELD_NAMES = new String[]{"enumFloat", "field_74385_A"};
    private static final String[] VALUE_MIN_FIELD_NAMES = new String[]{"valueMin", "field_148271_N"};
    private static final String[] VALUE_MAX_FIELD_NAMES = new String[]{"valueMax", "field_148272_O"};
    private static final String[] VALUE_STEP_FIELD_NAMES = new String[]{"valueStep", "field_148270_M"};

    public static void apply() {
        guiScaleButtonIntoSlider();
    }

    private static void guiScaleButtonIntoSlider() {
        makeGuiScaleSliderStoreFloat();
        setGuiScaleSliderMin();
        setGuiScaleSliderMax();
        setGuiScaleSliderStep();
    }

    private static void makeGuiScaleSliderStoreFloat() {
        setGuiScaleOptionField(true, ENUM_FLOAT_FIELD_NAMES);
    }

    private static void setGuiScaleSliderMin() {
        setGuiScaleOptionField(GUI_SCALE_MIN, VALUE_MIN_FIELD_NAMES);
    }

    private static void setGuiScaleSliderMax() {
        setGuiScaleOptionField(GUI_SCALE_MAX, VALUE_MAX_FIELD_NAMES);
    }

    private static void setGuiScaleSliderStep() {
        setGuiScaleOptionField(GUI_SCALE_STEP, VALUE_STEP_FIELD_NAMES);
    }

    @SneakyThrows
    private static void setGuiScaleOptionField(@NonNull Object value, @NonNull String... fieldNameAliases) {
        setField(GameSettings.Options.class, GameSettings.Options.GUI_SCALE, value, fieldNameAliases);
    }

    @SneakyThrows
    @SuppressWarnings("SameParameterValue")
    private static <T> void setField(@NonNull Class<T> clazz,
                                     @NonNull T target,
                                     @NonNull Object value,
                                     @NonNull String... fieldNameAliases) {
        val field = ReflectionHelper.findField(clazz, fieldNameAliases);
        FieldUtils.removeFinalModifier(field);
        FieldUtils.writeField(field, target, value);
    }
}
