package com.github.basdxz.rightproperguiscale.mixin.plugin;

import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import com.github.basdxz.rightproperguiscale.RightProperGUIScale;
import lombok.*;

import java.util.List;
import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.*;
import static com.github.basdxz.rightproperguiscale.mixin.plugin.TargetedMod.OPTIFINE;

@Getter
@RequiredArgsConstructor
public enum Mixin implements IMixin {
    GameSettingMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled), "minecraft.GameSettingMixin"),
    ScaledResolutionMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled), "minecraft.ScaledResolutionMixin"),
    GuiEnchantmentMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled), "minecraft.GuiEnchantmentMixin"),
    LoadingScreenRendererMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled), "minecraft.LoadingScreenRendererMixin"),
    GuiNewChatMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled), "minecraft.GuiNewChatMixin"),
    MinecraftMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled), "minecraft.MinecraftMixin"),

    GuiVideoSettingsMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled).and(avoid(OPTIFINE)), "minecraft.GuiVideoSettingsMixin"),

    GuiScreenMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled).and(require(OPTIFINE)), "optifine.GuiScreenMixin"),
    TooltipProviderOptionsMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled).and(require(OPTIFINE)), "optifine.TooltipProviderOptionsMixin"),

    ;
    private final Side side;
    private final Predicate<List<ITargetedMod>> filter;
    private final String mixin;
}
