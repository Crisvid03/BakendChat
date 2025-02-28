package da.chat.mensaje.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El ID del usuario emisor no puede ser nulo")
    @Column(name = "id_usuario_emisor", nullable = false)
    private Integer idUsuarioEmisor;

    @NotNull(message = "El ID del usuario receptor no puede ser nulo")
    @Column(name = "id_usuario_receptor", nullable = false)
    private Integer idUsuarioReceptor;

    @NotBlank(message = "El contenido del mensaje no puede estar vacío")
    @Column(name = "contenido", nullable = false, length = 500)
    private String contenido;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false, updatable = false)
    private Date fecha;

    // Constructor sin argumentos (necesario para JPA)
    public Mensaje() {}

    // Constructor con argumentos
    public Mensaje(Integer idUsuarioEmisor, Integer idUsuarioReceptor, String contenido) {
        this.idUsuarioEmisor = idUsuarioEmisor;
        this.idUsuarioReceptor = idUsuarioReceptor;
        this.contenido = contenido;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuarioEmisor() {
        return idUsuarioEmisor;
    }

    public void setIdUsuarioEmisor(Integer idUsuarioEmisor) {
        this.idUsuarioEmisor = idUsuarioEmisor;
    }

    public Integer getIdUsuarioReceptor() {
        return idUsuarioReceptor;
    }

    public void setIdUsuarioReceptor(Integer idUsuarioReceptor) {
        this.idUsuarioReceptor = idUsuarioReceptor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}