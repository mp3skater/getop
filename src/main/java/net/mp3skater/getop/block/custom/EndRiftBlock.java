package net.mp3skater.getop.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.mp3skater.getop.util.ModUtils;
import net.mp3skater.getop.world.dimension.ModDimensions;

public class EndRiftBlock extends GlassBlock {
	private final ResourceKey<Level> dimension;
	private final Vec3 location;

	public EndRiftBlock(Properties properties, ResourceKey<Level> dimension, Vec3 location) {
		super(properties.noCollission().noOcclusion()); // Makes the block walkthrough and see-through
		this.dimension = dimension;
		this.location = location;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		// Tells the game to render this block as a model
		return RenderShape.MODEL;
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		// Teleport player when they move inside the block
		if (entity instanceof ServerPlayer serverPlayer) {
			ModUtils.teleportEntityToDimension(serverPlayer, ModDimensions.GTDIM_KEY, new Vec3(0, 100, 90));
		}
		super.entityInside(state, level, pos, entity);
	}
}
