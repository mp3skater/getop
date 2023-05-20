package net.mp3skater.getop.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class VoidShredderEntity extends FlyingMob implements IAnimatable, Enemy {
    private AnimationFactory factory = new AnimationFactory(this);
    public VoidShredderEntity(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 50;
        //this.moveControl = new VoidShredder.VoidShredderMoveControl(this);
        //this.lookControl = new VoidShredder.VoidShredderLookControl(this);
    }
    
    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 85.0D)
                .add(Attributes.ATTACK_DAMAGE, 20.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f)
                .add(Attributes.FLYING_SPEED, 0.6f).build();
    }

    //Goals of Entity
    protected void registerGoals() {
        //this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1d, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8f));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Shulker.class, false));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        //this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1d));
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    //Called when the entity is attacked.
        public boolean hurt(DamageSource pSource, float pAmount) {
            if (this.isInvulnerableTo(pSource)) {
                return false;
            } else if (pSource.getDirectEntity() instanceof LightningBolt && pSource.getEntity() instanceof Player) {
                super.hurt(pSource, 1000.0F);
                return true;
            } else {
                return super.hurt(pSource, pAmount);
            }
        }

    //Sets the animation in different States
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("forward", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
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
        protected SoundEvent getAmbientSound() { return SoundEvents.GUARDIAN_AMBIENT; }
        protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
            return SoundEvents.WITHER_HURT;
        }
        protected SoundEvent getDeathSound() {
            return SoundEvents.VEX_DEATH;
        }
        protected float getSoundVolume() {
        return 1f;
    }
}
