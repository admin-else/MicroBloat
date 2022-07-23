package de.sqrt.microBloat.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.sqrt.microBloat.Util;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

@Mixin(ControlsOptionsScreen.class)
public class ControlsOptionsScreenMixin extends GameOptionsScreen{
	
	List<ClickableWidget> buttons = Screens.getButtons((Screen) (Object) this);

	public ControlsOptionsScreenMixin(Screen parent, GameOptions gameOptions) {
		super(parent, gameOptions, Text.translatable("controls.title"));
	}
	
	@Inject(method = "init",at = @At("TAIL"))
	protected void init(CallbackInfo ci) {
		if(Util.deletButton(buttons, "mouse_settings")) {
			ClickableWidget mouse_settingsButton = Util.getButton(buttons, "mouse_settings");
			ClickableWidget sensitivityButton = client.options.getMouseSensitivity().createButton(gameOptions, height, height, width);
			sensitivityButton.x = mouse_settingsButton.x;
			sensitivityButton.y = mouse_settingsButton.y;
			sensitivityButton.setWidth(150);
			addDrawableChild(sensitivityButton);
		}
		if(Util.deletButton(buttons, "autoJump")) {
			Util.getButton(buttons, "done").y = Util.getButton(buttons, "autoJump").y;
			Util.getButton(buttons, "done").x = Util.getButton(buttons, "autoJump").x;
			Util.getButton(buttons, "done").setWidth(310);
		}
		
	}
	
}
