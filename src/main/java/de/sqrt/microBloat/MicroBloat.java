package de.sqrt.microBloat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.sqrt.microBloat.config.ConfigHandler;
import de.sqrt.microBloat.otherFeatures.AutoJumpPlus;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class MicroBloat implements ModInitializer{
	public static final Logger LOGGER = LoggerFactory.getLogger("microbloat");
	
	public static final boolean LEGIT = false;
	
	@Override
	public void onInitialize() {
		ConfigHandler.init();
		ClientTickEvents.END_CLIENT_TICK.register(this::tick);
	}

	private void tick(MinecraftClient client) {
		AutoJumpPlus.tick();
	}
}
