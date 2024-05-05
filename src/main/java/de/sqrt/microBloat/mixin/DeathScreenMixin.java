package de.sqrt.microBloat.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.sqrt.microBloat.config.ConfigHandler;
import de.sqrt.microBloat.config.SettingState;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.text.Text;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {

	@Shadow @Final @Mutable
	private Text scoreText;
	
	@Inject(method = "init", at = @At("TAIL"))
	protected void init(CallbackInfo ci) {
		if(ConfigHandler.get("death_score")==SettingState.HIDDEN)scoreText = Text.literal("");
	}
}
