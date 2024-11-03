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

    public String uploadRecipeImage(MultipartFile imageFile) throws IOException {
        // Génère un nom unique pour le fichier
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);
        Path filePath = uploadPath.resolve(fileName);

        // Crée le répertoire d'upload s'il n'existe pas
        Files.createDirectories(uploadPath);
        FileCopyUtils.copy(imageFile.getInputStream(), Files.newOutputStream(filePath));

        return fileName; // Retourne le nom du fichier
    }

    public byte[] getImage(String imageName) throws IOException {
        Path imagePath = Paths.get(UPLOAD_DIR + imageName);
        if (Files.exists(imagePath)) {
            return Files.readAllBytes(imagePath);
        } else {
            return null;
        }
    }

    public void deleteImageFile(String imageUrl) {
        try {
            Path imagePath = Paths.get(UPLOAD_DIR).resolve(imageUrl).normalize();
            Files.deleteIfExists(imagePath); // Supprime l'image si elle existe
        } catch (IOException e) {
            e.printStackTrace();
            // Vous pouvez ajouter une gestion d’erreur supplémentaire ici
        }
    }

}
