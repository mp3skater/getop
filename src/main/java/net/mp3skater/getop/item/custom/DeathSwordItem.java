package net.mp3skater.getop.item.custom;

import net.minecraft.core.particles.ParticleTypes;
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

import java.util.Random;

import static net.minecraft.world.item.Rarity.RARE;

public class DeathSwordItem extends AxeItem implements RareItem {
	public DeathSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
		super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
		return false;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack>
	use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
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
			//player.sendMessage(new TextComponent("In a radius of " + GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get()
			//        + " blocks these entities will get damaged 25 hp: " + entities), player.getUUID());
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
			//damages weapon per hit
			ItemStack itemstack = player.getItemInHand(hand);
			itemstack.hurtAndBreak(1, player,
							player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
		}

		// Spawn the particles
		if(level.isClientSide) {
			Random random = new Random();

			// Spawn 20 damage indicator particles
			for (int i = 0; i < 40; i++) {
				double angle = i * Math.PI / 10;
				double offsetX = 3 * Math.cos(angle) * random.nextDouble();
				double offsetY = 3 * random.nextDouble();
				double offsetZ = 3 * Math.sin(angle) * random.nextDouble();
				Vec3 particlePos = start.add(look.x+offsetX-1, look.y+offsetY-1, look.z+offsetZ-1);
				double speed = 8 + random.nextDouble()*2;
				level.addParticle(ParticleTypes.DAMAGE_INDICATOR,
								particlePos.x, particlePos.y, particlePos.z,
								look.x*speed*random.nextDouble(),
								look.y*speed*random.nextDouble(),
								look.z*speed*random.nextDouble());
			}
		}

		//gives the End Sceptre a cool down of half a sec after being used
		player.getCooldowns().addCooldown(this, 60);

		return super.use(level, player, hand);
	}

	@Override
	public boolean hurtEnemy(@NotNull ItemStack pStack, LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
		if (!pAttacker.level.isClientSide()) {
			pTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 2), pAttacker);
		}
		return super.hurtEnemy(pStack, pTarget, pAttacker);
	}

	@Override
	public float getDestroySpeed(ItemStack pStack, BlockState pState) {
		return 1.0f;
	}
}
