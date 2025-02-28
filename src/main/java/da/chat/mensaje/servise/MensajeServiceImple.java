package da.chat.mensaje.servise;

import da.chat.mensaje.model.Mensaje;
import da.chat.mensaje.repository.MensajeRepository;
import da.chat.user.model.Users;
import da.chat.user.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensajeServiceImple implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UserService userService;

    @Override
    public Mensaje enviarMensaje(Integer idUsuarioEmisor, Integer idUsuarioReceptor, String contenido) {
        // Validar que el usuario emisor y receptor no sean el mismo
        if (idUsuarioEmisor.equals(idUsuarioReceptor)) {
            throw new RuntimeException("No puedes enviarte un mensaje a ti mismo");
        }

        // Validar que el usuario receptor exista
        Users usuarioReceptor = userService.flitrarUsuarioPorId(idUsuarioReceptor);
        if (usuarioReceptor == null) {
            throw new RuntimeException("El usuario receptor no existe");
        }

        // Validar que el contenido no esté vacío
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new RuntimeException("El contenido del mensaje no puede estar vacío");
        }

        // Crear y guardar el mensaje
        Mensaje mensaje = new Mensaje(idUsuarioEmisor, idUsuarioReceptor, contenido);
        return mensajeRepository.save(mensaje);
    }

    @Override
    public List<Mensaje> obtenerMensajesRecibidos(Integer idUsuarioReceptor) {
        // Validar que el usuario receptor exista
        Users usuarioReceptor = userService.flitrarUsuarioPorId(idUsuarioReceptor);
        if (usuarioReceptor == null) {
            throw new RuntimeException("El usuario receptor no existe");
        }

        // Obtener los mensajes recibidos por el usuario
        return mensajeRepository.findByIdUsuarioReceptor(idUsuarioReceptor);
    }
}