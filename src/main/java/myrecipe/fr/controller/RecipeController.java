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

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok().body(recipeService.createRecipe(recipeDTO));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable long id) {
        return ResponseEntity.ok().body(recipeService.getRecipe(id));
    }



}
