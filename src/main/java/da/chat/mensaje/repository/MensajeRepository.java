package da.chat.mensaje.repository;

import da.chat.mensaje.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
    List<Mensaje> findByIdUsuarioReceptor(Integer idUsuarioReceptor);
}