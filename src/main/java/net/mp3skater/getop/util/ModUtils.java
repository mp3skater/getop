package net.mp3skater.getop.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ModUtils {
    // Return true if tp position isn't in a wall
    public static boolean TpAblePos_Air(Vec3 vec_end, Level level) {
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
                baseColumn.getBlock(height+1).isAir() && // big air gap
                baseColumn.getBlock(height+2).isAir() && // bigger air gap
                baseColumn.getBlock(height+3).isAir(); // big enough air gap
    }
}