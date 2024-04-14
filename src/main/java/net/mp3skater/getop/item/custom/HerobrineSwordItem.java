package net.mp3skater.getop.item.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.util.ModUtils;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.item.Rarity.EPIC;

public class HerobrineSwordItem extends SwordItem {
    public HerobrineSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        Double reach = GetOPCommonConfigs.HEROBRINE_SWORD_REACH_DISTANCE.get();

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

        if(!player.level.isClientSide) {
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
                ServerLevel world = ((ServerLevel) player.level);
                EntityType.LIGHTNING_BOLT.spawn(world, null, player, position,
                        MobSpawnType.TRIGGERED, true, true);
                player.setDeltaMovement(0, 0, 0);
                nearestEntity.setDeltaMovement(look);
                player.moveTo(pos);
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (!pAttacker.getLevel().isClientSide() && pAttacker instanceof Player player) {
            Level level = pAttacker.getLevel();
            BlockPos position = pTarget.blockPosition();
            EntityType.LIGHTNING_BOLT.spawn((ServerLevel) level, null, player, position,
                    MobSpawnType.TRIGGERED, true, true);
        }
        if(pTarget.getHealth() <= 0) {
            pTarget.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 1), pAttacker);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
    public Rarity getRarity(ItemStack pStack) {
        return EPIC;
    }
}