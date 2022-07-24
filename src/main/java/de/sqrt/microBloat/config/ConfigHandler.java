package de.sqrt.microBloat.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.LinkedHashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import de.sqrt.microBloat.Util;

import static com.google.gson.JsonParser.parseReader;

import net.fabricmc.loader.api.FabricLoader;

//FUCKIN DONE FINALLY (:<
public class ConfigHandler {

	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("microbloat.json");

	public static LinkedHashMap<String, Integer> config = new LinkedHashMap<String, Integer>();

	public static final String[] settings = new String[] { "menu.online", "button.accessibility", "button.language",
			"menu.quit", "Copyright", "edition", "splash", "background", "Telemetry", "death_score",
			"advancements", "stats", "sendFeedback", "reportBugs", "shareToLan", "difficulty_lock", "options.online",
			"options.difficulty", "accessibility_guide", "autoJump", "mouse_settings" };

	private static void write() {
		try (final FileWriter fw = new FileWriter(CONFIG_PATH.toString()); final JsonWriter jw = new JsonWriter(fw)) {
			jw.setIndent("    ");
			jw.beginObject();
			for (int i = 0; i < settings.length; i++) {
				jw.name(settings[i]).value(config.get(settings[i]));
			}
			jw.endObject();
			jw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}

	public static void init() {
		if (CONFIG_PATH.toFile().exists()) {
			try (final FileReader fr = new FileReader(CONFIG_PATH.toString())) {
				final JsonElement je = parseReader(fr);
				if (!je.isJsonObject()) {
					createConfig();
				}
				JsonObject jo = je.getAsJsonObject();

				for (int i = 0; i < settings.length; i++) {
					config.put(settings[i], readInt(jo, settings[i]));
				}
				Util.onUpdate();
			} catch (Exception e) {
				new File(CONFIG_PATH.toString()).delete();
				createConfig();
			}
		} else
			createConfig();
	}

	private static int readInt(JsonObject json, String key) {
		final JsonElement el = json.get(key);
		return el.getAsInt();
	}

	private static void createConfig() {
		config.clear();
		for (int i = 0; i < settings.length; i++) {
			config.put(settings[i], 0);
		}
		write();
	}

	public static int get(String key) {
		if (config.get(key) == null)
			return 0;
		return config.get(key);
	}

	public static void put(String key, int value) {
		config.put(key, value);
		write();
	}
	
	public static int toggle(String key, Boolean booleanSetting) {
		if(ConfigHandler.get(key)==0) {
			ConfigHandler.put(key, 1);
			return 1;
		}else if(ConfigHandler.get(key)==1) {
			if(booleanSetting) {
				ConfigHandler.put(key, 0);
				return 0;
			}
			ConfigHandler.put(key, 2);
			return 2;
		}else {
			ConfigHandler.put(key, 0);
			return 0;
		}
	}
}
