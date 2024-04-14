package net.mp3skater.getop.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.mp3skater.getop.world.feature.ModConfiguredFeatures;
import net.mp3skater.getop.world.feature.ModOrePlacement;

import static net.mp3skater.getop.config.GetOPCommonConfigs.PAINITE_ORE_VEIN_SIZE;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> END_PAINITE_ORE_PLACED = PlacementUtils.register("end_painite_ore_placed",
            ModConfiguredFeatures.PAINITE_ORE_BLOCK, ModOrePlacement.commonOrePlacement(PAINITE_ORE_VEIN_SIZE.get(), // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-15), VerticalAnchor.aboveBottom(15))));


}
