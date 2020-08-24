package id.privy.repository;

import id.privy.entity.BankBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBalanceRepository extends JpaRepository<BankBalance, Integer> {

    @Query(value = "select nextval('bank-balance-seq')", nativeQuery = true)
    public Integer getBankBalanceSequence();

    public BankBalance findByUserId(Integer userId);
}
