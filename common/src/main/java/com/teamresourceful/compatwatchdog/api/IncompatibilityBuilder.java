package com.teamresourceful.compatwatchdog.api;

import com.teamresourceful.compatwatchdog.impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class IncompatibilityBuilder {

    private final String modId;
    private final Map<String, List<CompatPredicate>> map;

    IncompatibilityBuilder(String modId, Map<String, List<CompatPredicate>> map) {
        this.modId = modId;
        this.map = map;
    }

    private IncompatibilityBuilder add(CompatPredicate predicate) {
        map.computeIfAbsent(modId, s -> new ArrayList<>()).add(predicate);
        return this;
    }

    public IncompatibilityBuilder predicate(String name, Predicate<Map<String, ModInfo>> predicate) {
        add(new PredicateIncompatible(name, predicate));
        return this;
    }


    public IncompatibilityBuilder message(String message, Predicate<Map<String, ModInfo>> predicate) {
        add(new CustomIncompatible(message, predicate));
        return this;
    }

    public IncompatibilityBuilder mod(String id) {
        add(new ModIncompatible(id));
        return this;
    }

    public IncompatibilityBuilder modWithFix(String modidd, String fixedModId) {
        add(new ModIncompatibleWithFix(modidd, fixedModId));
        return this;
    }

    public IncompatibilityBuilder hasClass(String name, String clazz) {
        add(new ClassIncompatible(name, clazz));
        return this;
    }
}
