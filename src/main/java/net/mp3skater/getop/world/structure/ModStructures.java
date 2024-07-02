package net.mp3skater.getop.world.structure;

import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mp3skater.getop.GetOP;

public class ModStructures {

    /**
     * We are using the Deferred Registry system to register our structure as this is the preferred way on Forge.
     * This will handle registering the base structure for us at the correct time so we don't have to handle it ourselves.
     *
     * HOWEVER, do note that Deferred Registries only work for anything that is a Forge Registry. This means that
     * configured structures and configured features need to be registered directly to BuiltinRegistries as there
     * is no Deferred Registry system for them.
     */
    public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, GetOP.MOD_ID);

    /**
     * Registers the base structure itself and sets what its path is. In this case,
     * this base structure will have the resourcelocation of structure_tutorial:sky_structures.
     */
    public static final RegistryObject<StructureFeature<?>> SKY_STRUCTURES =
            DEFERRED_REGISTRY_STRUCTURE.register("sky_structures", SkyStructures::new);
    public static final RegistryObject<StructureFeature<?>> END_CASTLE_STRUCTURES =
            DEFERRED_REGISTRY_STRUCTURE.register("end_castle_structures", EndCastleStructures::new);
    public static final RegistryObject<StructureFeature<?>> NETHER_STRUCTURES =
            DEFERRED_REGISTRY_STRUCTURE.register("nether_structures", NetherStructures::new);
    public static final RegistryObject<StructureFeature<?>> CAVE_STRUCTURES =
            DEFERRED_REGISTRY_STRUCTURE.register("cave_structures", CaveStructures::new);
    public static final RegistryObject<StructureFeature<?>> END_STRUCTURES =
            DEFERRED_REGISTRY_STRUCTURE.register("end_structures", EndStructures::new);
    public static final RegistryObject<StructureFeature<?>> WATER_STRUCTURES =
            DEFERRED_REGISTRY_STRUCTURE.register("water_structures", WaterStructures::new);
    public static final RegistryObject<StructureFeature<?>> LAVA_STRUCTURES =
            DEFERRED_REGISTRY_STRUCTURE.register("lava_structures", CaveStructures::new);


    public static void register(IEventBus eventBus) {
        DEFERRED_REGISTRY_STRUCTURE.register(eventBus);
    }
}