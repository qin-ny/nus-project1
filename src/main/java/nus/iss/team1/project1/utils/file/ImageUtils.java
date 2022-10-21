package nus.iss.team1.project1.utils.file;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.regex.*;

public class ImageUtils {

    public static final String PATH = "/root/files/photos/";
    public static final String CANTEEN_IMAGE_NAME = "canteen-sample";

    private String canteenID;
    private String dishID;
    private String path;

    public ImageUtils(String canteenID, String dishID) {
        this.canteenID = canteenID;
        this.dishID = dishID;
        this.path = System.getenv("IMAGE_PATH")+"/"+canteenID;
    }

    public boolean validateFormat(MultipartFile image) {
        String pattern = ".*(.jpg|.png|.jpeg)$";
        return Pattern.matches(pattern, image.getOriginalFilename());
    }

    public void upload(MultipartFile image) throws Exception {
        FileUtils.upload(image, path, Objects.requireNonNullElse(dishID, CANTEEN_IMAGE_NAME));
    }

    public FileSystemResource getImage() {
        String resourcePath = path;
        resourcePath = resourcePath + "/" + Objects.requireNonNullElse(dishID, CANTEEN_IMAGE_NAME);
        FileSystemResource fileSystemResource = new FileSystemResource(resourcePath);
        if(fileSystemResource.exists()) return fileSystemResource;
        else return null;
    }

    public void delete() {

    }
}
