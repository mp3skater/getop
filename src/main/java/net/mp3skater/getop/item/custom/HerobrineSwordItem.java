package net.mp3skater.getop.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
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

        if (!pAttacker.level.isClientSide()) {
            ServerLevel level = ((ServerLevel) pAttacker.level);
            BlockPos position = pTarget.blockPosition();
            boolean skyclear = false;
            {
                for (int i = 0; i < 127; i++) {
                    if (i > 16) {
                        i += 16;
                    }
                    BlockPos pos = new BlockPos(pTarget.getPosition(1));
                    pos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ());
                    BlockState state = pTarget.level.getBlockState(pos);
                    if (state.getBlock() instanceof AirBlock || state.is(Blocks.WATER) || state.is(Blocks.LAVA)
                            || state.is(Blocks.POWDER_SNOW) || state.is(Blocks.TALL_SEAGRASS)
                            || state.is(Blocks.GRASS) || state.is(Blocks.TALL_GRASS)
                            || state.is(Blocks.GLASS) || state.is(Blocks.GLASS_PANE)) {
                        skyclear = true;
                        break;
                    }
                }
            }
            if (skyclear) {
                EntityType.LIGHTNING_BOLT.spawn(level, null, null, position,
                        MobSpawnType.TRIGGERED, true, true);
                EntityType.LIGHTNING_BOLT.spawn(level, null, null, position,
                        MobSpawnType.TRIGGERED, true, true);
                EntityType.LIGHTNING_BOLT.spawn(level, null, null, position,
                        MobSpawnType.TRIGGERED, true, true);

            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}