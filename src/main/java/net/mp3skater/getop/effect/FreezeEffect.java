package net.mp3skater.getop.effect;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class FreezeEffect extends MobEffect {
    public FreezeEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if(!pLivingEntity.level.isClientSide()) {
            if(pLivingEntity instanceof Player player) {
                player.hurtMarked = true;
                player.setDeltaMovement(player.getDeltaMovement().x * 0.5D, 0, player.getDeltaMovement().z * 0.5D);
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 80, 0));
            }
            //double x = pLivingEntity.getX();
            //double y = pLivingEntity.getY();
            //double z = pLivingEntity.getZ();
//
            //pLivingEntity.teleportTo(x, y, z);
            //pLivingEntity.setDeltaMovement(0, 0, 0);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}