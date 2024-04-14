package net.mp3skater.getop.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.custom.EndSceptreEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EndSceptreModel extends AnimatedGeoModel<EndSceptreEntity> {

    @Override
    public ResourceLocation getModelLocation(EndSceptreEntity object) {
        return new ResourceLocation(GetOP.MOD_ID, "geo/end_sceptre_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EndSceptreEntity object) {
        return new ResourceLocation(GetOP.MOD_ID, "textures/entity/end_sceptre_entity/end_sceptre_entity.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EndSceptreEntity animatable) {
        return new ResourceLocation(GetOP.MOD_ID, "animations/end_sceptre_entity.animation.json");
    }
}
