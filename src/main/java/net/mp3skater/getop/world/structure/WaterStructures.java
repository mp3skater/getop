package net.mp3skater.getop.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.mp3skater.getop.GetOP;
import org.apache.logging.log4j.Level;

import java.util.Optional;

public class WaterStructures extends StructureFeature<JigsawConfiguration> {

    public WaterStructures() {
        // Create the pieces layout of the structure and give it to the game
        super(JigsawConfiguration.CODEC, WaterStructures::createPiecesGenerator, PostPlacementProcessor.NONE);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        ChunkPos chunkpos = context.chunkPos();

        // No ocean structures nearby
        return !context.chunkGenerator().hasFeatureChunkInRange(BuiltinStructureSets.OCEAN_MONUMENTS, context.seed(), chunkpos.x, chunkpos.z, 10) &&
            !context.chunkGenerator().hasFeatureChunkInRange(BuiltinStructureSets.OCEAN_RUINS, context.seed(), chunkpos.x, chunkpos.z, 5) &&
            !context.chunkGenerator().hasFeatureChunkInRange(BuiltinStructureSets.SHIPWRECKS, context.seed(), chunkpos.x, chunkpos.z, 5);
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

        // Returning an empty optional tells the game to skip this spot as it will not generate the structure.
        if (!WaterStructures.isFeatureChunk(context)) {
            return Optional.empty();
        }

        // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);

        // Find the top Y value of the land and then offset our structure to 60 blocks above that.
        // WORLD_SURFACE_WG will stop at top water, so we don't accidentally put our structure into the ocean if it is a super deep ocean.
        int topLandY = context.chunkGenerator().getFirstFreeHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Types.OCEAN_FLOOR, context.heightAccessor());
        blockpos = blockpos.above(topLandY);

        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
                JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, blockpos, false,  false);

        if(structurePiecesGenerator.isPresent()) {
            GetOP.LOGGER.log(Level.DEBUG, "Sky Structures at {}", blockpos);
        }

        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
        return structurePiecesGenerator;
    }
}