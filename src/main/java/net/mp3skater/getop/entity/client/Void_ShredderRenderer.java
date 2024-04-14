package net.mp3skater.getop.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.custom.VoidShredderEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Void_ShredderRenderer extends GeoEntityRenderer<VoidShredderEntity> {
    public Void_ShredderRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Void_ShredderModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public ResourceLocation getTextureLocation(VoidShredderEntity instance) {
        return new ResourceLocation(GetOP.MOD_ID, "textures/entity/void_shredder/void_shredder.png");
    }

    @Override
    public RenderType getRenderType(VoidShredderEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1.0F, 1.0F, 1.0F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

}
