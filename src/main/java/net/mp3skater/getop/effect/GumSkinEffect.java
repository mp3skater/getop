package net.mp3skater.getop.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class GumSkinEffect extends MobEffect {
    public GumSkinEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

   @Mod.EventBusSubscriber
   public static class ModEventBusEvents {
       @SubscribeEvent
       public static void onEntityStruckByLightning(EntityStruckByLightningEvent event)
       {
           if(event.getEntity() instanceof Player player && player.hasEffect(ModEffect.GUM_SKIN_EFFECT.get()))
               event.setCanceled(true);
       }
   }
}