package da.chat.tweet.controller;

import da.chat.tweet.model.Tweet;
import da.chat.tweet.servise.TweetService;
import da.chat.user.model.Users;
import da.chat.user.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Tweet>> obtenerTodosLosTweets() {
        List<Tweet> tweets = tweetService.obtenerTodosLosTweets();
        return ResponseEntity.ok(tweets);
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> crearTweet(
            @RequestParam String contenido,
            @RequestParam Integer idUser
    ) {
        // Validar si el contenido está vacío
        if (contenido == null || contenido.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El contenido del tweet no puede estar vacío");
        }

        try {
            // Obtener el usuario desde la base de datos
            Users usuario = userService.flitrarUsuarioPorId(idUser);

            // Crear el tweet
            Tweet tweet = tweetService.crearTweet(contenido, usuario);
            return ResponseEntity.ok(tweet);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{idUser}")
    public ResponseEntity<List<Tweet>> obtenerTweetsPorUsuario(@PathVariable Integer idUser) {
        List<Tweet> tweets = tweetService.obtenerTweetsPorUsuario(idUser);
        return ResponseEntity.ok(tweets);
    }

    @PostMapping("/{idTweet}/like")
    public ResponseEntity<String> darLikeATweet(@PathVariable Integer idTweet) {
        tweetService.darLikeATweet(idTweet);
        return ResponseEntity.ok("Like agregado al tweet con ID: " + idTweet);
    }

    @PostMapping("/{idTweet}/retweet")
    public ResponseEntity<String> hacerRetweet(@PathVariable Integer idTweet) {
        tweetService.hacerRetweet(idTweet);
        return ResponseEntity.ok("Retweet agregado al tweet con ID: " + idTweet);
    }
}