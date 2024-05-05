package de.sqrt.microBloat.mixin;

import de.sqrt.microBloat.config.ConfigHandler;
import net.minecraft.client.util.telemetry.TelemetryManager;
import net.minecraft.client.util.telemetry.TelemetrySender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TelemetryManager.class)
public class TelemetryManagerMixin {
    /**
     * @author Trolman
     * @reason no telemetry
     */
    @Inject(method = "computeSender", at = @At("HEAD"), cancellable = true)
    public void disableTelemetry(CallbackInfoReturnable<TelemetrySender> cir) {
        if(ConfigHandler.bget("Telemetry")) {
            cir.setReturnValue(TelemetrySender.NOOP);
        }
    }
 }
