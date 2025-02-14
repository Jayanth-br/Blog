package org.web.upload;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class UploadFactory {

    public static String uploadToLocalSystem(MultipartFile file, Path uploadDirectory) throws IOException {
        if(file != null && !file.isEmpty()){
            String uniqueFileName = UUID.randomUUID() + "." + getFileExtension(file.getOriginalFilename());
            Path filePath = uploadDirectory.resolve(uniqueFileName);
            Files.createDirectories(uploadDirectory);
            Files.copy(file.getInputStream(), filePath);
            return filePath.toString();
        }
        return null;
    }

    public static void deleteExistingProfilePicture(String filePath) throws IOException {
        if(filePath != null && !filePath.isEmpty()){
            Files.delete(Paths.get(filePath));
        }
    }

    public static String getFileExtension(String fileName){
        if(fileName == null || fileName.isEmpty()){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
