package net.mp3skater.getop.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.custom.VoidShredderEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Void_ShredderModel extends AnimatedGeoModel<VoidShredderEntity> {
    @Override
    public ResourceLocation getModelLocation(VoidShredderEntity object) {
        return new ResourceLocation(GetOP.MOD_ID, "geo/void_shredder.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(VoidShredderEntity object) {
        return new ResourceLocation(GetOP.MOD_ID, "textures/entity/void_shredder/void_shredder.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(VoidShredderEntity animatable) {
        return new ResourceLocation(GetOP.MOD_ID, "animations/void_shredder.animation.json");
    }
}
