package myrecipe.fr.domain.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myrecipe.fr.domain.Entity.Ingredient;
import myrecipe.fr.domain.Entity.Step;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {

    private Long id;

    private String name;

    private String imageURL;

    private List<IngredientDTO> ingredients;

    private List<StepDTO> steps;
}
