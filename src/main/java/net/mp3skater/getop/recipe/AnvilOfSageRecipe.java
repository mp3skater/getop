package net.mp3skater.getop.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.mp3skater.getop.GetOP;
import net.mp3skater.getop.item.ModItems;
import net.mp3skater.getop.item.custom.BrokenWeapon;
import org.slf4j.Logger;

public class AnvilOfSageRecipe implements Recipe<SimpleContainer> {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final ResourceLocation id;
    private final ItemStack output;
    private final ItemStack input;

    public AnvilOfSageRecipe(ResourceLocation id, ItemStack output, ItemStack input) {
        this.id = id;
        this.output = output;
        this.input = input;
    }

    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        return simpleContainer.getItem(1).getItem() == ModItems.PAINITE_INGOT.get() &&
          simpleContainer.getItem(0).getItem() instanceof BrokenWeapon;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AnvilOfSageRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "anvil_of_sage";
    }

    public static class Serializer implements RecipeSerializer<AnvilOfSageRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(GetOP.MOD_ID, "anvil_of_sage");

        @Override
        public AnvilOfSageRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            ItemStack input = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "ingredient"));

            return new AnvilOfSageRecipe(id, output, input);
        }

        @Override
        public AnvilOfSageRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            ItemStack input = buf.readItem();
            ItemStack output = buf.readItem();
            return new AnvilOfSageRecipe(id, output, input);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AnvilOfSageRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}


