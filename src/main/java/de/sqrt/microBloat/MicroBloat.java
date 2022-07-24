package de.sqrt.microBloat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.sqrt.microBloat.config.ConfigHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.text.Text;

public class MicroBloat implements ModInitializer{
	public static final Logger LOGGER = LoggerFactory.getLogger("microbloat");
	

	@Override
	public void onInitialize() {
		ConfigHandler.init();
	}

}
