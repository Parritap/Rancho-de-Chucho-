package co.edu.uniquindio.pos_resturant_app.services.implementations;


import co.edu.uniquindio.pos_resturant_app.services.specifications.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ImagesServiceImp implements ImageService {

    private final Cloudinary cloudinary;


    public ImagesServiceImp() {
        var dotenv = Dotenv.load(); // Librería para leer de archivos .env y así no exponer claves de API
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", dotenv.get("cloudinaryName"));
        config.put("api_key", dotenv.get("cloudinaryApiKey"));
        config.put("api_secret", dotenv.get("cloudinaryApiSecret"));
        cloudinary = new Cloudinary(config);
    }

    @Override
    public Map uploadImage(MultipartFile image) throws Exception {
        File file = convertir(image);
        return cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "projectImages"));
    }


    @Override
    public Map deleteImage(String idImage) throws Exception {
        return cloudinary.uploader().destroy(idImage, ObjectUtils.emptyMap());
    }

    @Override
    public byte[] getImage(String id) throws Exception {
        return new byte[0];
    }


    private File convertir(MultipartFile imagen) throws IOException {
        File file = File.createTempFile(Objects.requireNonNull(imagen.getOriginalFilename()), null);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagen.getBytes());
        fos.close();
        return file;
    }


}
