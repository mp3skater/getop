package net.mp3skater.getop.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.custom.EndSceptreEntity;
import net.mp3skater.getop.entity.custom.VoidShredderEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EndSceptreRenderer extends GeoEntityRenderer<EndSceptreEntity> {
    public EndSceptreRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EndSceptreModel());
        this.shadowRadius = 0.2f;
    }

    @Override
    public ResourceLocation getTextureLocation(EndSceptreEntity instance) {
        return new ResourceLocation(GetOP.MOD_ID, "textures/entity/end_sceptre_entity/end_sceptre_entity.png");
    }

    @Override
    public RenderType getRenderType(EndSceptreEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1.0F, 1.0F, 1.0F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

}
