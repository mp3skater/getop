package net.mp3skater.getop.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.mp3skater.getop.item.ModItems;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class EndSceptreEntity extends ThrowableProjectile implements IAnimatable {
	public EndSceptreEntity(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
		super(p_37466_, p_37467_);
	}

	private int despawnTimer = 0;

	private final AnimationFactory factory = new AnimationFactory(this);

	// When an entity is hit, hurt it by 2f
	@Override
	protected void onHitEntity(EntityHitResult pResult) {
		super.onHitEntity(pResult);
		pResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 2.0F);
	}

	// When the entity hits something, from the ThrownEnderpearl class
	@Override
	protected void onHit(HitResult pResult) {
		super.onHit(pResult);

		for(int i = 0; i < 32; ++i) {
			this.level.addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
		}

		if(!this.level.isClientSide && !this.isRemoved()) {
			Entity entity = this.getOwner();

			if(entity != null) {
				entity.teleportTo(this.getX(), this.getY(), this.getZ());
				entity.resetFallDistance();
			}

			this.discard();
		}
	}

	@Override
	protected void defineSynchedData() {}

	// Called to update the entity's position/logic
	// From the ThrownEnderpearl class
	@Override
	public void tick() {
		Entity entity = this.getOwner();
		if(entity instanceof Player && !entity.isAlive()) {
			this.discard();
		} else {
			super.tick();

			despawnTimer++;

			// Despawn after 5 sec
			if(despawnTimer >= 100) {
				this.discard();
			}
		}
	}

	// No gravity
	@Override
	protected float getGravity() {
		return 0;
	}

	// From the ThrownEnderpearl class
	@Nullable
	@Override
	public Entity changeDimension(ServerLevel pServer, net.minecraftforge.common.util.ITeleporter teleporter) {
		Entity entity = this.getOwner();
		if(entity != null && entity.level.dimension() != pServer.dimension()) {
			this.setOwner(null);
		}

		return super.changeDimension(pServer, teleporter);
	}

	public float getBrightness() {
		return -1.0F;
	}

	// Sets the animation
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("tp_idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, "controller",
			1, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}