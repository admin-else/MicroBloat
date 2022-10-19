package de.sqrt.microBloat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.sqrt.microBloat.config.ConfigHandler;
import de.sqrt.microBloat.config.SettingState;
import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(RecipeToast.class)
public class RecipeToastMixin {
	
	@Inject(at = @At ("HEAD"), method = "draw", cancellable = true)
	private void hideToastInstantly(MatrixStack matrices, ToastManager manager, long startTime,CallbackInfoReturnable<Toast.Visibility> cir) {
		if(ConfigHandler.get("recipe_toast")==SettingState.HIDDEN)cir.setReturnValue(Toast.Visibility.HIDE);
	}

}
