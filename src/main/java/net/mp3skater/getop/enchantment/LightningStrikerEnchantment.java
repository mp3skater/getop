package net.mp3skater.getop.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LightningStrikerEnchantment  extends Enchantment {
    public LightningStrikerEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

        @Override
        public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
            if(!pAttacker.level.isClientSide()){
                ServerLevel level = ((ServerLevel) pAttacker.level);
                BlockPos position = pTarget.blockPosition();
                boolean skyclear = false; {
                    for (int i = 0; i < 127; i++) {
                        if (i > 16) {
                            i+=16;
                        }
                        BlockPos pos = new BlockPos(pTarget.getPosition(1));
                        pos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ());
                        BlockState state = pTarget.level.getBlockState(pos);
                        if (state.getBlock() instanceof AirBlock|| state.is(Blocks.WATER) || state.is(Blocks.LAVA)
                                || state.is(Blocks.POWDER_SNOW) || state.is(Blocks.TALL_SEAGRASS)
                                || state.is(Blocks.GRASS) || state.is(Blocks.TALL_GRASS)
                                || state.is(Blocks.GLASS) || state.is(Blocks.GLASS_PANE)) {
                            skyclear = true;
                            break;
                        }
                    }
                }
                if(skyclear) {
                    EntityType.LIGHTNING_BOLT.spawn(level, null, null, position,
                            MobSpawnType.TRIGGERED, true, true);
                    EntityType.LIGHTNING_BOLT.spawn(level, null, null, position,
                            MobSpawnType.TRIGGERED, true, true);
                    EntityType.LIGHTNING_BOLT.spawn(level, null, null, position,
                            MobSpawnType.TRIGGERED, true, true);

                }
            super.doPostAttack(pAttacker, pTarget, pLevel);
        }
    }
}
