package co.com.orbitec.cliente.web;


import co.com.orbitec.cliente.domain.Persona;
import co.com.orbitec.cliente.servicio.IPersonaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.util.List;


@Controller
@Slf4j
public class ControladorInicio {

    @Autowired
    private IPersonaService iPersonaService;

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
    public String guardar(@Valid Persona persona, Errors result, RedirectAttributes redirectAttrs){
        if(result.hasErrors()){
                return "modificar";
            }

        /*Nota:cuando el objeto traiga un valor en el id no va a crer un nuevo registro
        si no que va actualizar el registro del id*/
        iPersonaService.guardar(persona);
        redirectAttrs
                .addFlashAttribute("mensajeDatoGuardado", "Guardado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/";
    }
    @GetMapping("/editar/{id}")
    public String editar(Persona persona, Model model){
        persona= iPersonaService.encontrarPersona(persona);
        model.addAttribute("persona", persona);
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
    public String eliminar(Persona persona){ //hace el set del id automaticamente
        iPersonaService.eliminar(persona);
        return "redirect:/";
    }
}
