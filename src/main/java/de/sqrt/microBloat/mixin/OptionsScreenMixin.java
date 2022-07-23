package de.sqrt.microBloat.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.sqrt.microBloat.MicroBloatScreen;
import de.sqrt.microBloat.Util;
import de.sqrt.microBloat.config.ConfigHandler;
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
		if (ConfigHandler.get("difficulty_lock") == 1 && this.client.world != null
				&& this.client.isIntegratedServerRunning()) {
			Util.getButton(buttons, "difficulty_lock").visible = false;
			Util.getButton(buttons, "options.difficulty").setWidth(150);
		}

		if (ConfigHandler.get("menu.online") == 1 && ConfigHandler.get("options.online") == 0
				&& !(this.client.world != null && this.client.isIntegratedServerRunning())) {
			ClickableWidget allowServerListingButton = client.options.getAllowServerListing().createButton(client.options,this.width / 2 + 5, this.height / 6 - 12 + 24,150);
			allowServerListingButton.y = Util.getButton(buttons, "options.fov").y;//this.height / 6 - 12 + 24;
			allowServerListingButton.x = this.width / 2 + 5;
			addDrawableChild(allowServerListingButton);
			Util.getButton(buttons, "options.online").visible = false;
		}
		
		if(Util.deletButton(buttons, "options.online"))Util.getButton(buttons, "fov").setWidth(310);
		if(Util.deletButton(buttons, "options.difficulty")) {
			Util.getButton(buttons, "fov").setWidth(310);
			Util.getButton(buttons, "difficulty_lock").visible = false;
		}
		
		addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 148 - 6, 150, 20, Text.translatable("microbloat.settings.title"), button -> {
			client.setScreen(new MicroBloatScreen(this));
		}));
	}
}
