package de.sqrt.microBloat.mixin;

import java.util.List;

import de.sqrt.microBloat.OldButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.sqrt.microBloat.Util;
import de.sqrt.microBloat.config.ConfigHandler;
import de.sqrt.microBloat.config.SettingState;
import de.sqrt.microBloat.screens.MicroBloatScreen;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {

	List<ClickableWidget> buttons = Screens.getButtons((Screen) (Object) this);

	protected OptionsScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "init", at = @At("TAIL"))
	protected void init(CallbackInfo ci) {
		if (ConfigHandler.get("difficulty_lock") == SettingState.SPECIAL && this.client.world != null
				&& this.client.isIntegratedServerRunning()) {
			Util.getButton(buttons, "difficulty_lock").visible = false;
			Util.getButton(buttons, "options.difficulty").setWidth(150);
		}

		if (ConfigHandler.get("menu.online") == SettingState.HIDDEN
				&& ConfigHandler.get("online") == SettingState.NORMAL
				&& !(this.client.world != null && this.client.isIntegratedServerRunning())) {
			ClickableWidget allowServerListingButton = client.options.getAllowServerListing()
					.createWidget(client.options, this.width / 2 + 5, this.height / 6 - 12 + 24, 150);
			allowServerListingButton.setY(Util.getButton(buttons, "options.fov").getY());
			allowServerListingButton.setX(this.width / 2 + 5);
			addDrawableChild(allowServerListingButton);
			Util.getButton(buttons, "online").visible = false;
		}

		if (Util.deleteButton(buttons, "online"))
			Util.getButton(buttons, "fov").setWidth(310);

		if (Util.deleteButton(buttons, "options.difficulty")) {
			Util.getButton(buttons, "fov").setWidth(310);
			Util.getButton(buttons, "difficulty_lock").visible = false;
		}

		addDrawableChild(new OldButton(this.width / 2 - 155, this.height / 6 + 145 - 6 + 25, 150, 20,
				Text.translatable("microbloat.settings.title"), button -> {
					client.setScreen(new MicroBloatScreen(this));
				}));

		ClickableWidget doneButton = Util.getButton(buttons, "done");
		doneButton.setX(this.width / 2 + 5);
		doneButton.setY(this.height / 6 + 145 - 6 + 25);
		doneButton.setWidth(150);
	}
}
