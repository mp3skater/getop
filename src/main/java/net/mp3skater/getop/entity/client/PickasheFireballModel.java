package net.mp3skater.getop.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.custom.CoolFireballEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PickasheFireballModel extends AnimatedGeoModel<CoolFireballEntity> {

    @Override
    public ResourceLocation getModelLocation(CoolFireballEntity object) {
        return new ResourceLocation(GetOP.MOD_ID, "geo/pickashe_fireball.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CoolFireballEntity object) {
        return new ResourceLocation(GetOP.MOD_ID, "textures/entity/pickashe_fireball/pickashe_fireball.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CoolFireballEntity animatable) {
        return new ResourceLocation(GetOP.MOD_ID, "animations/pickashe_fireball.animation.json");
    }
}
