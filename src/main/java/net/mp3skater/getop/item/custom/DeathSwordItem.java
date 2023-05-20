package net.mp3skater.getop.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.config.GetOPCommonConfigs;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

public class DeathSwordItem extends SwordItem {
    public DeathSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 15), pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack>
    use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        Level world = player.getLevel();
        Vec3 vec = player.getLookAngle();
        //is required for the server to control the Players movement
            player.hurtMarked = true;
        BlockPos pos = player.getOnPos();
        int radius = GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get();
        List<Entity> entities = world.getEntities(player, new AABB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius));
        //player.sendMessage(new TextComponent("In a radius of " + GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get()
        //        + " blocks these entities will get damaged 20 hp: " + entities), player.getUUID());
        for (Entity entity : entities) {
            if(((LivingEntity) entity).hasLineOfSight(player)) {
                    if(((LivingEntity) entity).hasLineOfSight(player))
                        entity.hurt(DamageSource.MAGIC, 20);
                    entity.setDeltaMovement(0.5f * vec.x, 0.5d * vec.y, 0.5d * vec.z);
            }
        }
        return super.use(level, player, hand);
    }



}
