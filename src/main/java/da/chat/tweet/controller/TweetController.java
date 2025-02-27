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
import java.util.Map;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    // Endpoint para obtener todos los tweets
    @GetMapping
    public ResponseEntity<List<Tweet>> obtenerTodosLosTweets() {
        List<Tweet> tweets = tweetService.obtenerTodosLosTweets();
        return ResponseEntity.ok(tweets);
    }

    // Endpoint para crear un tweet
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

    // Endpoint para obtener todos los tweets de un usuario
    @GetMapping("/usuario/{idUser}")
    public ResponseEntity<List<Tweet>> obtenerTweetsPorUsuario(@PathVariable Integer idUser) {
        List<Tweet> tweets = tweetService.obtenerTweetsPorUsuario(idUser);
        return ResponseEntity.ok(tweets);
    }

    // Endpoint para dar like a un tweet
    @PostMapping("/{idTweet}/like")
    public ResponseEntity<String> darLikeATweet(@PathVariable Integer idTweet) {
        tweetService.darLikeATweet(idTweet);
        return ResponseEntity.ok("Like agregado al tweet con ID: " + idTweet);
    }

    // Endpoint para hacer un retweet
    @PostMapping("/retweet")
    public ResponseEntity<?> hacerRetweet(@RequestBody Map<String, Integer> requestBody) {
        try {
            // Obtener los parámetros del cuerpo de la solicitud
            Integer idUser = requestBody.get("idUser");
            Integer idTweet = requestBody.get("idTweet");

            // Validar que los valores no sean nulos
            if (idUser == null || idTweet == null) {
                return ResponseEntity.badRequest().body("Faltan parámetros: idUser y/o idTweet");
            }

            // Buscar usuario
            Users usuario = userService.flitrarUsuarioPorId(idUser);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID: " + idUser);
            }

            // Hacer el retweet
            tweetService.hacerRetweet(idTweet, usuario);
            return ResponseEntity.ok("Retweet agregado correctamente al tweet con ID: " + idTweet);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
