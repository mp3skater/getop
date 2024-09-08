package net.mp3skater.getop.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mp3skater.getop.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

import java.util.List;

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

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		if (pStack.getItem() == ModItems.PAINITE_HELMET.get()) {
			pTooltipComponents.add(new TranslatableComponent("item.getop.painite_helmet.tooltip"));
		} else if (pStack.getItem() == ModItems.PAINITE_CHESTPLATE.get()) {
			pTooltipComponents.add(new TranslatableComponent("item.getop.painite_chestplate.tooltip"));
		} else if (pStack.getItem() == ModItems.PAINITE_LEGGINGS.get()) {
			pTooltipComponents.add(new TranslatableComponent("item.getop.painite_leggings.tooltip"));
		} else if (pStack.getItem() == ModItems.PAINITE_BOOTS.get()) {
			pTooltipComponents.add(new TranslatableComponent("item.getop.painite_boots.tooltip"));
		}
	}

	@Mod.EventBusSubscriber
	public static class ModEventBusEvents {

		@SubscribeEvent
		public static void onLivingDamage(LivingDamageEvent event) {
			if (event.getEntity() instanceof Player player) {
				// Helmet: Lightning protection
				if (event.getSource() == DamageSource.LIGHTNING_BOLT) {
					ItemStack helmet = player.getInventory().getArmor(3); // 3 = helmet slot
					if (helmet.getItem() == ModItems.PAINITE_HELMET.get()) {
						// Cancel lightning damage
						event.setCanceled(true);
					}
					player.getInventory().getArmor(3).hurtAndBreak(2, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
				}

				// Chestplate: Explosion protection
				else if (event.getSource().isExplosion()) {
					ItemStack chestplate = player.getInventory().getArmor(2); // 2 = chestplate slot
					if (chestplate.getItem() == ModItems.PAINITE_CHESTPLATE.get()) {
						// Cancel explosion damage
						event.setCanceled(true);
					}
					player.getInventory().getArmor(2).hurtAndBreak(2, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
				}

				// Leggings: Fire protection
				else if (event.getSource().isFire()) {
					ItemStack leggings = player.getInventory().getArmor(1); // 1 = leggings slot
					if (leggings.getItem() == ModItems.PAINITE_LEGGINGS.get()) {
						// Cancel fire damage
						event.setCanceled(true);
					}
					player.getInventory().getArmor(1).hurtAndBreak(2, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
				}

				// Boots: Cancel all damage when wearing boots
				else if(event.getSource().isFall()) {
					ItemStack boots = player.getInventory().getArmor(0); // 0 = boots slot
					if (boots.getItem() == ModItems.PAINITE_BOOTS.get()) {
						// Cancel all damage
						event.setCanceled(true);
					}
					player.getInventory().getArmor(0).hurtAndBreak(2, player, player1 -> player.broadcastBreakEvent(player.getUsedItemHand()));
				}
			}
		}
	}
}
