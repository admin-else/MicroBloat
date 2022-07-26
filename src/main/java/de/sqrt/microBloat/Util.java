package de.sqrt.microBloat;

import java.util.Iterator;
import java.util.List;

import de.sqrt.microBloat.config.ConfigHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class Util {
	
	static MinecraftClient client = MinecraftClient.getInstance();
	
	

	public static boolean deletButton(List<ClickableWidget> buttons, String text) {
		for (Iterator<ClickableWidget> iterator = buttons.iterator(); iterator.hasNext();) {
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
		for (Iterator<ClickableWidget> iterator = buttons.iterator(); iterator.hasNext();) {
			ClickableWidget clickableWidget = (ClickableWidget) iterator.next();
			if(clickableWidget.getMessage().toString().contains(text))return clickableWidget;
		}
		return null;
	}
	
	public static void printbuttonNames(List<ClickableWidget> buttons) {
		for (Iterator<ClickableWidget> iterator = buttons.iterator(); iterator.hasNext();) {
			ClickableWidget clickableWidget = (ClickableWidget) iterator.next();
			MicroBloat.LOGGER.info(clickableWidget.getMessage().toString());
		}
	}
	
	private static String extraSpecialStates(int state,String key) {
		if(key == "background") {
			if(state==0)return Text.translatable("microbloat.settingstate.normal").getString();
			if(state==1)return Text.translatable("microbloat.setting.background.noSpin").getString();
			if(state==2)return Text.translatable("microbloat.setting.background.dirt").getString();
		}
		
		if(key=="Telemetry"&&state==1)return Text.translatable("microbloat.setting.background.disenabled").getString(); 
		
		
		return null;
	}
	
	private static String getSettingState(int state,String key) {
		if(extraSpecialStates(state, key)!=null)return extraSpecialStates(state, key);
		if(state==0)return Text.translatable("microbloat.settingstate.normal").getString();
		if(state==1)return Text.translatable("microbloat.settingstate.hidden").getString();
		if(state==2) {
			if(Text.translatable("microbloat.setting."+key+"specialoption").getString().equals("microbloat.setting."+key+"specialoption"))
				return Text.translatable("microbloat.settingstate.special").getString();
			return Text.translatable("microbloat.setting."+key+"specialoption").getString();
		}
		return "invalvid state";
	}
	
	public static ButtonWidget getSettingsButton(String key,int x,int y,int widht,boolean booleanSetting) {
		return new ButtonWidget(x, y, widht, 20, Text.translatable("microbloat.setting."+key, getSettingState(ConfigHandler.get(key), key)), button -> {
			button.setMessage(Text.translatable("microbloat.setting."+key, getSettingState(ConfigHandler.toggle(key,booleanSetting), key)));
		});

	}
}
