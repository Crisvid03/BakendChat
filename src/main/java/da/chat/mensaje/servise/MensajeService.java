package da.chat.mensaje.servise;

import da.chat.mensaje.model.Mensaje;

import java.util.List;

public interface MensajeService {
    Mensaje enviarMensaje(Integer idUsuarioEmisor, Integer idUsuarioReceptor, String contenido);
    List<Mensaje> obtenerMensajesRecibidos(Integer idUsuarioReceptor);
}