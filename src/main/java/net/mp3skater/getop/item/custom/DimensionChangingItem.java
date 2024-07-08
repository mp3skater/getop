package net.mp3skater.getop.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceKey;
import net.mp3skater.getop.util.ModUtils;

public class DimensionChangingItem extends Item {
	private final ResourceKey<Level> dimension;

	public DimensionChangingItem(Properties pProperties, ResourceKey<Level> dimension) {
		super(pProperties);
		this.dimension = dimension;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		// Changing to GetOP Dimension
		ModUtils.changeDimension(pPlayer, pLevel, dimension, this);

		return super.use(pLevel, pPlayer, pUsedHand);
	}
}