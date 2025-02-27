package da.chat.user.repository;

import da.chat.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users , Integer> {
    Optional<Users> findByUserName(String userName);
    Optional<Users> findByMail(String mail);
}
