package id.privy.repository;

import id.privy.entity.UserBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceHistoryRepository extends JpaRepository<UserBalanceHistory, Integer> {

    @Query(value = "select nextval('user-balance-history-seq')", nativeQuery = true)
    public Integer getUserBalanceHistorySequence();
}
