package net.mp3skater.getop.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
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
import net.mp3skater.getop.util.ModUtils;
import org.apache.logging.log4j.Level;

import java.util.Optional;

public class NetherStructures extends StructureFeature<JigsawConfiguration> {

    public NetherStructures() {
        super(JigsawConfiguration.CODEC, NetherStructures::createPiecesGenerator, PostPlacementProcessor.NONE);
    }
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    /*
     * If you are doing Nether structures, you'll probably want to spawn your structure on top of ledges.
     * Best way to do that is to use getBaseColumn to grab a column of blocks at the structure's x/z position.
     * Then loop through it and look for land with air above it and set blockpos's Y value to it.
     * Make sure to set the final boolean in JigsawPlacement.addPieces to false so
     * that the structure spawns at blockpos's y value instead of placing the structure on the Bedrock roof!
     *
     * Also, please for the love of god, do not do dimension checking here.
     * If you do and another mod's dimension is trying to spawn your structure,
     * the locate command will make minecraft hang forever and break the game.
     * Use the biome tags for where to spawn the structure and users can datapack
     * it to spawn in specific biomes that aren't in the dimension they don't like if they wish.
     */
    private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        ChunkPos chunkpos = context.chunkPos();

        // Returns position as valid if there are no nether structures within a distance of 5 chunks
        return !context.chunkGenerator().hasFeatureChunkInRange(BuiltinStructureSets.NETHER_COMPLEXES, context.seed(), chunkpos.x, chunkpos.z, 5) &&
            context.validBiome().test(context.chunkGenerator().getNoiseBiome(QuartPos.fromBlock(context.chunkPos().getMiddleBlockX()), QuartPos.fromBlock(64), QuartPos.fromBlock(context.chunkPos().getMiddleBlockZ())));
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

        // Returning an empty optional tells the game to skip this spot as it will not generate the structure.
        if (!NetherStructures.isFeatureChunk(context)) {
            return Optional.empty();
        }

        // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);

        // Find the first place where there is air on top of a block
        NoiseColumn baseColumn = context.chunkGenerator().getBaseColumn(blockpos.getX(), blockpos.getZ(), context.heightAccessor())
                ;
        int netherRoofHeight = context.chunkGenerator().getFirstFreeHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor())
                ;
        BlockState checkingBlock;
        boolean placefound = false;
        int height;
        for(height = context.getLowestY(blockpos.getX(), blockpos.getZ()); height<netherRoofHeight && !placefound; height++) {
            checkingBlock = baseColumn.getBlock(height);
            if(ModUtils.spawnablePos(checkingBlock, height, baseColumn))
                placefound = true;
        }

        blockpos = blockpos.atY(placefound? height : context.chunkGenerator().getSpawnHeight(context.heightAccessor()));

        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
                JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, blockpos, false,  false);

        if(structurePiecesGenerator.isPresent()) {
            GetOP.LOGGER.log(Level.DEBUG, "Sky Structures at {}", blockpos);
        }

        return structurePiecesGenerator;
    }
}