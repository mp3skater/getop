package net.mp3skater.getop.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.util.ModUtils;

public class TpStepThroughBlock extends Block {
    private final ResourceKey<Level> dimension;
    private final Vec3 location;

    public TpStepThroughBlock(Properties properties, ResourceKey<Level> dimension, Vec3 location) {
        super(properties);
        this.dimension = dimension;
        this.location = location;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        // Trigger when entity is inside the block
        if (pEntity instanceof ServerPlayer serverPlayer) {
            ModUtils.teleportEntityToDimension(serverPlayer, dimension, location);
        }

        super.entityInside(pState, pLevel, pPos, pEntity);
    }
}
