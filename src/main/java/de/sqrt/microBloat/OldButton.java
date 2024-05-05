package de.sqrt.microBloat;

import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class OldButton extends PressableWidget {
    protected final OldButton.PressAction onPress;

    public OldButton(int x, int y, int width, int height, Text message, OldButton.PressAction onPress) {
        super(x, y, width, height, message);
        this.onPress = onPress;
    }

    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }


    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(OldButton button);
    }
}

