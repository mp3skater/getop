package net.mp3skater.getop.item.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.mp3skater.getop.effect.GetOPEffects;
import net.mp3skater.getop.item.ModArmorMaterials;
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

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);

		// Return if not on server
		if (world.isClientSide()) return;

		// Give custom effects if you're wearing the armor
		if (hasPieceOfArmorOn(player, 0) && hasCorrectArmorOn(player, 0))
			add(player, GetOPEffects.GUM_SKIN_EFFECT.get());
		if (hasPieceOfArmorOn(player, 1) && hasCorrectArmorOn(player, 1))
			add(player, MobEffects.FIRE_RESISTANCE);
		if (hasPieceOfArmorOn(player, 2) && hasCorrectArmorOn(player, 2))
			add(player, GetOPEffects.HARDENED_SKIN_EFFECT.get());
		if (hasPieceOfArmorOn(player, 3) && hasCorrectArmorOn(player, 3))
			add(player, GetOPEffects.FEATHER_FALL_EFFECT.get());
	}

	private void add(Player player, MobEffect effect) {
		if (!player.hasEffect(effect)) {
			player.addEffect(new MobEffectInstance(effect, 200, 1));
		}
	}

	private boolean hasPieceOfArmorOn(Player player, int slot) {
		ItemStack boots = player.getInventory().getArmor(0);
		ItemStack leggings = player.getInventory().getArmor(1);
		ItemStack breastplate = player.getInventory().getArmor(2);
		ItemStack helmet = player.getInventory().getArmor(3);

		return !helmet.isEmpty() && !breastplate.isEmpty()
			&& !leggings.isEmpty() && !boots.isEmpty();
	}

	private boolean hasCorrectArmorOn(Player player, int slot) {

		for (ItemStack armorStack : player.getInventory().armor) {
			if (!(armorStack.getItem() instanceof ArmorItem)) {
				return false;
			}
		}

		ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());
		ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
		ArmorItem breastplate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
		ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());

		return helmet.getMaterial() == ModArmorMaterials.PAINITE && breastplate.getMaterial() == ModArmorMaterials.PAINITE &&
			leggings.getMaterial() == ModArmorMaterials.PAINITE && boots.getMaterial() == ModArmorMaterials.PAINITE;
	}
}
