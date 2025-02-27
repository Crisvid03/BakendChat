package da.chat.tweet.repository;

import da.chat.tweet.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    // Buscar todos los tweets de un usuario
    List<Tweet> findByUsuarioIdUser(Integer idUser);
}