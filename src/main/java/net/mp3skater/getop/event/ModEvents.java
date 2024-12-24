package net.mp3skater.getop.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.item.ModItems;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static net.mp3skater.getop.world.dimension.ModDimensions.GTDIM_KEY;

@Mod.EventBusSubscriber(modid = GetOP.MOD_ID)
public class ModEvents {
    //@SubscribeEvent
    //public void PlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
    //    Player player = event.getPlayer();
    //    ResourceKey<Level> dimType = event.getTo();
    //    if(dimType==GTDIM_KEY) {
//
    //    }
    //}
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.EMERALD, 20);
            int villagerLevel = 3;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.PAINITE.get(), 1),
                    stack,10,8,0.02F));
        }
    }
}