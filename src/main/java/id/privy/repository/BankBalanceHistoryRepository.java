package id.privy.repository;

import id.privy.entity.BankBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBalanceHistoryRepository extends JpaRepository<BankBalanceHistory, Integer> {

    @Query(value = "select nextval('bank-balance-history-seq')", nativeQuery = true)
    public Integer getBankBalanceHistorySequence();
}
