package com.teamresourceful.compatwatchdog.mixins;

import com.teamresourceful.compatwatchdog.CompatWatchDog;
import com.teamresourceful.compatwatchdog.fabric.ModJsonCompatHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void onChangeScreen(CallbackInfo ci) {
        ModJsonCompatHandler.load();
        CompatWatchDog.init();
    }
}
