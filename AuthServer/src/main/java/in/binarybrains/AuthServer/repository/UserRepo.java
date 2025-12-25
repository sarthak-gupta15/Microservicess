package in.binarybrains.AuthServer.repository;


import in.binarybrains.AuthServer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByName(String username);
}
