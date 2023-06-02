package net.mp3skater.getop;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.mp3skater.getop.block.ModBlocks;
import net.mp3skater.getop.config.GetOPClientConfigs;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import net.mp3skater.getop.effect.ModEffect;
import net.mp3skater.getop.enchantment.ModEnchantments;
import net.mp3skater.getop.entity.ModEntityTypes;
import net.mp3skater.getop.entity.client.Void_ShredderRenderer;
import net.mp3skater.getop.item.ModItems;
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

        eventBus.addListener(this::setup);

        eventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, GetOPClientConfigs.SPEC, "getop-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GetOPCommonConfigs.SPEC, "getop-common.toml");
    }

    private void setup(final FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntityTypes.VOID_SHREDDER.get(),
                Void_ShredderRenderer::new);
    }
}
