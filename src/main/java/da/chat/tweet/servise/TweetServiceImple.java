package da.chat.tweet.servise;

import da.chat.tweet.model.Tweet;
import da.chat.tweet.repository.TweetRepository;
import da.chat.user.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        // Crear el tweet usando el constructor con parámetros
        Tweet tweet = new Tweet(contenido, usuario);

        // Guardar el tweet en la base de datos
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
    public void hacerRetweet(Integer idTweet) {
        Tweet tweet = tweetRepository.findById(idTweet)
                .orElseThrow(() -> new RuntimeException("Tweet no encontrado"));
        tweet.setRetweets(tweet.getRetweets() + 1);
        tweetRepository.save(tweet);
    }


}