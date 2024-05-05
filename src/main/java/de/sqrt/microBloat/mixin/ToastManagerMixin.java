package de.sqrt.microBloat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.sqrt.microBloat.config.ConfigHandler;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.SystemToast.Type;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.toast.TutorialToast;

@Mixin(ToastManager.class)
public class ToastManagerMixin {

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    public void add(Toast toast, CallbackInfo ci) {
        if (ConfigHandler.bget("all_toasts"))
            ci.cancel();
        if (toast instanceof SystemToast) {
            SystemToast.Type type = (Type) toast.getType();
            if (type == SystemToast.Type.UNSECURE_SERVER_WARNING && ConfigHandler.bget("playerReporting_toast"))
                ci.cancel();
        }
        if (toast instanceof TutorialToast && ConfigHandler.bget("tutorial_toast"))
            ci.cancel();
        if (toast instanceof AdvancementToast && ConfigHandler.bget("advancements_toast"))
            ci.cancel();
        if (toast instanceof RecipeToast && ConfigHandler.bget("recipe_toast"))
            ci.cancel();
    }

}
