
package de.sqrt.microBloat.mixin;

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
	@Shadow
	private float time;

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void render(float delta, float alpha, CallbackInfo info) {
		if (ConfigHandler.get("background") == SettingState.SPECIAL) {
			this.client.currentScreen.renderBackgroundTexture(0);
			info.cancel();
		} else if (ConfigHandler.get("background") == SettingState.HIDDEN) {
			time -= delta;
		}
	}
}