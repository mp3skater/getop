package net.mp3skater.getop.item.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import static net.minecraft.world.item.Rarity.RARE;

public interface RareItem {
    public default Rarity getRarity(ItemStack pStack) {
        return RARE;
    }
}
