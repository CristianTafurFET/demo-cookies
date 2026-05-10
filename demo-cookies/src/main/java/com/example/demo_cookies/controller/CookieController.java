package com.example.demo_cookies.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieController {

    // 1. Endpoint para CREAR la cookie
    @GetMapping("/crear-cookie")
    public String crearCookie(HttpServletResponse response) {
        // Instanciamos la cookie con un nombre y un valor
        Cookie cookie = new Cookie("mi_sesion_usuario", "Usuario_JuanPerez_123");

        // Configuraciones de la cookie
        cookie.setMaxAge(7 * 24 * 60 * 60); // Expira en 7 días (el valor está en segundos)
        cookie.setHttpOnly(true); // Seguridad: evita que JavaScript (en el frontend) acceda a la cookie
        cookie.setPath("/"); // Hace que la cookie esté disponible en todas las rutas de la aplicación

        // Añadimos la cookie a la respuesta HTTP
        response.addCookie(cookie);

        return "¡Cookie de sesión creada exitosamente!";
    }

    // 2. Endpoint para LEER la cookie
    @GetMapping("/leer-cookie")
    public String leerCookie(@CookieValue(value = "mi_sesion_usuario", defaultValue = "No hay ninguna sesión activa") String valorSesion) {
        // La anotación @CookieValue busca automáticamente la cookie por su nombre
        return "El valor de tu cookie de sesión es: " + valorSesion;
    }

    // 3. Endpoint para ELIMINAR la cookie
    @GetMapping("/eliminar-cookie")
    public String eliminarCookie(HttpServletResponse response) {
        // Para eliminar una cookie, creamos una con el mismo nombre y valor null
        Cookie cookie = new Cookie("mi_sesion_usuario", null);

        // Lo más importante: establecemos el tiempo de vida (MaxAge) en 0
        cookie.setMaxAge(0);
        cookie.setPath("/"); // Debe coincidir con el path con el que se creó

        // Sobrescribimos la cookie anterior en el navegador del usuario
        response.addCookie(cookie);

        return "¡Cookie de sesión eliminada!";
    }
}