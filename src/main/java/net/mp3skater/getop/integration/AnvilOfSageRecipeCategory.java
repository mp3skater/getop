package net.mp3skater.getop.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.block.ModBlocks;
import net.mp3skater.getop.item.ModItems;
import net.mp3skater.getop.recipe.AnvilOfSageRecipe;

public class AnvilOfSageRecipeCategory implements IRecipeCategory<AnvilOfSageRecipe> {
	public final static ResourceLocation UID = new ResourceLocation(GetOP.MOD_ID, "anvil_of_sage");
	public final static ResourceLocation TEXTURE =
		new ResourceLocation(GetOP.MOD_ID, "textures/gui/anvilofsage_gui.png");

	private final IDrawable background;
	private final IDrawable icon;

	public AnvilOfSageRecipeCategory(IGuiHelper helper) {
		this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.ANVILOFSAGE_BLOCK.get()));
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<? extends AnvilOfSageRecipe> getRecipeClass() {
		return AnvilOfSageRecipe.class;
	}

	@Override
	public Component getTitle() {
		return new TextComponent("Anvil of Sage");
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, AnvilOfSageRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 27, 47).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.INPUT, 76, 47).addIngredients(Ingredient.of(ModItems.PAINITE_INGOT.get()));

		builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 47).addItemStack(recipe.getResultItem());
	}
}
