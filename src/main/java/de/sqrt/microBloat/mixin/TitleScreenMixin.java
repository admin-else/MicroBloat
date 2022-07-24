package de.sqrt.microBloat.mixin;

import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.sqrt.microBloat.Util;
import de.sqrt.microBloat.config.ConfigHandler;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

	protected TitleScreenMixin(Text title) {
		super(title);
	}

	List<ClickableWidget> buttons = Screens.getButtons((Screen) (Object) this);

	@Shadow
	private String splashText;
	@Shadow
	@Final
	@Mutable
	private static Identifier EDITION_TITLE_TEXTURE = getEDITION_TITLE_TEXTURE();

	@Inject(method = "init", at = @At("TAIL"))
	protected void init(CallbackInfo info) {
		if (Util.deletButton(buttons, "menu.online")) {
			Util.getButton(buttons, "options").y = this.height / 4 + 48 + 24 * 2;
			Util.getButton(buttons, "quit").y = this.height / 4 + 48 + 24 * 2;
			Util.getButton(buttons, "access").y = this.height / 4 + 48 + 24 * 2;
			Util.getButton(buttons, "lang").y = this.height / 4 + 48 + 24 * 2;
			this.client.options.getRealmsNotifications().setValue(false);
		}
		if (Util.deletButton(buttons, "menu.quit")) {
			Util.getButton(buttons, "options").setWidth(200);
		}
		if (ConfigHandler.get("splash") == 1) {
			splashText = null;
		}
		Util.deletButton(buttons, "button.accessibility");
		Util.deletButton(buttons, "button.language");
		Util.deletButton(buttons, "Copyright");
	}
	
	private static Identifier getEDITION_TITLE_TEXTURE() {
		Identifier edition = new Identifier("textures/gui/title/edition.png");
		if(ConfigHandler.get("edition") == 1)edition = new Identifier("microbloat", "textures/gui/title/edition_empty.png");
		if(ConfigHandler.get("edition") == 2)edition = new Identifier("microbloat", "textures/gui/title/edition_safe_minecraft.png"); //EASTER EGG
		return edition;
	}
}