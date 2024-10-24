package myrecipe.fr.domain.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Recipe {

    public Recipe(Long id, String name, String imageURL, List<Ingredient> ingredients, List<Step> steps){
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageURL;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe",  cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Step> steps = new ArrayList<Step>();


}
