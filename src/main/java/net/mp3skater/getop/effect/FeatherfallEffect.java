package net.mp3skater.getop.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class FeatherfallEffect extends MobEffect {
    public FeatherfallEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

   @Mod.EventBusSubscriber
   public static class ModEventBusEvents {
       @SubscribeEvent
       public static void onEntityFall(LivingFallEvent event) {
            if (event.getEntity() instanceof Player player && player.hasEffect(GetOPEffects.FEATHER_FALL_EFFECT.get())) {
                // Prevent fall damage for player
                float distance = event.getDistance();
                event.setDistance((distance<20? 0 : distance-20)*0.8F);
            }
       }
   }
}