package co.com.orbitec.cliente.servicio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class UploadFileService implements IUploadFileService{
    private final static String UPLOADS_FOLDER="uploads";

    @Override
    public Resource load(String filename) throws MalformedURLException {
      /*  Path pathFoto=getPath(filename);
        Resource recurso = new UrlResource(pathFoto.toUri());

        if(!recurso.exists() || !recurso.isReadable()){
            throw new RuntimeException("Error: no se puede cargar la imagen:" + pathFoto.toString());
        }

        return recurso;*/
        return null;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFileName= UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
        Path rootPath = getPath(uniqueFileName); //ruta relativa al proyecto
      //  Path rootAbsolutPath= rootPath.toAbsolutePath();   //ruta completa ejemplo desde la unidad c:
        log.info("rootPath:"+ rootPath);
        Files.copy(file.getInputStream(), rootPath);

        return uniqueFileName;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();
        if (archivo.exists() && archivo.canRead()) {
            if (archivo.delete()) {
                return true;
            }
        }

        return false;
    }

    public Path getPath(String fileName){


        return Paths.get(UPLOADS_FOLDER).resolve(fileName).toAbsolutePath();

    }

}
