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
import de.sqrt.microBloat.config.SettingState;
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
		if (Util.deleteButton(buttons, "menu.online")) {
			Util.getButton(buttons, "options").y = this.height / 4 + 48 + 24 * 2;
			Util.getButton(buttons, "quit").y = this.height / 4 + 48 + 24 * 2;
			Util.getButton(buttons, "access").y = this.height / 4 + 48 + 24 * 2;
			Util.getButton(buttons, "lang").y = this.height / 4 + 48 + 24 * 2;
			this.client.options.getRealmsNotifications().setValue(false);
		}
		if (Util.deleteButton(buttons, "menu.quit")) {
			Util.getButton(buttons, "options").setWidth(200);
		}
		if (ConfigHandler.get("splash") == SettingState.HIDDEN) {
			splashText = null;
		}
		Util.deleteButton(buttons, "button.accessibility");
		Util.deleteButton(buttons, "button.language");
		Util.deleteButton(buttons, "Copyright");
	}

	private static Identifier getEDITION_TITLE_TEXTURE() {
		Identifier edition = new Identifier("textures/gui/title/edition.png");
		if (ConfigHandler.get("edition") == SettingState.HIDDEN)
			edition = new Identifier("microbloat", "textures/gui/title/edition_empty.png");
		if (ConfigHandler.get("edition") == SettingState.SPECIAL)
			edition = new Identifier("microbloat", "textures/gui/title/edition_safe_minecraft.png"); // EASTER EGG
		return edition;
	}
}