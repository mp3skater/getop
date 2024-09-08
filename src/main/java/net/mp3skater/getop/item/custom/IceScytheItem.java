package net.mp3skater.getop.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.effect.GetOPEffects;
import net.mp3skater.getop.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IceScytheItem extends SwordItem implements RareItem {
	public IceScytheItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
		super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
	}

	@Override
	public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
		Level level = pAttacker.getLevel();
		ModUtils.explosionParticleEffect(level, pTarget.getEyePosition(), 40, 2f,
			new ArrayList<>(List.of(ParticleTypes.ENCHANTED_HIT)));
		if (!level.isClientSide()) {
			pTarget.addEffect(new MobEffectInstance(GetOPEffects.FREEZE.get(), 40, 1), pAttacker);
		}
		return super.hurtEnemy(pStack, pTarget, pAttacker);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		pTooltipComponents.add(new TranslatableComponent("item.getop.ice_scythe.tooltip"));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		// Spawn particles on the client
		if(level.isClientSide) ModUtils.explosionParticleEffect(level, player.getEyePosition(), 40, 2f,
			new ArrayList<>(Arrays.asList(ParticleTypes.SNOWFLAKE, ParticleTypes.ENCHANTED_HIT)));

		// Give the effect on the server
		else addEffectToEntities(player, level);

		// Damages weapon per use
		ItemStack itemstack = player.getItemInHand(hand);
		itemstack.hurtAndBreak(1, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));

		// Gives the Ice Scythe a cooldown of 20 seconds (400 ticks) after being used
		player.getCooldowns().addCooldown(this, 100);

		return super.use(level, player, hand);
	}

	private void addEffectToEntities(Player player, Level level) {
		Vec3 center = player.getEyePosition();
		double radius = GetOPCommonConfigs.ICE_SCYTHE_RADIUS.get();

		double minX = center.x - radius;
		double minY = center.y - radius;
		double minZ = center.z - radius;
		double maxX = center.x + radius;
		double maxY = center.y + radius;
		double maxZ = center.z + radius;

		AABB boundingBox = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
		Iterable<Entity> entities = level.getEntities(player, boundingBox);
		for(Entity entity : entities) {
			if(entity instanceof LivingEntity && entity.isAlive() && !level.isClientSide()) {
				entity.hurt(DamageSource.MAGIC, 2);
				((LivingEntity) entity).addEffect(new MobEffectInstance(GetOPEffects.FREEZE.get(), 50, 1), player);
			}
		}
	}
}