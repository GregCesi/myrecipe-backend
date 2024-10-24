package myrecipe.fr.utils;

import myrecipe.fr.domain.DTO.IngredientDTO;
import myrecipe.fr.domain.DTO.RecipeDTO;
import myrecipe.fr.domain.DTO.RecipeListDTO;
import myrecipe.fr.domain.DTO.StepDTO;
import myrecipe.fr.domain.Entity.Ingredient;
import myrecipe.fr.domain.Entity.Recipe;
import myrecipe.fr.domain.Entity.Step;

public class RecipeMapper {

    public static Recipe transformRecipeDTOToRecipe(RecipeDTO recipeDTO) {
        return new Recipe(recipeDTO.getId(), recipeDTO.getName(), recipeDTO.getImageURL(), recipeDTO.getIngredients().stream().map(RecipeMapper::transformIngredientDTOToIngredient).toList(), recipeDTO.getSteps().stream().map(RecipeMapper::transformStepDTOToStep).toList());
    }

    public static Ingredient transformIngredientDTOToIngredient(IngredientDTO ingredientDTO) {
        return new Ingredient(ingredientDTO.getId(), ingredientDTO.getName(), ingredientDTO.getQuantity(), ingredientDTO.getUnit(), ingredientDTO.getPrice());
    }

    public static RecipeDTO transformRecipeToRecipeDTO(Recipe recipe) {
        return new RecipeDTO(recipe.getId(), recipe.getName(), recipe.getImageURL(), recipe.getIngredients().stream().map(RecipeMapper::transformIngredientToIngredientDTO).toList(), recipe.getSteps().stream().map(RecipeMapper::transformStepToStepDTO).toList());
    }

    public static RecipeListDTO transformRecipeToRecipeListDTO(Recipe recipe) {
        return new RecipeListDTO(recipe.getId(), recipe.getName(), recipe.getImageURL());
    }

    public static IngredientDTO transformIngredientToIngredientDTO(Ingredient ingredient) {
        return new IngredientDTO(ingredient.getId(), ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit(), ingredient.getPrice());
    }

    public static Step transformStepDTOToStep(StepDTO stepDTO) {
        return new Step(stepDTO.getId(), stepDTO.getDescription()) ;
    }

    public static StepDTO transformStepToStepDTO(Step step) {
        return new StepDTO(step.getId(), step.getDescription()) ;
    }


}
