package net.mp3skater.getop.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;
import java.util.function.Supplier;

public class AltarBlock extends Block {
	private final Supplier<Item> itemSupplier;

	public AltarBlock(Properties properties, Supplier<Item> itemSupplier) {
		super(properties);
		this.itemSupplier = itemSupplier;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!world.isClientSide && hand == InteractionHand.MAIN_HAND) {
			// Give the player an item (e.g., a diamond)
			ItemStack item = new ItemStack(itemSupplier.get());
			if (!player.getInventory().add(item)) {
				player.drop(item, false);
			}

			// Play a sound effect
			world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, player.getSoundSource(), 1.0F, 1.0F);

			// Spawn particles
			if (world instanceof ServerLevel serverLevel) {
				Random random = new Random();
				for (int i = 0; i < 50; i++) {
					serverLevel.sendParticles(
						net.minecraft.core.particles.ParticleTypes.END_ROD,
						pos.getX() + 0.5 + random.nextGaussian() * 0.3,
						pos.getY() + 1 + random.nextDouble() * 0.5,
						pos.getZ() + 0.5 + random.nextGaussian() * 0.3,
						1,
						0, 0, 0, 0
					);
				}
			}

			// Remove the block
			world.removeBlock(pos, false);

			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
