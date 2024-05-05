package de.sqrt.microBloat.screens;

import de.sqrt.microBloat.OldButton;
import de.sqrt.microBloat.Util;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class PauseMenuSettingsScreen extends Screen {

	Screen parent;

	public PauseMenuSettingsScreen(Screen parent) {
		super(Text.translatable("microbloat.settings.pause"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		addDrawableChild(
				Util.getSettingsButton("advancements", this.width / 2 - 155, this.height / 6 + 48 - 6, 150, false));
		addDrawableChild(Util.getSettingsButton("stats", this.width / 2 + 5, this.height / 6 + 48 - 6, 150, false));
		addDrawableChild(
				Util.getSettingsButton("sendFeedback", this.width / 2 - 155, this.height / 6 + 72 - 6, 150, false));
		addDrawableChild(
				Util.getSettingsButton("reportBugs", this.width / 2 + 5, this.height / 6 + 72 - 6, 150, false));
		addDrawableChild(
				Util.getSettingsButton("shareToLan", this.width / 2 - 155, this.height / 6 + 96 - 6, 150, false));
		addDrawableChild(new OldButton(width / 2 - 100, height / 6 + 168, 200, 20, ScreenTexts.DONE,
				button -> client.setScreen(parent)));
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context);
		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
		super.render(context, mouseX, mouseY, delta);
	}

}
