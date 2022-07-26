package com.github.basdxz.rightproperguiscale.reflection;

import com.github.basdxz.rightproperguiscale.RightProperGUIScale;
import com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig;
import cpw.mods.fml.relauncher.ReflectionHelper;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.settings.GameSettings;
import org.apache.commons.lang3.reflect.FieldUtils;

import static com.github.basdxz.rightproperguiscale.config.RightProperGUIScaleConfig.*;

/**
 * Reflects into {@link GameSettings.Options#GUI_SCALE}, turning the GUI Scale button into a slider and
 * updating the min/max/step of the settings with respect to {@link RightProperGUIScaleConfig}.
 * <p>
 * TODO: On FalsePatternLib 0.10.* release, replace field names with UniversalFields
 */
@UtilityClass
public final class GameSettingReflections {
    private static final String[] ENUM_FLOAT_FIELD_NAMES = new String[]{"enumFloat", "field_74385_A"};
    private static final String[] VALUE_MIN_FIELD_NAMES = new String[]{"valueMin", "field_148271_N"};
    private static final String[] VALUE_MAX_FIELD_NAMES = new String[]{"valueMax", "field_148272_O"};
    private static final String[] VALUE_STEP_FIELD_NAMES = new String[]{"valueStep", "field_148270_M"};
    private static final String SUCCESS_INFO = "Successfully Applied changes to GUI_SCALE enum!";
    private static final String NEW_SETTINGS_INFO = "New Settings: [MIN:{}] [MAX:{}] [STEP:{}]";
    /**
     * Flag state which is set to true after the first call to
     * {@link GameSettingReflections#guiScaleButtonIntoSlider()}.
     */
    private static boolean GUI_SETTING_IS_SLIDER = false;

    /**
     * Applies the Game Setting Reflections.
     */
    public static void apply() {
        guiScaleButtonIntoSlider();
        setNewGUIScaleSliderSettings();
        logSuccess();
        logNewSettings();
    }

    /**
     * Converts {@link GameSettings.Options#GUI_SCALE} from a button into a slider the first time it is invoked,
     * does nothing on subsequent invocations.
     */
    private static void guiScaleButtonIntoSlider() {
        if (GUI_SETTING_IS_SLIDER)
            return;
        makeGuiScaleSliderStoreFloat();
        GUI_SETTING_IS_SLIDER = true;
    }

    /**
     * Sets the values in {@link GameSettings.Options#GUI_SCALE}
     * to values configured in {@link RightProperGUIScaleConfig}.
     */
    private static void setNewGUIScaleSliderSettings() {
        setGuiScaleSliderMin();
        setGuiScaleSliderMax();
        setGuiScaleSliderStep();
    }

    /**
     * Sets the field {@link GameSettingReflections#ENUM_FLOAT_FIELD_NAMES}
     * in {@link GameSettings.Options#GUI_SCALE} to {@link true}.
     */
    private static void makeGuiScaleSliderStoreFloat() {
        setGuiScaleOptionField(true, ENUM_FLOAT_FIELD_NAMES);
    }

    /**
     * Sets the field {@link GameSettingReflections#VALUE_MIN_FIELD_NAMES}
     * in {@link GameSettings.Options#GUI_SCALE} to {@link RightProperGUIScaleConfig#GUI_SCALE_MIN}.
     */
    private static void setGuiScaleSliderMin() {
        setGuiScaleOptionField(GUI_SCALE_MIN, VALUE_MIN_FIELD_NAMES);
    }

    /**
     * Sets the field {@link GameSettingReflections#VALUE_MAX_FIELD_NAMES}
     * in {@link GameSettings.Options#GUI_SCALE} to {@link RightProperGUIScaleConfig#GUI_SCALE_MAX}.
     */
    private static void setGuiScaleSliderMax() {
        setGuiScaleOptionField(GUI_SCALE_MAX, VALUE_MAX_FIELD_NAMES);
    }

    /**
     * Sets the field {@link GameSettingReflections#VALUE_STEP_FIELD_NAMES}
     * in {@link GameSettings.Options#GUI_SCALE} to {@link RightProperGUIScaleConfig#GUI_SCALE_STEP}.
     */
    private static void setGuiScaleSliderStep() {
        setGuiScaleOptionField(GUI_SCALE_STEP, VALUE_STEP_FIELD_NAMES);
    }

    /**
     * Sets the first found aliased field in {@link GameSettings.Options#GUI_SCALE} to provided value.
     *
     * @param value            new value
     * @param fieldNameAliases field name aliases
     */
    @SneakyThrows
    private static void setGuiScaleOptionField(@NonNull Object value, @NonNull String... fieldNameAliases) {
        setField(GameSettings.Options.class, GameSettings.Options.GUI_SCALE, value, fieldNameAliases);
    }

    /**
     * Sets the first found field in target object to provided value.
     * <p>
     * This also bypasses access protection such as private and final modifiers.
     *
     * @param clazz            target class
     * @param target           target object
     * @param value            new value
     * @param fieldNameAliases field name aliases
     * @param <T>              type of the object
     */
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

    /**
     * Logs reflection success.
     */
    private static void logSuccess() {
        RightProperGUIScale.logger.info(SUCCESS_INFO);
    }

    /**
     * Logs new settings.
     */
    private static void logNewSettings() {
        RightProperGUIScale.logger.info(NEW_SETTINGS_INFO, GUI_SCALE_MIN, GUI_SCALE_MAX, GUI_SCALE_STEP);
    }
}
