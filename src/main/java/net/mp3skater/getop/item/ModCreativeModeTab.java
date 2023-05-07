package net.mp3skater.getop.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {

    public static final CreativeModeTab GET_OP_TAB = new CreativeModeTab("getoptab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.PAINITE.get());
        }
    };

}
