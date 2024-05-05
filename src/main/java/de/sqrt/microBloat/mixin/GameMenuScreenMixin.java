package de.sqrt.microBloat.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.sqrt.microBloat.Util;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {

	protected GameMenuScreenMixin(Text title) {
		super(title);
	}

	List<ClickableWidget> buttons = Screens.getButtons((Screen) (Object) this);

	private boolean advancements;
	private boolean stats;
	private boolean sendFeedback;
	private boolean reportBugs;
	private boolean shareToLan;

	private ClickableWidget advancementsButton;
	private ClickableWidget statsButton;
	private ClickableWidget sendFeedbackButton;
	private ClickableWidget reportBugsButton;
	private ClickableWidget shareToLanButton;
	private ClickableWidget optionsButton;
	private ClickableWidget returnToMenuButton;

	@Inject(method = "initWidgets", at = @At("TAIL"))
	private void initWidgets(CallbackInfo ci) {
		advancements = Util.deleteButton(buttons, "advancements");
		stats = Util.deleteButton(buttons, "stats");
		sendFeedback = Util.deleteButton(buttons, "sendFeedback");
		reportBugs = Util.deleteButton(buttons, "reportBugs");
		shareToLan = (Util.getButton(buttons, "shareToLan")!=null
				? Util.deleteButton(buttons, "shareToLan")
				: Util.deleteButton(buttons, "playerReporting"));

		advancementsButton = Util.getButton(buttons, "advancements");
		statsButton = Util.getButton(buttons, "stats");
		sendFeedbackButton = Util.getButton(buttons, "sendFeedback");
		reportBugsButton = Util.getButton(buttons, "reportBugs");
		shareToLanButton = (Util.getButton(buttons, "shareToLan")!=null
				? Util.getButton(buttons, "shareToLan")
				: Util.getButton(buttons, "playerReporting"));
		optionsButton = Util.getButton(buttons, "options");
		returnToMenuButton = (MinecraftClient.getInstance().isIntegratedServerRunning()
				? Util.getButton(buttons, "returnToMenu")
				: Util.getButton(buttons, "conne"));

		if (advancements && stats) {
			if (sendFeedback && reportBugs) {
				optionsButton.setY(this.height / 4 + 48 + -16);
				shareToLanButton.setY(this.height / 4 + 48 + -16);
				returnToMenuButton.setY(this.height / 4 + 72 + -16);
			} else {
				optionsButton.setY(this.height / 4 + 72 + -16);
				shareToLanButton.setY(this.height / 4 + 72 + -16);
				returnToMenuButton.setY(this.height / 4 + 96 + -16);
				sendFeedbackButton.setY(this.height / 4 + 48 + -16);
				reportBugsButton.setY(this.height / 4 + 48 + -16);
			}
		}

		if (sendFeedback && reportBugs) {
			optionsButton.setY(this.height / 4 + 72 + -16);
			shareToLanButton.setY(this.height / 4 + 72 + -16);
			returnToMenuButton.setY(this.height / 4 + 96 + -16);

		}

		if (shareToLan)
			optionsButton.setWidth(204);
		if (reportBugs)
			sendFeedbackButton.setWidth(204);
		if (stats)
			advancementsButton.setWidth(204);

		if (sendFeedback) {
			reportBugsButton.setWidth(204);
			reportBugsButton.setX(this.width / 2 - 102);
		}

		if (advancements) {
			statsButton.setWidth(204);
			statsButton.setX(this.width / 2 - 102);
		}

		if (!(this.client.world != null && this.client.isIntegratedServerRunning())) {
			shareToLanButton.visible = false;
			optionsButton.setWidth(204);
		}
	}

}
