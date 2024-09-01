package net.mp3skater.getop.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Random;

public class DeathSwordItem extends AxeItem implements RareItem {
	public DeathSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
		super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
	}

	@Override
	public boolean canPerformAction(@NotNull ItemStack stack, net.minecraftforge.common.@NotNull ToolAction toolAction) {
		return false;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack>
	use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		// Return if the hand isn't the main hand
		if(hand!=InteractionHand.MAIN_HAND) return new InteractionResultHolder<>(InteractionResult.FAIL, player.getItemInHand(hand));

		// Getting some vecs
		Vec3 look = player.getLookAngle();
		Vec3 start = player.getEyePosition();

		// Do the actual like attack
		if(!level.isClientSide) {
			double margin_xz = 3; // margin of x/z-axe around the death-ray
			double margin_y = 2; // margin of y-axe around the death-ray
			Vec3 end = start.add(look.x * GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get(),
				look.y * GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get(),
				look.z * GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get());

			double minX = Math.min(start.x, end.x) - margin_xz;
			double minY = Math.min(start.y, end.y) - margin_y;
			double minZ = Math.min(start.z, end.z) - margin_xz;
			double maxX = Math.max(start.x, end.x) + margin_xz;
			double maxY = Math.max(start.y, end.y) + margin_y;
			double maxZ = Math.max(start.z, end.z) + margin_xz;
			AABB boundingBox = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
			Iterable<Entity> entities = level.getEntities(player, boundingBox);

			int dmg = 0;
			int maxDmg = 5;
			for (Entity entity : entities) {
				if ((entity instanceof LivingEntity)) {
					entity.hurt(DamageSource.MAGIC, 12);
					entity.setDeltaMovement(0.5d * look.x, 0, 0.5d * look.z);
					if(dmg<maxDmg) dmg++;
				}
				if(dmg>0) player.hurt(DamageSource.WITHER, dmg);
			}
			// damages weapon per hit
			ItemStack itemstack = player.getItemInHand(hand);
			itemstack.hurtAndBreak(1, player,
							player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
		}

		// Spawn the particles
		else {
			Random random = new Random();
			double margin_xz = 3; // margin of x/z-axe around the death-ray
			double margin_y = 2; // margin of y-axe around the death-ray
			Vec3 end = start.add(look.x * GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get(),
				look.y * GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get(),
				look.z * GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get());

			double minX = Math.min(start.x, end.x) - margin_xz;
			double minY = Math.min(start.y, end.y) - margin_y;
			double minZ = Math.min(start.z, end.z) - margin_xz;
			double maxX = Math.max(start.x, end.x) + margin_xz;
			double maxY = Math.max(start.y, end.y) + margin_y;
			double maxZ = Math.max(start.z, end.z) + margin_xz;
			AABB boundingBox = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
			Iterable<Entity> entities = level.getEntities(player, boundingBox);

			// Number of elements to retrieve
			int limit = 5;
			Iterator<Entity> iterator = entities.iterator();

			int count = 0;
			while (iterator.hasNext() && count < limit) {
				Entity entity = iterator.next();

				// Spawn 10 damage indicator particles
				Vec3 eye = entity.getEyePosition();
				spawnParticles(level, eye, 20, 0.5f);
				count++;
			}
		}
		//gives the End Sceptre a cool down of half a sec after being used
		player.getCooldowns().addCooldown(this, 60);

		return super.use(level, player, hand);
	}

	private void spawnParticles(Level level, Vec3 start, int numParticles, double speed) {
		Random random = new Random();

		// Spawn particles in all directions
		for (int i = 0; i < numParticles; i++) {
			// Generate a random direction in spherical coordinates
			double theta = random.nextDouble() * 2 * Math.PI; // Azimuthal angle
			double phi = Math.acos(2 * random.nextDouble() - 1); // Polar angle

			// Convert spherical coordinates to Cartesian coordinates
			double offsetX = Math.sin(phi) * Math.cos(theta);
			double offsetY = Math.cos(phi);
			double offsetZ = Math.sin(phi) * Math.sin(theta);

			// Apply speed to the offset direction
			offsetX *= speed;
			offsetY *= speed;
			offsetZ *= speed;

			// Compute the particle's position
			Vec3 particlePos = start.add(offsetX, offsetY, offsetZ);

			// Add the particle to the level
			level.addParticle(ParticleTypes.DAMAGE_INDICATOR, // You can replace this with any other particle type
				particlePos.x, particlePos.y, particlePos.z,
				offsetX, offsetY, offsetZ);
		}
	}

	@Override
	public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
		if (!pAttacker.level.isClientSide()) {
			pTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 2), pAttacker);
		}
		return super.hurtEnemy(pStack, pTarget, pAttacker);
	}

	@Override
	public float getDestroySpeed(@NotNull ItemStack pStack, @NotNull BlockState pState) {
		return 1.0f;
	}
}
