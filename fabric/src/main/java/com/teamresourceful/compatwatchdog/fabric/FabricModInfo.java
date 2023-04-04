package com.teamresourceful.compatwatchdog.fabric;

import com.teamresourceful.compatwatchdog.api.ModInfo;
import net.fabricmc.loader.api.ModContainer;

public record FabricModInfo(ModContainer container) implements ModInfo {
    @Override
    public String id() {
        return container.getMetadata().getId();
    }

    @Override
    public String name() {
        return container.getMetadata().getName();
    }
}
