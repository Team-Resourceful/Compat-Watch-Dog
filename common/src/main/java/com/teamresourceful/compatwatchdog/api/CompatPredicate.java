package com.teamresourceful.compatwatchdog.api;

import java.util.Map;
import java.util.function.Predicate;

public interface CompatPredicate extends Predicate<Map<String, ModInfo>> {

    String getMessage();
}
