package nus.iss.team1.project1.utils.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /**
     *
     * @param file 文件
     * @param path 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String path, String fileName){
        String realPath = path + "/" + fileName;

        File dest = new File(realPath);


        if(!dest.getParentFile().exists()){
            try {
                Files.createDirectories(Paths.get(dest.getParentFile().toString()));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            Files.write(Paths.get(realPath), file.getBytes(), StandardOpenOption.CREATE);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String path, String fileName){
        String realPath = path + "/" + fileName;

        try {
            Files.deleteIfExists(Paths.get(realPath));
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}