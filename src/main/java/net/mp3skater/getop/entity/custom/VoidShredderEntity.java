package net.mp3skater.getop.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class VoidShredderEntity extends FlyingMob implements IAnimatable, Enemy {
    private AnimationFactory factory = new AnimationFactory(this);
    public VoidShredderEntity(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
        this.moveControl = new VoidShredderEntity.VoidShredder_MoveControl(this);
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
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 8,
                true, false, (entity) -> Math.abs(entity.getY() - this.getY()) <= 4.0D));
        this.goalSelector.addGoal(7, new VoidShredderEntity.VoidshredderLookGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new VoidShredderEntity.RandomFloatAroundGoal(this)) ;
        this.goalSelector.addGoal(2, new VoidShredderEntity.Voidshredder_AttackGoal());
        this.targetSelector.addGoal(1, new Voidshredder_TargetGoal());
    }

    //Called when the entity is attacked
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
    public boolean isPersistenceRequired() {
        return true;
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


        public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }
        protected SoundEvent getAmbientSound() { return SoundEvents.GUARDIAN_AMBIENT; }
        protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.WITHER_HURT; }
        protected SoundEvent getDeathSound() { return SoundEvents.VEX_DEATH; }
    protected float getSoundVolume() {
        return 5f;
    }
    
    static class RandomFloatAroundGoal extends Goal {
        private final VoidShredderEntity voidshredder;

        public RandomFloatAroundGoal(VoidShredderEntity p_32783_) {
            this.voidshredder = p_32783_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            MoveControl movecontrol = this.voidshredder.getMoveControl();
            if (!movecontrol.hasWanted()) {
                return true;
            } else {
                double d0 = movecontrol.getWantedX() - this.voidshredder.getX();
                double d1 = movecontrol.getWantedY() - this.voidshredder.getY();
                double d2 = movecontrol.getWantedZ() - this.voidshredder.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Random random = this.voidshredder.getRandom();
            double d0 = this.voidshredder.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.voidshredder.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.voidshredder.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.voidshredder.getMoveControl().setWantedPosition(d0, d1, d2, 0.3D);
        }
    }

    static class VoidshredderLookGoal extends Goal {
        private final VoidShredderEntity voidShredder;

        public VoidshredderLookGoal(VoidShredderEntity p_32762_) {
            this.voidShredder = p_32762_;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.voidShredder.getTarget() == null) {
                Vec3 vec3 = this.voidShredder.getDeltaMovement();
                this.voidShredder.setYRot(-((float) Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI));
                this.voidShredder.yBodyRot = this.voidShredder.getYRot();
            } else {
                LivingEntity livingentity = this.voidShredder.getTarget();
                double d0 = 64.0D;
                if (livingentity.distanceToSqr(this.voidShredder) < 4096.0D) {
                    double d1 = livingentity.getX() - this.voidShredder.getX();
                    double d2 = livingentity.getZ() - this.voidShredder.getZ();
                    this.voidShredder.setYRot(-((float)Mth.atan2(d1, d2)) * (180F / (float)Math.PI));
                    this.voidShredder.yBodyRot = this.voidShredder.getYRot();
                }
            }

        }
    }

    static class VoidShredder_MoveControl extends MoveControl {
        private final VoidShredderEntity voidShredder;
        private int floatDuration;

        public VoidShredder_MoveControl(VoidShredderEntity entity) {
            super(entity);
            this.voidShredder = entity;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.voidShredder.getRandom().nextInt(5) + 2;
                    Vec3 vec3 = new Vec3(this.wantedX - this.voidShredder.getX(), this.wantedY - this.voidShredder.getY(), this.wantedZ - this.voidShredder.getZ());
                    double d0 = vec3.length();
                    vec3 = vec3.normalize();
                    if (this.canReach(vec3, Mth.ceil(d0))) {
                        this.voidShredder.setDeltaMovement(this.voidShredder.getDeltaMovement().add(vec3.scale(2d)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }

            }
        }
        private boolean canReach(Vec3 vec3, int i1) {
            AABB aabb = new AABB(this.voidShredder.getBoundingBox().maxX, this.voidShredder.getBoundingBox().maxY,
                    this.voidShredder.getBoundingBox().maxZ, this.voidShredder.getBoundingBox().minX,
                    this.voidShredder.getBoundingBox().minY, this.voidShredder.getBoundingBox().minZ);

            for(int i = 1; i < i1; ++i) {
                aabb = aabb.move(vec3);
                if (!this.voidShredder.level.noCollision(this.voidShredder, aabb)) {
                    return false;
                }
            }

            return true;
        }
    }

    class Voidshredder_TargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
        private int nextScanTick = reducedTickDelay(20);

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
            } else {
                this.nextScanTick = reducedTickDelay(60);
                List<Player> list = VoidShredderEntity.this.level.getNearbyPlayers(this.attackTargeting, VoidShredderEntity.this, VoidShredderEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

                    for(Player player : list) {
                        if (VoidShredderEntity.this.canAttack(player, TargetingConditions.DEFAULT)) {
                            VoidShredderEntity.this.setTarget(player);
                            return true;
                        }
                    }
                }

            }
            return false;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = VoidShredderEntity.this.getTarget();
            return livingentity != null && VoidShredderEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }
    }


    class Voidshredder_AttackGoal extends VoidShredderEntity.Voidshredder_TargetGoal {
        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return true;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = VoidShredderEntity.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (livingentity instanceof Player player) {
                    if (livingentity.isSpectator() || player.isCreative()) {
                        return false;
                    }
                }
                return this.canUse();
            }
        }
    }
}
