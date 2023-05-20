package net.mp3skater.getop.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VoidwalkEffect extends MobEffect{
    public VoidwalkEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {

            // Check if the entity is a player
            if (pLivingEntity instanceof Player) {
                Player player = (Player) pLivingEntity;
                Vec3 vec = player.getDeltaMovement();
                player.setDeltaMovement(vec.x, 0.3, vec.z);

            } else {
                Vec3 vec = pLivingEntity.getDeltaMovement();
                pLivingEntity.setDeltaMovement(vec.x, 0.01, vec.z);
            }

            Vec3 vec = pLivingEntity.getDeltaMovement();
            pLivingEntity.setDeltaMovement(vec.x, 1, vec.z);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
