package net.mp3skater.getop.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.effect.FreezeEffect;
import net.mp3skater.getop.effect.ModEffect;
import org.jetbrains.annotations.NotNull;

public class IceScytheItem extends SwordItem {
    public IceScytheItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        Level level = pAttacker.getLevel();
        if(!level.isClientSide()) {
            pTarget.addEffect(new MobEffectInstance(new FreezeEffect(MobEffectCategory.HARMFUL, 3124690),
                    40, 1), pAttacker);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack>
    use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        Vec3 center = player.getEyePosition();
        double radius = GetOPCommonConfigs.ICE_SCYTHE_RADIUS.get();
        if(player.hasEffect(ModEffect.PAINITE_ARMOR_BOOST.get())) {
            radius = radius * 2;
        }

        double minX = center.x - radius;
        double minY = center.y - radius;
        double minZ = center.z - radius;
        double maxX = center.x + radius;
        double maxY = center.y + radius;
        double maxZ = center.z + radius;

        AABB boundingBox = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
        Iterable<Entity> entities = level.getEntities(player, boundingBox);
        for (Entity entity : entities) {
                if(entity instanceof LivingEntity && !entity.isAlive() && !level.isClientSide){
                    entity.hurt(DamageSource.MAGIC, 2);
                    ((LivingEntity) entity).addEffect(new MobEffectInstance(new FreezeEffect(MobEffectCategory.HARMFUL, 3124690),
                            50, 1), player);
                }
        }

        //damages weapon per hit
            ItemStack itemstack = player.getItemInHand(hand);
            itemstack.hurtAndBreak(1, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
        //gives the End Sceptre a cool down of half a sec after being used
            player.getCooldowns().addCooldown(this, 400);

        return super.use(level, player, hand);
    }

}
