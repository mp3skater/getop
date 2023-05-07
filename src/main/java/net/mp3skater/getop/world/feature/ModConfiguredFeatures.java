package net.mp3skater.getop.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.mp3skater.getop.block.ModBlocks;
import net.mp3skater.getop.config.GetOPClientConfigs;
import net.mp3skater.getop.config.GetOPCommonConfigs;

import java.util.List;
public class ModConfiguredFeatures {

    public static final List<OreConfiguration.TargetBlockState> END_PAINITE_ORE = List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE),
                    ModBlocks.PAINITE_ORE_BLOCK.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration,
            ?>> PAINITE_ORE_BLOCK = FeatureUtils.register("painite_ore_block",
            Feature.ORE, new OreConfiguration(END_PAINITE_ORE, GetOPCommonConfigs.PAINITE_ORE_VEIN_SIZE.get()));

}