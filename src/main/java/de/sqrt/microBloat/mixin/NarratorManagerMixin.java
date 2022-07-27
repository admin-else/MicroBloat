package de.sqrt.microBloat.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.text2speech.Narrator;
import com.mojang.text2speech.NarratorDummy;

import net.minecraft.client.util.NarratorManager;

@Mixin(NarratorManager.class)
public class NarratorManagerMixin {

	@Shadow
	@Final
	@Mutable
	private final Narrator narrator = new NarratorDummy();
}
