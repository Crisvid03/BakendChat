package da.chat.user.controller;

import da.chat.user.model.Users;
import da.chat.user.servise.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") // Rutas comienzan con /users
@CrossOrigin //recibir peticiones del front
// (value = "http://localhost:3000")

public class UserController {
    private  static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Users>> listarUsuarios() {
        List<Users> usuarios = userService.mostrarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Users> obtenerUsuarioPorId(@PathVariable Integer id) {
        Users usuario = userService.flitrarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // Crear o actualizar un usuario
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody Users users) {
        try {
            Users usuarioGuardado = userService.guardarUsuario(users);
            return ResponseEntity.ok(usuarioGuardado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Autenticar usuario
    @PostMapping("/login")
    public ResponseEntity<?> autenticarUsuario(
            @RequestParam String userName,
            @RequestParam String password
    ) {
        try {
            Users usuarioAutenticado = userService.autenticarUsuario(userName, password);
            return ResponseEntity.ok(usuarioAutenticado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
        Users usuario = userService.flitrarUsuarioPorId(id);
        userService.eliminarUsuario(usuario);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}
