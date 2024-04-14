package net.mp3skater.getop.entity.client.armor;

import net.minecraft.resources.ResourceLocation;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.item.custom.PainiteArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PainiteArmorModel extends AnimatedGeoModel<PainiteArmorItem> {
    @Override
    public ResourceLocation getModelLocation(PainiteArmorItem object) {
        return new ResourceLocation(GetOP.MOD_ID, "geo/painite_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PainiteArmorItem object) {
        return new ResourceLocation(GetOP.MOD_ID, "textures/models/armor/painite_armor_texture.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PainiteArmorItem animatable) {
        return new ResourceLocation(GetOP.MOD_ID, "animations/armor_animation.json");
    }
}
