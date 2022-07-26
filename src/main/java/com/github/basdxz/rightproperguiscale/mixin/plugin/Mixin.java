package com.github.basdxz.rightproperguiscale.mixin.plugin;

import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import com.github.basdxz.rightproperguiscale.RightProperGUIScale;
import lombok.*;

import java.util.List;
import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.*;
import static com.github.basdxz.rightproperguiscale.mixin.plugin.TargetedMod.BETTER_LOADING_SCREEN;
import static com.github.basdxz.rightproperguiscale.mixin.plugin.TargetedMod.OPTIFINE;


/**
 * Mixin declaration enum which is loaded before the Forge injection point: {@link RightProperGUIScale}.
 * <p>
 * Sponge Mixins handles the loading of the mixins, and the location is provided to it by the buildscript.
 */
@Getter
@RequiredArgsConstructor
public enum Mixin implements IMixin {
    /**
     * Always required Mixins
     */
    GameSettingMixin(Side.CLIENT, always(), "minecraft.GameSettingMixin"),
    ScaledResolutionMixin(Side.CLIENT, always(), "minecraft.ScaledResolutionMixin"),
    GuiEnchantmentMixin(Side.CLIENT, always(), "minecraft.GuiEnchantmentMixin"),
    LoadingScreenRendererMixin(Side.CLIENT, always(), "minecraft.LoadingScreenRendererMixin"),
    GuiNewChatMixin(Side.CLIENT, always(), "minecraft.GuiNewChatMixin"),
    /**
     * Better Loading Screen exclusive Mixins
     */
    MinecraftMixin(Side.CLIENT, avoid(BETTER_LOADING_SCREEN), "minecraft.MinecraftMixin"),
    /**
     * OptiFine exclusive Mixins
     */
    GuiVideoSettingsMixin(Side.CLIENT, always().and(avoid(OPTIFINE)), "minecraft.GuiVideoSettingsMixin"),
    /**
     * OptiFine specific Mixins.
     */
    GuiScreenMixin(Side.CLIENT, require(OPTIFINE), "optifine.GuiScreenMixin"),
    TooltipProviderOptionsMixin(Side.CLIENT, require(OPTIFINE), "optifine.TooltipProviderOptionsMixin");

    private final Side side;
    private final Predicate<List<ITargetedMod>> filter;
    private final String mixin;
}
