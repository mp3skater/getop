package net.mp3skater.getop.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.block.entity.ModBlockEntities;
import net.mp3skater.getop.item.ModItems;
import net.mp3skater.getop.recipe.AnvilOfSageRecipe;
import net.mp3skater.getop.screen.AnvilofSage_Menu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class AnvilOfSageBlock_Entity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;

    public AnvilOfSageBlock_Entity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.ANVIL_OF_SAGE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return -1;
            }
            public void set(int index, int value) {}
            public int getCount() {
                return -1;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent(""); // So it doesn't show
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new AnvilofSage_Menu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i<itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, AnvilOfSageBlock_Entity pBlockEntity) {
        GetOP.LOGGER.info("Tick started for AnvilOfSageBlock_Entity at position: {}", pPos);
        if (hasRecipe(pBlockEntity)) {
            setChanged(pLevel, pPos, pState);
            GetOP.LOGGER.info("Valid recipe found. Starting crafting process.");
            craftResult(pBlockEntity);
            removeIngredients(pBlockEntity);
        } else {
            GetOP.LOGGER.info("No valid recipe found.");
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(AnvilOfSageBlock_Entity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());

        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
            GetOP.LOGGER.info("Slot {} contains: {}", i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AnvilOfSageRecipe> match = level.getRecipeManager()
          .getRecipeFor(AnvilOfSageRecipe.Type.INSTANCE, inventory, level);

        boolean matchPresent = match.isPresent();
        GetOP.LOGGER.info("Recipe match found: {}", matchPresent);

        boolean outputSlotHasSpace = canInsertAmountIntoOutputSlot(inventory);
        GetOP.LOGGER.info("Output slot has space: {}", outputSlotHasSpace);

        boolean outputSlotCompatible = matchPresent && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
        GetOP.LOGGER.info("Output slot compatible with result item: {}", outputSlotCompatible);

        boolean hasPainite = hasPainiteInPainiteSlot(entity);
        GetOP.LOGGER.info("Painite slot contains Painite Ingot: {}", hasPainite);

        boolean hasRecipe = matchPresent && outputSlotHasSpace && outputSlotCompatible && hasPainite;
        GetOP.LOGGER.info("Overall recipe validity: {}", hasRecipe);

        return hasRecipe;
    }

    private static boolean hasPainiteInPainiteSlot(AnvilOfSageBlock_Entity entity) {
			return entity.itemHandler.getStackInSlot(1).getItem() == ModItems.PAINITE_INGOT.get();
    }

    private static void removeIngredients(AnvilOfSageBlock_Entity entity) {
        GetOP.LOGGER.info("Removing ingredients from slots");

//        Optional<AnvilOfSageRecipe> match = entity.level.getRecipeManager()
//          .getRecipeFor(AnvilOfSageRecipe.Type.INSTANCE, new SimpleContainer(entity.itemHandler.getSlots()), entity.level);

//        if (match.isPresent()) {
            entity.itemHandler.extractItem(0, 1, false);
            entity.itemHandler.extractItem(1, 1, false);
            GetOP.LOGGER.info("Ingredients removed from slots 0 and 1");
//        } else {
//            GetOP.LOGGER.warn("Attempted to remove ingredients, but no recipe matched.");
//        }
    }

    private static void craftResult(AnvilOfSageBlock_Entity entity) {
        GetOP.LOGGER.info("Attempting to craft result");

        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());

        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AnvilOfSageRecipe> match = level.getRecipeManager()
          .getRecipeFor(AnvilOfSageRecipe.Type.INSTANCE, inventory, level);

        if (match.isPresent()) {
            ItemStack result = match.get().getResultItem();
            if (canInsertItemIntoOutputSlot(inventory, result) && canInsertAmountIntoOutputSlot(inventory)) {
                entity.itemHandler.setStackInSlot(2, new ItemStack(match.get().getResultItem().getItem(),
                        entity.itemHandler.getStackInSlot(2).getCount() + 1));
                GetOP.LOGGER.info("Crafting successful. Result item inserted into output slot.");
            } else {
                GetOP.LOGGER.warn("Output slot full or incompatible. Crafting aborted.");
            }
        } else {
            GetOP.LOGGER.warn("No recipe match during crafting. Crafting aborted.");
        }
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        boolean canInsert = inventory.getItem(2).getItem() == output.getItem() || inventory.getItem(2).isEmpty();
        GetOP.LOGGER.info("Can insert item into output slot: {}", canInsert);
        return canInsert;
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        boolean canInsertAmount = inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
        GetOP.LOGGER.info("Can insert amount into output slot: {}", canInsertAmount);
        return canInsertAmount;
    }
}
