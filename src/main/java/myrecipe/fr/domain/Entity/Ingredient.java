package myrecipe.fr.domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myrecipe.fr.domain.Unit;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Ingredient {

    public Ingredient(Long id, String name, double quantity, Unit unit, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double quantity;

    private Unit unit;

    private double price;

    @ManyToMany(mappedBy = "ingredients")
    private List<Recipe> recipes;
}
