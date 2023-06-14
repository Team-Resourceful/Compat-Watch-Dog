package com.teamresourceful.compatwatchdog.fabric;

import com.mojang.logging.LogUtils;
import com.teamresourceful.compatwatchdog.api.CompatManager;
import com.teamresourceful.compatwatchdog.api.IncompatibilityBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.CustomValue;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.slf4j.Logger;

public class ModJsonCompatHandler {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String KEY = "compatwatchdog:compats";

    public static void load() {
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            ModMetadata metadata = mod.getMetadata();
            if (metadata.containsCustomValue(KEY)) {
                try {
                    initMod(mod, metadata);
                }catch (Exception e) {
                    LOGGER.error("Compat Watch Dog failed to load resource pack for mod: " + metadata.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    private static void initMod(ModContainer mod, ModMetadata metadata) {
        var packs = metadata.getCustomValue(KEY).getAsArray();
        for (CustomValue pack : packs) {
            loadIncompatibilities(mod, pack);
        }
    }

    private static void loadIncompatibilities(ModContainer container, CustomValue value) {
        IncompatibilityBuilder builder = CompatManager.create(container.getMetadata().getId());
        switch (value.getType()) {
            case ARRAY -> {
                for (CustomValue entry : value.getAsArray()) {
                    loadIncompatibilities(container, entry);
                }
            }
            case OBJECT -> {
                CustomValue.CvObject object = value.getAsObject();
                CustomValue type = object.get("type");
                String typeId = type != null && type.getType() == CustomValue.CvType.STRING ? type.getAsString() : "mod";
                switch (typeId) {
                    case "mod" -> builder.mod(object.get("id").getAsString());
                    case "modWithFix" -> builder.modWithFix(object.get("id").getAsString(), object.get("fixedModId").getAsString());
                    case "class" -> builder.hasClass(object.get("name").getAsString(), object.get("class").getAsString());
                    default -> throw new IllegalStateException("Unexpected value: " + typeId);
                }
            }
            case STRING -> builder.mod(value.getAsString());
        }
    }
}
