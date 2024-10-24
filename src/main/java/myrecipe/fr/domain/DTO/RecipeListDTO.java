package myrecipe.fr.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeListDTO {

    private Long id;

    private String name;

    private String imageURL;

}
