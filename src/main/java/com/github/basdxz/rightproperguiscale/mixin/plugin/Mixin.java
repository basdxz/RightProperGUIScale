package com.github.basdxz.rightproperguiscale.mixin.plugin;

import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import com.github.basdxz.rightproperguiscale.RightProperGUIScale;
import lombok.*;

import java.util.List;
import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.condition;

@Getter
@RequiredArgsConstructor
public enum Mixin implements IMixin {
    GameSettingMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled), "minecraft.GameSettingMixin"),
    ScaledResolutionMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled), "minecraft.ScaledResolutionMixin"),
    GuiVideoSettingsMixin(Side.CLIENT, condition(RightProperGUIScale::isEnabled), "minecraft.GuiVideoSettingsMixin");

    private final Side side;
    private final Predicate<List<ITargetedMod>> filter;
    private final String mixin;
}
