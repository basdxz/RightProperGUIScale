package com.github.basdxz.rightproperguiscale.mixin.plugin;

import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.*;

import java.util.List;
import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.*;
import static com.github.basdxz.rightproperguiscale.mixin.plugin.TargetedMod.BETTER_LOADING_SCREEN;
import static com.github.basdxz.rightproperguiscale.mixin.plugin.TargetedMod.OPTIFINE;

@Getter
@RequiredArgsConstructor
public enum Mixin implements IMixin {
    GameSettingMixin(Side.CLIENT, always(), "minecraft.GameSettingMixin"),
    ScaledResolutionMixin(Side.CLIENT, always(), "minecraft.ScaledResolutionMixin"),
    GuiEnchantmentMixin(Side.CLIENT, always(), "minecraft.GuiEnchantmentMixin"),
    LoadingScreenRendererMixin(Side.CLIENT, always(), "minecraft.LoadingScreenRendererMixin"),
    GuiNewChatMixin(Side.CLIENT, always(), "minecraft.GuiNewChatMixin"),
    MinecraftMixin(Side.CLIENT, avoid(BETTER_LOADING_SCREEN), "minecraft.MinecraftMixin"),

    GuiVideoSettingsMixin(Side.CLIENT, always().and(avoid(OPTIFINE)), "minecraft.GuiVideoSettingsMixin"),

    GuiScreenMixin(Side.CLIENT, require(OPTIFINE), "optifine.GuiScreenMixin"),
    TooltipProviderOptionsMixin(Side.CLIENT, require(OPTIFINE), "optifine.TooltipProviderOptionsMixin");

    private final Side side;
    private final Predicate<List<ITargetedMod>> filter;
    private final String mixin;
}
