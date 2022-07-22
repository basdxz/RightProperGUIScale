package com.myname.mymodid.mixin.plugin;

import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.*;

import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.ITargetedMod.PredicateHelpers.startsWith;

@Getter
@RequiredArgsConstructor
public enum TargetedMod implements ITargetedMod {
    OPTIFINE("OptiFine", false, startsWith("optifine").or(startsWith("gt5u")));

    private final String modName;
    private final boolean loadInDevelopment;
    private final Predicate<String> condition;
}
