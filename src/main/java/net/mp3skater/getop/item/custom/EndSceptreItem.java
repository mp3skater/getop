package net.mp3skater.getop.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.effect.ModEffect;
import net.mp3skater.getop.util.ModUtils;

public class EndSceptreItem extends AxeItem implements RareItem{
    public EndSceptreItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack>
    use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            Double reach = GetOPCommonConfigs.END_SCEPTRE_REACH_DISTANCE.get();
            Double air_reach = GetOPCommonConfigs.END_SCEPTRE_AIR_DASH_DISTANCE.get();

            ItemStack stack = player.getItemInHand(hand);
            Vec3 look = player.getLookAngle();
            Vec3 eye = player.getEyePosition();
            Vec3 vec_end = null;
            boolean tped = false;

            float f = player.getXRot();
            float f1 = player.getYRot();
            float f2 = Mth.cos(-f1 * ((float) Math.PI / 180f) - (float) Math.PI);
            float f3 = Mth.sin(-f1 * ((float) Math.PI / 180f) - (float) Math.PI);
            float f4 = -Mth.cos(-f * ((float) Math.PI / 180f));
            float f5 = Mth.sin(-f * ((float) Math.PI / 180f));
            float f6 = f3 * f4; float f7 = f2 * f4;
            double d0;

            for (d0 = 0d; d0 <= reach; d0++) {
                vec_end = eye.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
                if(ModUtils.TpAblePos(vec_end, level)) {
                    tped = true;
                    break;
                }
            }

            if(tped) {
                player.hurtMarked = true;
                player.addEffect(new MobEffectInstance(ModEffect.FEATHER_FALL_EFFECT.get(), 1));
                BlockPos block_end = new BlockPos(vec_end);
                player.moveTo(block_end.getX() + 0.5d, block_end.getY() + 1, block_end.getZ() + 0.5d);
                player.causeFoodExhaustion(16f);
                player.getCooldowns().addCooldown(this, 8);
            } else {
                Vec3 vec_air = eye.add(look.x * air_reach, look.y * air_reach, look.z * air_reach);
                if(ModUtils.TpAblePos_Air(vec_air, level)) {
                    player.hurtMarked = true;
                    player.setDeltaMovement(0, 0, 0);
                    BlockPos block_end = new BlockPos(vec_air);
                    player.moveTo(block_end.getX() + 0.5d, block_end.getY() + 1, block_end.getZ() + 0.5d);
                    player.causeFoodExhaustion(16f);
                    player.getCooldowns().addCooldown(this, 8);
                    tped = true;
                }
            }
            stack.hurtAndBreak(1, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
        }
        return super.use(level, player, hand);
    }
}