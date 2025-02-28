package da.chat.mensaje.controller;

import da.chat.mensaje.model.Mensaje;
import da.chat.mensaje.servise.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @PostMapping("/enviar")
    public ResponseEntity<?> enviarMensaje(
            @RequestParam Integer idUsuarioEmisor,
            @RequestParam Integer idUsuarioReceptor,
            @RequestParam String contenido
    ) {
        try {
            Mensaje mensaje = mensajeService.enviarMensaje(idUsuarioEmisor, idUsuarioReceptor, contenido);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    // Nuevo endpoint para obtener mensajes recibidos por un usuario
    @GetMapping("/recibidos/{idUsuarioReceptor}")
    public ResponseEntity<?> obtenerMensajesRecibidos(@PathVariable Integer idUsuarioReceptor) {
        try {
            List<Mensaje> mensajes = mensajeService.obtenerMensajesRecibidos(idUsuarioReceptor);
            return ResponseEntity.ok(mensajes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}