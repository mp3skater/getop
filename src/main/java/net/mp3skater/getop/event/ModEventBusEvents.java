package net.mp3skater.getop.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.ModEntityTypes;
import net.mp3skater.getop.entity.client.armor.PainiteArmorModel;
import net.mp3skater.getop.entity.client.armor.PainiteArmorRenderer;
import net.mp3skater.getop.entity.custom.VoidShredderEntity;
import net.mp3skater.getop.particle.ModParticles;
import net.mp3skater.getop.particle.custom.Deathray_Particle;
import software.bernie.example.item.PotatoArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = GetOP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.VOID_SHREDDER.get(), VoidShredderEntity.setAttributes());
    }
    @SubscribeEvent
    public static void registerArmorRenderer(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(PotatoArmorItem.class, new PainiteArmorRenderer());
    }

    @SubscribeEvent
    public static void registerParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.DEATHRAY_PARTICLE.get(),
                Deathray_Particle.Provider::new);
    }
}
