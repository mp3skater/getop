package net.mp3skater.getop.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FreezeEffect extends MobEffect {

    public FreezeEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide()) {
            // Set the entity's movement to zero to prevent all movement
            entity.setDeltaMovement(0, entity.getDeltaMovement().y, 0);
        }
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}