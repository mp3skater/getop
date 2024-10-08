package net.mp3skater.getop.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.entity.ModEntityTypes;
import net.mp3skater.getop.entity.custom.EndSceptreEntity;
import net.mp3skater.getop.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class EndSceptreItem extends SwordItem implements RareItem {
	public EndSceptreItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
		super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
	}

	@Override
	public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.SPEAR;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack>
	use(Level level, Player player, InteractionHand hand) {
		// Return if the hand isn't the main hand or the player isn't on the ground
		if(hand!=InteractionHand.MAIN_HAND)
			return new InteractionResultHolder<>(InteractionResult.FAIL, player.getItemInHand(hand));

		// Getting the player's look direction and eye position
		Vec3 look = player.getLookAngle();
		Vec3 eye = player.getEyePosition();

		// Spawn the particles on the client
		if(level.isClientSide) spawnParticles(level, eye);

		// Do the teleportation and boost on the server side
		else {
			// If the player is shifting, he boosts and teleports
			if(player.isShiftKeyDown()) boostAndTeleport(eye, look, level, player);

			// If he isn't shifting, he throws the teleport entity
			else throwTpEntity(level, player);
		}

		// Add Cooldown
		player.getCooldowns().addCooldown(this, 20);

		// Break it on use
		player.getItemInHand(hand).hurtAndBreak(1, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));

		return super.use(level, player, hand);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		pTooltipComponents.add(new TranslatableComponent("item.getop.end_sceptre.tooltip"));
	}

	private void boostAndTeleport(Vec3 eye, Vec3 look, Level level, Player player) {
		// Get config values
		Double tp_length = GetOPCommonConfigs.END_SCEPTRE_TP_LENGTH.get();
		Double dash_speed = GetOPCommonConfigs.END_SCEPTRE_AIR_DASH_SPEED.get();

		// Calculate the teleportation target position
		if(tp_length>0) {
			Vec3 vec_end = eye.add(look.scale(tp_length));
			if(ModUtils.tpAblePosAir(vec_end, level)) {
				BlockPos block_end = new BlockPos(vec_end);
				player.moveTo(block_end.getX(), block_end.getY(), block_end.getZ());
			}
		}

		// Apply the movement boost
		if(dash_speed>0) {
			player.hurtMarked = true;
			player.setDeltaMovement(look.scale(dash_speed));
		}
	}

	// Inside your class where the method belongs
	private void throwTpEntity(Level level, Player player) {
		// Ensure it's running on the server side
		if (!(level instanceof ServerLevel world)) {
			return;
		}

		EndSceptreEntity entity = new EndSceptreEntity(ModEntityTypes.ENDSCEPTRE_ENTITY.get(), world);

		// Set the initial position and motion of the entity
		entity.setPos(player.getX(), player.getEyeY(), player.getZ());
		entity.setOwner(player);

		// Calculate the velocity based on player's look direction
		float rotationYaw = player.getYRot();
		float rotationPitch = player.getXRot();
		float velocity = 2.5F;

		// Adjust the motion of the entity based on the player's rotation
		double motionX = -Math.sin(Math.toRadians(rotationYaw)) * Math.cos(Math.toRadians(rotationPitch));
		double motionY = -Math.sin(Math.toRadians(rotationPitch));
		double motionZ = Math.cos(Math.toRadians(rotationYaw)) * Math.cos(Math.toRadians(rotationPitch));

		// Set the velocity for the entity
		entity.setDeltaMovement(motionX * velocity, motionY * velocity, motionZ * velocity);

		// Spawn the entity into the world
		world.addFreshEntity(entity);
	}

	private void spawnParticles(Level level, Vec3 eye) {
		// Spawn the particles
		if (level.isClientSide) {
			Random random = new Random();
			float maxDistance = 2;

			// Spawn witch spell particles
			for (int i = 0; i < 20; i++) {
				double offsetX = (random.nextDouble() * 2 - 1) * maxDistance;
				double offsetY = (random.nextDouble() * 2 - 1) * maxDistance;
				double offsetZ = (random.nextDouble() * 2 - 1) * maxDistance;
				Vec3 particlePos = eye.add(offsetX, offsetY, offsetZ);
				level.addParticle(ParticleTypes.WITCH, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0);
			}

			// Spawn end rod particles
			for (int i = 0; i < 7; i++) {
				double offsetX = (random.nextDouble() * 2 - 1) * maxDistance;
				double offsetY = (random.nextDouble() * 2 - 1) * maxDistance;
				double offsetZ = (random.nextDouble() * 2 - 1) * maxDistance;
				Vec3 particlePos = eye.add(offsetX, offsetY, offsetZ);
				level.addParticle(ParticleTypes.END_ROD, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0);
			}
		}
	}

	public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
		return !pPlayer.isCreative();
	}
}