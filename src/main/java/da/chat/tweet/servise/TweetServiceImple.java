package da.chat.tweet.servise;

import da.chat.tweet.model.Tweet;
import da.chat.tweet.repository.TweetRepository;
import da.chat.user.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TweetServiceImple implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;


    @Override
    public List<Tweet> obtenerTodosLosTweets() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet crearTweet(String contenido, Users usuario) {
        // Validar que el contenido no esté vacío
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new RuntimeException("El contenido del tweet no puede estar vacío");
        }

        // Crear el tweet normal
        Tweet tweet = new Tweet(contenido, usuario);
        tweet.setFechaPublicacion(new Date());
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> obtenerTweetsPorUsuario(Integer idUser) {
        return tweetRepository.findByUsuarioIdUser(idUser);
    }

    @Override
    public void darLikeATweet(Integer idTweet) {
        Tweet tweet = tweetRepository.findById(idTweet)
                .orElseThrow(() -> new RuntimeException("Tweet no encontrado"));
        tweet.setLikes(tweet.getLikes() + 1);
        tweetRepository.save(tweet);
    }

    @Override
    public void hacerRetweet(Integer idTweet, Users usuario) {
        // Obtener el tweet original
        Tweet tweetOriginal = tweetRepository.findById(idTweet)
                .orElseThrow(() -> new RuntimeException("Tweet original no encontrado"));

        // Validar que el usuario no sea el autor del tweet original
        if (tweetOriginal.getUsuario().getIdUser().equals(usuario.getIdUser())) {
            throw new RuntimeException("No puedes retuitear tu propio tweet");
        }

        // Incrementar el contador de retweets del tweet original
        tweetOriginal.setRetweets(tweetOriginal.getRetweets() + 1);
        tweetRepository.save(tweetOriginal);

        // Crear el retweet con el mismo contenido que el original
        Tweet retweet = new Tweet(usuario, idTweet);
        retweet.setContenido(tweetOriginal.getContenido()); // Aquí se copia el contenido
        retweet.setFechaPublicacion(new Date());
        tweetRepository.save(retweet);
    }

}