package net.mp3skater.getop.world.dimension;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.mp3skater.getop.GetOP;

public class ModDimensions {
    public static final ResourceKey<Level> GTDIM_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(GetOP.MOD_ID, "gtdim_key"));
    public static final ResourceKey<DimensionType> GTDIM_TYPE =
            ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, GTDIM_KEY.getRegistryName());

    public static void register() {
        System.out.println("Registering ModDimensions for " + GetOP.MOD_ID);
    }
}
