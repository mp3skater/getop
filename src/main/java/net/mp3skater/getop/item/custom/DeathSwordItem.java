package net.mp3skater.getop.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
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
import net.mp3skater.getop.effect.ModEffect;
import net.mp3skater.getop.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

public class DeathSwordItem extends SwordItem {
    public DeathSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack>
    use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        Vec3 look = player.getLookAngle();
        Vec3 start = player.getEyePosition();
        double margin_xz = 3; // margin of x/z-axe around the death-ray
        double margin_y = 2; // margin of y-axe around the death-ray
        int cooldown = 60;

        if(player.hasEffect(ModEffect.PAINITE_ARMOR_BOOST.get())) {
            margin_xz = margin_xz * 2;
            margin_y = 3;
            cooldown = cooldown / 2;
        }

        Vec3 end = start.add(look.x * GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get(),
                             look.y * GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get(),
                             look.z * GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get());
        if (hand == InteractionHand.MAIN_HAND || !level.isClientSide) {

            spawnDeathParticle(start, look, level);

            double minX = Math.min(start.x, end.x) - margin_xz;
            double minY = Math.min(start.y, end.y) - margin_y;
            double minZ = Math.min(start.z, end.z) - margin_xz;
            double maxX = Math.max(start.x, end.x) + margin_xz;
            double maxY = Math.max(start.y, end.y) + margin_y;
            double maxZ = Math.max(start.z, end.z) + margin_xz;
            AABB boundingBox = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
            Iterable<Entity> entities = level.getEntities(player, boundingBox);
                //player.sendMessage(new TextComponent("In a radius of " + GetOPCommonConfigs.DEATH_SWORD_REACH_DISTANCE.get()
                //        + " blocks these entities will get damaged 25 hp: " + entities), player.getUUID());
            for (Entity entity : entities) {
                        if((entity instanceof LivingEntity))
                            entity.hurt(DamageSource.MAGIC, 12);
                        entity.setDeltaMovement(0.5d * look.x, 0, 0.5d * look.z);
            }
            //damages weapon per hit
            ItemStack itemstack = player.getItemInHand(hand);
            if(!player.hasEffect(ModEffect.PAINITE_ARMOR_BOOST.get())) {
                itemstack.hurtAndBreak(1, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
            }
        }
        //gives the End Sceptre a cool down of half a sec after being used
        player.getCooldowns().addCooldown(this, cooldown);

        return super.use(level, player, hand);
    }

    private void spawnDeathParticle(Vec3 start, Vec3 look, Level level) {
        level.addParticle(ModParticles.DEATHRAY_PARTICLE.get(),
                start.x, start.y, start.z, look.x * 1.2d,  look.y * 1.2d,  look.z * 1.2d);
    }

}
