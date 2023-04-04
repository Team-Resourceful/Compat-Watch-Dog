package com.teamresourceful.compatwatchdog;

import com.mojang.datafixers.util.Pair;
import com.teamresourceful.compatwatchdog.api.CompatManager;
import com.teamresourceful.compatwatchdog.api.CompatPredicate;
import com.teamresourceful.compatwatchdog.api.ModInfo;
import com.teamresourceful.compatwatchdog.client.SplitLineEntry;
import com.teamresourceful.compatwatchdog.client.IncompatabilityScreen;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CompatWatchDog {

    public static void init() {
        final var badMods = CompatManager.getSuccessfulPredicates(getMods());
        if (badMods.isEmpty()) return;
        final Stream<Component> text = badMods.stream().map(CompatWatchDog::createMessage).map(Component::literal);
        Minecraft.getInstance()
                .setScreen(new IncompatabilityScreen(
                        Component.literal("Incompatible Mods"),
                        SplitLineEntry.splitToWidth(
                                Minecraft.getInstance().font, text, 320).toList()
                ));
    }

    @ExpectPlatform
    public static void openModsFolder() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Map<String, ModInfo> getMods() {
        throw new AssertionError();
    }

    private static String createMessage(Pair<String, List<CompatPredicate>> pair) {
        final var builder = new StringBuilder();
        builder.append(pair.getFirst()).append(":\n");
        pair.getSecond().forEach(predicate -> builder.append(predicate.getMessage()).append("\n"));
        return builder.substring(0, builder.length() - 1);
    }
}