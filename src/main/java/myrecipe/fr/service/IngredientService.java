package myrecipe.fr.service;

import myrecipe.fr.domain.DTO.IngredientDTO;
import myrecipe.fr.domain.Entity.Ingredient;
import myrecipe.fr.repository.IngredientRepository;
import myrecipe.fr.utils.RecipeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // Mise à jour d'un ingrédient existant
    public Ingredient updateIngredient(IngredientDTO dto) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(dto.getId());

        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();
            ingredient.setName(dto.getName());
            ingredient.setQuantity(dto.getQuantity());
            ingredient.setUnit(dto.getUnit());
            ingredient.setPrice(dto.getPrice());
            return ingredientRepository.save(ingredient);
        } else {
            return addNewIngredient(dto);
        }
    }

    // Ajout d'un nouvel ingrédient
    public Ingredient addNewIngredient(IngredientDTO dto) {
        Ingredient ingredient = RecipeMapper.transformIngredientDTOToIngredient(dto);
        return ingredientRepository.save(ingredient);
    }

    // Suppression des ingrédients non utilisés
    public void removeUnusedIngredients(List<Long> ingredientIds) {
        ingredientRepository.deleteAllById(ingredientIds);
    }
}
