package id.privy.service;

import id.privy.entity.BankBalance;
import id.privy.entity.UserBalance;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BalanceService {

    public List<UserBalance> getUserBalance();

    public UserBalance getByUser(Integer userId);

    public List<BankBalance> getBankBalance();

    public BankBalance getByUserId(Integer userId);

    public void saveUserBalance(UserBalance userBalance);
}
