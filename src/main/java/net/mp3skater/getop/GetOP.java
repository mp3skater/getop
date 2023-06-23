package net.mp3skater.getop;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mp3skater.getop.block.ModBlocks;
import net.mp3skater.getop.block.entity.ModBlockEntities;
import net.mp3skater.getop.config.GetOPClientConfigs;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.effect.ModEffect;
import net.mp3skater.getop.enchantment.ModEnchantments;
import net.mp3skater.getop.entity.ModEntityTypes;
import net.mp3skater.getop.entity.client.Void_ShredderRenderer;
import net.mp3skater.getop.item.ModItems;
import net.mp3skater.getop.particle.ModParticles;
import net.mp3skater.getop.screen.AnvilofSage_Screen;
import net.mp3skater.getop.screen.ModMenuTypes;
import net.mp3skater.getop.world.structure.ModStructures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(GetOP.MOD_ID)
public class GetOP
{

    public static final String MOD_ID = "getop";
    public static final Logger LOGGER = LogManager.getLogger();

    public GetOP() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModEffect.register(eventBus);
        ModEnchantments.register(eventBus);
        ModEntityTypes.register(eventBus);
        ModStructures.register(eventBus);
        ModBlockEntities.register(eventBus);
        GeckoLib.initialize();
        ModMenuTypes.register(eventBus);
        ModParticles.register(eventBus);

        //eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, GetOPClientConfigs.SPEC, "getop-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GetOPCommonConfigs.SPEC, "getop-common.toml");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.ANVILOFSAGE_MENU.get(), AnvilofSage_Screen::new);
        EntityRenderers.register(ModEntityTypes.VOID_SHREDDER.get(),
                Void_ShredderRenderer::new);
    }
}
