package com.springboot.springmvc.app.controllers;

import com.springboot.springmvc.app.entities.User;
import com.springboot.springmvc.app.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

// Controlador principal para la gestión de usuarios
@Controller
@RequestMapping("/app")
public class UserController {

    private final UserService service;

    // Inyección de dependencia por constructor
    public UserController(UserService service) {
        this.service = service;
    }

    // Vista de ejemplo con datos estáticos
    @GetMapping({"/look", "/another"})
    public String look(Model model) {
        model.addAttribute("title", "Hola Mundo Spring Boot");
        model.addAttribute("message", "Aplicación de ejemplo usando Spring Boot");
        model.addAttribute("user", new User("Kevin", "Royo"));
        return "view";
    }

    // Lista todos los usuarios registrados
    @GetMapping({"/", "/list"})
    public String list(Model model) {
        model.addAttribute("title", "Estado de usuario");
        model.addAttribute("users", service.findAll());
        return "list";
    }

    // Muestra el formulario para crear un nuevo usuario
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Crear Usuario");
        return "form";
    }

    // Muestra el formulario para editar un usuario existente por id
    @GetMapping("/form/{id}")
    public String form(@PathVariable Long id, Model model) {
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            model.addAttribute("title", "Editar Usuario");
            return "form";
        }
        return "redirect:/app/list";
    }

    // Guarda o actualiza un usuario según si ya tiene id asignado
    @PostMapping("/form")
    public String save(User user, RedirectAttributes redirect) {
        String message;
        if (user.getId() > 0) {
            message = "El usuario " + user.getName() + " se ha actualizado con éxito";
        } else {
            message = "El usuario " + user.getName() + " se ha creado con éxito";
        }
        service.saver(user);
        redirect.addFlashAttribute("success", message);
        return "redirect:/app/list";
    }

    // Elimina un usuario por id si existe
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()) {
            service.remove(id);
            redirect.addFlashAttribute("success", "El usuario " +
                    optionalUser.get().getName() +
                    " se ha eliminado con éxito");
        } else {
            redirect.addFlashAttribute("error", "El usuario con id " +
                    id +
                    " no existe en el sistema");
        }
        return "redirect:/app/list";
    }
}