package co.com.orbitec.cliente.web;


import co.com.orbitec.cliente.domain.Persona;
import co.com.orbitec.cliente.servicio.IPersonaService;
import co.com.orbitec.cliente.servicio.IUploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Controller
@Slf4j
public class ControladorInicio {

    @Autowired
    private IPersonaService iPersonaService;
    @Autowired
    private IUploadFileService iUploadFileService;

    private final static String UPLOADS_FOLDER="uploads";

    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user){
  //      List personas = Arrays.asList(persona, persona2);

         List<Persona> personas = iPersonaService.listarPersonas();
        var mensaje = "Mensaje con Thymeleaf";
        log.info("ejecutando el controlador Spring MVC");
        log.info("Usuario que hizo login:"+ user);
        //compartimos la variable enviando llave y valor
      //  model.addAttribute("mensaje", mensaje);

        model.addAttribute("personas", personas);
        double saldoTotal=0; //cero en decimal
        for (var p: personas){
            saldoTotal += p.getSaldo();
        }
        model.addAttribute("saldoTotal", saldoTotal);
        model.addAttribute("totalClientes", personas.size());

        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Persona persona){
        return "modificar";
    }

    @PostMapping("/guardar")

   // @ResponseBody
    public String guardar(@Valid Persona persona, Errors result, @RequestParam("file") MultipartFile foto, RedirectAttributes redirectAttrs){
        if(result.hasErrors()){
                return "modificar";
            }

        if(!foto.isEmpty()){

            if (persona.getId()!=null
                    && persona.getId()>0
                    && persona.getFoto()!=null
                    && persona.getFoto().length()>0){
                    iUploadFileService.delete(persona.getFoto());
            }
            String uniqueFileName=null;

            try {
//                Files.copy(foto.getInputStream(), rootAbsolutPath);
               uniqueFileName= iUploadFileService.copy(foto);


            } catch (IOException e) {
                e.printStackTrace();
            }
            redirectAttrs.addFlashAttribute("mensajeFoto", "foto almacenada correctamente '"+uniqueFileName+"'");
            persona.setFoto(uniqueFileName);


        }

        /*Nota:cuando el objeto traiga un valor en el id no va a crer un nuevo registro
        si no que va actualizar el registro del id*/
        iPersonaService.guardar(persona);
        redirectAttrs
                .addFlashAttribute("mensajeTransaccion", "Guardado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(Persona persona, Model model){
        persona= iPersonaService.encontrarPersona(persona);

        Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(persona.getFoto()).toAbsolutePath();
        String ruta= rootPath.toString();
        System.out.println("rootPath = " + rootPath);
        System.out.println("ruta = " + ruta);
        
        model.addAttribute("persona", persona);
        model.addAttribute("rutaFoto",ruta);
        return "modificar";
    }
    /*
    @GetMapping("/eliminar/{id}")
    public String editar(Persona persona){
        iPersonaService.eliminar(persona);
        return "redirect:/";
    }*/
    //SEGUNDA FORMAR POR QUERY PARAMETER
    @GetMapping("/eliminar")
    public String eliminar(Persona persona, RedirectAttributes redirectAttrs) { //hace el set del id automaticamente


        if (persona.getId() > 0) {
            Persona perEncontrada = iPersonaService.encontrarPersona(persona);
            String nombreFoto= perEncontrada.getFoto();
            iPersonaService.eliminar(persona);

               redirectAttrs
                    .addFlashAttribute("mensajeTransaccion", "Registro del cliente eliminado")
                    .addFlashAttribute("clase", "success");


            if(iUploadFileService.delete(nombreFoto)){
                redirectAttrs.addFlashAttribute("mensajeFoto", "foto "+nombreFoto+" Foto eliminada correctamente ");
            }

        }
        return "redirect:/";
    }

}
