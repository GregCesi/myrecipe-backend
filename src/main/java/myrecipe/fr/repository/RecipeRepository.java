package myrecipe.fr.repository;

import myrecipe.fr.domain.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    public Recipe findByName(String name);

    public void deleteById(Long id);
}
