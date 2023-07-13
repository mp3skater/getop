package net.mp3skater.getop.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;

public class VoidShredderEntity extends FlyingMob implements IAnimatable, Enemy {
    private AnimationFactory factory = new AnimationFactory(this);
    public VoidShredderEntity(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.xpReward = 20;
        this.moveControl = new VoidShredderMoveControl(this);
        this.lookControl = new VoidShredderEntity.VoidShredderLookControl(this);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 85.0D)
                .add(Attributes.ATTACK_DAMAGE, 39.0f)
                .add(Attributes.ATTACK_SPEED, 2.5f)
                .add(Attributes.MOVEMENT_SPEED, 20f)
                .add(Attributes.FLYING_SPEED, 1f).build();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
    }

    //Goals of Entity
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new ChargeTargetGoal());
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(6, new RandomFloatAroundGoal());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness() {
        return -1.0F;
    }

    //Sets the animation in different States
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        Vec3 v = getDeltaMovement();
        if(v.y > 0) {
            System.out.println("Entity is going up");
            //    event.getController().setAnimation(new AnimationBuilder().addAnimation("forward", true));
            //    return PlayState.CONTINUE;
        }
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

    class ChargeTargetGoal extends Goal {
        public ChargeTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (VoidShredderEntity.this.getTarget() != null && !VoidShredderEntity.this.getMoveControl().hasWanted() && VoidShredderEntity.this.random.nextInt(reducedTickDelay(3)) == 0) {
                return VoidShredderEntity.this.distanceToSqr(VoidShredderEntity.this.getTarget()) > 4.0D;
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return VoidShredderEntity.this.getMoveControl().hasWanted() && VoidShredderEntity.this.getTarget() != null && VoidShredderEntity.this.getTarget().isAlive();
        }
        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            LivingEntity livingentity = VoidShredderEntity.this.getTarget();
            if (livingentity != null) {
                Vec3 vec3 = livingentity.getEyePosition();
                VoidShredderEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y - 1, vec3.z, 8.0D);
            }

            VoidShredderEntity.this.playSound(SoundEvents.VEX_CHARGE, 2.0F, 1.0F);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = VoidShredderEntity.this.getTarget();
            if (livingentity != null) {
                int intMinX = (int) Math.floor(VoidShredderEntity.this.getBoundingBox().minX - 1);
                int intMinY = (int) Math.floor(VoidShredderEntity.this.getBoundingBox().minY - 1);
                int intMinZ = (int) Math.floor(VoidShredderEntity.this.getBoundingBox().minZ - 1);
                int intMaxX = (int) Math.ceil(VoidShredderEntity.this.getBoundingBox().maxX + 1);
                int intMaxY = (int) Math.ceil(VoidShredderEntity.this.getBoundingBox().maxY + 1);
                int intMaxZ = (int) Math.ceil(VoidShredderEntity.this.getBoundingBox().maxZ + 1);
                BoundingBox box = new BoundingBox(intMinX, intMinY, intMinZ, intMaxX, intMaxY, intMaxZ);
                if (VoidShredderEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    VoidShredderEntity.this.doHurtTarget(livingentity);
                } else {
                    double d0 = VoidShredderEntity.this.distanceToSqr(livingentity);
                    if (d0 < 9.0D) {
                        Vec3 vec3 = livingentity.getEyePosition();
                        VoidShredderEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
                    }
                }

            }
        }
    }

    //Called when the entity is attacked
        public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getDirectEntity() instanceof LightningBolt && pSource.getEntity() instanceof Player) {
                super.hurt(pSource, 1000.0F);
                return true;
            } else {
                return super.hurt(pSource, pAmount);
            }
        }
    public boolean isPersistenceRequired() {
        return true;
    }

    class VoidShredderLookControl extends LookControl {
        public VoidShredderLookControl(Mob pMob) {
            super(pMob);
        }
        //Updates look every tick
        public void tick() {
        }
    }

    class VoidShredderMoveControl extends MoveControl {
        public VoidShredderMoveControl(VoidShredderEntity p_34062_) {
            super(p_34062_);
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - VoidShredderEntity.this.getX(), this.wantedY - VoidShredderEntity.this.getY(), this.wantedZ - VoidShredderEntity.this.getZ());
                double d0 = vec3.length();
                if (d0 < VoidShredderEntity.this.getBoundingBox().getSize()) {
                    this.operation = MoveControl.Operation.WAIT;
                    VoidShredderEntity.this.setDeltaMovement(VoidShredderEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    VoidShredderEntity.this.setDeltaMovement(VoidShredderEntity.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05D / d0)));
                    if (VoidShredderEntity.this.getTarget() == null) {
                        Vec3 vec31 = VoidShredderEntity.this.getDeltaMovement();
                        VoidShredderEntity.this.setYRot(-((float)Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
                        VoidShredderEntity.this.yBodyRot = VoidShredderEntity.this.getYRot();
                    } else {
                        double d2 = VoidShredderEntity.this.getTarget().getX() - VoidShredderEntity.this.getX();
                        double d1 = VoidShredderEntity.this.getTarget().getZ() - VoidShredderEntity.this.getZ();
                        VoidShredderEntity.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                        VoidShredderEntity.this.yBodyRot = VoidShredderEntity.this.getYRot();
                    }
                }

            }
        }
    }


    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    public SoundSource getSoundSource() { return SoundSource.HOSTILE; }
    protected SoundEvent getAmbientSound() { return SoundEvents.GUARDIAN_AMBIENT; }
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.WITHER_HURT; }
    protected SoundEvent getDeathSound() { return SoundEvents.VEX_DEATH; }
    protected float getSoundVolume() { return 5f; }

    class RandomFloatAroundGoal extends Goal {
        public RandomFloatAroundGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !VoidShredderEntity.this.getMoveControl().hasWanted() && VoidShredderEntity.this.random.nextInt(reducedTickDelay(3)) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            BlockPos blockpos = VoidShredderEntity.this.blockPosition();

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(VoidShredderEntity.this.random.nextInt(15) - 7, VoidShredderEntity.this.random.nextInt(11) - 5, VoidShredderEntity.this.random.nextInt(15) - 7);
                if (VoidShredderEntity.this.level.isEmptyBlock(blockpos1)) {
                    VoidShredderEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (VoidShredderEntity.this.getTarget() == null) {
                        VoidShredderEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }
}
