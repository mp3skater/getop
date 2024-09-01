package net.mp3skater.getop.item.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.phys.Vec3;
import net.mp3skater.getop.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class DimensionChangingItem extends Item {
	private final ResourceKey<Level> dimension;
	private final Vec3 location;

	public DimensionChangingItem(Properties pProperties, ResourceKey<Level> dimension, Vec3 location) {
		super(pProperties);
		this.dimension = dimension;
		this.location = location;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		// Changing to GetOP Dimension
		if(pPlayer instanceof ServerPlayer serverPlayer) {
			ModUtils.teleportEntityToDimension(serverPlayer, dimension, location, this);
		}

		return super.use(pLevel, pPlayer, pUsedHand);
	}
}