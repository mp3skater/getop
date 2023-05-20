package net.mp3skater.getop.item.custom;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.mp3skater.getop.effect.FreezeEffect;

public class IceScytheItem extends SwordItem {
    public IceScytheItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(new FreezeEffect(MobEffectCategory.HARMFUL, 3124690),
                40, 1), pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
