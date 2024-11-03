package myrecipe.fr.controller;

import myrecipe.fr.domain.DTO.RecipeDTO;
import myrecipe.fr.domain.DTO.RecipeListDTO;
import myrecipe.fr.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeListDTO>> getAllRecipes() {
        return ResponseEntity.ok().body(recipeService.getAllRecipes());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable long id) {
        return ResponseEntity.ok().body(recipeService.getRecipe(id));
    }

    // Création d'une recette avec URL de l'image, étapes et ingrédients
    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok().body(this.recipeService.createRecipe(recipeDTO));
    }

    // Mise à jour de l'URL de l'image de la recette
    @PostMapping("/update-image")
    public ResponseEntity<RecipeDTO> updateRecipeImage(@RequestParam String recipeId, @RequestParam String imageUrl) {
        return ResponseEntity.ok().body(this.recipeService.updateImageUrlRecipe(recipeId, imageUrl));
    }

    // Mise à jour d'une recette complète avec un objet RecipeDTO
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable long id, @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok().body(recipeService.updateRecipe(id, recipeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

}
