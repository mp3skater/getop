package net.mp3skater.getop.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class EndSceptreEntity extends ThrownEnderpearl implements IAnimatable {
	public EndSceptreEntity(EntityType<? extends ThrownEnderpearl> p_37491_, Level p_37492_) {
		super(p_37491_, p_37492_);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	// When an entity is hit, hurt it by 2f
	protected void onHitEntity(EntityHitResult pResult) {
		super.onHitEntity(pResult);
		pResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 2.0F);
	}

	// When the entity hits something, from the ThrownEnderpearl class
	protected void onHit(HitResult pResult) {
		super.onHit(pResult);

		for(int i = 0; i < 32; ++i) {
			this.level.addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
		}

		if(!this.level.isClientSide && !this.isRemoved()) {
			Entity entity = this.getOwner();

			if(entity instanceof ServerPlayer serverplayer) {

				if(serverplayer.connection.getConnection().isConnected() && serverplayer.level == this.level && !serverplayer.isSleeping()) {
					net.minecraftforge.event.entity.EntityTeleportEvent.EnderPearl event = net.minecraftforge.event.ForgeEventFactory.onEnderPearlLand(serverplayer, this.getX(), this.getY(), this.getZ(), this, 5.0F);

					if(!event.isCanceled()) { // Don't indent to lower patch size
						if(entity.isPassenger()) {
							serverplayer.dismountTo(this.getX(), this.getY(), this.getZ());
						} else {
							entity.teleportTo(this.getX(), this.getY(), this.getZ());
						}

						entity.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());
						entity.resetFallDistance();
					} //Forge: End
				}
			} else if(entity != null) {
				entity.teleportTo(this.getX(), this.getY(), this.getZ());
				entity.resetFallDistance();
			}

			this.discard();
		}
	}

	// Called to update the entity's position/logic
	// From the ThrownEnderpearl class
	public void tick() {
		Entity entity = this.getOwner();
		if(entity instanceof Player && !entity.isAlive()) {
			this.discard();
		} else {
			super.tick();
		}
	}

	// From the ThrownEnderpearl class
	@Nullable
	public Entity changeDimension(ServerLevel pServer, net.minecraftforge.common.util.ITeleporter teleporter) {
		Entity entity = this.getOwner();
		if(entity != null && entity.level.dimension() != pServer.dimension()) {
			this.setOwner((Entity) null);
		}

		return super.changeDimension(pServer, teleporter);
	}

	public float getBrightness() {
		return -1.0F;
	}

	// Sets the animation
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("Idle", true));
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