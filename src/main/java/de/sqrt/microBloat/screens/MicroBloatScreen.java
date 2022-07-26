package de.sqrt.microBloat.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class MicroBloatScreen extends Screen {

	Screen parent;

	public MicroBloatScreen(Screen parent) {
		super(Text.translatable("microbloat.settings.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		addDrawableChild(new ButtonWidget(width / 2 - 155, this.height / 6 + 48 - 6, 150, 20,
				Text.translatable("microbloat.settings.titleScreen"),
				btn -> client.setScreen(new TitleScreenSettingsScreen(this))));
		addDrawableChild(new ButtonWidget(width / 2 + 5, this.height / 6 + 48 - 6, 150, 20,
				Text.translatable("microbloat.settings.inGame"),
				btn -> client.setScreen(new InGameSettingsScreen(this))));
		addDrawableChild(new ButtonWidget(width / 2 - 155, this.height / 6 + 72 - 6, 150, 20,
				Text.translatable("microbloat.settings.pause"),
				btn -> client.setScreen(new PauseMenuSettingsScreen(this))));
		addDrawableChild(new ButtonWidget(width / 2 + 5, this.height / 6 + 72 - 6, 150, 20,
				Text.translatable("microbloat.settings.options"),
				btn -> client.setScreen(new OptionsSettingsScreen(this))));
		addDrawableChild(new ButtonWidget(width / 2 - 155, this.height / 6 + 96 - 6, 150, 20,
				Text.translatable("microbloat.settings.microsoft"),
				btn -> client.setScreen(new MicrosoftSettingsScreen(this))));
		addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20,
				Text.translatable("menu.quit"), button -> this.client.scheduleStop()));

		addDrawableChild(new ButtonWidget(width / 2 - 100, height / 6 + 168, 200, 20, ScreenTexts.DONE,
				button -> client.setScreen(parent)));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
		super.render(matrices, mouseX, mouseY, delta);
	}
}
