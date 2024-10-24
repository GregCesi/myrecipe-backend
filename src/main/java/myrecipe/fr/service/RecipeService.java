package myrecipe.fr.service;

import jakarta.transaction.Transactional;
import myrecipe.fr.domain.DTO.RecipeDTO;
import myrecipe.fr.domain.DTO.RecipeListDTO;
import myrecipe.fr.domain.Entity.Recipe;
import myrecipe.fr.domain.Entity.Step;
import myrecipe.fr.repository.RecipeRepository;
import myrecipe.fr.repository.StepRepository;
import myrecipe.fr.utils.RecipeMapper;
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
import java.util.UUID;

@Service
public class RecipeService {


    private final RecipeRepository recipeRepository;
    private final ImageService imageService;

    private final String UPLOAD_DIR = "src/main/resources/images/";
    private final String SERVER_PATH = "http:://localhost:8080/";

    public RecipeService(RecipeRepository recipeRepository, ImageService imageService) {
        this.recipeRepository = recipeRepository;
        this.imageService = imageService;
    }

    @Transactional
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {

        Recipe recipe = recipeRepository.save(RecipeMapper.transformRecipeDTOToRecipe(recipeDTO));

        List<Step> steps = recipe.getSteps();
        for (Step step : steps) {
            step.setRecipe(recipe);
        }

        return RecipeMapper.transformRecipeToRecipeDTO(recipe) ;
    }

    public List<RecipeListDTO> getAllRecipes(){
        return recipeRepository.findAll().stream().map(RecipeMapper::transformRecipeToRecipeListDTO).toList();
    }

    public RecipeDTO getRecipe(long id) {
        return RecipeMapper.transformRecipeToRecipeDTO(recipeRepository.findById(id).orElseThrow());
    }

    public RecipeDTO updateImageUrlRecipe(String recipeId, MultipartFile imageFile) throws IOException {
        Recipe optionalRecipe = recipeRepository.findById(Long.parseLong(recipeId)).orElseThrow(RuntimeException::new);
        optionalRecipe.setImageURL(imageService.uploadRecipeImage(imageFile));
        Recipe recipe = recipeRepository.save(optionalRecipe);
        return RecipeMapper.transformRecipeToRecipeDTO(recipe);
    }

}
