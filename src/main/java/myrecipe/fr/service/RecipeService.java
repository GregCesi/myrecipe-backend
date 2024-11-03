package myrecipe.fr.service;

import jakarta.transaction.Transactional;
import myrecipe.fr.domain.DTO.RecipeDTO;
import myrecipe.fr.domain.DTO.RecipeListDTO;
import myrecipe.fr.domain.DTO.StepDTO;
import myrecipe.fr.domain.Entity.Ingredient;
import myrecipe.fr.domain.Entity.Recipe;
import myrecipe.fr.domain.Entity.Step;
import myrecipe.fr.repository.RecipeRepository;
import myrecipe.fr.utils.RecipeMapper;
import myrecipe.fr.service.IngredientService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecipeService {


    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final ImageService imageService;

    private final String UPLOAD_DIR = "src/main/resources/images/";
    private final String SERVER_PATH = "http:://localhost:8080/";

    public RecipeService(RecipeRepository recipeRepository, IngredientService ingredientService, ImageService imageService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.imageService = imageService;
    }

    @Transactional
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {

        Recipe recipe = RecipeMapper.transformRecipeDTOToRecipe(recipeDTO);

        // Sauvegarde de la recette avec ses étapes et ingrédients
        List<Step> steps = recipe.getSteps();
        for (Step step : steps) {
            step.setRecipe(recipe); // Lier chaque étape à la recette
        }

        recipe = recipeRepository.save(recipe);
        return RecipeMapper.transformRecipeToRecipeDTO(recipe);
    }

    public List<RecipeListDTO> getAllRecipes(){
        return recipeRepository.findAll().stream().map(RecipeMapper::transformRecipeToRecipeListDTO).toList();
    }

    public RecipeDTO getRecipe(long id) {
        return RecipeMapper.transformRecipeToRecipeDTO(recipeRepository.findById(id).orElseThrow());
    }

    public RecipeDTO updateImageUrlRecipe(String recipeId, String imageUrl) {
        Recipe recipe = recipeRepository.findById(Long.parseLong(recipeId)).orElseThrow(RuntimeException::new);
        recipe.setImageURL(imageUrl); // Enregistre uniquement l'URL de l'image
        recipe = recipeRepository.save(recipe);
        return RecipeMapper.transformRecipeToRecipeDTO(recipe);
    }

    @Transactional
    public RecipeDTO updateRecipe(long recipeId, RecipeDTO recipeDTO) {
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recette introuvable avec l'ID : " + recipeId));

        existingRecipe.setName(recipeDTO.getName());

        if (recipeDTO.getImageURL() != null && !recipeDTO.getImageURL().equals(existingRecipe.getImageURL())) {
            // Supprime l'ancienne image du serveur
            imageService.deleteImageFile(existingRecipe.getImageURL());
            existingRecipe.setImageURL(recipeDTO.getImageURL());
        }

        // Gestion des ingrédients - Efface la collection actuelle et ajoute les nouveaux éléments
        existingRecipe.getIngredients().clear();
        List<Ingredient> updatedIngredients = recipeDTO.getIngredients().stream()
                .map(ingredientService::updateIngredient)
                .collect(Collectors.toList());
        existingRecipe.getIngredients().addAll(updatedIngredients);

        // Gestion des étapes - Efface la collection actuelle et ajoute les nouvelles étapes
        existingRecipe.getSteps().clear();
        List<Step> updatedSteps = recipeDTO.getSteps().stream()
                .map(RecipeMapper::transformStepDTOToStep)
                .peek(step -> step.setRecipe(existingRecipe))
                .collect(Collectors.toList());
        existingRecipe.getSteps().addAll(updatedSteps);

        // Sauvegarder la recette avec les nouvelles listes modifiées sans remplacer la collection
        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        return RecipeMapper.transformRecipeToRecipeDTO(savedRecipe);
    }


    @Transactional
    public void deleteRecipe(Long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();

            // Supprime l'image associée via ImageService
            if (recipe.getImageURL() != null) {
                imageService.deleteImageFile(recipe.getImageURL());
            }

            // Supprime la recette
            recipeRepository.delete(recipe);
        }
    }

}
