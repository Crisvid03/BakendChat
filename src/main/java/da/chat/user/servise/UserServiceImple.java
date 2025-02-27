package da.chat.user.servise;

import da.chat.user.model.Users;
import da.chat.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Users> mostrarUsuarios() {
        return userRepository.findAll();
    }

    @Override
    public Users flitrarUsuarioPorId(Integer idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public Users guardarUsuario(Users users) {
        // Validar campos obligatorios
        if (users.getUserName() == null || users.getUserName().isEmpty()) {
            throw new RuntimeException("El nombre de usuario es obligatorio");
        }
        if (users.getMail() == null || users.getMail().isEmpty()) {
            throw new RuntimeException("El correo electrónico es obligatorio");
        }
        if (users.getPassword() == null || users.getPassword().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }

        // Si tiene ID, intenta actualizarlo
        if (users.getIdUser() != null) {
            Optional<Users> usuarioExistente = userRepository.findById(users.getIdUser());
            if (usuarioExistente.isPresent()) {
                return actualizarUsuarioExistente(usuarioExistente.get(), users);
            } else {
                throw new RuntimeException("No se encontró el usuario con ID: " + users.getIdUser());
            }
        }

        // Si no tiene ID, se crea un nuevo usuario
        Optional<Users> usuarioExistentePorNombre = userRepository.findByUserName(users.getUserName());
        if (usuarioExistentePorNombre.isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // Verificar si el correo electrónico ya está en uso
        Optional<Users> usuarioExistentePorCorreo = userRepository.findByMail(users.getMail());
        if (usuarioExistentePorCorreo.isPresent()) {
            throw new RuntimeException("El correo electrónico ya está en uso");
        }

        // Guardar el nuevo usuario
        return userRepository.save(users);
    }

    // Método privado para actualizar un usuario existente
    private Users actualizarUsuarioExistente(Users usuarioExistente, Users nuevosDatos) {
        usuarioExistente.setUserName(nuevosDatos.getUserName());
        usuarioExistente.setMail(nuevosDatos.getMail());
        usuarioExistente.setBiography(nuevosDatos.getBiography());
        usuarioExistente.setPhoto(nuevosDatos.getPhoto());

        // Si se envía una nueva contraseña, se actualiza (sin encriptar)
        if (nuevosDatos.getPassword() != null && !nuevosDatos.getPassword().isEmpty()) {
            usuarioExistente.setPassword(nuevosDatos.getPassword());
        }

        return userRepository.save(usuarioExistente);
    }

    @Override
    public void eliminarUsuario(Users users) {
        userRepository.delete(users);
    }

    @Override
    public Users autenticarUsuario(String userName, String password) {
        Users usuario = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!password.equals(usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }

}