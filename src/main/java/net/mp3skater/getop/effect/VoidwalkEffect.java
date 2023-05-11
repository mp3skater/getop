package net.mp3skater.getop.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VoidwalkEffect extends MobEffect {
    public VoidwalkEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }
    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        //to get the player from a Living Entity, var player is already defined in "instanceof" method,
        //then get vec, check if clientside to avoid problems with other servers...,
        //after that just a simple "player.setDeltaMovement" method to make the player float
        if (pLivingEntity instanceof Player player) {
            Vec3 vec = pLivingEntity.getDeltaMovement();
            if(!pLivingEntity.level.isClientSide()){
                player.setDeltaMovement(vec.x, 0.01, vec.z);
            }

        }
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
