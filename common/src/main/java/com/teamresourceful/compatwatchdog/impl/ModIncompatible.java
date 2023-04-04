package com.teamresourceful.compatwatchdog.impl;

import com.teamresourceful.compatwatchdog.api.CompatPredicate;
import com.teamresourceful.compatwatchdog.api.ModInfo;

import java.util.Map;

public class ModIncompatible implements CompatPredicate {

    private final String modId;
    private String name;

    public ModIncompatible(String modId) {
        this.modId = modId;
    }

    @Override
    public boolean test(Map<String, ModInfo> modInfos) {
        if (modInfos.containsKey(modId)) {
            name = modInfos.get(modId).name();
            return true;
        }
        return false;
    }

    @Override
    public String getMessage() {
        return name + " is not compatible with this mod.";
    }
}
