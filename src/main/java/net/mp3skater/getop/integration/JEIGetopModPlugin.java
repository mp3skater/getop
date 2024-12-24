package net.mp3skater.getop.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.recipe.AnvilOfSageRecipe;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIGetopModPlugin implements IModPlugin {
	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(GetOP.MOD_ID, "jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new
			AnvilOfSageRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
		List<AnvilOfSageRecipe> recipes = rm.getAllRecipesFor(AnvilOfSageRecipe.Type.INSTANCE);
		registration.addRecipes(new RecipeType<>(AnvilOfSageRecipeCategory.UID, AnvilOfSageRecipe.class), recipes);
	}
}
