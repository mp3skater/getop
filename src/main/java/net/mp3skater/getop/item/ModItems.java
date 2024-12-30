package net.mp3skater.getop.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.ModEntityTypes;
import net.mp3skater.getop.item.custom.*;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GetOP.MOD_ID);

    public static final RegistryObject<Item> PAINITE = ITEMS.register("painite",
            () -> new Item(new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));
    public static final RegistryObject<Item> PAINITE_INGOT = ITEMS.register("painite_ingot",
            () -> new Item(new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));

    //public static final RegistryObject<Item> NEW_WORLD_KEY = ITEMS.register("new_world_key",
    //  () -> new DimensionChangingItem(new Item.Properties().tab(ModCreativeModeTab.GET_OP_TAB),
    //    ModDimensions.GTDIM_KEY,
    //    new Vec3(0,0,0)));

    public static final RegistryObject<Item> DEATHAXE = ITEMS.register("deathaxe",
            () -> new DeathSwordItem(ModTiers.PAINITE, 2, -2.6f,
                    new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));
    public static final RegistryObject<Item> PICKASHE = ITEMS.register("pickashe",
            () -> new PickAsheItem(ModTiers.PAINITE, 3, -3f,
                    new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));
    public static final RegistryObject<Item> END_SCEPTRE = ITEMS.register("end_sceptre",
            () -> new EndSceptreItem(ModTiers.PAINITE, 2, -2.6f,
                    new Item.Properties().tab(ModCreativeModeTab.GET_OP_TAB)));
    public static final RegistryObject<Item> SENTINEL_BLADE = ITEMS.register("sentinel_blade",
            () -> new SwordItem(ModTiers.PAINITE, 2, -2.6f,
                    new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));
    public static final RegistryObject<Item> ICE_SCYTHE = ITEMS.register("ice_scythe",
      () -> new IceScytheItem(ModTiers.PAINITE, 1, -2f,
        new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));
    public static final RegistryObject<Item> ABYSS_SHIELD = ITEMS.register("abyss_shield",
      () -> new AbyssShieldItem(new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)
        .stacksTo(1)));
    public static final RegistryObject<Item> HEROBRINE_SWORD = ITEMS.register("herobrine_sword",
            () -> new HerobrineSwordItem(ModTiers.PAINITE, 10, -2.2f,
                    new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));

    public static final RegistryObject<Item> VOID_SHREDDER_SPAWN_EGG = ITEMS.register("void_shredder_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.VOID_SHREDDER,0xe7e7e7, 0x441f6a,
                    new Item.Properties().tab(ModCreativeModeTab.GET_OP_TAB)));

    public static final RegistryObject<Item> PAINITE_HELMET = ITEMS.register("painite_helmet",
            () -> new PainiteArmorItem(ModArmorMaterials.PAINITE, EquipmentSlot.HEAD,
                    new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));
    public static final RegistryObject<Item> PAINITE_CHESTPLATE = ITEMS.register("painite_chestplate",
            () -> new PainiteArmorItem(ModArmorMaterials.PAINITE, EquipmentSlot.CHEST,
                    new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));
    public static final RegistryObject<Item> PAINITE_LEGGINGS = ITEMS.register("painite_leggings",
            () -> new PainiteArmorItem(ModArmorMaterials.PAINITE, EquipmentSlot.LEGS,
                    new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));
    public static final RegistryObject<Item> PAINITE_BOOTS = ITEMS.register("painite_boots",
            () -> new PainiteArmorItem(ModArmorMaterials.PAINITE, EquipmentSlot.FEET,
                    new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB)));

    public static final RegistryObject<Item> BROKEN_DEATHAXE = ITEMS.register("broken_deathaxe",
            () -> new BrokenWeapon(new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB).stacksTo(1)));
    public static final RegistryObject<Item> BROKEN_PICKASHE = ITEMS.register("broken_pickashe",
            () -> new BrokenWeapon(new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB).stacksTo(1)));
    public static final RegistryObject<Item> BROKEN_END_SCEPTRE = ITEMS.register("broken_end_sceptre",
            () -> new BrokenWeapon(new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB).stacksTo(1)));
    public static final RegistryObject<Item> BROKEN_ICE_SCYTHE = ITEMS.register("broken_ice_scythe",
      () -> new BrokenWeapon(new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB).stacksTo(1)));
    public static final RegistryObject<Item> BROKEN_ABYSS_SHIELD = ITEMS.register("broken_abyss_shield",
      () -> new BrokenWeapon(new Item.Properties().fireResistant().tab(ModCreativeModeTab.GET_OP_TAB).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
