package co.edu.uniquindio.pos_resturant_app.services.specifications;


import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Map uploadImage(MultipartFile file) throws Exception;
    Map deleteImage(String id) throws Exception;
    byte[] getImage(String id) throws Exception;

}
