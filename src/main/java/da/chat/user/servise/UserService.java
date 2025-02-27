package da.chat.user.servise;

import  da.chat.user.model.Users;
import java.util.List;

public interface UserService  {

    List<Users> mostrarUsuarios();

    Users flitrarUsuarioPorId(Integer idUser);

    Users guardarUsuario(Users users);

    void eliminarUsuario(Users users);

    Users autenticarUsuario(String userName, String password);


}
