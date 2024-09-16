package net.mp3skater.getop.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.block.ModBlocks;
import net.mp3skater.getop.util.ModUtils;

public class TpStepOnBlock extends Block {
    private final ResourceKey<Level> dimension;
    private final Vec3 location;
    public TpStepOnBlock(Properties properties, ResourceKey<Level> dimension, Vec3 location) {
        super(properties);
			this.dimension = dimension;
			this.location = location;
		}

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        // Changing to GetOP Dimension
        if(pEntity instanceof ServerPlayer serverPlayer) {
            ModUtils.teleportEntityToDimension(serverPlayer, dimension, location);
        }

        //pLevel.setBlockAndUpdate(pPos, pushEntitiesUp(pState, ModBlocks. Blocks.DIRT.defaultBlockState(), pLevel, pPos));

        super.stepOn(pLevel, pPos, pState, pEntity);
    }
}