package net.mp3skater.getop.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class PickasheFireballEntity extends LargeFireball implements IAnimatable {
	public PickasheFireballEntity(EntityType<? extends PickasheFireballEntity> p_37199_, Level p_37200_) {
		super(p_37199_, p_37200_);
		this.power = 4f;
	}

	private final AnimationFactory factory = new AnimationFactory(this);

	// Explosion power
	float power;

	@Override
	protected void defineSynchedData() {}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	protected void onHit(HitResult pResult) {
		if (!this.level.isClientSide) {
			boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
			this.level.explode(null, this.getX(), this.getY(), this.getZ(), power, false, flag ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
			this.discard();
		}
	}

	// Called to update the entity's position/logic
	// From the ThrownEnderpearl class
	@Override
	public void tick() {
		Entity entity = this.getOwner();
		if(entity instanceof Player && !entity.isAlive()) {
			this.discard();
		} else {
			super.tick();
		}
	}

	public float getBrightness() {
		return -1.0F;
	}

	// Sets the animation
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("roll", true));
		event.getController().setAnimation(new AnimationBuilder().addAnimation("spin", true));
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