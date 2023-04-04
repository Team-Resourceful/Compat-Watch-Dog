package com.teamresourceful.compatwatchdog.forge;

import com.teamresourceful.compatwatchdog.api.ModInfo;
import net.minecraft.Util;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;

import java.util.HashMap;
import java.util.Map;

public class CompatWatchDogImpl {
    public static void openModsFolder() {
        Util.getPlatform().openFile(FMLPaths.MODSDIR.get().toFile());
    }

    public static Map<String, ModInfo> getMods() {
        return ModList.get().getMods().stream()
                .map(ForgeModInfo::new)
                .collect(HashMap::new, (map, info) -> map.put(info.id(), info), Map::putAll);
    }
}
