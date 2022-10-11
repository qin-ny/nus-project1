package nus.iss.team1.project1.utils.file;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;
import java.util.regex.*;

public class ImageUtils {

    public static final String PATH = "/root/files/photos/";

    private String canteenID;
    private String dishID;
    private String path;

    public ImageUtils(String canteenID, String dishID) {
        this.canteenID = canteenID;
        this.dishID = dishID;
        this.path = System.getenv("IMAGE_PATH")+"/"+canteenID;
    }

    public boolean validateFormat(MultipartFile image) {
        String pattern = ".*(.jpg|.png)$";
        return Pattern.matches(pattern, image.getOriginalFilename());
    }

    public void upload(MultipartFile image) throws Exception {
        FileUtils.upload(image, path, dishID);
    }

    public FileSystemResource getImage() {
        return new FileSystemResource(path+"/"+dishID);
    }

    public void delete() {

    }
}
