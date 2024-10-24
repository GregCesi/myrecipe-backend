package myrecipe.fr.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myrecipe.fr.domain.Entity.Recipe;
import myrecipe.fr.domain.Unit;

import java.util.List;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {

    private Long id;

    private String name;

    private double quantity;

    private Unit unit;

    private double price;

}
