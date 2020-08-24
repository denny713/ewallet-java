package id.privy.repository;

import id.privy.entity.UserBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBalanceHistoryRepository extends JpaRepository<UserBalanceHistory, Integer> {

    public List<UserBalanceHistory> findByIdOrderByUserBalanceIdDesc(Integer id);
}
