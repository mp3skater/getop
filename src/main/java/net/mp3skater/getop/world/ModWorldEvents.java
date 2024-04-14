package net.mp3skater.getop.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.world.gen.ModOreGeneration;

@Mod.EventBusSubscriber(modid = GetOP.MOD_ID)
public class ModWorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModOreGeneration.generateOres(event);


    }
}