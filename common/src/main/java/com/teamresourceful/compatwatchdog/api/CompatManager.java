package com.teamresourceful.compatwatchdog.api;

import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompatManager {

    private static final Map<String, List<CompatPredicate>> PREDICATES = new HashMap<>();

    public static IncompatibilityBuilder create(String modId) {
        return new IncompatibilityBuilder(modId, PREDICATES);
    }

    @ApiStatus.Internal
    public static List<Pair<String, List<CompatPredicate>>> getSuccessfulPredicates(Map<String, ModInfo> mods) {
        List<Pair<String, List<CompatPredicate>>> failedMods = new ArrayList<>();
        for (var entry : PREDICATES.entrySet()) {
            List<CompatPredicate> predicates = entry.getValue().stream()
                    .filter(predicate -> predicate.test(mods))
                    .toList();
            if (!predicates.isEmpty()) {
                final var info = mods.get(entry.getKey());
                failedMods.add(Pair.of(info == null ? entry.getKey() : info.name(), predicates));
            }
        }
        return failedMods;
    }
}
