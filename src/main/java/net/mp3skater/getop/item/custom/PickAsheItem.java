package net.mp3skater.getop.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.entity.ModEntityTypes;
import net.mp3skater.getop.entity.custom.PickasheFireballEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class PickAsheItem extends PickaxeItem implements RareItem {
	public PickAsheItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
		super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
	}

	@Override
	public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.CROSSBOW;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack>
	use(Level level, Player player, InteractionHand hand) {
		// Return if the hand isn't the main hand or the player isn't on the ground
		if(hand!=InteractionHand.MAIN_HAND)
			return new InteractionResultHolder<>(InteractionResult.FAIL, player.getItemInHand(hand));

		// Getting the player's eye position
		Vec3 eye = player.getEyePosition();

		// Do the teleportation and boost on the server side
		if(!level.isClientSide) throwFireball(level, player);

		// Add Cooldown
		player.getCooldowns().addCooldown(this, 20);

		// Spawn the particles on the client
		if(level.isClientSide) spawnParticles(level, eye);

		// Break it on use
		player.getItemInHand(hand).hurtAndBreak(1, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));

		return super.use(level, player, hand);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		pTooltipComponents.add(new TranslatableComponent("item.getop.pickashe.tooltip"));
	}

	// Inside your class where the method belongs
	private void throwFireball(Level level, Player player) {
		// Ensure it's running on the server side
		if (!(level instanceof ServerLevel world)) {
			return;
		}

		PickasheFireballEntity entity = new PickasheFireballEntity(ModEntityTypes.PICKASHE_FIREBALL.get(), level);

		// Set the initial position and motion of the entity
		entity.setPos(player.getX(), player.getEyeY(), player.getZ());
		entity.setOwner(player);

		// Calculate the velocity based on player's look direction
		float rotationYaw = player.getYRot();
		float rotationPitch = player.getXRot();
		float velocity = 5f;

		// Adjust the motion of the entity based on player's rotation
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
		Random random = new Random();
		float maxDistance = 2;

		// Spawn smoke particles
		for (int i = 0; i < 20; i++) {
			double offsetX = (random.nextDouble() * 2 - 1) * maxDistance;
			double offsetY = (random.nextDouble() * 2 - 1) * maxDistance;
			double offsetZ = (random.nextDouble() * 2 - 1) * maxDistance;
			Vec3 particlePos = eye.add(offsetX, offsetY, offsetZ);
			level.addParticle(ParticleTypes.LARGE_SMOKE, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0);
		}

		// Spawn lava particles
		for (int i = 0; i < 20; i++) {
			double offsetX = (random.nextDouble() * 2 - 1) * maxDistance;
			double offsetY = (random.nextDouble() * 2 - 1) * maxDistance;
			double offsetZ = (random.nextDouble() * 2 - 1) * maxDistance;
			Vec3 particlePos = eye.add(offsetX, offsetY, offsetZ);
			level.addParticle(ParticleTypes.LAVA, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0);
		}
	}

	public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
		return !pPlayer.isCreative();
	}
}