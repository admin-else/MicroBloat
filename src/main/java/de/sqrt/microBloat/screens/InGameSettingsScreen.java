package de.sqrt.microBloat.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class InGameSettingsScreen extends Screen{

	Screen parent;
	
	public InGameSettingsScreen(Screen parent) {
		super(Text.translatable("microbloat.settings.inGame"));
		this.parent = parent;
	}
	
	@Override
	protected void init() {
		addDrawableChild(new ButtonWidget(width / 2 - 100, height / 6 + 168, 200, 20, ScreenTexts.DONE, button -> client.setScreen(parent)));
	}
	
	@Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
	
}
