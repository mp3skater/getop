package net.mp3skater.getop.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class VoidwalkEffect extends MobEffect {
    public VoidwalkEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        //Timer timer = new Timer();
        //timer.schedule(new TimerTask() {
            //@Override
            //public void run() {
                Vec3 vec = pLivingEntity.getDeltaMovement();
                if(!pLivingEntity.level.isClientSide()){
                    pLivingEntity.setDeltaMovement(vec.x, 0.1, vec.z);
                }
        //    }
        //}, 1000); // delay for 1 second
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
