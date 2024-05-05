package net.mp3skater.getop.event;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.entity.ModEntityTypes;
import net.mp3skater.getop.entity.client.armor.PainiteArmorRenderer;
import net.mp3skater.getop.entity.custom.VoidShredderEntity;
import net.mp3skater.getop.particle.ModParticles;
import net.mp3skater.getop.particle.custom.Deathray_Particle;
import net.mp3skater.getop.recipe.AnvilOfSageRecipe;
import software.bernie.example.item.PotatoArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = GetOP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.VOID_SHREDDER.get(), VoidShredderEntity.setAttributes());
    }
    @SubscribeEvent
    public static void registerArmorRenderer(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(PotatoArmorItem.class, new PainiteArmorRenderer());
    }
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, AnvilOfSageRecipe.Type.ID, AnvilOfSageRecipe.Type.INSTANCE);
    }
}
