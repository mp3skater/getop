package net.mp3skater.getop.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class RadioactiveBlock extends Block {
    public RadioactiveBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(!pLevel.isClientSide()) {
            if(pEntity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 21, 20));
            }
            if(pEntity instanceof LivingEntity livingEntity && livingEntity.verticalCollision) {
                Vec3 v = livingEntity.getDeltaMovement();
                float f = (float) v.y;
                livingEntity.setDeltaMovement(v.x * 0.2, Mth.fastInvCubeRoot(f) + 0.8, v.x * 0.2);
            }
        }


        super.stepOn(pLevel, pPos, pState, pEntity);
    }
}