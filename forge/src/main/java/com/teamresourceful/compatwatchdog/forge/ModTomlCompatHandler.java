package com.teamresourceful.compatwatchdog.forge;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.mojang.logging.LogUtils;
import com.teamresourceful.compatwatchdog.api.CompatManager;
import com.teamresourceful.compatwatchdog.api.IncompatibilityBuilder;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

public class ModTomlCompatHandler {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String KEY = "compatwatchdog:compats";

    public static void load() {
        for (IModInfo mod : ModList.get().getMods()) {
            if (mod.getModProperties().containsKey(KEY)) {
                try {
                    initMod(mod, mod.getModProperties());
                }catch (Exception e) {
                    LOGGER.error("Compat Watch Dog failed to load resource pack for mod: " + mod.getDisplayName());
                    e.printStackTrace();
                }
            }
        }
    }

    private static void initMod(IModInfo mod, Map<String, Object> metadata) {
        for (Object pack : (List<?>) metadata.get(KEY)) {
            loadIncompatibilities(mod, pack);
        }
    }

    private static void loadIncompatibilities(IModInfo mod, Object value) {
        IncompatibilityBuilder builder = CompatManager.create(mod.getModId());
        if (value instanceof UnmodifiableConfig config) {
            Map<String, Object> map = config.valueMap();
            String typeId = map.get("type") instanceof String str ? str : "mod";
            switch (typeId) {
                case "mod" -> builder.mod(getOrThrow(map, "id"));
                case "modWithFix" -> builder.modWithFix(getOrThrow(map, "id"), getOrThrow(map, "fixedModId"));
                case "class" -> builder.hasClass(getOrThrow(map, "name"), getOrThrow(map, "class"));
                default -> throw new IllegalStateException("Unexpected value: " + typeId);
            }
        } else if (value instanceof String id) {
            builder.mod(id);
        } else if (value instanceof List<?> list) {
            list.forEach(entry -> loadIncompatibilities(mod, entry));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T getOrThrow(Map<?, ?> map, String id) {
        if (!map.containsKey(id)) throw new IllegalStateException("Missing key: " + id);
        return (T) map.get(id);
    }
}
