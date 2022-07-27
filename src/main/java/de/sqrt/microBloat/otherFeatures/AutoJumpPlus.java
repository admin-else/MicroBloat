package de.sqrt.microBloat.otherFeatures;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import de.sqrt.microBloat.MicroBloat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

public class AutoJumpPlus {

	public static void tick() {
		MinecraftClient MC = MinecraftClient.getInstance();
		ClientPlayerEntity player = MC.player;

		if (MC.world == null || player == null || MicroBloat.LEGIT)
			return;

		player.stepHeight = 0.5F;

		if (!player.horizontalCollision)
			return;

		if (!player.isOnGround() || player.isClimbing() || player.isTouchingWater() || player.isInLava())
			return;

		if (player.input.movementForward == 0 && player.input.movementSideways == 0)
			return;

		if (player.input.jumping)
			return;

		Box box = player.getBoundingBox().offset(0, 0.05, 0).expand(0.05);

		if (!MC.world.isSpaceEmpty(player, box.offset(0, 1, 0)))
			return;

		double stepHeight = -1;

		ArrayList<Box> blockCollisions = StreamSupport.stream(MC.world.getCollisions(player, box).spliterator(), false)
				.map(VoxelShape::getBoundingBox).collect(Collectors.toCollection(ArrayList::new));

		for (Box bb : blockCollisions)
			if (bb.maxY > stepHeight)
				stepHeight = bb.maxY;

		stepHeight = stepHeight - player.getY();

		if (stepHeight < 0 || stepHeight > 1)
			return;

		ClientPlayNetworkHandler netHandler = player.networkHandler;

		netHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(player.getX(),
				player.getY() + 0.42 * stepHeight, player.getZ(), player.isOnGround()));

		netHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(player.getX(),
				player.getY() + 0.753 * stepHeight, player.getZ(), player.isOnGround()));

		player.setPosition(player.getX(), player.getY() + 1 * stepHeight, player.getZ());
	}

}
