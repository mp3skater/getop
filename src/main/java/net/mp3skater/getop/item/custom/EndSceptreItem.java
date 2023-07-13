package net.mp3skater.getop.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.effect.ModEffect;
import org.jetbrains.annotations.NotNull;

public class EndSceptreItem extends SwordItem {
    public static int COOLDOWN_EVERY_7_TPS = 0;
    public EndSceptreItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if(pAttacker.hasEffect(ModEffect.PAINITE_ARMOR_BOOST.get()))pTarget.hurt(DamageSource.MAGIC, 5);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack>
    use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        Vec3 look = player.getLookAngle();
        //is required for the server to control the Players movement
            player.hurtMarked = true;
        ItemStack itemstack = player.getItemInHand(hand);

        if (hand == InteractionHand.MAIN_HAND) {
            float f = player.getXRot();
            float f1 = player.getYRot();
            Vec3 vec3 = player.getEyePosition();
            float f2 = Mth.cos(-f1 * ((float) Math.PI / 180f) - (float) Math.PI);
            float f3 = Mth.sin(-f1 * ((float) Math.PI / 180f) - (float) Math.PI);
            float f4 = -Mth.cos(-f * ((float) Math.PI / 180f));
            float f5 = Mth.sin(-f * ((float) Math.PI / 180f));
            float f6 = f3 * f4;
            float f7 = f2 * f4;
            double d0;
            Vec3 vec_end = null;
            boolean tped = false;

            for (d0 = 0d; d0 <= GetOPCommonConfigs.END_SCEPTRE_REACH_DISTANCE.get(); d0++) {
                vec_end = vec3.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
                BlockPos blockpos = new BlockPos(vec_end);
                BlockState state = level.getBlockState(blockpos);
                BlockPos tpable_pos = blockpos.above();
                BlockState tp = level.getBlockState(tpable_pos);
                BlockPos tpable_pos2 = blockpos.above();
                BlockState tp2 = level.getBlockState(tpable_pos2);
                boolean isnotBlock = state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.LAVA)
                        || state.is(Blocks.POWDER_SNOW) || state.is(Blocks.TALL_SEAGRASS)
                        || state.is(Blocks.GRASS) || state.is(Blocks.TALL_GRASS);
                boolean is_nottpable = tp.isAir() || tp.is(Blocks.WATER) || tp.is(Blocks.LAVA)
                        || tp.is(Blocks.POWDER_SNOW) || tp.is(Blocks.TALL_SEAGRASS)
                        || tp.is(Blocks.GRASS) || tp.is(Blocks.TALL_GRASS);
                boolean is_nottpable2 = tp2.isAir() || tp2.is(Blocks.WATER) || tp2.is(Blocks.LAVA)
                        || tp2.is(Blocks.POWDER_SNOW) || tp2.is(Blocks.TALL_SEAGRASS)
                        || tp2.is(Blocks.GRASS) || tp2.is(Blocks.TALL_GRASS);
                if (!isnotBlock && is_nottpable && is_nottpable2) {
                        tped = true;
                        break;
                }
            }
            assert vec_end != null : "Attention vec_end is null!!!";
            BlockPos block_end = new BlockPos(vec_end);

            if (tped) {
                player.teleportTo(block_end.getX() + 0.5d, block_end.getY() + 1, block_end.getZ() + 0.5d);
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 4, 4), player);
                player.setDeltaMovement(0, 0, 0);
                //Painite-Armor makes you get no "fall-damage"..
                if(!player.hasEffect(ModEffect.PAINITE_ARMOR_BOOST.get())) {
                    player.hurt(DamageSource.MAGIC, 1);
                }
            } else if (!level.isClientSide()) {
                // differentiates between dashing upwards and dashing downwards,
                // giving a boost in the corresponding direction
                if (look.y >= 0) {
                    player.setDeltaMovement(look.x * 2d, look.y + 0.7d, look.z * 2d);
                } else {
                    player.setDeltaMovement(look.x * 2d, look.y, look.z * 2d);
                }
                if(!player.hasEffect(ModEffect.PAINITE_ARMOR_BOOST.get())) {
                    //damages weapon per hit
                    itemstack.hurtAndBreak(1, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
                }
            }
                //gives the End Sceptre a cool down of half a sec after being used
                    player.getCooldowns().addCooldown(this, 10);
                    COOLDOWN_EVERY_7_TPS++;
                    //if(COOLDOWN_EVERY_7_TPS >= 7){
                    //    player.getCooldowns().addCooldown(this, 200);
                    //    player.sendMessage(new TextComponent("End Sceptre 10s cooldown.."
                    //            //+ " every 7 uses without Painite-Armor"
                    //            ), player.getUUID());
                    //    COOLDOWN_EVERY_7_TPS = 0;
                    //}
            }
        return super.use(level, player, hand);
    }
}