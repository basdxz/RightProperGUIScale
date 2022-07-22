package com.myname.mymodid.mixin.mixins.client.minecraft;

import com.myname.mymodid.GUIJiggler;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.myname.mymodid.GUIJiggler.*;

@Mixin(GameSettings.class)
public abstract class GameSettingMixin {
    @Shadow
    public int guiScale;

    @Inject(method = {"<init>()V",
                      "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V"},
            at = @At(value = "RETURN"),
            require = 1)
    private void guiScaleLabel(CallbackInfo ci) {
        guiScale = guiScaleAsInt();
    }

    @Inject(method = "getKeyBinding(Lnet/minecraft/client/settings/GameSettings$Options;)Ljava/lang/String;",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void guiScaleLabel(GameSettings.Options option, CallbackInfoReturnable<String> cir) {
        if (isScaleOption(option)) {
            cir.setReturnValue(guiScaleSliderLabel());
            cir.cancel();
        }
    }

    @Inject(method = "setOptionFloatValue(Lnet/minecraft/client/settings/GameSettings$Options;F)V",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void setGuiScaleValue(GameSettings.Options option, float value, CallbackInfo ci) {
        if (isScaleOption(option)) {
            GUIJiggler.setGuiScale(value);
            ci.cancel();
        }
    }

    @Inject(method = "getOptionFloatValue(Lnet/minecraft/client/settings/GameSettings$Options;)F",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void getGuiScaleValue(GameSettings.Options option, CallbackInfoReturnable<Float> cir) {
        if (isScaleOption(option)) {
            cir.setReturnValue(GUIJiggler.guiScaleAsFloat());
            cir.cancel();
        }
    }

    private boolean isScaleOption(GameSettings.Options option) {
        return GameSettings.Options.GUI_SCALE == option;
    }

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
    private int loadGuiScale(String guiScaleString) {
        float guiScale = Float.parseFloat(guiScaleString);
        GUIJiggler.setGuiScale(guiScale);
        return MathHelper.ceiling_float_int(guiScale);
    }

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
        return instance.append(guiScaleAsFloat());
    }
}
