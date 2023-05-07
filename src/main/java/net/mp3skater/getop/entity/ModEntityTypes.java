package net.mp3skater.getop.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.custom.VoidShredderEntity;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, GetOP.MOD_ID);

    public static final RegistryObject<EntityType<VoidShredderEntity>> VOID_SHREDDER =
            ENTITY_TYPES.register("void_shredder",
                    () -> EntityType.Builder.of(VoidShredderEntity::new, MobCategory.MONSTER)
                            .sized(0.8f, 2.0f)
                            .build(new ResourceLocation(GetOP.MOD_ID, "void_shredder").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
