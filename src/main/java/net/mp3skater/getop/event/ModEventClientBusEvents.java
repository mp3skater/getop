package net.mp3skater.getop.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.ModEntityTypes;
import net.mp3skater.getop.entity.client.EndSceptreRenderer;
import net.mp3skater.getop.entity.client.Void_ShredderRenderer;
import net.mp3skater.getop.entity.client.armor.PainiteArmorRenderer;
import net.mp3skater.getop.item.custom.PainiteArmorItem;
import net.mp3skater.getop.particle.ModParticles;
import net.mp3skater.getop.particle.custom.Deathray_Particle;
import net.mp3skater.getop.screen.AnvilofSage_Screen;
import net.mp3skater.getop.screen.ModMenuTypes;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = GetOP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {
    @SubscribeEvent
    public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(PainiteArmorItem.class, new PainiteArmorRenderer());
    }
    @SubscribeEvent
    public static void registerParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.DEATHRAY_PARTICLE.get(),
                Deathray_Particle.Provider::new);
    }
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.ANVILOFSAGE_MENU.get(), AnvilofSage_Screen::new);
        EntityRenderers.register(ModEntityTypes.VOID_SHREDDER.get(),
                Void_ShredderRenderer::new);
        EntityRenderers.register(ModEntityTypes.ENDSCEPTRE_ENTITY.get(),
                EndSceptreRenderer::new);
    }
}
