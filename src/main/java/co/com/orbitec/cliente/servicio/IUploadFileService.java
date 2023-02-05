package co.com.orbitec.cliente.servicio;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadFileService {
    public Resource load(String filename);
    public String copy(MultipartFile file) throws IOException;
    public boolean delete(String filename);


}
