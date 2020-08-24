package id.privy.repository;

import id.privy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);

    public User findFirstByUsernameAndPassword(String username, String password);

    @Query(value = "select nextval('user-seq')", nativeQuery = true)
    public Integer getUserSequence();
}
