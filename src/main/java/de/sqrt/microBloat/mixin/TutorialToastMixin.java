package de.sqrt.microBloat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.sqrt.microBloat.config.ConfigHandler;
import de.sqrt.microBloat.config.SettingState;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(TutorialToast.class)
public class TutorialToastMixin {
	
	@Inject(at = @At ("HEAD"), method = "draw", cancellable = true)
	private void hideToastInstantly(MatrixStack matrices, ToastManager manager, long startTime,CallbackInfoReturnable<Toast.Visibility> cir) {
		if(ConfigHandler.get("tutorial")==SettingState.HIDDEN)cir.setReturnValue(Toast.Visibility.HIDE);
	}

}
