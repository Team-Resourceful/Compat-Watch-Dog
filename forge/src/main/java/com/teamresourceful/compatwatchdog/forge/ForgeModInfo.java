package com.teamresourceful.compatwatchdog.forge;

import com.teamresourceful.compatwatchdog.api.ModInfo;
import net.minecraftforge.forgespi.language.IModInfo;

public record ForgeModInfo(IModInfo info) implements ModInfo {
    @Override
    public String id() {
        return info.getModId();
    }

    @Override
    public String name() {
        return info.getDisplayName();
    }
}
