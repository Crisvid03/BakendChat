package da.chat.tweet.servise;

import da.chat.tweet.model.Tweet;
import da.chat.user.model.Users;

import java.util.List;

public interface TweetService {
    List<Tweet> obtenerTodosLosTweets();
    Tweet crearTweet(String contenido, Users usuario);
    List<Tweet> obtenerTweetsPorUsuario(Integer idUser);
    void darLikeATweet(Integer idTweet);
    void hacerRetweet(Integer idTweet, Users usuario);
}