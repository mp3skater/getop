package net.mp3skater.getop.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.custom.PickasheFireballEntity;
import net.mp3skater.getop.entity.custom.PickasheFireballEntity;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class PickasheFireballRenderer extends GeoProjectilesRenderer<PickasheFireballEntity> {
    public PickasheFireballRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PickasheFireballModel());
        this.shadowRadius = 0.2f;
    }

    @Override
    public ResourceLocation getTextureLocation(PickasheFireballEntity instance) {
        return new ResourceLocation(GetOP.MOD_ID, "textures/entity/pickashe_fireball/pickashe_fireball.png");
    }

    @Override
    public RenderType getRenderType(PickasheFireballEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1.0F, 1.0F, 1.0F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}