package net.mp3skater.getop.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.enchantment.LightningStrikerEnchantment;
import org.jetbrains.annotations.NotNull;

public class HerobrineSwordItem extends SwordItem {
    public HerobrineSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 30, 1), pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public void onCreated(ItemStack stack, Level level, Player player) {
        stack.enchant(new LightningStrikerEnchantment(Enchantment.Rarity.RARE,
                EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}), 3);
        super.onCraftedBy(stack, level, player);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack>
    use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        Vec3 look = player.getLookAngle();
        //is required for the server to control the Players movement
        player.hurtMarked = true;
        ItemStack itemstack = player.getItemInHand(hand);
        ServerLevel s_level = ((ServerLevel) player.level);

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
                BlockPos tpable_pos = new BlockPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                BlockState tp = level.getBlockState(tpable_pos);
                BlockPos tpable_pos2 = new BlockPos(tpable_pos.getX(), tpable_pos.getY() + 1, tpable_pos.getZ());
                BlockState tp2 = level.getBlockState(tpable_pos2);
                boolean isnotBlock = state.isAir() || state.is(Blocks.GRASS) || state.is(Blocks.TALL_GRASS);
                if (!isnotBlock) {
                    break;
                }
            }
            assert vec_end != null : "Attention vec_end is null!!!";
            BlockPos block_end = new BlockPos(vec_end.x, vec_end.y, vec_end.z);
            EntityType.LIGHTNING_BOLT.spawn(s_level, null, null, block_end,
                    MobSpawnType.TRIGGERED, true, true);
            EntityType.LIGHTNING_BOLT.spawn(s_level, null, null, block_end,
                    MobSpawnType.TRIGGERED, true, true);
                //damages weapon per hit
                itemstack.hurtAndBreak(1, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
            }
            //gives the End Sceptre a cool down of half a sec after being used
            player.getCooldowns().addCooldown(this, 30);
        return super.use(level, player, hand);
    }
}
