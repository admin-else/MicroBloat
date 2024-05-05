
package de.sqrt.microBloat.mixin;

import net.minecraft.client.gui.CubeMapRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.sqrt.microBloat.config.ConfigHandler;
import de.sqrt.microBloat.config.SettingState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.RotatingCubeMapRenderer;

@Mixin(RotatingCubeMapRenderer.class)
public abstract class RotatingCubeMapRendererMixin {
	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow @Final private CubeMapRenderer cubeMap;

	@Shadow
	private static float wrapOnce(float a, float b) {
		return 0;
	}

	@Shadow private float pitch;

	@Shadow private float yaw;

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void render(float delta, float alpha, CallbackInfo info) {
		if (ConfigHandler.get("background") == SettingState.SPECIAL) {
			float f = (float)((double)delta * (Double)this.client.options.getPanoramaSpeed().getValue());
			this.pitch = wrapOnce(this.pitch + f * 0.1F, 360.0F);
			this.yaw = wrapOnce(this.yaw + f * 0.001F, 6.2831855F);
			this.cubeMap.draw(this.client, 10.0F, -this.pitch, alpha);
			info.cancel();
		} else if (ConfigHandler.get("background") == SettingState.HIDDEN) {
			info.cancel();
		}
	}
}