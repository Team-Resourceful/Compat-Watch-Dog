package com.teamresourceful.compatwatchdog.impl;

import com.teamresourceful.compatwatchdog.api.CompatPredicate;
import com.teamresourceful.compatwatchdog.api.ModInfo;

import java.util.Map;

public class ModIncompatibleWithFix implements CompatPredicate {

    private final String id;
    private final String fix;

    private String name;

    public ModIncompatibleWithFix(String id, String fix) {
        this.id = id;
        this.fix = fix;
    }

    @Override
    public boolean test(Map<String, ModInfo> modInfos) {
        if (modInfos.containsKey(id)) {
            name = modInfos.get(id).name();
            return !modInfos.containsKey(fix);
        }
        return false;
    }

    @Override
    public String getMessage() {
        return name + " is not compatible without " + fix + ".";
    }
}
