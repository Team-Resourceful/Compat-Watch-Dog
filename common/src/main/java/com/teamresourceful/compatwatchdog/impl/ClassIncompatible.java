package com.teamresourceful.compatwatchdog.impl;

import com.teamresourceful.compatwatchdog.api.CompatPredicate;
import com.teamresourceful.compatwatchdog.api.ModInfo;

import java.util.Map;

public record ClassIncompatible(String name, String className) implements CompatPredicate {

    @Override
    public boolean test(Map<String, ModInfo> modInfos) {
        try {
            Class.forName(className);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getMessage() {
        return name + " is not compatible with this mod.";
    }
}
