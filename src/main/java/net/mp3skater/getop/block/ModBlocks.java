package net.mp3skater.getop.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.block.custom.AnvilOfSageBlock;
import net.mp3skater.getop.block.custom.EndRiftBlock;
import net.mp3skater.getop.item.ModCreativeModeTab;
import net.mp3skater.getop.item.ModItems;
import net.mp3skater.getop.world.dimension.ModDimensions;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, GetOP.MOD_ID);

    public static final RegistryObject<Block> PAINITE_ORE_BLOCK = registerBlock("painite_ore_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(7f).requiresCorrectToolForDrops()), ModCreativeModeTab.GET_OP_TAB);

    public static final RegistryObject<Block> PAINITE_BLOCK = registerBlock("painite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(12f).requiresCorrectToolForDrops()), ModCreativeModeTab.GET_OP_TAB);

    public static final RegistryObject<Block> ANVILOFSAGE_BLOCK = registerBlock("anvilofsage_block",
            () -> new AnvilOfSageBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().sound(SoundType.ANVIL)),
            ModCreativeModeTab.GET_OP_TAB);

    public static final RegistryObject<Block> END_RIFT_BLOCK = registerBlock("end_rift_block",
      () -> new EndRiftBlock(BlockBehaviour.Properties.of(Material.AIR)
        .strength(3f).sound(SoundType.STONE), ModDimensions.GTDIM_KEY,
        new Vec3(0,0,0)), ModCreativeModeTab.GET_OP_TAB);

    public static final RegistryObject<Block> AMETHYST_STAIRS = registerBlock("amethyst_stairs",
            () -> new StairBlock(Blocks.AMETHYST_BLOCK::defaultBlockState, BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(7f).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()), ModCreativeModeTab.GET_OP_TAB);

    public static final RegistryObject<Block> AMETHYST_SLAB = registerBlock("amethyst_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(7f).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()), ModCreativeModeTab.GET_OP_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
