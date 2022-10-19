package de.sqrt.microBloat.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.sqrt.microBloat.Util;
import de.sqrt.microBloat.config.ConfigHandler;
import de.sqrt.microBloat.config.SettingState;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.SimpleOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionsScreenMixin extends SimpleOptionsScreen {

	private static SimpleOption<?>[] getOptions(GameOptions gameOptions) {
		if (ConfigHandler.get("autoJump") == SettingState.NORMAL)
			return new SimpleOption[] { gameOptions.getShowSubtitles(), gameOptions.getTextBackgroundOpacity(),
					gameOptions.getBackgroundForChatOnly(), gameOptions.getChatOpacity(),
					gameOptions.getChatLineSpacing(), gameOptions.getChatDelay(), gameOptions.getAutoJump(),
					gameOptions.getSneakToggled(), gameOptions.getSprintToggled(),
					gameOptions.getDistortionEffectScale(), gameOptions.getFovEffectScale(),
					gameOptions.getMonochromeLogo(), gameOptions.getHideLightningFlashes(),
					gameOptions.getDarknessEffectScale() };
		return new SimpleOption[] { gameOptions.getShowSubtitles(), gameOptions.getTextBackgroundOpacity(),
				gameOptions.getBackgroundForChatOnly(), gameOptions.getChatOpacity(), gameOptions.getChatLineSpacing(),
				gameOptions.getChatDelay(), gameOptions.getSneakToggled(), gameOptions.getSprintToggled(),
				gameOptions.getDistortionEffectScale(), gameOptions.getFovEffectScale(),
				gameOptions.getMonochromeLogo(), gameOptions.getHideLightningFlashes(),
				gameOptions.getDarknessEffectScale() };
	}

	public AccessibilityOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title,
			SimpleOption<?>[] options) {
		super(parent, gameOptions, Text.translatable("options.accessibility.title"), getOptions(gameOptions));
	}

	List<ClickableWidget> buttons = Screens.getButtons((Screen) (Object) this);

	@Inject(method = "initFooter", at = @At("TAIL"))
	protected void initFooter(CallbackInfo ci) {
		if (ConfigHandler.get("accessibility_guide") == SettingState.HIDDEN) {
			Util.getButton(buttons, "accessibility.link").visible = false;
			Util.getButton(buttons, "done").x = this.width / 2 - 100;
			Util.getButton(buttons, "done").setWidth(200);
		}

	}
}
