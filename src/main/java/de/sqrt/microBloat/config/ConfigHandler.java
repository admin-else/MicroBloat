package de.sqrt.microBloat.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.LinkedHashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import de.sqrt.microBloat.MicroBloat;
import static com.google.gson.JsonParser.parseReader;

import net.fabricmc.loader.api.FabricLoader;

//FUCKIN DONE FINALLY (:<
public class ConfigHandler {

    // TODO array with for each statement

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("microbloat.json");

    public static LinkedHashMap<String, Integer> config = new LinkedHashMap<String, Integer>();

    public static final String[] settings = new String[] { "menu.online", "button.accessibility", "button.language",
            "menu.quit", "Copyright", "edition", "splash", "background", "Telemetry", "death_score", "advancements",
            "stats", "sendFeedback", "reportBugs", "shareToLan", "difficulty_lock", "online", "options.difficulty",
            "accessibility_guide", "autoJump", "mouse_settings", "playerReporting", "tutorial_toast", "recipe_toast",
            "advancements_toast", "playerReporting_toast", "all_toasts", "menu.singleplayer", "menu.multiplayer"};

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
            } catch (Exception e) {
                e.printStackTrace();
                new File(CONFIG_PATH.toString()).delete();
                createConfig();
            }
        } else {
            MicroBloat.LOGGER.error("The config does not exist!");
            createConfig();
        }

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
        MicroBloat.LOGGER.info("createdConfig");
    }

    public static SettingState get(String key) {
        return traslate(config.get(key));
    }

    public static boolean bget(String key) {
        if (get(key) == SettingState.HIDDEN)
            return true;
        return false;
    }

    public static void put(String key, SettingState state) {
        config.put(key, traslateBackwards(state));
        write();
    }

    public static SettingState toggle(String key, boolean booleanSetting) {
        SettingState state = ConfigHandler.get(key);
        if (state == SettingState.NORMAL) {
            ConfigHandler.put(key, SettingState.HIDDEN);
            return SettingState.HIDDEN;
        }
        if (state == SettingState.HIDDEN) {
            if (booleanSetting) {
                ConfigHandler.put(key, SettingState.NORMAL);
                return SettingState.NORMAL;
            }
            ConfigHandler.put(key, SettingState.SPECIAL);
            return SettingState.SPECIAL;
        } else {
            ConfigHandler.put(key, SettingState.NORMAL);
            return SettingState.NORMAL;
        }
    }

    private static SettingState traslate(Integer value) {
        switch (value) {
            case 1:
                return SettingState.HIDDEN;
            case 2:
                return SettingState.SPECIAL;
            default:
                return SettingState.NORMAL;
        }
    }

    private static int traslateBackwards(SettingState value) {
        switch (value) {
            case HIDDEN:
                return 1;
            case SPECIAL:
                return 2;
            default:
                return 0;
        }
    }
}
