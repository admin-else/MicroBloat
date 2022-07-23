package de.sqrt.microBloat.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import de.sqrt.microBloat.config.ConfigHandler;

@Pseudo
@Mixin(targets = "net.minecraft.client.util.telemetry.TelemetrySender")
@Environment(EnvType.CLIENT)
public class TelemetrySenderMixin {
    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"))
    private boolean disableTelemetrySession() {//I don't know if this works i just hope this works please open an issues if this does not work (: .
    										   //Also credit to kb-1000 i copied his code from https://github.com/kb-1000/no-telemetry thanks.
    	if(ConfigHandler.get("Telemetry")==1)return true;
        return false;
    }
}