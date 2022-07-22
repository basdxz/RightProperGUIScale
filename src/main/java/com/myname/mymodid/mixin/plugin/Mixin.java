package com.myname.mymodid.mixin.plugin;

import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.*;

import java.util.List;
import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.always;

@Getter
@RequiredArgsConstructor
public enum Mixin implements IMixin {
    GameSettingMixin(Side.CLIENT, always(), "minecraft.GameSettingMixin"),
    ScaledResolutionMixin(Side.CLIENT, always(), "minecraft.ScaledResolutionMixin"),
    GuiVideoSettingsMixin(Side.CLIENT, always(), "minecraft.GuiVideoSettingsMixin");

    private final Side side;
    private final Predicate<List<ITargetedMod>> filter;
    private final String mixin;
}
