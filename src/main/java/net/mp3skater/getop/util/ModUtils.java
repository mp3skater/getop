package net.mp3skater.getop.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.mp3skater.getop.GetOP;

import java.util.Objects;
import java.util.function.Function;

public class ModUtils {
	public static void teleportEntityToDimension(Player player, ResourceKey<Level> destinationType, Vec3 location, Item usedItem) {
		if (player.level.isClientSide) return;

		ServerLevel destinationWorld = Objects.requireNonNull(player.getServer()).getLevel(destinationType);

		if (destinationWorld != null && player.level.dimension() != destinationType) {
			GetOP.LOGGER.info("Player: {} tries to change to dimension: {} using {}",
				player.getDisplayName().getString(),
				destinationWorld.dimension().location(),
				usedItem != null ? usedItem.getDescription().getString() : "null");

			player.changeDimension(destinationWorld, new SimpleTeleporter(location));
		} else {
			// Log more details for debugging
			if (destinationWorld == null) {
				GetOP.LOGGER.error("Destination world is null! Make sure the dimension {} is registered and available on the server.", destinationType.location());
			} else {
				GetOP.LOGGER.warn("Player is already in the target dimension: {}", destinationType.location());
			}

			// Optional: Log available dimensions for more context
			player.getServer().getAllLevels().forEach(level ->
				GetOP.LOGGER.info("Available dimension: {}", level.dimension().location())
			);
		}
	}

	private record SimpleTeleporter(Vec3 location) implements ITeleporter {

		@Override
			public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
				// Teleport entity to the specified location in the new dimension
				entity.teleportTo(location.x(), location.y(), location.z());
				entity.setYRot(yaw); // yaw
				entity.setXRot(entity.getXRot()); // pitch
				return repositionEntity.apply(true);
			}
		}

	// Return true if tp position isn't in a wall
	public static boolean tpAblePosAir(Vec3 vec_end, Level level) {
		BlockPos block_end = new BlockPos(vec_end);
		BlockState tp = level.getBlockState(block_end);

		BlockPos tpable_pos = block_end.above();
		BlockState tp2 = level.getBlockState(tpable_pos);

		boolean isTp_able = !tp.isSolidRender(level, block_end);
		boolean isTp_able2 = !tp2.isSolidRender(level, tpable_pos);

		return isTp_able && isTp_able2;
	}

	// Spawnable position for a structure, needs to be in a big air gap
	public static boolean spawnablePos(BlockState checkingBlock, int height, NoiseColumn baseColumn) {
		return checkingBlock.isAir() && // Air gap found
			baseColumn.getBlock(height + 1).isAir() && // big air gap
			baseColumn.getBlock(height + 2).isAir() && // bigger air gap
			baseColumn.getBlock(height + 3).isAir(); // big enough air gap
	}
}