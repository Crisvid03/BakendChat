package da.chat.tweet.model;

import da.chat.user.model.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@ToString

public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTweet;

    @Column(name = "contenido", nullable = false, length = 280) // Asegúrate de que el nombre coincida con la columna en la base de datos
    private String contenido;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_publicacion", nullable = false, updatable = false) // Asegúrate de que el nombre coincida con la columna en la base de datos
    private Date fechaPublicacion;

    @Column(nullable = true) // Permitir valores nulos
    private Integer retweets;

    @Column(nullable = true) // Permitir valores nulos
    private Integer likes;

    // Relación muchos a uno con la entidad Users
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false) // Asegúrate de que el nombre coincida con la columna en la base de datos
    private Users usuario;

    // Nuevo campo para identificar si es un retweet
    @Column(name = "es_retweet", nullable = false)
    private boolean esRetweet = false;

    // Nuevo campo para almacenar el ID del tweet original (si es un retweet)
    @Column(name = "id_tweet_original", nullable = true)
    private Integer idTweetOriginal;

    // Constructor para tweets normales
    public Tweet(String contenido, Users usuario) {
        this.contenido = contenido;
        this.usuario = usuario;
        this.retweets = 0;
        this.likes = 0;
        this.esRetweet = false;
    }

    // Constructor para retweets
    public Tweet(Users usuario, Integer idTweetOriginal) {
        this.usuario = usuario;
        this.idTweetOriginal = idTweetOriginal;
        this.esRetweet = true;
        this.retweets = 0;
        this.likes = 0;
    }

    // Constructor sin argumentos (necesario para JPA)
    public Tweet() {}

    public boolean isEsRetweet() {
        return esRetweet;
    }

    public void setEsRetweet(boolean esRetweet) {
        this.esRetweet = esRetweet;
    }

    public Integer getIdTweetOriginal() {
        return idTweetOriginal;
    }

    public void setIdTweetOriginal(Integer idTweetOriginal) {
        this.idTweetOriginal = idTweetOriginal;
    }

    public Integer getIdTweet() {
        return idTweet;
    }

    public void setIdTweet(Integer idTweet) {
        this.idTweet = idTweet;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Integer getRetweets() {
        return retweets;
    }

    public void setRetweets(Integer retweets) {
        this.retweets = retweets;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Users getUsuario() {
        return usuario;
    }

    public void setUsuario(Users usuario) {
        this.usuario = usuario;
    }
}


