package net.mp3skater.getop.item.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mp3skater.getop.item.ModItems;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class PainiteArmorItem extends GeoArmorItem implements IAnimatable, RareItem {
	private AnimationFactory factory = new AnimationFactory(this);

	public PainiteArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties settings) {
		super(material, slot, settings);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<PainiteArmorItem>(this, "controller",
			0, this::predicate));
	}

	private static final PainiteArmorItem.Properties painiteArmorProperties = new PainiteArmorItem.Properties();

	static {
		painiteArmorProperties.fireResistant();
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		return PlayState.STOP;
	}

	private static boolean hasNoArmorOn(Player player) {
		ItemStack boots = player.getInventory().getArmor(0);
		ItemStack leggings = player.getInventory().getArmor(1);
		ItemStack breastplate = player.getInventory().getArmor(2);
		ItemStack helmet = player.getInventory().getArmor(3);

		return helmet.isEmpty() && breastplate.isEmpty()
			&& leggings.isEmpty() && boots.isEmpty();
	}

	@Mod.EventBusSubscriber
	public static class ModEventBusEvents {

		@SubscribeEvent
		public static void onLivingDamage(LivingDamageEvent event) {
			if (event.getEntity() instanceof Player player) {
				// Return if no armor on
				if(hasNoArmorOn(player)) return;

				// Helmet: Lightning protection
				if (event.getSource() == DamageSource.LIGHTNING_BOLT) {
					ItemStack helmet = player.getInventory().getArmor(3); // 3 = helmet slot
					if (helmet.getItem() == ModItems.PAINITE_HELMET.get()) {
						// Cancel lightning damage
						event.setCanceled(true);
					}
				}

				// Chestplate: Explosion protection
				if (event.getSource().isExplosion()) {
					ItemStack chestplate = player.getInventory().getArmor(2); // 2 = chestplate slot
					if (chestplate.getItem() == ModItems.PAINITE_CHESTPLATE.get()) {
						// Cancel explosion damage
						event.setCanceled(true);
					}
				}

				// Leggings: Fire protection
				if (event.getSource().isFire() || event.getSource().isExplosion() || event.getSource().isBypassArmor()) {
					ItemStack leggings = player.getInventory().getArmor(1); // 1 = leggings slot
					if (leggings.getItem() == ModItems.PAINITE_LEGGINGS.get()) {
						// Cancel fire damage
						event.setCanceled(true);
					}
				}

				// Boots: Cancel all damage when wearing boots
				if(event.getSource().isFall()) {
					ItemStack boots = player.getInventory().getArmor(0); // 0 = boots slot
					if (boots.getItem() == ModItems.PAINITE_BOOTS.get()) {
						// Cancel all damage
						event.setCanceled(true);
					}
				}
			}
		}
	}
}
