package net.mp3skater.getop.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.util.ModUtils;
import net.mp3skater.getop.world.dimension.ModDimensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.item.Rarity.EPIC;

public class HerobrineSwordItem extends SwordItem {
	public HerobrineSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
		super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		pTooltipComponents.add(new TranslatableComponent("item.getop.herobrine_sword.tooltip"));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		// Return if it isn't main hand
		if(hand!=InteractionHand.MAIN_HAND) return super.use(level, player, hand);

		// Normal Herobrine ability (lightning and tp)
		if(!player.isShiftKeyDown()) lightningPower(level, player);

		// Go to GetOP Dimension
		else ModUtils.teleportEntityToDimension(player, ModDimensions.GTDIM_KEY, new Vec3(0,0,0), this);

		return super.use(level, player, hand);
	}

	private void lightningPower(Level level, Player player) {
		// Get config value
		Double reach = GetOPCommonConfigs.HEROBRINE_SWORD_REACH_DISTANCE.get();

		// Get look direction and player eye position vectors
		Vec3 look = player.getLookAngle();
		Vec3 eye = player.getEyePosition();

		Entity nearestEntity = null;
		Vec3 vec_end = eye.add(look.x * reach, look.y * reach, look.z * reach);

		double minX = Math.min(eye.x, vec_end.x);
		double minY = Math.min(eye.y, vec_end.y);
		double minZ = Math.min(eye.z, vec_end.z);
		double maxX = Math.max(eye.x, vec_end.x);
		double maxY = Math.max(eye.y, vec_end.y);
		double maxZ = Math.max(eye.z, vec_end.z);

		// Iterate through all entities in front of the player and select the nearest
		if (!player.level.isClientSide) {
			AABB boundingBox = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
			Iterable<Entity> entities = level.getEntities(player, boundingBox);
			for (Entity entity : entities) {
				if (entity instanceof LivingEntity) {
					if (nearestEntity == null || entity.distanceTo(player) < nearestEntity.distanceTo(player)) {
						nearestEntity = entity;
					}
				}
			}
			if (nearestEntity != null && nearestEntity.isAlive()) {
				BlockPos position = nearestEntity.blockPosition();
				Vec3 pos = nearestEntity.position();
				player.hurtMarked = true;
				nearestEntity.hurt(DamageSource.MAGIC, 6);
				ServerLevel world = (ServerLevel) player.level;
				LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
				lightningBolt.moveTo(position.getX(), position.getY(), position.getZ(), 0.0F, 0.0F);
				world.addFreshEntity(lightningBolt);
				player.setDeltaMovement(0, 0, 0);
				nearestEntity.setDeltaMovement(look);
				player.moveTo(pos);
			}
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
		if (!pAttacker.getLevel().isClientSide() && pAttacker instanceof Player) {
			Level level = pAttacker.getLevel();
			BlockPos position = pTarget.blockPosition();
			LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
			lightningBolt.moveTo(position.getX(), position.getY(), position.getZ(), 0.0F, 0.0F);
			level.addFreshEntity(lightningBolt);
		}
		if (pTarget.getHealth() <= 0) {
			pTarget.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 1), pAttacker);
		}
		return super.hurtEnemy(pStack, pTarget, pAttacker);
	}

	public @NotNull Rarity getRarity(ItemStack pStack) {
		return EPIC;
	}
}
