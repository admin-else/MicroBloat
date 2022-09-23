package de.sqrt.microBloat;

import java.util.List;

import de.sqrt.microBloat.config.ConfigHandler;
import de.sqrt.microBloat.config.SettingState;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public final class Util {
	
	private Util() {}

	public static boolean deleteButton(List<ClickableWidget> buttons, String text) {
		for(ClickableWidget button:buttons) {
			if(!button.getMessage().toString().contains(text))continue;
			SettingState buttonState = ConfigHandler.get(text);
			if (buttonState == SettingState.HIDDEN) {
				button.visible = false;
				return true;
			}
			if (buttonState == SettingState.SPECIAL) {
				button.visible = false;
				return false;
			}
		}
		return false;
	}

	public static ClickableWidget getButton(List<ClickableWidget> buttons, String text) {
		for (ClickableWidget clickableWidget:buttons) {
			if (clickableWidget.getMessage().toString().contains(text))
				return clickableWidget;
		}
		return null;
	}

	public static void printbuttonNames(List<ClickableWidget> buttons) {
		for (ClickableWidget clickableWidget:buttons) {
			MicroBloat.LOGGER.info(clickableWidget.getMessage().toString());
		}
	}

	private static String extraSpecialStates(SettingState state, String key) {
		if (key == "background") {
			if (state == SettingState.NORMAL)
				return Text.translatable("microbloat.settingstate.normal").getString();
			if (state == SettingState.HIDDEN)
				return Text.translatable("microbloat.setting.background.noSpin").getString();
			if (state == SettingState.SPECIAL)
				return Text.translatable("microbloat.setting.background.dirt").getString();
		}

		if (key == "Telemetry" && state == SettingState.HIDDEN)
			return Text.translatable("microbloat.setting.disenabled").getString();

		return null;
	}

	private static String getSettingState(SettingState state, String key) {
		if (extraSpecialStates(state, key) != null)
			return extraSpecialStates(state, key);
		if (state == SettingState.NORMAL)
			return Text.translatable("microbloat.settingstate.normal").getString();
		if (state == SettingState.HIDDEN)
			return Text.translatable("microbloat.settingstate.hidden").getString();
		if (state == SettingState.SPECIAL) {
			if (Text.translatable("microbloat.setting." + key + ".special").getString()
					.equals("microbloat.setting." + key + ".special"))
				return Text.translatable("microbloat.settingstate.special").getString();
			return Text.translatable("microbloat.setting." + key + ".special").getString();
		}
		return "invalid state";
	}

	public static ButtonWidget getSettingsButton(String key, int x, int y, int widht, boolean booleanSetting) {
		return new ButtonWidget(x, y, widht, 20,
				Text.translatable("microbloat.setting." + key, getSettingState(ConfigHandler.get(key), key)),
				button -> {
					button.setMessage(Text.translatable("microbloat.setting." + key,
							getSettingState(ConfigHandler.toggle(key, booleanSetting), key)));
				});

	}
}
