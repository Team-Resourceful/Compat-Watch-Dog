package com.teamresourceful.compatwatchdog.impl;

import com.teamresourceful.compatwatchdog.api.CompatPredicate;
import com.teamresourceful.compatwatchdog.api.ModInfo;

import java.util.Map;
import java.util.function.Predicate;

public record CustomIncompatible(String message, Predicate<Map<String, ModInfo>> predicate) implements CompatPredicate {

    @Override
    public boolean test(Map<String, ModInfo> modInfos) {
        return predicate.test(modInfos);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
