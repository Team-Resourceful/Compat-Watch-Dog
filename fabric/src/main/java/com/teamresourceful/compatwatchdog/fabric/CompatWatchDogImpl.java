package com.teamresourceful.compatwatchdog.fabric;

import com.teamresourceful.compatwatchdog.api.ModInfo;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;

import java.util.HashMap;
import java.util.Map;

public class CompatWatchDogImpl {
    public static void openModsFolder() {
        Util.getPlatform().openFile(FabricLoader.getInstance().getGameDir().resolve("mods").toFile());
    }

    public static Map<String, ModInfo> getMods() {
        return FabricLoader.getInstance().getAllMods().stream()
                .map(FabricModInfo::new)
                .collect(HashMap::new, (map, info) -> map.put(info.id(), info), Map::putAll);
    }
}
