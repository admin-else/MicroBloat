package de.sqrt.microBloat;

import java.util.Iterator;
import java.util.List;

import com.mojang.text2speech.Narrator;
import com.mojang.text2speech.NarratorDummy;

import de.sqrt.microBloat.config.ConfigHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.text.Text;

public class Util {
	
	static MinecraftClient client = MinecraftClient.getInstance();
	
	

	public static boolean deletButton(List<ClickableWidget> buttons, String text) {
		for (Iterator iterator = buttons.iterator(); iterator.hasNext();) {
			ClickableWidget button = (ClickableWidget) iterator.next();
		if (button.getMessage().toString().contains(text) && ConfigHandler.get(text) == 1) {
			button.visible = false;
			return true;
		}
		if (button.getMessage().toString().contains(text) && ConfigHandler.get(text) == 2) {
			button.visible = false;
			return false;
		}
		}
		return false;
	}
	
	public static ClickableWidget getButton(List<ClickableWidget> buttons, String text) {
		for (Iterator iterator = buttons.iterator(); iterator.hasNext();) {
			ClickableWidget clickableWidget = (ClickableWidget) iterator.next();
			if(clickableWidget.getMessage().toString().contains(text))return clickableWidget;
		}
		return null;
	}
	
	public static void printbuttonNames(List<ClickableWidget> buttons) {
		for (Iterator iterator = buttons.iterator(); iterator.hasNext();) {
			ClickableWidget clickableWidget = (ClickableWidget) iterator.next();
			MicroBloat.LOGGER.info(clickableWidget.getMessage().toString());
		}
	}
	
	public static void onUpdate() {
		if(ConfigHandler.get("autoJump")==1)client.options.getAutoJump().setValue(false);
	}
	
	public static ButtonWidget getSettingsButton(String key,int x,int y,int widht,boolean booleanSetting) {
		return new ButtonWidget(x, y, widht, 20, Text.literal(key+": "+ConfigHandler.get(key)), button -> {
			button.setMessage(Text.literal(key+": "+ConfigHandler.toggle(key,booleanSetting)));
		});

	}
}
