package net.mp3skater.getop.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.GetOP;

public class ModUtils {

//    public void loadStructure(BlockPos pos, Level level, String name) {
//        ServerLevel server = (ServerLevel) level;
//        MinecraftServer minecraftserver = level.getServer();
//        StructureManager  structureManager = server.getStructureManager();
//        ResourceLocation loc = new ResourceLocation(GetOP.MOD_ID, name);
//        StructureTemplate template = structureManager.readStructure(minecraftserver, loc);
//
//        if (template != null) {
//            BlockState iblockstate = level.getBlockState(pos);
//            Placement placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
//                    .setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos) null)
//                    .setReplacedBlock((Block) null).setIgnoreStructureBlock(false);
//
//            template.addBlocksToWorldChunk(world, pos.add(0, 1, 0), placementsettings);
//        }
//    }
    public static boolean TpAblePos(Vec3 vec_end, Level level) {
        BlockPos block_end = new BlockPos(vec_end);
        BlockState state = level.getBlockState(block_end);

        BlockPos tpable_pos = block_end.above();
        BlockState tp = level.getBlockState(tpable_pos);

        BlockPos tpable_pos2 = block_end.above();
        BlockState tp2 = level.getBlockState(tpable_pos2);

        boolean isBlock = state.isSolidRender(level, block_end);
        boolean isTp_able = !tp.isSolidRender(level, tpable_pos);
        boolean isTp_able2 = !tp2.isSolidRender(level, tpable_pos2);

        return isBlock && isTp_able && isTp_able2;
    }
    public static boolean TpAblePos_Air(Vec3 vec_end, Level level) {
        BlockPos block_end = new BlockPos(vec_end);

        BlockPos tpable_pos = block_end.above();
        BlockState tp = level.getBlockState(tpable_pos);

        BlockPos tpable_pos2 = block_end.above();
        BlockState tp2 = level.getBlockState(tpable_pos2);

        boolean isTp_able = !tp.isSolidRender(level, tpable_pos);
        boolean isTp_able2 = !tp2.isSolidRender(level, tpable_pos2);

        return isTp_able && isTp_able2;
    }
}