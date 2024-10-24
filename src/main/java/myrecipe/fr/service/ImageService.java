package myrecipe.fr.service;

import jakarta.transaction.Transactional;
import myrecipe.fr.domain.DTO.RecipeDTO;
import myrecipe.fr.domain.DTO.RecipeListDTO;
import myrecipe.fr.domain.Entity.Recipe;
import myrecipe.fr.domain.Entity.Step;
import myrecipe.fr.repository.RecipeRepository;
import myrecipe.fr.repository.StepRepository;
import myrecipe.fr.utils.RecipeMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    private final String UPLOAD_DIR = "src/main/resources/images/";

    public ImageService() {

    }

    public byte[] getImage(String imageName) throws IOException {

        String imagesDirectory = "images/";

        Resource resource = new ClassPathResource(imagesDirectory + imageName);
        return Files.readAllBytes(Path.of(resource.getURI()));

    }

    public String uploadRecipeImage(MultipartFile imageFile) throws IOException {

        // Générer un nom de fichier unique (TODO : Uniquement UUID + Substract nom du fichier jusquau . pour avoir le format (png jpeg)
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        // Créer le chemin d'accès complet pour enregistrer le fichier
        Path uploadPath = Paths.get(UPLOAD_DIR);
        Path filePath = uploadPath.resolve(fileName);

        // Vérifier si le répertoire d'upload existe, sinon le créer
        Files.createDirectories(uploadPath);

        // Copier le contenu du fichier dans le répertoire d'upload
        FileCopyUtils.copy(imageFile.getInputStream(), Files.newOutputStream(filePath));

        // Construire et retourner l'URL de l'image
        String imageUrl = fileName; // URL relative
        // String imageUrl = "http://localhost:8080/images/" + fileName; // URL absolue

        return imageUrl;
    }

}
