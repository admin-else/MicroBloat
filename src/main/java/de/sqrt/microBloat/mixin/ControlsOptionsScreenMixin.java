package de.sqrt.microBloat.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.sqrt.microBloat.Util;
import de.sqrt.microBloat.config.ConfigHandler;
import de.sqrt.microBloat.config.SettingState;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

@Mixin(ControlsOptionsScreen.class)
public class ControlsOptionsScreenMixin extends GameOptionsScreen{
	
	@Unique
	List<ClickableWidget> buttons = Screens.getButtons((Screen) (Object) this);

	public ControlsOptionsScreenMixin(Screen parent, GameOptions gameOptions) {
		super(parent, gameOptions, Text.translatable("controls.title"));
	}
	
	@Inject(method = "init",at = @At("TAIL"))
	protected void init(CallbackInfo ci) {
		
		ClickableWidget doneButton = Util.getButton(buttons, "done");
		ClickableWidget autoJumpButton = Util.getButton(buttons, "autoJump");
		doneButton.setY(autoJumpButton.getY());
		doneButton.setX((this.width / 2 - 155)+160);
		doneButton.setWidth(150);
		
		if(ConfigHandler.get("mouse_settings")==SettingState.SPECIAL) {
			ClickableWidget mouse_settingsButton = Util.getButton(buttons, "mouse_settings");
			ClickableWidget sensitivityButton = client.options.getMouseSensitivity().createWidget(gameOptions, height, height, width);
			sensitivityButton.setX(mouse_settingsButton.getX());
			sensitivityButton.setY(mouse_settingsButton.getY());
			sensitivityButton.setWidth(150);
			addDrawableChild(sensitivityButton);
		}
		 
		if(ConfigHandler.get("autoJump")!=SettingState.NORMAL) {
			autoJumpButton.visible = false;
			doneButton.setX(autoJumpButton.getX());
			doneButton.setY(autoJumpButton.getY());
			doneButton.setWidth(310);
		}
		
		if(Util.deleteButton(buttons, "mouse_settings")){
			ClickableWidget keysButton = Util.getButton(buttons, "controls.keybinds");
			keysButton.setX(this.width / 2 - 155);
			keysButton.setWidth(310);
		}
	}
	
}
