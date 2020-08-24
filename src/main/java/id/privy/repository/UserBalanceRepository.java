package id.privy.repository;

import id.privy.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer> {

    @Query(value = "select nextval('user-balance-seq')", nativeQuery = true)
    public Integer getUserBalanceSequence();

    public UserBalance findByUserId(Integer userId);
}
