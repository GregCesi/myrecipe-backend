package myrecipe.fr.controller;

import myrecipe.fr.domain.DTO.RecipeDTO;
import myrecipe.fr.service.ImageService;
import myrecipe.fr.service.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @PostMapping("/recipe")
    public ResponseEntity<RecipeDTO> uploadRecipeImage(@RequestParam("recipeId") String recipeId, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(recipeService.updateImageUrlRecipe(recipeId, imageFile));
    }

    @GetMapping(path = "/{image}")
    public ResponseEntity<byte[]> getImage(@PathVariable String image) throws IOException {

        byte[] imageData = imageService.getImage(image);

        // Vérifier si l'image a été trouvée
        if (imageData != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageData);
        } else {                  
            // Si l'image n'est pas trouvée, retourner une réponse 404 Not Found
            return ResponseEntity.ok().build();
        }
    }
}
