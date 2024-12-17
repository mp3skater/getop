package net.mp3skater.getop;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mp3skater.getop.block.ModBlocks;
import net.mp3skater.getop.block.entity.ModBlockEntities;
import net.mp3skater.getop.config.GetOPClientConfigs;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.effect.GetOPEffects;
import net.mp3skater.getop.enchantment.ModEnchantments;
import net.mp3skater.getop.entity.ModEntityTypes;
import net.mp3skater.getop.item.ModItems;
import net.mp3skater.getop.particle.ModParticles;
import net.mp3skater.getop.recipe.ModRecipes;
import net.mp3skater.getop.screen.ModMenuTypes;
import net.mp3skater.getop.world.dimension.ModDimensions;
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

        GetOPEffects.register(eventBus);
        ModEnchantments.register(eventBus);

        ModStructures.register(eventBus);
        ModDimensions.register();

        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);

        ModParticles.register(eventBus);
        ModEntityTypes.register(eventBus);

        GeckoLib.initialize();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        ModRecipes.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, GetOPClientConfigs.SPEC, "getop-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GetOPCommonConfigs.SPEC, "getop-common.toml");
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.END_RIFT_BLOCK.get(), RenderType.translucent());
    }

}
